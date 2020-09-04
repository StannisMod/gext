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

package ru.quarter.gui.lib.components.container;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsComponentScroll;
import ru.quarter.gui.lib.api.IScrollable;

public class GPanel<T extends IGraphicsComponent> extends BasicLayout<T> implements IScrollable {

    private IGraphicsComponentScroll scrollHandler;
    private int scrollVertical;
    private int scrollHorizontal;
    private int contentMinX;
    private int contentMaxX;
    private int contentMinY;
    private int contentMaxY;

    protected GPanel() {}

    @Override
    public int addComponent(int depth, T component) {
        int id = super.addComponent(depth, component);
        contentMinX = Math.min(contentMinX, component.getX());
        contentMaxX = Math.max(contentMaxX, component.getX() + component.getWidth());
        contentMinY = Math.min(contentMinY, component.getY());
        contentMaxY = Math.max(contentMaxY, component.getY() + component.getHeight());
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
    public void draw(int mouseX, int mouseY) {
        if (scrollEnabled() && scrollHandler.checkUpdates()) {
            scrollHandler.update();
        }
        GL11.glTranslatef(-scrollHorizontal, -scrollVertical, 0.0F);
        super.draw(mouseX, mouseY);
        //StyleMap.current().drawFrame(0, 0, 2000, 2000);
    }

    public static class Builder<T extends IGraphicsComponent> {

        private final GPanel<T> instance = new GPanel<>();

        public Builder<T> size(int width, int height) {
            instance.setWidth(width);
            instance.setHeight(height);
            return this;
        }

        public Builder<T> placeAt(int x, int y) {
            instance.setX(x);
            instance.setY(y);
            return this;
        }

        public GPanel<T> build() {
            return instance;
        }
    }
}
