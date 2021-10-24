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
import com.github.quarter.gui.lib.utils.ComponentBuilder;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GTextPanel extends GBasic implements IScrollable {

    /** Text offsets on board */
    protected int xOffset;
    protected int yOffset;
    /** Interval between text lines */
    protected int interval;
    /** Has the current text on the textbox */
    private final List<String> text = new ArrayList<>();
    protected String title;
    private int maxStringLength;
    private boolean enableBackgroundDrawing;
    protected float scale = 1;
    /** True if this textbox is visible */
    private boolean visible = true;

    /** Inner variables for easy using */
    protected int mouseX;
    protected int mouseY;

    protected IFontRenderer renderer;
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

    public static class Builder<SELF extends Builder<?, T>, T extends GTextPanel> extends ComponentBuilder<SELF, T> {

        private final GTextPanel instance = new GTextPanel();

        public SELF title(String title) {
            instance().title = title;
            return self();
        }

        public SELF text(String text) {
            instance().setText(text);
            return self();
        }

        public SELF text(List<String> text) {
            instance().setText(text);
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

        public SELF renderer(IFontRenderer renderer) {
            instance().renderer = renderer;
            return self();
        }

        public T build() {
            if (instance().renderer == null) {
                renderer(GuiLib.standardRenderer());
            }
            return super.build();
        }
    }
}
