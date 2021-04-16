/*
 * Copyright 2020 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.quarter.gui.lib.components;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.api.IGraphicsComponentScroll;
import com.github.quarter.gui.lib.api.IScrollable;
import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.api.adapter.IScaledResolution;
import com.github.quarter.gui.lib.utils.GInitializationException;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A code-driven text panel. Contents of the box can be updated
 * from source code, not by user interface. On the user view,
 * it is fully immutable.
 *
 * This instance supports system copy-paste buffer.
 */
public class GTextPanel extends GBasic implements IScrollable {

    // Text offsets on board
    private int xOffset;
    private int yOffset;

    /** Interval between text lines */
    private int interval;
    private final List<String> text = new ArrayList<>();
    private float scale = 1;
    private String title;
    private float titleScale = 1.5F;

    private boolean enableBackgroundDrawing;
    private boolean wrapContent;

    // for selection
    private boolean selectionEnabled;
    private int selectionStartX;
    private int selectionStartY;
    private int selectionStartXPos;
    private int selectionStartYPos;
    private int selectionEndX;
    private int selectionEndY;
    private int selectionEndXPos;
    private int selectionEndYPos;

    private int eventButton = -1;

    // cursor
    protected int cursorXPos;
    protected int cursorYPos;
    protected int cursorX;
    protected int cursorY;

    protected IFontRenderer renderer;

    // scrolling stuff
    private IGraphicsComponentScroll scrollHandler;
    private int scrolled;

    protected GTextPanel() {}

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getTitleScale() {
        return titleScale * getScale();
    }

    public void setTitleScale(float titleScale) {
        this.titleScale = titleScale;
    }

    public GTextPanel setTitle(String title) {
        this.title = title;
        return this;
    }

    public GTextPanel setText(String textIn) {
        this.text.clear();
        return appendText(textIn);
    }

    public GTextPanel setText(List<String> textIn) {
        this.text.clear();
        return appendText(textIn);
    }

    public GTextPanel clear() {
        this.text.clear();
        return this;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public GTextPanel appendText(String text) {
        return putText(getLinesCount(), 0, text);
    }

    /**
     * Append line without any recalculation
     */
    public void appendToLine(int line, int pos, String text) {
        String old = getText().get(line);
        getText().set(line, old.substring(0, pos) + text + old.substring(pos));
    }

    public void appendToLine(int line, String text) {
        appendToLine(line, 0, text);
    }

    /**
     *
     * @param line the line that text should be put. If {@code line > #getLinesCount()}, text should be appended
     * @param pos the position in the line
     * @param textIn the text that should be appended
     * @return this
     */
    public GTextPanel putText(int line, int pos, String textIn) {
        String newLine = textIn;
        if (wrapContent) {
            if (line < getLinesCount()) {
                String old = getText().get(line);
                newLine = old.substring(0, pos) + newLine + old.substring(pos);
                this.text.set(line, newLine);
            } else {
                this.text.add(newLine);
            }
            this.growWidth((int) Math.max(0, renderer.getStringWidth(newLine) * scale - getContentWidth()));
            this.growHeight(getLineHeight());
            return this;
        } else {
            if (line < getLinesCount()) {
                String old = getText().get(line);
                getText().remove(line);
                newLine = old.substring(0, pos) + text + old.substring(pos);
            }
            return putText(line, renderer.listTextToWidth(newLine, this.getMaxStringLength()));
        }
    }

    public GTextPanel appendText(List<String> textIn) {
        return putText(getLinesCount(), textIn);
    }

    public GTextPanel putText(int line, List<String> textIn) {
        this.text.addAll(line, textIn);
        this.markDirty();
        return this;
    }

    public GTextPanel appendText(String[] textIn) {
        return appendText(Arrays.stream(textIn).collect(Collectors.toList()));
    }

    /**
     * Returns the contents of the text box, which are referenced to the content of the text box
     */
    public List<String> getText() {
        return this.text;
    }

    public int getLineHeight() {
        return getTextHeight() + this.interval;
    }

    public int getTextStart() {
        return (int)(hasTitle() ? renderer.getFontHeight() * getTitleScale() : 0) + getYOffset();
    }

    public int getTextHeight() {
        return (int) (renderer.getFontHeight() * scale);
    }

    public int getLinesCount() {
        return this.text.size();
    }

    public boolean hasTitle() {
        return this.title != null && !this.title.isEmpty();
    }

    public void wrapContent() {
        int width = 0;
        for (String s : getText()) {
            width = Math.max(width, renderer.getStringWidth(s));
        }

        this.setWidth(width + xOffset * 2);
        this.setHeight(getContentHeight() + yOffset * 2);
    }

    /**
     * Draws the text box
     */

    public void draw(int mouseXIn, int mouseYIn) {
        if (enableBackgroundDrawing) {
            StyleMap.current().drawFrame(0, 0, getWidth(), getHeight());
        }

        GL11.glTranslatef(xOffset, yOffset, 0);

        // Draw selection

        if (selectionEnabled) {
            GL11.glColor4f(0.0F, 0.0F, 1.0F, 1.0F);

            if (selectionEndYPos > selectionStartYPos) {
                StyleMap.current().drawTextSelection(selectionStartX, getLineStart(selectionStartYPos), renderer.getStringWidth(text.get(selectionStartYPos)) - selectionStartX, getTextHeight());
                for (int i = selectionStartYPos + 1; i < selectionEndYPos; i++) {
                    StyleMap.current().drawTextSelection(0, getLineStart(i), renderer.getStringWidth(text.get(i)), getTextHeight());
                }
                StyleMap.current().drawTextSelection(0, getLineStart(selectionEndYPos), selectionEndX, getTextHeight());
            } else {
                StyleMap.current().drawTextSelection(selectionStartX, getLineStart(selectionStartYPos), selectionEndX - selectionStartX, getTextHeight());
            }
        }

        // Draw title

        if (hasTitle()) {
            GraphicsHelper.drawScaledString(renderer, title, 0, 0, getTitleScale(), 0xffffff);
            GL11.glTranslatef(0.0F, renderer.getFontHeight() * getTitleScale(), 0.0F);
        }

        // Draw text

        text.forEach(str -> {
            GraphicsHelper.drawScaledString(renderer, str, 0, 0, scale, 0xffffff);
            GL11.glTranslatef(0.0F, getLineHeight(), 0.0F);
        });
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    @Override
    public void onMouseInput() {
        super.onMouseInput();
        if (selectionEnabled) {
            IScaledResolution res = GuiLib.scaled();
            int x = Mouse.getEventX() / res.getScaleFactor() - getX();
            int y = (res.getViewHeight() - Mouse.getEventY()) / res.getScaleFactor() - getY();
            int k = Mouse.getEventButton();

            if (x < getXOffset()) {
                x = getXOffset();
            }

            if (y < getTextStart()) {
                y = getTextStart();
            }

            y -= getTextStart();

            if (Mouse.getEventButtonState()) {
                this.eventButton = k;

                this.selectionStartX = x - getXOffset();
                this.selectionStartYPos = (y - getYOffset()) / getLineHeight();
                int length = renderer.getStringWidth(getText().get(selectionStartYPos));
                if (selectionStartX > length) {
                    selectionStartX = length;
                }

                // TODO Optimize
                String line = getText().get(selectionStartYPos);
                for (selectionStartXPos = 0; renderer.getStringWidth(line.substring(0, selectionStartXPos)) < selectionStartX && selectionStartXPos < line.length(); selectionStartXPos++);
                selectionStartX = renderer.getStringWidth(line.substring(0, selectionStartXPos));

                this.updateCursor(selectionStartYPos, selectionStartXPos);

                this.selectionEndX = selectionStartX;
                this.selectionEndYPos = selectionStartYPos;
            } else if (k != -1) {
                this.eventButton = -1;
                if (selectionStartX == selectionEndX && selectionStartYPos == selectionEndYPos) {
                    selectionStartX = 0;
                    selectionStartYPos = 0;
                    selectionEndX = 0;
                    selectionEndYPos = 0;
                }
            } else if (this.eventButton != -1) {
                // drag

                int selection = x - getXOffset();
                int selectionLine = (y - getYOffset()) / getLineHeight();
                int selectionPos;

                int length = renderer.getStringWidth(getText().get(selectionLine));
                if (selectionLine > length) {
                    selectionLine = length;
                }

                // TODO Optimize
                String line = getText().get(selectionLine);
                for (selectionPos = 0; renderer.getStringWidth(line.substring(0, selectionPos)) < selection && selectionPos < line.length(); selectionPos++);
                selection = renderer.getStringWidth(line.substring(0, selectionPos));

                this.updateCursor(selectionLine, selectionPos, true);
            }
        }
    }

    protected void updateCursor(int selectionLine, int selectionPos) {
        this.updateCursor(selectionLine, selectionPos, false);
    }

    protected void updateCursor(int selectionLine, int selectionPos, boolean updateSelection) {
        if (selectionLine < 0) {
            selectionLine = 0;
        }
        if (selectionLine > getLinesCount() - 1) {
            selectionLine = getLinesCount() - 1;
        }
        if (selectionPos < 0) {
            selectionPos = 0;
        }
        if (selectionPos > renderer.getStringWidth(getText().get(selectionLine)) - 1) {
            selectionPos = renderer.getStringWidth(getText().get(selectionLine)) - 1;
        }

        this.cursorXPos = selectionPos;
        this.cursorYPos = selectionLine;
        this.recalculateCursorFromPos();
        if (updateSelection) {
            updateSelectionFromCursor();
        }
    }

    private boolean rightTrapped;

    protected void updateSelectionFromCursor() {
        /*
        if (selectionStartYPos == selectionEndYPos && selectionStartYPos == 0) {
            selectionStartYPos = selectionEndYPos = cursorYPos;
            selectionStartXPos = selectionEndXPos = cursorXPos;
            return;
        }*/
        if (selectionEndYPos < cursorYPos) {
            if (!rightTrapped) {
                startToEndSelection();
            }
            updateSelectionEndFromCursor();
        } else if (selectionEndYPos == cursorYPos) {
            if (selectionStartYPos == selectionEndYPos) {
                // they all are on the same line
                if (selectionStartXPos < cursorXPos) {
                    if (cursorXPos < selectionEndXPos) {
                        if (rightTrapped) {
                            updateSelectionEndFromCursor();
                        } else {
                            updateSelectionStartFromCursor();
                        }
                    } else {
                        updateSelectionEndFromCursor();
                    }
                } else {
                    updateSelectionStartFromCursor();
                }
            } else if (selectionStartYPos < selectionEndYPos) {
                updateSelectionEndFromCursor();
            }
        } else if (selectionStartYPos < cursorYPos) {
            // now we have cursorYPos < selectionEndPos
            if (rightTrapped) {
                updateSelectionEndFromCursor();
            } else {
                updateSelectionStartFromCursor();
            }
        } else if (cursorYPos <= selectionStartYPos) {
            if (rightTrapped) {
                endToStartSelection();
            }
            updateSelectionStartFromCursor();
        } else {
            // now we have cursorYPos == selectionStartYPos
            // variant selectionStartYPos == selectionEndYPos was been handler earlier
            System.out.println("Other variant!!!");
        }
    }

    private void updateSelectionStartFromCursor() {
        selectionStartXPos = cursorXPos;
        selectionStartYPos = cursorYPos;
        selectionStartX = cursorX;
        selectionStartY = cursorY;
        rightTrapped = false;
    }

    private void updateSelectionEndFromCursor() {
        selectionEndXPos = cursorXPos;
        selectionEndYPos = cursorYPos;
        selectionEndX = cursorX;
        selectionEndY = cursorY;
        rightTrapped = true;
    }

    private void startToEndSelection() {
        selectionStartYPos = selectionEndYPos;
        selectionStartXPos = selectionEndXPos;
        selectionStartY = selectionEndY;
        selectionStartX = selectionEndX;
    }

    private void endToStartSelection() {
        selectionEndYPos = selectionStartYPos;
        selectionEndXPos = selectionStartXPos;
        selectionEndY = selectionStartY;
        selectionEndX = selectionStartX;
    }

    protected void recalculateCursorFromPos() {
        this.cursorX = renderer.getStringWidth(getText().get(cursorYPos).substring(0, cursorXPos));
        this.cursorY = getLineStart(cursorYPos);
    }

    public String getSelectedText() {
        if (selectionStartYPos == selectionEndYPos) {
            return getText().get(selectionStartYPos).substring(selectionStartXPos, selectionEndXPos);
        }
        return getText().get(selectionStartYPos).substring(selectionStartXPos)
                + String.join("\n", getText().subList(Math.min(selectionStartYPos + 1, selectionEndYPos), Math.max(selectionEndYPos - 1, selectionStartYPos)))
                + getText().get(selectionEndYPos).substring(0, selectionEndXPos);
    }

    /**
     * returns the maximum number of character that can be contained in this text box
     */
    public int getMaxStringLength() {
        return (int)((this.getWidth() - xOffset * 2) / scale);
    }

    /**
     * Gets whether the background and outline of this text box should be drawn (true if so).
     */
    public boolean getEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing;
    }

    /**
     * Sets whether or not the background and outline of this text box should be drawn.
     */
    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawingIn) {
        this.enableBackgroundDrawing = enableBackgroundDrawingIn;
    }

    @Override
    public boolean checkUpdates() {
        return false;
    }

    @Override
    public void update() {
        if (wrapContent) {
            wrapContent();
        }
    }

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void setScrollHandler(IGraphicsComponentScroll handler) {
        this.scrollHandler = handler;
    }

    @Override
    public IGraphicsComponentScroll getScrollHandler() {
        return scrollHandler;
    }

    @Override
    public int getScrollVertical() {
        return scrolled;
    }

    @Override
    public int getScrollHorizontal() {
        throw new UnsupportedOperationException("TextPanel does not support horizontal scrolling!");
    }

    @Override
    public void setScrollVertical(int value) {
        scrolled = value;
    }

    @Override
    public void setScrollHorizontal(int value) {
        throw new UnsupportedOperationException("TextPanel does not support horizontal scrolling!");
    }

    @Override
    public int getContentWidth() {
        return getMaxStringLength();
    }

    @Override
    public int getContentHeight() {
        return getContentHeight(getLinesCount());
    }

    public int getContentHeight(int lines) {
        return (int)((hasTitle() ? renderer.getFontHeight() * getTitleScale() : 0)
                + lines * renderer.getFontHeight() * scale + Math.max(0, lines - 1) * interval);
    }

    public int getLineStart(int line) {
        return getContentHeight(line) + (line == 0 ? 0 : interval);
    }

    public static class Builder {

        protected GTextPanel instance = new GTextPanel();

        public Builder title(String title) {
            instance.title = title;
            return this;
        }

        private void checkOrInitRenderer() {
            if (instance.renderer == null) {
                renderer(GuiLib.standardRenderer());
            }
        }

        /**
         * Fills the panel with containing text
         *
         * <p>Text from given string would be wrapped to panel size, so
         * this should be called AFTER setting not-null size</p>
         * @param text given text
         */
        public Builder text(String text) {
            if (instance.getWidth() == 0) {
                throw new GInitializationException("Setting text to null-sized text panel");
            }
            checkOrInitRenderer();
            instance.setText(text);
            return this;
        }

        /**
         * Fills the panel with containing text AS IS, WITHOUT resize
         */
        public Builder text(List<String> text) {
            checkOrInitRenderer();
            instance.setText(text);
            return wrap();
        }

        /**
         * Wraps the panel size to content size
         *
         * <p>This method should compute the dimension bounds
         * of the rendered text and set it into panel size</p>
         *
         * <p>This computation executes once in init-time, so
         * should be called AFTER setting all text properties</p>
         *
         * <p>All text adding calls until this moment should
         * initiate the panel size growth</p>
         */
        public Builder wrap() {
            checkOrInitRenderer();
            instance.wrapContent();
            instance.wrapContent = true;
            return this;
        }

        public Builder scale(float scale) {
            instance.scale = scale;
            return this;
        }

        public Builder offsets(int xOffset, int yOffset) {
            instance.xOffset = xOffset;
            instance.yOffset = yOffset;
            return this;
        }

        public Builder enableBackground() {
            instance.setEnableBackgroundDrawing(true);
            return this;
        }

        public Builder interval(int interval) {
            instance.interval = interval;
            return this;
        }

        public Builder enableSelection() {
            instance.selectionEnabled = true;
            return this;
        }

        public Builder renderer(IFontRenderer renderer) {
            instance.renderer = renderer;
            return this;
        }

        public Builder size(int width, int height) {
            instance.setWidth(width);
            instance.setHeight(height);
            return this;
        }

        public Builder placeAt(int x, int y) {
            instance.setX(x);
            instance.setY(y);
            return this;
        }

        public GTextPanel build() {
            checkOrInitRenderer();
            return instance;
        }
    }
}
