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

package com.github.quarter.gui.lib.components.text;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.api.IGraphicsComponentScroll;
import com.github.quarter.gui.lib.api.IScrollable;
import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.components.GBasic;
import com.github.quarter.gui.lib.utils.ComponentBuilder;
import com.github.quarter.gui.lib.utils.GInitializationException;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GTextPanel extends GBasic implements IScrollable {

    // Text offsets on board
    protected int xOffset;
    protected int yOffset;

    /** Interval between text lines */
    protected int interval;
    private final List<String> text = new ArrayList<>();
    protected float scale = 1;
    protected String title;
    private float titleScale = 1.5F;

    protected boolean enableBackgroundDrawing;
    protected boolean wrapContent;
    protected int maxLines;

    // for selection
    protected final Selection selection = new Selection();

    private int eventButton = -1;

    // cursor
    protected final Cursor cursor = new Cursor();
    private boolean hasFocus;

    protected IFontRenderer renderer;

    // scrolling stuff
    private IGraphicsComponentScroll scrollHandler;
    private int scrolled;

    protected GTextPanel() {}

    public int getMaxLines() {
        return maxLines;
    }

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

    public boolean canAppendTo(String text, int line) {
        if (line < 0 || line >= getLinesCount()) {
            return false;
        }
        if (wrapContent || line > getLinesCount()) {
            return true;
        }
        int need = renderer.getStringWidth(text);
        int provided = getMaxStringLength() - renderer.getStringWidth(getText().get(line)) + (getMaxLines() - getLinesCount()) * getMaxStringLength();
        return provided >= need;
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
                newLine = old.substring(0, pos) + textIn + old.substring(pos);
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

    @Override
    public void markDirty() {
        super.markDirty();
        if (text.size() > maxLines) {
            int clip = text.size() - maxLines;
            for (int i = clip - 1; i >= 0; i--) {
                text.remove(maxLines + i);
            }
            if (cursor.yPos() > maxLines - 1) {
                cursor.setYPos(maxLines - 1);
                cursor.setXPos(renderer.getStringWidth(text.get(maxLines - 1)) - 1);
                recalculateCursorFromPos();
            }
        }
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

    public String getMergedText() {
        return String.join(System.lineSeparator(), getText());
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

    public boolean hasFocus() {
        return hasFocus;
    }

    protected void setFocus(boolean focus) {
        hasFocus = focus;
        if (!focus) {
            selection.drop();
        }
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

    @Override
    public void draw(int mouseXIn, int mouseYIn) {
        if (enableBackgroundDrawing) {
            StyleMap.current().drawFrame(0, 0, getWidth(), getHeight());
        }

        GL11.glTranslatef(xOffset, yOffset, 0);

        // Draw selection

        if (selection.isEnabled() && hasFocus()) {
            GL11.glColor4f(0.0F, 0.0F, 1.0F, 1.0F);

            if (selection.endYPos() > selection.startYPos()) {
                StyleMap.current().drawTextSelection(selection.startX(), getLineStart(selection.startYPos()), renderer.getStringWidth(text.get(selection.startYPos())) - selection.startX(), getTextHeight());
                for (int i = selection.startYPos() + 1; i < selection.endYPos(); i++) {
                    StyleMap.current().drawTextSelection(0, getLineStart(i), renderer.getStringWidth(text.get(i)), getTextHeight());
                }
                StyleMap.current().drawTextSelection(0, getLineStart(selection.endYPos()), selection.endX(), getTextHeight());
            } else {
                StyleMap.current().drawTextSelection(selection.startX(), getLineStart(selection.startYPos()), selection.endX() - selection.startX(), getTextHeight());
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

    protected int getLineClicked(int yClicked) {
        int clickedLine = (yClicked - getYOffset()) / getLineHeight();
        if (clickedLine > getLinesCount() - 1) {
            clickedLine = getLinesCount() - 1;
        }
        return clickedLine;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        int x = mouseX;
        int y = mouseY;

        if (mouseButton != -1) {
            if (intersectsInner(x, y)) {
                if (!hasFocus()) {
                    setFocus(true);
                }
            } else {
                setFocus(false);
                return;
            }
        }

        if (!selection.isEnabled() || getText().isEmpty()) {
            return;
        }

        if (x < getXOffset()) {
            x = getXOffset();
        }

        if (y < getTextStart()) {
            y = getTextStart();
        }

        y -= getTextStart();

        this.eventButton = mouseButton;

        selection.moveTo(cursor);

        this.selection.setStartX(x - getXOffset());
        this.selection.setStartYPos(getLineClicked(y));
        int length = renderer.getStringWidth(getText().get(selection.startYPos()));
        if (selection.startX() > length) {
            selection.setStartX(length);
        }

        // TODO Optimize?
        String line = getText().get(selection.startYPos());
        for (selection.setStartXPos(0); renderer.getStringWidth(line.substring(0, selection.startXPos())) < selection.startX() && selection.startXPos() < line.length(); selection.setStartXPos(selection.startXPos() + 1));
        selection.setStartX(renderer.getStringWidth(line.substring(0, selection.startXPos())));

        this.updateCursor(selection.startYPos(), selection.startXPos());

        this.selection.endToStart();
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!selection.isEnabled() || getText().isEmpty()) {
            return;
        }

        this.eventButton = -1;
        if (selection.isEmpty()) {
            selection.drop();
        }
    }

    @Override
    public void onMouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double xAmount, final double yAmount) {
        super.onMouseDragged(mouseX, mouseY, mouseButton, xAmount, yAmount);

        int x = (int) mouseX;
        int y = (int) mouseY;

        if (x < getXOffset()) {
            x = getXOffset();
        }

        if (y < getTextStart()) {
            y = getTextStart();
        }

        y -= getTextStart();

        int selection = x - getXOffset();
        int selectionLine = getLineClicked(y);
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

    @Override
    public void onMouseMoved(final int mouseX, final int mouseY) {
        super.onMouseMoved(mouseX, mouseY);
    }

    @Override
    public void onMouseScrolled(final int mouseX, final int mouseY, final double amountScrolled) {
        super.onMouseScrolled(mouseX, mouseY, amountScrolled);
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
        if (selectionPos > renderer.getStringWidth(getText().get(selectionLine))) {
            selectionPos = renderer.getStringWidth(getText().get(selectionLine));
        }

        this.cursor.setXPos(selectionPos);
        this.cursor.setYPos(selectionLine);
        this.recalculateCursorFromPos();
        if (updateSelection) {
            selection.updateFrom(cursor);
        }
    }

    protected void recalculateCursorFromPos() {
        cursor.setX(renderer.getStringWidth(getText().get(cursor.yPos()).substring(0, cursor.xPos())));
        cursor.setY(getLineStart(cursor.yPos()));
    }

    public String getSelectedText() {
        if (selection.startYPos() == selection.endYPos()) {
            return getText().get(selection.startYPos()).substring(selection.startXPos(), selection.endXPos());
        }
        return getText().get(selection.startYPos()).substring(selection.startXPos())
                + String.join(System.lineSeparator(), getText().subList(Math.min(selection.startYPos() + 1, selection.endYPos()), Math.max(selection.endYPos() - 1, selection.startYPos())))
                + getText().get(selection.endYPos()).substring(0, selection.endXPos());
    }

    /**
     * returns the maximum number of character that can be contained in this text box
     */
    public int getMaxStringLength() {
        return (int)((this.getWidth() - xOffset * 2) / scale);
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
        if (getText().isEmpty()) {
            getText().add("");
        }
    }

    @Override
    public void init() {
        markDirty();
    }

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

    public static class Builder<SELF extends ComponentBuilder<?, T>, T extends GTextPanel> extends ComponentBuilder<SELF, T> {

        public SELF title(String title) {
            instance().title = title;
            return self();
        }

        private void checkOrInitRenderer() {
            if (instance().renderer == null) {
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
        public SELF text(String text) {
            if (instance().getWidth() == 0) {
                throw new GInitializationException("Setting text to null-sized text panel");
            }
            checkOrInitRenderer();
            instance().setText(text);
            return self();
        }

        /**
         * Fills the panel with containing text AS IS, WITHOUT resize
         */
        public SELF text(List<String> text) {
            checkOrInitRenderer();
            instance().setText(text);
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
        public SELF wrap() {
            checkOrInitRenderer();
            instance().wrapContent();
            instance().wrapContent = true;
            return self();
        }

        public SELF scale(float scale) {
            instance().scale = scale;
            return self();
        }

        public SELF offsets(int xOffset, int yOffset) {
            instance().xOffset = xOffset;
            instance().yOffset = yOffset;
            return self();
        }

        public SELF enableBackground() {
            instance().setEnableBackgroundDrawing(true);
            return self();
        }

        public SELF interval(int interval) {
            instance().interval = interval;
            return self();
        }

        public SELF enableSelection() {
            instance().selection.setEnabled(true);
            return self();
        }

        public SELF renderer(IFontRenderer renderer) {
            instance().renderer = renderer;
            return self();
        }

        public SELF size(int width, int height) {
            super.size(width, height);
            if (!instance().wrapContent) {
                checkOrInitRenderer();
                instance().maxLines = height / instance().getLineHeight();
            }
            return self();
        }

        public T build() {
            checkOrInitRenderer();
            if (instance().getText().isEmpty()) {
                instance().getText().add("");
            }
            return super.build();
        }
    }
}
