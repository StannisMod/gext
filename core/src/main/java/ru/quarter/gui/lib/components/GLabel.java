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

import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.adapter.IFontRenderer;
import ru.quarter.gui.lib.utils.GInitializationException;
import ru.quarter.gui.lib.utils.GraphicsHelper;

import java.awt.*;

public class GLabel extends GBasic {

    protected String text;
    protected int color;
    private IFontRenderer fontRenderer;
    private float scale;

    protected GLabel() {}

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
    public void draw(int mouseX, int mouseY) {
        GraphicsHelper.drawScaledString(fontRenderer, text, 0, 0, scale, color);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder {

        protected GLabel instance = new GLabel();

        public Builder placeAt(int x, int y) {
            instance.setX(x);
            instance.setY(y);
            return this;
        }

        public Builder renderer(IFontRenderer fontRenderer) {
            if (fontRenderer == null) {
                throw new GInitializationException("FontRenderer instance mustn't be null");
            }
            instance.fontRenderer = fontRenderer;
            return this;
        }

        public Builder text(String text) {
            return text(text, Color.BLACK.getRGB());
        }

        public Builder text(String text, int color) {
            if (text == null) {
                throw new GInitializationException("Given text mustn't be null");
            }
            instance.text = text;
            instance.color = color;
            scale(1.0F);
            return this;
        }

        public Builder scale(float scale) {
            if (instance.text == null) {
                throw new GInitializationException("Trying to set scale before defining a text");
            }
            if (instance.fontRenderer == null) {
                instance.fontRenderer = GuiLib.standardRenderer();
            }
            instance.scale = scale;
            instance.setWidth((int)(instance.fontRenderer.getStringWidth(instance.text) * scale));
            instance.setHeight((int)(instance.fontRenderer.getFontHeight() * scale));
            return this;
        }

        public Builder setCentered() {
            if (instance.getWidth() == 0) {
                throw new GInitializationException("Trying to set centered before defining a text");
            }
            instance.growWidth(-instance.getWidth() / 2);
            return this;
        }

        public GLabel build() {
            return instance;
        }
    }
}
