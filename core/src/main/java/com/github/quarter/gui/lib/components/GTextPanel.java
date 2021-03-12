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
    private int selectionStart;
    private int selectionStartPos;
    private int selectionStartLine;
    private int selectionEnd;
    private int selectionEndPos;
    private int selectionEndLine;

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

            if (selectionEndLine > selectionStartLine) {
                StyleMap.current().drawTextSelection(selectionStart, getLineStart(selectionStartLine), renderer.getStringWidth(text.get(selectionStartLine)) - selectionStart, getTextHeight());
                for (int i = selectionStartLine + 1; i < selectionEndLine; i++) {
                    StyleMap.current().drawTextSelection(0, getLineStart(i), renderer.getStringWidth(text.get(i)), getTextHeight());
                }
                StyleMap.current().drawTextSelection(0, getLineStart(selectionEndLine), selectionEnd, getTextHeight());
            } else {
                StyleMap.current().drawTextSelection(selectionStart, getLineStart(selectionStartLine), selectionEnd - selectionStart, getTextHeight());
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

                this.selectionStart = x - getXOffset();
                this.selectionStartLine = (y - getYOffset()) / getLineHeight();
                int length = renderer.getStringWidth(getText().get(selectionStartLine));
                if (selectionStart > length) {
                    selectionStart = length;
                }

                // TODO Optimize
                String line = getText().get(selectionStartLine);
                for (selectionStartPos = 0; renderer.getStringWidth(line.substring(0, selectionStartPos)) < selectionStart && selectionStartPos < line.length(); selectionStartPos++);
                selectionStart = renderer.getStringWidth(line.substring(0, selectionStartPos));

                this.updateCursor(selectionStartLine, selectionStartPos);

                this.selectionEnd = selectionStart;
                this.selectionEndLine = selectionStartLine;
            } else if (k != -1) {
                this.eventButton = -1;
                if (selectionStart == selectionEnd && selectionStartLine == selectionEndLine) {
                    selectionStart = 0;
                    selectionStartLine = 0;
                    selectionEnd = 0;
                    selectionEndLine = 0;
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

                this.updateCursor(selectionLine, selectionPos);

                if (selectionLine < selectionStartLine || (selectionLine == selectionStartLine && selection <= selectionStart)) {
                    selectionStartLine = selectionLine;
                    selectionStart = selection;
                    selectionStartPos = selectionPos;
                } else {
                    selectionEndLine = selectionLine;
                    selectionEnd = selection;
                    selectionEndPos = selectionPos;
                }

                /*
                if (selectionEndLine < selectionStartLine) {
                    int tmp = selectionStartLine;
                    selectionStartLine = selectionEndLine;
                    selectionEndLine = tmp;

                    tmp = selectionStart;
                    selectionStart = selectionEnd;
                    selectionEnd = tmp;

                    tmp = selectionStartPos;
                    selectionStartPos = selectionEndPos;
                    selectionEndPos = tmp;
                } else if (selectionStartLine == selectionEndLine && selectionEnd < selectionStart) {
                    int tmp = selectionStart;
                    selectionStart = selectionEnd;
                    selectionEnd = tmp;
                }*/
            }
        }
    }

    protected void updateCursor(int selectionLine, int selectionPos) {
        this.cursorXPos = selectionPos;
        this.cursorYPos = selectionLine;
        this.recalculateCursorFromPos();
    }

    protected void recalculateCursorFromPos() {
        this.cursorX = renderer.getStringWidth(getText().get(cursorYPos).substring(0, cursorXPos));
        this.cursorY = getLineStart(cursorYPos);
    }

    public String getSelectedText() {
        if (selectionStartLine == selectionEndLine) {
            return getText().get(selectionStartLine).substring(selectionStartPos, selectionEndPos);
        }
        return getText().get(selectionStartLine).substring(selectionStartPos)
                + String.join("\n", getText().subList(Math.min(selectionStartLine + 1, selectionEndLine), Math.max(selectionEndLine - 1, selectionStartLine)))
                + getText().get(selectionEndLine).substring(0, selectionEndPos);
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
