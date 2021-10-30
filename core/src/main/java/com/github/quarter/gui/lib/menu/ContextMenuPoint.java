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
import com.github.quarter.gui.lib.api.menu.IContextMenuPoint;
import com.github.quarter.gui.lib.utils.GraphicsHelper;
import com.github.quarter.gui.lib.utils.Icon;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.function.Consumer;

public class ContextMenuPoint extends ContextMenuBase implements IContextMenuPoint {

    private static final int LABEL_OFFSET = 2;

    private String label;
    private Icon icon;
    private Consumer<IContextMenuPoint> action;

    protected boolean hovered;
    protected boolean pressed;

    public ContextMenuPoint() {
        this.setHeight(10);
    }

    @Override
    public int getWidth() {
        return getParent().getListWidth();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(final String label) {
        this.label = label;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public void setIcon(final Icon icon) {
        this.icon = icon;
    }

    @Override
    public void setAction(final Consumer<IContextMenuPoint> action) {
        this.action = action;
    }

    @Override
    public Consumer<IContextMenuPoint> getAction() {
        return action;
    }

    @Override
    public boolean shouldRenderContents() {
        return false;
    }

    @Override
    public void setShouldRenderContents(final boolean shouldRenderContents) {
        // empty stub
    }

    @Override
    public void draw(final int mouseX, final int mouseY) {
        hovered = intersects(mouseX, mouseY);

//        if (hovered && !pressed) {
//            GraphicsHelper.drawColoredModalRect(0, 0, getWidth(), getHeight(), 1.0F, 1.0F, 1.0F, 0.5F, 0.0F);
//        }
        // TODO Check visual effect, maybe we should use upper variant
        float opacity = 0.0F;

        if (pressed) {
            opacity = 0.8F;
        } else if (hovered) {
            opacity = 0.5F;
        } else if (shouldRenderContents()) {
            opacity = 0.3F;
        }

        if (opacity != 0) {
            GraphicsHelper.drawColoredModalRect(0, 0, getWidth(), getHeight(), 1.0F, 1.0F, 1.0F, opacity, 0.0F);
        }

        final int ICON_OFFSET = (getHeight() - GuiLib.standardRenderer().getFontHeight()) / 2;
        if (icon != null) {
            StyleMap.current().drawIcon(icon, ICON_OFFSET, ICON_OFFSET, getHeight());
        }
        GraphicsHelper.drawString(label, ICON_OFFSET + getHeight() + LABEL_OFFSET, ICON_OFFSET, Color.WHITE.getRGB());
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        if (!intersects(mouseX, mouseY)) {
            return;
        }
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

    @Override
    public void onHover(final int mouseX, final int mouseY) {

    }
}
