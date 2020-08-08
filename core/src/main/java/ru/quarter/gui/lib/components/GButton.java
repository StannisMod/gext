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
import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.utils.StyleMap;
import ru.quarter.gui.lib.utils.TextureMapping;

import java.util.function.Consumer;

public class GButton extends GBasic {

    private TextureMapping mapping;
    private Consumer<GButton> action;
    private GLabel label;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    public boolean hasAction() {
        return action != null;
    }

    public boolean hasLabel() {
        return label != null;
    }

    private void switchOn() {
        active = true;
        if (mapping != null) {
            mapping = mapping.down();
        }
    }

    private void switchOff() {
        active = false;
        if (mapping != null) {
            mapping = mapping.up();
        }
    }

    @Override
    public boolean checkUpdates() {
        return hovered != prevHovered;
    }

    @Override
    public void update() {
        if (active && !hovered) {
            switchOff();
        }
        needUpdate = false;
    }

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw(int mouseX, int mouseY) {
        prevHovered = hovered;
        hovered = false;

        if (mapping == null) {
            StyleMap.current().drawButton(active, 0, 0, getWidth(), getHeight());
        } else {
            mapping.draw(0, 0, getWidth(), getHeight(), 0);
        }
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        hovered = true;
        if (hasLabel()) {
            label.onHover(mouseX, mouseY);
        }
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        switchOn();
        if (hasLabel()) {
            label.onMousePressed(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        switchOff();
        if (hasAction()) {
            action.accept(this);
        }
        if (hasLabel()) {
            label.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder {

        private final GButton instance = new GButton();

        public Builder texture(IResource location) {
            return texture(location, 256, 256);
        }

        public Builder texture(IResource location, int textureWidth, int textureHeight) {
            instance.mapping = new TextureMapping(location);
            instance.mapping.setTextureWidth(textureWidth);
            instance.mapping.setTextureHeight(textureHeight);
            return this;
        }

        public Builder uv(int startU, int startV, int u, int v) {
            instance.mapping.setU(startU);
            instance.mapping.setV(startV);
            instance.mapping.setTextureX(u);
            instance.mapping.setTextureY(v);
            return this;
        }

        public Builder action(Consumer<GButton> listener) {
            instance.action = listener;
            return this;
        }

        public Builder label(GLabel label) {
            label.x = instance.getWidth() / 2;
            label.y = instance.getHeight() / 2;
            instance.label = label;
            return this;
        }

        public Builder size(int width, int height) {
            instance.width = width;
            instance.height = height;
            return this;
        }

        public Builder placeAt(int x, int y) {
            instance.x = x;
            instance.y = y;
            return this;
        }

        public GBasic build() {
            if (!instance.hasAction()) {
                GuiLib.warn("GraphicsComponentButton was built without an action. It can be inferred statement, but in most cases indicates a broken component");
            }
            return instance;
        }
    }
}
