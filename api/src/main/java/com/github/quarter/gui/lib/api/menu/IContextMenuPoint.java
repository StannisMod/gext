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

import java.util.function.Consumer;

/**
 * Some extracted API for plain menu point
 * @since 1.4
 */
public interface IContextMenuPoint extends IContextMenuElement {

    void setLabel(String label);

    String getLabel();

    void setIcon(Icon icon);

    Icon getIcon();

    void setAction(Consumer<IContextMenuPoint> action);

    Consumer<IContextMenuPoint> getAction();
}
