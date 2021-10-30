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

package com.github.quarter.gui.lib.api.menu;

import com.github.quarter.gui.lib.utils.Icon;

import java.util.List;
import java.util.function.Consumer;

/**
 * API for context menu lists
 * @param <T> the type of menu items should be stored
 * @since 1.4
 */
public interface IContextMenuList<T extends IContextMenuElement> extends IContextMenuPoint {

    default boolean isRoot() {
        return getParent() == null;
    }

    int getListWidth();

    void setListWidth(int listWidth);

    int getListHeight();

    void setListHeight(int listHeight);

    IContextMenuList<T> addElement(T element);

    IContextMenuList<T> putSimpleAction(String label, Consumer<IContextMenuPoint> action);

    IContextMenuList<T> putSimpleAction(Icon icon, String label, Consumer<IContextMenuPoint> action);

    IContextMenuList<T> putList(Icon icon, String label, int width, IContextMenuList<? extends IContextMenuElement> list);

    List<T> getContents();

    default void growListWidth(int amount) {
        setListWidth(getListWidth() + amount);
    }

    default void growListHeight(int amount) {
        setListHeight(getListHeight() + amount);
    }
}
