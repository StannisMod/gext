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

import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ContextMenuList extends ContextMenuPoint {

    private static final int ARROW_SIZE = 8;

    private final List<IContextMenuElement> elements = new ArrayList<>();
    private int width;

    private boolean opened;

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void addElement(IContextMenuElement element) {
        elements.add(element);
    }

    public void putSimpleAction(String label, Consumer<ContextMenuPoint> action) {
        ContextMenuPoint point = new ContextMenuPoint();
        point.label = label;
        point.action = action;
        addElement(point);
    }

    public void putSimpleAction(StyleMap.Icon icon, String label, Consumer<ContextMenuPoint> action) {
        ContextMenuPoint point = new ContextMenuPoint();
        point.icon = icon;
        point.label = label;
        point.action = action;
        addElement(point);
    }

    public void putList(StyleMap.Icon icon, String label, int width) {
        ContextMenuList point = new ContextMenuList();
        point.icon = icon;
        point.label = label;
        point.width = width;
        point.action = p -> ((ContextMenuList) p).opened = true;
        addElement(point);
    }

    private void forEachRelatively(BiConsumer<IContextMenuElement, Integer> f) {
        int relY = 0;
        for (IContextMenuElement element : elements) {
            f.accept(element, relY);
            relY += element.getHeight();
        }
    }

    @Override
    public void draw(final int mouseX, final int mouseY) {
        super.draw(mouseX, mouseY);
        StyleMap.current().drawIcon(StyleMap.Icon.RIGHT_ARROW, getWidth() - ARROW_SIZE, (getHeight() - ARROW_SIZE) / 2, ARROW_SIZE);
        if (opened) {
            if (!hovered) {
                opened = false;
                return;
            }
            forEachRelatively((element, relY) -> {
                if (element.intersects(mouseX - getWidth(), mouseY - relY)) {
                    GL11.glPushMatrix();
                    element.draw(mouseX - getWidth(), mouseY - relY);
                    GL11.glPopMatrix();
                }
                GL11.glTranslatef(0.0F, element.getHeight(), 0.0F);
            });
        }
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMousePressed(mouseX, mouseY, mouseButton);
        if (opened) {
            forEachRelatively((element, relY) -> {
                if (element.intersects(mouseX - getWidth(), mouseY - relY)) {
                    element.onMousePressed(mouseX - getWidth(), mouseY - relY, mouseButton);
                }
            });
        }
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        if (opened) {
            forEachRelatively((element, relY) -> {
                if (element.intersects(mouseX - getWidth(), mouseY - relY)) {
                    element.onMouseReleased(mouseX - getWidth(), mouseY - relY, mouseButton);
                }
            });
        }
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        if (opened) {
            elements.forEach(element -> element.onKeyPressed(typedChar, keyCode));
        }
    }
}
