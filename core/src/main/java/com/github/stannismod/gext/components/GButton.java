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
import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.utils.*;
import org.jetbrains.annotations.VisibleForTesting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class GButton extends GBasic {

    protected TextureMapping mapping;
    /**
     * TODO Get button amount from LWJGL3
     */
    @SuppressWarnings("unchecked")
    protected final Consumer<GButton>[] action = new Consumer[3];
    protected GLabel label;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    protected GButton(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                      final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                      final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                      final List<IListener> listeners, GLabel label, Consumer<GButton>[] action) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners);
        this.label = label;
        System.arraycopy(action, 0, this.action, 0, action.length);
    }

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

    public void switchOn() {
        active = true;
        if (mapping != null) {
            mapping = mapping.down();
        }
    }

    public void switchOff() {
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

    public boolean isPressed(){
        return active;
    }

    @Override
    public boolean checkUpdates() {
        return hovered != prevHovered;
    }

    @Override
    public void update() {
        super.update();
        if (active && !hovered) {
            switchOff();
        }
        needUpdate = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        prevHovered = hovered;
        hovered = false;

        if (mapping == null) {
            StyleMap.current().drawButton(active, 0, 0, getWidth(), getHeight());
        } else {
            mapping.draw(0, 0, getWidth(), getHeight(), 0);
        }
        if (hasLabel()) {
            label.render(mouseX, mouseY, partialTicks);
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
        if (mouseButton == 1){
            switchOn();
        }

        if (hasLabel()) {
            label.onMousePressed(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 1){
            switchOff();
        }
        if (hasAction(mouseButton)) {
            action[mouseButton].accept(this);
        }
        if (hasLabel()) {
            label.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GButton> extends ComponentBuilder<SELF, T> {

        protected TextureMapping mapping;
        @SuppressWarnings("unchecked")
        protected Consumer<GButton>[] action = new Consumer[3];
        protected GLabel label;

        public SELF texture(ITexture location) {
            return texture(location, 256, 256);
        }

        public SELF texture(ITexture location, int textureWidth, int textureHeight) {
            this.mapping = new TextureMapping(location);
            this.mapping.setTextureWidth(textureWidth);
            this.mapping.setTextureHeight(textureHeight);
            return self();
        }

        public SELF uv(int startU, int startV, int u, int v) {
            this.mapping.setU(startU);
            this.mapping.setV(startV);
            this.mapping.setTextureX(u);
            this.mapping.setTextureY(v);
            return self();
        }

        public SELF action(Consumer<GButton> listener) {
            return action(0, listener);
        }

        public SELF action(int button, Consumer<GButton> listener) {
            this.action[button] = listener;
            return self();
        }

        public SELF label(String label) {
            return label(label, Color.BLACK.getRGB());
        }

        public SELF label(String label, int color) {
            return label(Graphics.label().text(label, color).setCentered().build());
        }

        public SELF label(GLabel label) {
            this.label = label;
            label.setClippingEnabled(false);
            return self();
        }

        private void setupLabel() {
            if (this.label != null) {
                this.label.setX(width / 2);
                this.label.setY((height - this.label.getHeight()) / 2);
                if (this.label.isCentered()) {
                    this.label.shiftX(-this.label.getWidth() / 2);
                }
            }
        }

        private boolean hasAnyAction() {
            return !Arrays.stream(action).allMatch(Objects::isNull);
        }

        public T build() {
            if (!this.hasAnyAction()) {
                GExt.warn("GButton was built without an action. It can be inferred statement, but in most cases indicates a broken component");
            }
            setupLabel();
            return super.build();
        }
    }
}
