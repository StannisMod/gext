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

package com.github.quarter.gui.lib.components.container;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsComponentScroll;
import com.github.quarter.gui.lib.api.IScrollable;
import com.github.quarter.gui.lib.utils.ComponentBuilder;
import org.lwjgl.opengl.GL11;

public class GPanel<T extends IGraphicsComponent> extends BasicLayout<T> implements IScrollable {

    private IGraphicsComponentScroll scrollHandler;
    private int scrollVertical;
    private int scrollHorizontal;

    protected boolean wrapContent;

    protected GPanel() {}

    @Override
    public int addComponent(int depth, T component) {
        int id = super.addComponent(depth, component);
        if (wrapContent) {
            this.setWidth(getLayout().getEfficientWidth());
            this.setHeight(getLayout().getEfficientHeight());
        }
        return id;
    }

    @Override
    public void setScrollHandler(IGraphicsComponentScroll handler) {
        if (handler == null) {
            throw new IllegalArgumentException("ScrollHandler mustn't be null");
        }
        scrollHandler = handler;
        scrollHandler.setTarget(this);
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
        return getLayout().getContentWidth();
    }

    @Override
    public int getContentHeight() {
        return getLayout().getContentHeight();
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
    public void draw(int mouseX, int mouseY) {
        if (scrollEnabled() && scrollHandler.checkUpdates()) {
            scrollHandler.update();
        }
        GL11.glTranslatef(-scrollHorizontal, -scrollVertical, 0.0F);
        super.draw(mouseX, mouseY);
    }

    public static class Builder<SELF extends Builder<?, T>, T extends GPanel<? extends IGraphicsComponent>> extends ComponentBuilder<SELF, T> {

        protected int xOffset;
        protected int yOffset;

        public SELF offsets(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return self();
        }

        public SELF setWrapContent() {
            instance().wrapContent = true;
            return self();
        }

        @Override
        public T build() {
            instance().setLayout(Layouts.offset(xOffset, yOffset));
            return super.build();
        }
    }
}
