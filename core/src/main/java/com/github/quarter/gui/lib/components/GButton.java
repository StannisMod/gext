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
import com.github.quarter.gui.lib.api.adapter.IResource;
import com.github.quarter.gui.lib.utils.ComponentBuilder;
import com.github.quarter.gui.lib.utils.StyleMap;
import com.github.quarter.gui.lib.utils.TextureMapping;
import org.lwjgl.input.Mouse;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class GButton extends GBasic {

    private TextureMapping mapping;
    @SuppressWarnings("unchecked")
    private final Consumer<GButton>[] action = new Consumer[Mouse.getButtonCount()];
    private GLabel label;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    protected GButton() {}

    public boolean hasAction(int button) {
        if (button < 0 || button >= action.length) {
            return false;
        }
        return action[button] != null;
    }

    public boolean hasAnyAction() {
        return !Arrays.stream(action).allMatch(Objects::isNull);
    }

    public boolean hasLabel() {
        return getLabel() != null;
    }

    public GLabel getLabel() {
        return label;
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

    public void setAction(Consumer<GButton> action) {
        this.setAction(0, action);
    }

    public void setAction(int button, Consumer<GButton> action) {
        this.action[button] = action;
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
        if (hasLabel()) {
            label.render(mouseX, mouseY);
        }
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        super.onHover(mouseX, mouseY);
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
        if (hasAction(mouseButton)) {
            action[mouseButton].accept(this);
        }
        if (hasLabel()) {
            label.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder<SELF extends Builder<?, T>, T extends GButton> extends ComponentBuilder<SELF, T> {

        public SELF texture(IResource location) {
            return texture(location, 256, 256);
        }

        public SELF texture(IResource location, int textureWidth, int textureHeight) {
            instance().mapping = new TextureMapping(location);
            instance().mapping.setTextureWidth(textureWidth);
            instance().mapping.setTextureHeight(textureHeight);
            return self();
        }

        public SELF uv(int startU, int startV, int u, int v) {
            instance().mapping.setU(startU);
            instance().mapping.setV(startV);
            instance().mapping.setTextureX(u);
            instance().mapping.setTextureY(v);
            return self();
        }

        public SELF action(Consumer<GButton> listener) {
            return action(0, listener);
        }

        public SELF action(int button, Consumer<GButton> listener) {
            instance.action[button] = listener;
            return self();
        }

        public SELF label(String label) {
            return label(Graphics.label().text(label).setCentered().build());
        }

        public SELF label(GLabel label) {
            instance().label = label;
            label.setClippingEnabled(false);
            return self();
        }

        private void setupLabel() {
            if (instance().hasLabel()) {
                instance().label.setX(instance().getWidth() / 2);
                instance().label.setY((instance().getHeight() - instance().label.getHeight()) / 2);
                if (instance().label.isCentered()) {
                    instance().label.shiftX(-instance().label.getWidth() / 2);
                }
            }
        }

        public T build() {
            if (!instance().hasAction()) {
                GuiLib.warn("GButton was built without an action. It can be inferred statement, but in most cases indicates a broken component");
            }
            setupLabel();
            return super.build();
        }
    }
}
