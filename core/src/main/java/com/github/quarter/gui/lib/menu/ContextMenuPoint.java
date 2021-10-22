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

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.function.Consumer;

public class ContextMenuPoint extends ContextMenuBase {

    private static final int LABEL_OFFSET = 2;

    protected String label;
    protected StyleMap.Icon icon;
    protected Consumer<ContextMenuPoint> action;
    protected int height;

    protected boolean hovered;
    protected boolean pressed;

    @Override
    public int getWidth() {
        return getParent().getWidth();
    }

    @Override
    public void setWidth(final int height) {
        throw new UnsupportedOperationException("Width can't be set directly, it's inherited from parent list");
    }

    @Override
    public void draw(final int mouseX, final int mouseY) {
        hovered = intersects(mouseX, mouseY);

//        if (hovered && !pressed) {
//            GraphicsHelper.drawColoredModalRect(0, 0, getWidth(), getHeight(), 1.0F, 1.0F, 1.0F, 0.5F, 0.0F);
//        }
        // TODO Check visual effect, maybe we should use upper variant
        if (pressed) {
            GraphicsHelper.drawColoredModalRect(0, 0, getWidth(), getHeight(), 1.0F, 1.0F, 1.0F, 0.8F, 0.0F);
        } else if (hovered) {
            GraphicsHelper.drawColoredModalRect(0, 0, getWidth(), getHeight(), 1.0F, 1.0F, 1.0F, 0.5F, 0.0F);
        }

        if (icon != null) {
            StyleMap.current().drawIcon(icon, 0, 0, getHeight());
        }
        GraphicsHelper.drawString(label, getHeight() + LABEL_OFFSET, (getHeight() - GuiLib.standardRenderer().getFontHeight()) / 2, Color.BLACK.getRGB());
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        if (action != null) {
            action.accept(this);
        }
        pressed = true;
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        pressed = false;
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        if (hovered && keyCode == Keyboard.KEY_RETURN) {
            action.accept(this);
        }
    }
}
