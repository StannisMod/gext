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
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.StyleMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GVerticalScroll extends GScrollBasic {

    protected float scrollFactor;
    protected int barWidth;

    protected boolean mousePressed = false;

    // interpolation
    protected int prevScrolled;
    protected int scrolled;

    protected int prevY;

    public GVerticalScroll(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                           final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                           final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                           final List<IListener> listeners, int barWidth, float scrollFactor) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners);
        this.barWidth = barWidth;
        this.scrollFactor = scrollFactor;
    }


    @Override
    public void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        this.setX(parent.getWidth() - barWidth);
        this.setY(0);
        this.setWidth(getBarWidth());
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

    private int getBarWidth() {
        return barWidth;
    }

    private int getScrollBarPosition() {
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
        // TODO Add feature for content scrolling interpolation disabling

        prevScrolled = interpolate(prevScrolled, scrolled, partialTicks);
        if (prevScrolled != scrolled) {
            getTarget().setScrollVertical(prevScrolled);
        }
        if (prevY != getScrollBarPosition()) {
            prevY = interpolate(prevY, getScrollBarPosition(), partialTicks);
        }

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
            scrolled = (int)(getScrollable() * f);
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
            scrolled = (int)(getScrollable() * f);
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
            this.scrolled = result;
            mousePressed = false;
        }
    }

    public abstract static class Builder<SELF extends Builder<?, T>, T extends GVerticalScroll> extends ComponentBuilder<SELF, T> {

        protected int barWidth = 8;
        protected float scrollFactor;

        public SELF barWidth(int width) {
            this.barWidth = width;
            return self();
        }

        public SELF scrollFactor(float scrollFactor) {
            this.scrollFactor = scrollFactor;
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
