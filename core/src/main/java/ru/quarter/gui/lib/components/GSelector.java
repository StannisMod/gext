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

import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.ISelectable;
import ru.quarter.gui.lib.api.ISelector;

public class GSelector extends GControl implements ISelector {

    private int selected;

    @Override
    public int getSelectedId() {
        return this.selected;
    }

    @Override
    public void select(int element) {
        this.selected = element;
    }

    @Override
    public void onSelect(IGraphicsComponent component) {
        if (isSelected()) {
            onDeselect(getSelectedComponent());
        }
        if (component instanceof ISelectable) {
            ((ISelectable) component).onSelect();
        }
    }

    @Override
    public void onDeselect(IGraphicsComponent component) {
        if (component instanceof ISelectable) {
            ((ISelectable) component).onDeselect();
        }
    }

    @Override
    public boolean checkUpdates() {
        return false;
    }

    @Override
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}
}
