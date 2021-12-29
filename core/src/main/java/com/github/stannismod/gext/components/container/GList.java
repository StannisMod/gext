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

package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.utils.StyleMap;
import com.github.stannismod.gext.utils.TextureMapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GList<T extends IGraphicsComponent> extends GPanel<T> {

    private final List<Integer> order = new ArrayList<>();
    protected int selected;

    protected TextureMapping background;
    protected boolean drawBackground;

    /** Some offsets */
    protected int interval;

    @Override
    public int addComponent(int depth, @NotNull T component) {
        component.setX(xOffset);
        component.setY(yOffset + this.getContentHeight() + interval);
        int id = super.addComponent(depth, component);
        order.add(id);
        return id;
    }

    public T getSelectedElement() {
        if (hasSelector() && getSelector().isSelected()) {
            return getComponent(getSelector().getSelectedId());
        }
        return null;
    }

    public int getInterval() {
        return interval;
    }

    public T getByIndex(int index) {
        return checkBounds(index) ? getComponent(order.get(index)) : null;
    }

    private boolean checkBounds(int index) {
        return index >= 0 && index < size();
    }

    @Override
    public T removeComponent(int id) {
        order.remove(new Integer(id));
        return super.removeComponent(id);
    }

    private int removeFromOrder(int index) {
        if (!checkBounds(index)) {
            throw new IndexOutOfBoundsException("Trying to remove component under index " + index + ", size: " + order.size());
        }
        int shift = -(getByIndex(index).getHeight() + interval);
        for (int i = index + 1; i < order.size(); i++) {
            getByIndex(i).shiftY(shift);
        }
        return order.remove(index);
    }

    public boolean removeByIndex(int index) {
//        if (index == -1 && !isEmpty()) {
//            index = 0;
//        }
        if (!checkBounds(index)) {
            throw new IndexOutOfBoundsException("Trying to remove component under index " + index + ", size: " + order.size());
        }
        int id = removeFromOrder(index);
        removeComponent(id);
        if (index == 0 && !isEmpty()) {
            this.getSelector().select(order.get(0));
        } else if (index == getSelector().getSelectedId()) {
            this.selected--;
        }
        return true;
    }

    @Override
    public void draw(int mouseXIn, int mouseYIn, float partialTicks) {
        if (background != null) {
            background.draw(0, 0, getWidth(), getHeight(), 0.0F);
        } else if (drawBackground) {
            StyleMap.current().drawFrame(0, 0, getWidth(), getHeight());
        }
        super.draw(mouseXIn, mouseYIn, partialTicks);
    }

    public static class Builder<SELF extends Builder<?, T>, T extends GList<?>> extends GPanel.Builder<SELF, T> {

        public SELF background(TextureMapping background) {
            instance().background = background;
            return self();
        }

        public SELF enableBackground() {
            instance().drawBackground = true;
            return self();
        }

        public SELF interval(int interval) {
            instance().interval = interval;
            return self();
        }
    }
}
