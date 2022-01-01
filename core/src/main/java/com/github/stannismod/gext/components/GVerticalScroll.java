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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.StyleMap;
import org.jetbrains.annotations.NotNull;

public class GVerticalScroll extends GScrollBasic {

    protected float scrollFactor;
    protected int scrollBarWidth = 8;

    protected boolean mousePressed = false;
    protected int prevY;

    protected GVerticalScroll() {}

    @Override
    public void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        this.setX(parent.getWidth() - scrollBarWidth);
        this.setY(0);
        this.setWidth(getScrollBarWidth());
        this.setHeight(parent.getHeight());
    }

    public boolean shouldRenderBar() {
        return getScrollable() > 0;
    }

    private int getScrollable() {
        return getTarget().getContentHeight() - getTarget().getHeight();
    }

    private int getScrollBarHeight() {
        return (int) Math.ceil(1.0F * getHeight() * getHeight() / getTarget().getContentHeight());
    }

    private int getScrollBarWidth() {
        return scrollBarWidth;
    }

    private int getScrollBarPosition() {
        int scrolled = getTarget().getScrollVertical();
        return (int)((getHeight() - getScrollBarHeight()) * (1.0F * scrolled / getScrollable()));
    }

    private int interpolate(int from, int to, float partialTicks) {
        if (Math.abs(to - from) < 0.000001) {
            return to;
        }
        return from + (int)((to - from) * (1 - partialTicks));
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (!shouldRenderBar()) {
            return;
        }
        prevY = interpolate(prevY, getScrollBarPosition(), partialTicks);
        //prevY = getScrollBarPosition();

        StyleMap.current().drawVerticalScrollTrace(getX(), getY(), getWidth(), getHeight());
        StyleMap.current().drawVerticalScrollBar(getX(), prevY, getWidth(), getScrollBarHeight());
    }

    @Override
    public void onMousePressed(final int mouseX, final int mouseY, final int mouseButton) {
        if (!shouldRenderBar()) {
            return;
        }
        super.onMousePressed(mouseX, mouseY, mouseButton);
        if (intersects(mouseX, mouseY)) {
            float f = 1.0F * (mouseY - getScrollBarHeight() / 2) / (getHeight() - getScrollBarHeight());
            if (f > 1.0F) {
                f = 1.0F;
            } else if (f < 0.0F) {
                f = 0.0F;
            }
            getTarget().setScrollVertical((int)(getScrollable() * f));
            mousePressed = true;
        } else {
            mousePressed = false;
        }
    }

    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        if (!shouldRenderBar()) {
            return;
        }
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        mousePressed = false;
    }

    @Override
    public void onMouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double xAmount, final double yAmount) {
        if (!shouldRenderBar()) {
            return;
        }
        super.onMouseDragged(mouseX, mouseY, mouseButton, xAmount, yAmount);
        if (mousePressed) {
            float f = 1.0F * ((int) mouseY - getScrollBarHeight() / 2) / (getHeight() - getScrollBarHeight());
            if (f > 1.0F) {
                f = 1.0F;
            } else if (f < 0.0F) {
                f = 0.0F;
            }
            getTarget().setScrollVertical((int)(getScrollable() * f));
        }
    }

    @Override
    public void onMouseScrolled(final int mouseX, final int mouseY, final double amountScrolled) {
        if (!shouldRenderBar()) {
            return;
        }
        super.onMouseScrolled(mouseX, mouseY, amountScrolled);
        if (getTarget().intersectsInner(mouseX, mouseY)) {
            int scrolled = (int) (-amountScrolled * scrollFactor);
            int scrollable = getScrollable();
            int result = getTarget().getScrollVertical() + scrolled;

            if (result < 0) {
                result = 0;
            } else if (result > scrollable) {
                result = scrollable;
            }
            getTarget().setScrollVertical(result);
            mousePressed = false;
        }
    }

    public static class Builder<SELF extends Builder<?, T>, T extends GVerticalScroll> extends ComponentBuilder<SELF, T> {

        public SELF barWidth(int width) {
            instance().scrollBarWidth = width;
            return self();
        }

        public SELF scrollFactor(float scrollFactor) {
            instance().scrollFactor = scrollFactor;
            return self();
        }

        @Override
        public SELF size(final int width, final int height) {
            throw new UnsupportedOperationException("Sizing GVerticalScroll is forbidden");
        }

        @Override
        public SELF placeAt(final int x, final int y) {
            throw new UnsupportedOperationException("Placing GVerticalScroll is forbidden");
        }
    }
}
