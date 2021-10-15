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
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.ILayoutType;
import com.github.quarter.gui.lib.utils.LayoutContent;

public class Layouts {

    public enum Plane {
        VERTICAL,
        HORIZONTAL
    }

    public static FixedLayoutType fixed() {
        return new FixedLayoutType();
    }

    public static OffsetLayoutType offset(int xOffset, int yOffset) {
        return new OffsetLayoutType(xOffset, yOffset);
    }

    public static LinearLayoutType linear(Plane plane, int interval, int xOffset, int yOffset) {
        return new LinearLayoutType(plane, interval, xOffset, yOffset);
    }

    public static CircularLayoutType circular(int radius) {
        return new CircularLayoutType(radius);
    }

    public static GridLayoutType grid(int cellWidth, int cellHeight) {
        return new GridLayoutType(cellWidth, cellHeight);
    }

    public static abstract class BaseLayoutType implements ILayoutType {

        protected IGraphicsLayout<? extends IGraphicsComponent> target;
        private int contentMinX;
        private int contentMaxX;
        private int contentMinY;
        private int contentMaxY;

        public int getContentWidth() {
            return contentMaxX - contentMinX;
        }

        public int getContentHeight() {
            return contentMaxY - contentMinY;
        }

        @Override
        public int getEfficientWidth() {
            return getContentWidth();
        }

        @Override
        public int getEfficientHeight() {
            return getContentHeight();
        }

        protected LayoutContent<? extends IGraphicsComponent> getContent() {
            return target.getContent();
        }

        @Override
        public void setTarget(final IGraphicsLayout<? extends IGraphicsComponent> target) {
            this.target = target;
        }

        protected void drop() {
            contentMinX = contentMaxX = contentMinY = contentMaxY = 0;
        }

        /**
         * Recalculates the new bounds with given component
         * @param component the component given
         */
        protected void expandWith(IGraphicsComponent component) {
            contentMinX = Math.min(contentMinX, component.getX());
            contentMaxX = Math.max(contentMaxX, component.getX() + component.getWidth());
            contentMinY = Math.min(contentMinY, component.getY());
            contentMaxY = Math.max(contentMaxY, component.getY() + component.getHeight());
        }

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            expandWith(component);
        }

        @Override
        public void onComponentRemoved(final IGraphicsComponent component) {
            // fully recalc all sizes from zero
            drop();
            for (IGraphicsComponent c : getContent().getContent().values()) {
                if (c != component) {
                    expandWith(c);
                }
            }
        }
    }

    public static class FixedLayoutType extends BaseLayoutType {

        protected FixedLayoutType() {}

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            super.onComponentPlaced(component);
        }

        @Override
        public void onComponentRemoved(final IGraphicsComponent component) {
            super.onComponentRemoved(component);
        }
    }

    public static class OffsetLayoutType extends BaseLayoutType {

        private final int xOffset;
        private final int yOffset;

        protected OffsetLayoutType() {
            this(0, 0);
        }

        protected OffsetLayoutType(final int xOffset, final int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        @Override
        public int getEfficientWidth() {
            return super.getEfficientWidth() + xOffset * 2;
        }

        @Override
        public int getEfficientHeight() {
            return super.getEfficientHeight() + yOffset * 2;
        }

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            // bouncing from left-up corner
            component.shiftX(xOffset);
            component.shiftY(yOffset);
            // TODO Check if bouncing from right-down corner is needed
//            if (component.getX() + component.getWidth() > target.getWidth() - xOffset) {
//                component.shiftX(target.getWidth() - (component.getX() + component.getWidth() + xOffset));
//            }
//            if (component.getY() + component.getHeight() > target.getHeight() - yOffset) {
//                component.shiftY(target.getHeight() - (component.getY() + component.getHeight() + yOffset));
//            }
            super.onComponentPlaced(component);
        }
    }

    public static class LinearLayoutType extends OffsetLayoutType {

        private final Plane plane;
        private final int interval;

        protected LinearLayoutType(final Plane plane, final int interval, final int xOffset, final int yOffset) {
            super(xOffset, yOffset);
            this.plane = plane;
            this.interval = interval;
        }

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            switch (plane) {
                case VERTICAL:
                    component.setX(0);
                    component.setY(getContentHeight());
                    if (target.size() > 1) {
                        component.shiftY(interval);
                    }
                    break;
                case HORIZONTAL:
                    component.setY(0);
                    component.setX(getContentWidth());
                    if (target.size() > 1) {
                        component.shiftX(interval);
                    }
            }
            super.onComponentPlaced(component);
        }
    }

    public static class CircularLayoutType extends OffsetLayoutType {

        private final int radius;

        protected CircularLayoutType(final int radius) {
            this.radius = radius;
        }

        protected CircularLayoutType(final int radius, final int xOffset, final int yOffset) {
            super(xOffset, yOffset);
            this.radius = radius;
        }

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            drop();
            final int count = target.size();
            double alpha = 1.5 * Math.PI / count; // 1.5 because our '0' should be on top, not right
            for (IGraphicsComponent c : getContent().getContent().values()) {
                int centerX = radius + (int)(Math.cos(alpha) * radius);
                int centerY = radius + (int)(Math.sin(alpha) * radius);
                c.setX(centerX - c.getWidth() / 2);
                c.setY(centerY - c.getHeight() / 2);
            }
            super.onComponentPlaced(component);
        }
    }

    public static class GridLayoutType extends OffsetLayoutType {

        private final int cellWidth;
        private final int cellHeight;

        protected GridLayoutType(final int cellWidth, final int cellHeight) {
            this(cellWidth, cellHeight, 0, 0);
        }

        protected GridLayoutType(final int cellWidth, final int cellHeight, final int xOffset, final int yOffset) {
            super(xOffset, yOffset);
            this.cellWidth = cellWidth;
            this.cellHeight = cellHeight;
        }

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            final int cellsCapacityX = target.getWidth() / cellWidth;
            final int cellsCapacityY = target.getHeight() / cellHeight;
            int pos = target.size() + 1;
            final int posX = pos % cellsCapacityX;
            final int posY = pos / cellsCapacityY;
            component.setX(posX * cellWidth);
            component.setY(posY * cellWidth);
            super.onComponentPlaced(component);
        }
    }
}
