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
import com.github.quarter.gui.lib.utils.GInitializationException;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GTextPanel extends GBasic implements IScrollable {

    /** Text offsets on board */
    private int xOffset;
    private int yOffset;
    /** Interval between text lines */
    private int interval;
    /** Has the current text on the textbox */
    private final List<String> text = new ArrayList<>();
    private String title;
    private boolean enableBackgroundDrawing;
    private float scale = 1;
    private float titleScale = 1.5F;
    private boolean wrapContent;

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

    public int getOffset() {
        return this.xOffset;
    }

    /**
     * Adds the text of the text box
     */
    public GTextPanel appendText(String textIn) {
        if (wrapContent) {
            this.text.add(textIn);
            this.growWidth((int) Math.max(0, renderer.getStringWidth(textIn) * scale - getContentWidth()));
            this.growHeight(getLineHeight());
            return this;
        } else {
            return appendText(renderer.listTextToWidth(textIn, this.getMaxStringLength()));
        }
    }

    public GTextPanel appendText(List<String> textIn) {
        this.text.addAll(textIn);
        if (wrapContent) {
            wrapContent();
        }
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
        return (int) (renderer.getFontHeight() * scale) + this.interval;
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
        this.mouseX = mouseXIn;
        this.mouseY = mouseYIn;

        if (enableBackgroundDrawing) {
            StyleMap.current().drawFrame(0, 0, getWidth(), getHeight());
        }

        GL11.glTranslatef(xOffset, yOffset, 0);

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

    /**
     * returns the maximum number of character that can be contained in this textbox
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
    public void update() {}

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
        return (int)((hasTitle() ? renderer.getFontHeight() * getTitleScale() : 0)
                + getLinesCount() * renderer.getFontHeight() * scale + (getLinesCount() - 1) * interval);
    }

    public static class Builder {

        private final GTextPanel instance = new GTextPanel();

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
