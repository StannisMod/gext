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

package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.api.*;
import com.github.stannismod.gext.engine.GlStateManager;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class GPanel<T extends IGraphicsComponent> extends BasicLayout<T> implements IScrollable {

    private IGraphicsComponentScroll scrollHandler;
    private int scrollVertical;
    private int scrollHorizontal;
    private int contentMinX;
    private int contentMaxX;
    private int contentMinY;
    private int contentMaxY;

    /** Some offsets */
    protected int xOffset;
    protected int yOffset;

    protected boolean wrapContent;

    public GPanel(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                  final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                  final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                  final List<IListener> listeners, final IGraphicsListener<? extends BasicLayout<T>> tooltip,
                  final ISelector selector,
                  // additional in this class
                  final IGraphicsComponentScroll scrollHandler,
                  final int xOffset, final int yOffset, final boolean wrapContent) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                xPadding, yPadding, listeners, tooltip, selector);
        if (scrollHandler != null) {
            this.setScrollHandler(scrollHandler);
        }
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.wrapContent = wrapContent;
    }


    @Override
    public String addComponent(int depth, String id, @NotNull T component) {
        super.addComponent(depth, id, component);
        contentMinX = Math.min(contentMinX, component.getX());
        contentMaxX = Math.max(contentMaxX, component.getX() + component.getWidth());
        contentMinY = Math.min(contentMinY, component.getY());
        contentMaxY = Math.max(contentMaxY, component.getY() + component.getHeight());
        if (wrapContent) {
            this.setWidth(this.getContentWidth() + xOffset * 2);
            this.setHeight(this.getContentHeight() + yOffset * 2);
        }
        return id;
    }

    @Override
    public T removeComponent(final String id) {
        T removed = super.removeComponent(id);

        Collection<T> content = this.getContent().getContent().values();
        Optional<T> any = content.stream().findAny();
        if (any.isPresent()) {
            contentMinX = any.get().getX();
            contentMaxX = any.get().getX() + any.get().getWidth();
            contentMinY = any.get().getY();
            contentMaxY = any.get().getY() + any.get().getHeight();
            for (T component : content) {
                contentMinX = Math.min(contentMinX, component.getX());
                contentMaxX = Math.max(contentMaxX, component.getX() + component.getWidth());
                contentMinY = Math.min(contentMinY, component.getY());
                contentMaxY = Math.max(contentMaxY, component.getY() + component.getHeight());
            }
        } else {
            contentMinX = contentMaxX = 0;
            contentMinY = contentMaxY = 0;
        }

        if (wrapContent) {
            this.setWidth(this.getContentWidth() + xOffset * 2);
            this.setHeight(this.getContentHeight() + yOffset * 2);
        }

        return removed;
    }

    @Override
    public void setScrollHandler(@NotNull IGraphicsComponentScroll handler) {
        scrollHandler = handler;
        scrollHandler.setTarget(this);
        scrollHandler.setParent(this);
    }

    @Override
    public IGraphicsComponentScroll getScrollHandler() {
        return scrollHandler;
    }

    @Override
    public int getScrollVertical() {
        return scrollVertical;
    }

    @Override
    public int getScrollHorizontal() {
        return scrollHorizontal;
    }

    @Override
    public void setScrollVertical(int value) {
        scrollVertical = value;
    }

    @Override
    public void setScrollHorizontal(int value) {
        scrollHorizontal = value;
    }

    @Override
    public int getContentWidth() {
        return contentMaxX - contentMinX;
    }

    @Override
    public int getContentHeight() {
        return contentMaxY - contentMinY;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        super.onMousePressed(mouseX + scrollHorizontal, mouseY + scrollVertical, mouseButton);
        if (scrollEnabled()) {
            scrollHandler.onMousePressed(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleased(mouseX + scrollHorizontal, mouseY + scrollVertical, mouseButton);
        if (scrollEnabled()) {
            scrollHandler.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseInput(final int mouseX, final int mouseY, final int mouseButton) {
        super.onMouseInput(mouseX + scrollHorizontal, mouseY + scrollVertical, mouseButton);
        if (scrollEnabled()) {
            scrollHandler.onMouseInput(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double xAmount, final double yAmount) {
        super.onMouseDragged(mouseX + scrollHorizontal, mouseY + scrollVertical, mouseButton, xAmount, yAmount);
        if (scrollEnabled()) {
            scrollHandler.onMouseDragged(mouseX, mouseY, mouseButton, xAmount, yAmount);
        }
    }

    @Override
    public void onMouseMoved(final int mouseX, final int mouseY) {
        super.onMouseMoved(mouseX + scrollHorizontal, mouseY + scrollVertical);
        if (scrollEnabled()) {
            scrollHandler.onMouseMoved(mouseX, mouseY);
        }
    }

    @Override
    public void onMouseScrolled(final int mouseX, final int mouseY, final double amountScrolled) {
        super.onMouseScrolled(mouseX + scrollHorizontal, mouseY + scrollVertical, amountScrolled);
        if (scrollEnabled()) {
            scrollHandler.onMouseScrolled(mouseX, mouseY, amountScrolled);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        if (scrollEnabled()) {
            if (scrollHandler.checkUpdates()) {
                scrollHandler.update();
            }
            scrollHandler.draw(mouseX, mouseY, partialTicks);
        }
        GlStateManager.translate(-scrollHorizontal, -scrollVertical, 0.0F);
        super.draw(mouseX, mouseY, partialTicks);
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GPanel<? extends IGraphicsComponent>> extends BasicLayout.Builder<SELF, T> {

        protected int xOffset;
        protected int yOffset;
        protected boolean wrapContent;
        protected IGraphicsComponentScroll scrollHandler;

        public SELF offsets(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return self();
        }

        public SELF setWrapContent() {
            this.wrapContent = true;
            return self();
        }

        public SELF scrollHandler(IGraphicsComponentScroll scrollHandler) {
            assertRight(scrollHandler != null, "Passed scroll handler can't be null");
            this.scrollHandler = scrollHandler;
            return self();
        }
    }
}
