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
import com.github.quarter.gui.lib.utils.GInitializationException;

import java.awt.*;
import java.net.URI;

public class GLink extends GLabel {

    protected int activeColor;
    protected int inactiveColor;
    protected URI uri;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    protected GLink() {}

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

    public static class Builder<SELF extends Builder<?, T>, T extends GLink> extends GLabel.Builder<SELF, T> {

        @Override
        public SELF text(String text) {
            if (text == null) {
                throw new GInitializationException("Given text mustn't be null");
            }
            instance().text = text;
            return scale(1.0F);
        }

        @Override
        public SELF text(String text, int color) {
            throw new UnsupportedOperationException("For drawing link with color use Builder#color(activeColor, inactiveColor)");
        }

        public SELF url(String url) {
            instance().uri = URI.create(url);
            return self();
        }

        public SELF color(int color) {
            return color(color, color);
        }

        public SELF color(int activeColor, int inactiveColor) {
            instance().activeColor = activeColor;
            instance().inactiveColor = inactiveColor;
            instance().color = inactiveColor;
            return self();
        }
    }
}
