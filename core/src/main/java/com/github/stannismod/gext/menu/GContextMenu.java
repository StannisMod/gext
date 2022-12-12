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

package com.github.stannismod.gext.menu;

import com.github.stannismod.gext.api.menu.IContextMenuComponent;
import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.api.menu.IContextMenuList;
import com.github.stannismod.gext.components.GBasic;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Alignment;

import java.util.ArrayList;

public class GContextMenu<T extends IContextMenuElement> extends GBasic implements IContextMenuComponent<T> {

    private final IContextMenuList<T> content;

    public GContextMenu(final IContextMenuList<T> content) {
        super(0, 0, 0, 0, false, null, null, null,
                Alignment.FIXED, 0, 0, new ArrayList<>());
        this.content = content;
    }

    public IContextMenuList<T> getContent() {
        return content;
    }

    @Override
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
        content.draw(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMousePressed(mouseX, mouseY, mouseButton);
        content.onMousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        content.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        content.onKeyPressed(typedChar, keyCode);
    }

    @Override
    public void onHover(final int mouseX, final int mouseY) {
        super.onHover(mouseX, mouseY);
        content.onHover(mouseX, mouseY);
    }

    @Override
    public boolean intersectsInner(final int innerMouseX, final int innerMouseY) {
        return content.intersectsTree(innerMouseX, innerMouseY);
    }
}
