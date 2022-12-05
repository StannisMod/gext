/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.api.adapter.IFontRenderer;
import com.github.stannismod.gext.utils.*;

import java.awt.*;
import java.util.List;

public class GLabel extends GBasic {

    protected String text = "";
    protected int color;
    protected IFontRenderer fontRenderer;
    protected float scale;
    protected boolean centered;

    public GLabel(final int x, final int y, final boolean clippingEnabled,
                  final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                  final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                  final List<IListener> listeners, final String text, final int color, final IFontRenderer fontRenderer,
                  final float scale, final boolean centered) {
        super(x, y, 0, 0, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners);
        this.setClippingEnabled(false);
        this.centered = centered;
        this.scale = scale;
        this.color = color;
        this.fontRenderer = fontRenderer;
        this.setText(text);
    }

    public void setText(String text) {
        if (centered) {
            this.shiftX((fontRenderer.getStringWidth(this.text) - fontRenderer.getStringWidth(text)) / 2);
        }
        this.text = text;
        this.setWidth((int)(this.fontRenderer.getStringWidth(this.text) * scale));
        this.setHeight((int)(this.fontRenderer.getFontHeight() * scale));
    }

    public String getText() {
        return text;
    }

    public boolean isCentered() {
        return centered;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        GraphicsHelper.drawScaledString(fontRenderer, text, 0, 0, scale, color);
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GLabel> extends ComponentBuilder<SELF, T> {

        protected String text;
        protected int color;
        protected IFontRenderer fontRenderer = GExt.standardRenderer();
        protected float scale;
        protected boolean centered;

        public SELF renderer(IFontRenderer fontRenderer) {
            if (fontRenderer == null) {
                throw new GInitializationException("FontRenderer instance mustn't be null");
            }
            this.fontRenderer = fontRenderer;
            return self();
        }

        public SELF text(String text) {
            return text(text, Color.BLACK.getRGB());
        }

        public SELF text(String text, int color) {
            if (text == null) {
                throw new GInitializationException("Given text mustn't be null");
            }
            this.text = text;
            this.color = color;
            scale(1.0F);
            return self();
        }

        public SELF scale(float scale) {
            this.scale = scale;
            return self();
        }

        public SELF setCentered() {
            this.centered = true;
            return self();
        }
    }
}
