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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.menu.IContextMenuList;
import com.github.stannismod.gext.api.menu.IContextMenuPoint;
import com.github.stannismod.gext.utils.GraphicsHelper;
import com.github.stannismod.gext.utils.Icon;
import com.github.stannismod.gext.utils.Keyboard;
import com.github.stannismod.gext.utils.StyleMap;

import java.awt.*;
import java.util.function.BiConsumer;

public class ContextMenuPoint extends ContextMenuBase implements IContextMenuPoint {

    private static final int LABEL_OFFSET = 2;

    private String label;
    private Icon icon;
    private BiConsumer<IGraphicsComponent, IContextMenuPoint> action;

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
    public void setAction(final BiConsumer<IGraphicsComponent, IContextMenuPoint> action) {
        if (this instanceof IContextMenuList) {
            this.action = action;
        } else {
            this.action = action.andThen((c, p) -> c.getRoot().setActiveMenu(null));
        }
    }

    @Override
    public BiConsumer<IGraphicsComponent, IContextMenuPoint> getAction() {
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
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
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

        final int ICON_OFFSET = (getHeight() - GExt.standardRenderer().getFontHeight()) / 2;
        int xOffset = 0;
        if (icon != null) {
            StyleMap.current().drawIcon(icon, ICON_OFFSET, ICON_OFFSET, getHeight());
            xOffset = getHeight();
        }
        GraphicsHelper.drawString(label, ICON_OFFSET + xOffset + LABEL_OFFSET, ICON_OFFSET, Color.WHITE.getRGB());
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        if (!intersects(mouseX, mouseY)) {
            return;
        }
        if (action != null) {
            action.accept(getTarget(), this);
        }
        pressed = true;
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        pressed = false;
    }

    @Override
    public void onKeyPressed(final char typedChar, final int keyCode) {
        if (hovered && keyCode == Keyboard.KEY_ENTER) {
            action.accept(getTarget(), this);
        }
    }

    @Override
    public void onHover(final int mouseX, final int mouseY) {

    }
}
