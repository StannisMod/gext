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

import com.github.quarter.gui.lib.components.GBasic;

/**
 * Represents the root graphics component for menus
 * @since 1.4
 */
public class GContextMenu extends GBasic {

    private final ContextMenuList root;

    public GContextMenu(final ContextMenuList root) {
        this.root = root;
    }

    @Override
    public void draw(final int mouseX, final int mouseY) {
        root.draw(mouseX, mouseY);
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMousePressed(mouseX, mouseY, mouseButton);
        root.onMousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        root.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        root.onKeyPressed(typedChar, keyCode);
    }
}
