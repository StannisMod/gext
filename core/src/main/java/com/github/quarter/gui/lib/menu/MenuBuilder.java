/*
 *  Copyright 2020 Stanislav Batalenkov
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.quarter.gui.lib.menu;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.menu.IContextMenuElement;
import com.github.quarter.gui.lib.api.menu.IContextMenuList;
import com.github.quarter.gui.lib.api.menu.IContextMenuPoint;
import com.github.quarter.gui.lib.utils.Icon;

import java.util.function.BiConsumer;

public final class MenuBuilder<T extends IGraphicsComponent> {

    private IContextMenuList<? extends IContextMenuElement> instance;

    private MenuBuilder(final int width) {
        this(new ContextMenuList<>(width));
    }

    private MenuBuilder(final IContextMenuList<? extends IContextMenuElement> instance) {
        this.instance = instance;
    }

    public static <E extends IGraphicsComponent> MenuBuilder<E> create(int listWidth) {
        return new MenuBuilder<>(listWidth);
    }

    public static <E extends IGraphicsComponent> MenuBuilder<E> create(final IContextMenuList<? extends IContextMenuElement> instance) {
        return new MenuBuilder<>(instance);
    }

    public MenuBuilder<T> newList(String label, int width) {
        return newList(null, label, width);
    }

    public MenuBuilder<T> newList(Icon icon, String label, int width) {
        IContextMenuList<? extends IContextMenuElement> list = new ContextMenuList<>();
        instance.putList(icon, label, width, list);
        instance = list;
        return this;
    }

    public MenuBuilder<T> endList() {
        instance = instance.getParent();
        return this;
    }

    public MenuBuilder<T> point(String label, BiConsumer<T, IContextMenuPoint> action) {
        return point(null, label, action);
    }

    @SuppressWarnings("unchecked")
    public MenuBuilder<T> point(Icon icon, String label, BiConsumer<T, IContextMenuPoint> action) {
        instance.putSimpleAction(icon, label, (BiConsumer<IGraphicsComponent, IContextMenuPoint>) action);
        return this;
    }

    public IContextMenuList<? extends IContextMenuElement> build() {
        return instance;
    }
}
