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

package com.github.quarter.gui.lib.components.text;

import com.github.quarter.gui.lib.api.menu.IContextMenuElement;
import com.github.quarter.gui.lib.api.menu.IContextMenuList;
import com.github.quarter.gui.lib.menu.ContextMenuList;
import com.github.quarter.gui.lib.utils.Icon;

public final class MenuBuilder {

    private final IContextMenuList<? extends IContextMenuElement> instance;

    private MenuBuilder() {
        this(new ContextMenuList<>());
    }

    private MenuBuilder(final IContextMenuList<? extends IContextMenuElement> instance) {
        this.instance = instance;
    }

    public static MenuBuilder create() {
        return new MenuBuilder();
    }

    public MenuBuilder newList(Icon icon, String label, int width) {
        IContextMenuList<?> list = new ContextMenuList<>();
        list.setIcon(icon);
        list.setLabel(label);
        list.setListWidth(width);
        return this;
    }
}
