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

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.IListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GControl implements IGraphicsComponent {

    private final List<IListener<? extends IGraphicsComponent>> listeners = new LinkedList<>();
    private int id;
    private IGraphicsLayout<? extends IGraphicsComponent> parent;
    private boolean needUpdate;
    private boolean visible;
    private static final Rectangle frame = new Rectangle();

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    @Override
    public int getAbsoluteX() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int x) {}

    @Override
    public int getAbsoluteY() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {}

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void setWidth(int y) {}

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setHeight(int y) {}

    @Override
    public @NotNull Rectangle getFrame() {
        return frame;
    }

    @Override
    public @NotNull Rectangle getAbsoluteFrame() {
        return frame;
    }

    @Override
    public IGraphicsLayout<? extends IGraphicsComponent> getParent() {
        return parent;
    }

    @Override
    public void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        this.parent = parent;
    }

    @Override
    public void addListener(@NotNull IListener<? extends IGraphicsComponent> listener) {
        listeners.add(listener);
    }

    @Override
    public void setBinding(@NotNull IGraphicsComponent component) {
        throw new UnsupportedOperationException("GControl does not support component binding!");
    }

    @Override
    public IGraphicsComponent getBinding() {
        throw new UnsupportedOperationException("GControl does not support component binding!");
    }

    @Override
    public boolean clippingEnabled() {
        return false;
    }

    @Override
    public void setClippingEnabled(boolean enabled) {}

    @Override
    public void draw(int mouseX, int mouseY) {
        // just empty override because no render in GControl
    }

    @Override
    public void render(int mouseX, int mouseY) {
        // just empty override because no render in GControl
        listeners.forEach(IListener::listen);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        // just empty override because no render in GControl
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        // just empty override because no render in GControl
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        // just empty override because no render in GControl
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        // just empty override because no render in GControl
    }

    @Override
    public void markDirty() {
        needUpdate = true;
    }

    @Override
    public boolean needUpdate() {
        return needUpdate;
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public void setDepth(int depth) {
        // just empty override because no render in GControl
    }

    @Override
    public void onResize(int w, int h) {
        // just empty override because no render in GControl
    }
}
