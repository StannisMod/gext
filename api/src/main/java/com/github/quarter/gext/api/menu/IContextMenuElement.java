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

package com.github.quarter.gext.api.menu;

import com.github.quarter.gext.api.IGraphicsComponent;

/**
 * The general API of context menus
 * Every context menu implementation should implement this
 * @since 1.4
 */
public interface IContextMenuElement {

    IGraphicsComponent getTarget();

    void setTarget(IGraphicsComponent target);

    int getWidth();

    int getHeight();

    void setHeight(int height);

    void setParent(IContextMenuList<? extends IContextMenuElement> parent);

    IContextMenuList<? extends IContextMenuElement> getParent();

    boolean shouldRenderContents();

    void setShouldRenderContents(boolean shouldRenderContents);

    void draw(final int mouseX, final int mouseY);

    void onMousePressed(int mouseX, int mouseY, int mouseButton);

    void onMouseReleased(int mouseX, int mouseY, int mouseButton);

    void onKeyPressed(char typedChar, int keyCode);

    void onHover(int mouseX, int mouseY);

    default boolean intersects(int mouseX, int mouseY) {
        return 0 <= mouseX && mouseX < getWidth() && 0 <= mouseY && mouseY < getHeight();
    }

    default boolean intersectsTree(int mouseX, int mouseY) {
        return intersects(mouseX, mouseY);
    }
}
