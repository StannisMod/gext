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

import com.github.quarter.gui.lib.api.menu.IContextMenuElement;
import com.github.quarter.gui.lib.api.menu.IContextMenuList;
import com.github.quarter.gui.lib.api.menu.IContextMenuPoint;
import com.github.quarter.gui.lib.utils.Icon;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.apache.logging.log4j.util.TriConsumer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class ContextMenuList<T extends IContextMenuElement> extends ContextMenuPoint implements IContextMenuList<T> {

    private static final int ARROW_SIZE = 8;

    private final List<T> elements = new ArrayList<>();

    private int listWidth;
    private int listHeight;

    private boolean opened;

    @Override
    public int getWidth() {
        if (isRoot()) {
            return getListWidth();
        }
        return super.getWidth();
    }

    @Override
    public int getListWidth() {
        return listWidth;
    }

    @Override
    public void setListWidth(final int listWidth) {
        this.listWidth = listWidth;
    }

    @Override
    public int getListHeight() {
        return listHeight;
    }

    @Override
    public void setListHeight(final int listHeight) {
        this.listHeight = listHeight;
    }

    @Override
    public IContextMenuList<T> addElement(T element) {
        element.setParent(this);
        this.elements.add(element);
        this.growListHeight(element.getHeight());
        return this;
    }

    @Override
    public IContextMenuList<T> putSimpleAction(String label, Consumer<IContextMenuPoint> action) {
        IContextMenuPoint point = new ContextMenuPoint();
        point.setLabel(label);
        point.setAction(action);
        return addElement((T) point);
    }

    @Override
    public IContextMenuList<T> putSimpleAction(Icon icon, String label, Consumer<IContextMenuPoint> action) {
        ContextMenuPoint point = new ContextMenuPoint();
        point.setIcon(icon);
        point.setLabel(label);
        point.setAction(action);
        return addElement((T) point);
    }

    @Override
    public IContextMenuList<T> putList(Icon icon, String label, int listWidth, IContextMenuList<? extends IContextMenuElement> list) {
        list.setIcon(icon);
        list.setLabel(label);
        list.setListWidth(listWidth);
        list.setAction(p -> ((ContextMenuList<?>) p).opened = !((ContextMenuList<?>) p).opened);
        return addElement((T) list);
    }

    private void forEachRelatively(TriConsumer<IContextMenuElement, Integer, Integer> f) {
        int relX = isRoot() ? 0 : getWidth();
        int relY = 0;
        for (IContextMenuElement element : elements) {
            f.accept(element, relX, relY);
            relY += element.getHeight();
        }
    }

    private boolean shouldRender() {
        return (isRoot() || opened) && !elements.isEmpty();
    }

    @Override
    public void draw(final int mouseX, final int mouseY) {
        StyleMap.current().drawIcon(Icon.RIGHT_ARROW, getWidth() - ARROW_SIZE, (getHeight() - ARROW_SIZE) / 2, getHeight());
        if (!isRoot()) {
            super.draw(mouseX, mouseY);
            GL11.glTranslatef(getWidth(), 0.0F, 0.0F);
        }
        if (shouldRender()) {
//            if (!hovered && !isRoot()) {
//                opened = false;
//                return;
//            }
            StyleMap.current().drawFrame(0, 0, getListWidth(), getListHeight());
            forEachRelatively((element, relX, relY) -> {
                GL11.glPushMatrix();
                element.draw(mouseX - relX, mouseY - relY);
                GL11.glPopMatrix();
                GL11.glTranslatef(0.0F, element.getHeight(), 0.0F);
            });
        }
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMousePressed(mouseX, mouseY, mouseButton);
        if (shouldRender()) {
            forEachRelatively((element, relX, relY) -> {
                if (element.intersectsTree(mouseX - relX, mouseY - relY)) {
                    element.onMousePressed(mouseX - relX, mouseY - relY, mouseButton);
                }
            });
        }
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        if (shouldRender()) {
            forEachRelatively((element, relX, relY) -> {
                if (element.intersectsTree(mouseX - relX, mouseY - relY)) {
                    element.onMouseReleased(mouseX - relX, mouseY - relY, mouseButton);
                }
            });
        }
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        if (shouldRender()) {
            elements.forEach(element -> element.onKeyPressed(typedChar, keyCode));
        }
    }

    @Override
    public boolean canIntersect() {
        return shouldRender();
    }

    @Override
    public boolean intersectsTree(final int mouseX, final int mouseY) {
        if (intersects(mouseX, mouseY)) {
            return true;
        }
        if (!canIntersect()) {
            return false;
        }
        int relY = 0;
        for (IContextMenuElement e : elements) {
            int x = mouseX;
            int y = mouseY - relY;

            if (!isRoot()) {
                x -= getWidth();
            }

//            if (e.intersects(x, y)) {
//                return true;
//            }
            relY += e.getHeight();

//            if (!e.canIntersect()) {
//                continue;
//            }

//            if (e instanceof IContextMenuList<?>) {
//                x -= this.getWidth();
//            }
            if (e.intersectsTree(x, y)) {
                return true;
            }
        }
        return false;
    }
}