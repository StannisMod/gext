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

import com.github.stannismod.gext.api.*;
import com.github.stannismod.gext.components.container.BasicLayout;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import com.github.stannismod.gext.utils.StyleMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class GTooltip extends BasicLayout<IGraphicsComponent> implements IGraphicsListener<IGraphicsComponent> {

    protected int xOffset;
    protected int yOffset;

    protected int mouseX;
    protected int mouseY;

    private IGraphicsComponent target;

    private final Map<Class<? extends IGraphicsComponent>, List<IGraphicsComponent>> content = new HashMap<>();

    public GTooltip(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                    final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                    final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                    final List<IListener> listeners, final ISelector selector, final int xOffset, final int yOffset) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                xPadding, yPadding, listeners, null, selector);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    protected String addComponent(int depth, IGraphicsComponent component, Class<? extends IGraphicsComponent> clazz) {
        content.compute(clazz, (aClass, components) -> {
            if (components == null) {
                components = new LinkedList<>();
            }
            component.setDepth(depth);
            components.add(component);
            return components;
        });
        return component.getID();
    }

    @Override
    public void setTarget(@Nullable IGraphicsComponent target) {
        this.target = target;
        this.markDirty();
    }

    @Override
    public @NotNull IGraphicsComponent getTarget() {
        return target;
    }

    @Override
    public boolean checkUpdates() {
        return true;
    }

    @Override
    public void update() {
        if (intersects(mouseX, mouseY)) {
            this.setX(mouseX - getWidth() - xOffset);
            this.setY(mouseY - getHeight() - yOffset);
            return;
        }

        if (getX() + getWidth() <= getParent().getWidth()) {
            this.setX(mouseX + xOffset);
        } else {
            this.setX(getParent().getWidth() - getWidth());
        }

        if (getY() + getHeight() <= getParent().getHeight()) {
            this.setY(mouseY + yOffset);
        } else {
            this.setY(getParent().getHeight() - getHeight());
        }

        if (needUpdate()) {
            this.clear();
            this.setContent(content.get(getTarget().getClass()));
        }
    }

    public abstract void initTooltip();

    @Override
    public void init() {
        initTooltip();
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (visible()) {
            StyleMap.current().drawTooltip(getX(), getY(), getWidth(), getHeight());
            super.draw(mouseX, mouseY, partialTicks);
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
