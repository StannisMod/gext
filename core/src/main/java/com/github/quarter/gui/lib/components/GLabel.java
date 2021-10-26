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
import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.utils.ComponentBuilder;
import com.github.quarter.gui.lib.utils.GInitializationException;
import com.github.quarter.gui.lib.utils.GraphicsHelper;

import java.awt.*;

public class GLabel extends GBasic {

    protected String text;
    protected int color;
    protected IFontRenderer fontRenderer;
    protected float scale;
    protected boolean centered;

    protected GLabel() {}

    public void setText(String text) {
        if (centered) {
            this.shiftX((fontRenderer.getStringWidth(this.text) - fontRenderer.getStringWidth(text)) / 2);
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isCentered() {
        return centered;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        GraphicsHelper.drawScaledString(fontRenderer, text, 0, 0, scale, color);
    }

    public static class Builder<SELF extends Builder<?, T>, T extends GLabel> extends ComponentBuilder<SELF, T> {

        public SELF renderer(IFontRenderer fontRenderer) {
            if (fontRenderer == null) {
                throw new GInitializationException("FontRenderer instance mustn't be null");
            }
            instance().fontRenderer = fontRenderer;
            return self();
        }

        public SELF text(String text) {
            return text(text, Color.BLACK.getRGB());
        }

        public SELF text(String text, int color) {
            if (text == null) {
                throw new GInitializationException("Given text mustn't be null");
            }
            instance().text = text;
            instance().color = color;
            scale(1.0F);
            return self();
        }

        public SELF scale(float scale) {
            if (instance().text == null) {
                throw new GInitializationException("Trying to set scale before defining a text");
            }
            if (instance().fontRenderer == null) {
                instance().fontRenderer = GuiLib.standardRenderer();
            }
            instance().scale = scale;
            instance().setWidth((int)(instance().fontRenderer.getStringWidth(instance().text) * scale));
            instance().setHeight((int)(instance().fontRenderer.getFontHeight() * scale));
            return self();
        }

        public SELF setCentered() {
            if (instance().getWidth() == 0) {
                throw new GInitializationException("Trying to set centered before defining a text");
            }
            instance().centered = true;
            instance().shiftX(-instance().getWidth() / 2);
            return self();
        }
    }
}
