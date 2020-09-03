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

import org.lwjgl.input.Mouse;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsComponentScroll;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IScrollable;

public abstract class GScrollBasic extends GBasic implements IGraphicsComponentScroll {

    private IScrollable target;

    @Override
    public void setTarget(IScrollable target) {
        this.target = target;
    }

    @Override
    public IScrollable getTarget() {
        return target;
    }

    @Override
    public void init() {
        if (target == null) {
            throw new IllegalStateException("Rendering without target");
        }
    }

    @Override
    public boolean checkUpdates() {
        return Mouse.getDWheel() != 0;
    }

    @Override
    public void setParent(IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        this.setX(0);
        this.setY(0);
        this.setWidth(parent.getWidth());
        this.setHeight(parent.getHeight());
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void markDirty() {}

    @Override
    public boolean needUpdate() {
        return false;
    }
}
