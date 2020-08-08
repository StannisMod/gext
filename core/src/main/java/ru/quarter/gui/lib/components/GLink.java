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

import java.awt.*;
import java.net.URI;

public class GLink extends GLabel {

    private int activeColor;
    private int inactiveColor;
    private URI uri;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    public boolean isActive() {
        return active;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        active = true;
        color = activeColor;
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        active = false;
        color = inactiveColor;
        openWebLink(uri);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        hovered = true;
    }

    @Override
    public boolean checkUpdates() {
        return hovered != prevHovered;
    }

    @Override
    public void update() {
        if (active && !hovered) {
            active = false;
            color = inactiveColor;
        }
        needUpdate = false;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        prevHovered = hovered;
        hovered = false;
    }

    private void openWebLink(URI url) {
        try {
            Desktop.getDesktop().browse(url);
        } catch (Exception e) {
            GuiLib.error("Couldn't open link", e);
        }
    }

    public static class Builder extends GLabel.Builder {

        protected Builder() {
            instance = new GLink();
        }

        private GLink getInstance() {
            return (GLink) instance;
        }

        @Override
        public Builder placeAt(int x, int y) {
            super.placeAt(x, y);
            return this;
        }

        @Override
        public Builder renderer(IFontRenderer fontRenderer) {
            super.renderer(fontRenderer);
            return this;
        }

        @Override
        public Builder text(String text) {
            if (text == null) {
                throw new GInitializationException("Given text mustn't be null");
            }
            instance.text = text;
            scale(1.0F);
            return this;
        }

        @Override
        public Builder text(String text, int color) {
            throw new UnsupportedOperationException("For drawing link with color use Builder#color(activeColor, inactiveColor)");
        }

        @Override
        public Builder scale(float scale) {
            super.scale(scale);
            return this;
        }

        @Override
        public Builder setCentered() {
            super.setCentered();
            return this;
        }

        public Builder url(String url) {
            getInstance().uri = URI.create(url);
            return this;
        }

        public Builder color(int activeColor, int inactiveColor) {
            getInstance().activeColor = activeColor;
            getInstance().inactiveColor = inactiveColor;
            getInstance().color = inactiveColor;
            return this;
        }

        @Override
        public GLink build() {
            return getInstance();
        }
    }
}
