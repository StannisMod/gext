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

package com.github.quarter.gui.lib.components.container;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.utils.TextureMapping;

import java.util.ArrayList;
import java.util.List;

public class GList<T extends IGraphicsComponent> extends GPanel<T> {

    private final List<Integer> order = new ArrayList<>();
    protected int selected;

    protected TextureMapping background;

    /** Some offsets */
    protected int xOffset;
    protected int interval;

    @Override
    public int addComponent(int depth, T component) {
        component.setX(xOffset);
        component.setY(getContentHeight() + interval);
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
    public void draw(int mouseXIn, int mouseYIn) {
        super.draw(mouseXIn, mouseYIn);
        background.draw(getX(), getY(), getWidth(), getHeight(), 0.0F);
    }
}
