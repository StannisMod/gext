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

package ru.quarter.gui.lib.components;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.IGraphicsComponentScroll;
import ru.quarter.gui.lib.api.IScrollable;
import ru.quarter.gui.lib.api.adapter.IFontRenderer;
import ru.quarter.gui.lib.utils.GraphicsHelper;
import ru.quarter.gui.lib.utils.StyleMap;

import java.util.ArrayList;
import java.util.List;

public class GTextPanel extends GBasic implements IScrollable {

    /** Text offsets on board */
    private int xOffset;
    private int yOffset;
    /** Interval between text lines */
    private int interval;
    /** Has the current text on the textbox */
    private final List<String> text = new ArrayList<>();
    private String title;
    private int maxStringLength;
    private boolean enableBackgroundDrawing;
    private float scale = 1;
    /** True if this textbox is visible */
    private boolean visible = true;

    /** Inner variables for easy using */
    protected int mouseX;
    protected int mouseY;

    private IFontRenderer renderer;
    /** Scrolling stuff */
    private IGraphicsComponentScroll scrollHandler;
    private int scrolled;

    protected GTextPanel() {}

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
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

    public int getOffset() {
        return this.xOffset;
    }

    /**
     * Adds the text of the text box
     */
    public GTextPanel appendText(String textIn) {
        return appendText(renderer.listTextToWidth(textIn, this.getMaxStringLength()));
    }

    public GTextPanel appendText(List<String> textIn) {
        this.text.addAll(textIn);
        return this;
    }

    /**
     * Appends given string on a new line
     * @param str given text
     */

    public GTextPanel appendString(String str) {
        this.text.addAll(renderer.listTextToWidth(str, this.getMaxStringLength()));
        return this;
    }

    /**
     * Appends given strings each on a new line
     * @param strs given strings
     */

    public GTextPanel appendStrings(String[] strs) {
        for (String str : strs) {
            this.text.addAll(renderer.listTextToWidth(str, this.getMaxStringLength()));
        }
        return this;
    }

    /**
     * Returns the contents of the text box, which are referenced to the content of the text box
     */
    public List<String> getText() {
        return this.text;
    }

    public int getLineHeight() {
        return (int) (renderer.getFontHeight() * scale) + this.interval;
    }

    public int getLinesCount() {
        return this.text.size();
    }

    public boolean hasTitle() {
        return this.title != null && !this.title.equals("");
    }

    /**
     * Draws the text box
     */

    public void draw(int mouseXIn, int mouseYIn) {
        this.mouseX = mouseXIn;
        this.mouseY = mouseYIn;

        if (this.getVisible()) {

            if (enableBackgroundDrawing) {
                StyleMap.current().drawFrame(0, 0, getWidth(), getHeight());
            }

            GL11.glTranslatef(xOffset, yOffset, 0);

            // Draw title

            if (hasTitle()) {
                GraphicsHelper.drawScaledString(renderer, title, 0, 0, scale, 0xffffff);
                GL11.glTranslatef(0.0F, renderer.getFontHeight() * scale, 0.0F);
            }

            // Draw text

            text.forEach(str -> {
                GraphicsHelper.drawScaledString(renderer, str, getX(), 0, scale, 0xffffff);
                GL11.glTranslatef(0.0F, getLineHeight(), 0.0F);
            });
        }
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    /**
     * Sets the maximum length for the normal text in this text box. If the current text is longer than this length, the
     * current text will be trimmed.
     */
    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
    }

    /**
     * returns the maximum number of character that can be contained in this textbox
     */
    public int getMaxStringLength() {
        return (int)(this.maxStringLength / scale);
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
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    /**
     * returns true if this text box is visible
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Sets whether or not this text box is visible
     */
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
    }

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
        return (int)(getLinesCount() * renderer.getFontHeight() * scale + (getLinesCount() - 1) * interval);
    }

    public static class Builder {

        private final GTextPanel instance = new GTextPanel();

        public Builder title(String title) {
            instance.title = title;
            return this;
        }

        public Builder text(String text) {
            instance.setText(text);
            return this;
        }

        public Builder text(List<String> text) {
            instance.setText(text);
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
            if (instance.renderer == null) {
                renderer(GuiLib.standardRenderer());
            }
            return instance;
        }
    }
}
