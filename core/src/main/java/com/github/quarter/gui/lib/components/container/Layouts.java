package com.github.quarter.gui.lib.components.container;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.ILayout;
import com.github.quarter.gui.lib.utils.LayoutContent;

public class Layouts {

    public enum Plane {
        VERTICAL,
        HORIZONTAL
    }

    public static FixedLayout fixed() {
        return new FixedLayout();
    }

    public static LinearLayout linear(Plane plane, int interval, int xOffset, int yOffset) {
        return new LinearLayout(plane, interval, xOffset, yOffset);
    }

    public static CircularLayout circular(int radius) {
        return new CircularLayout(radius);
    }

    public static GridLayout grid(int cellWidth, int cellHeight) {
        return new GridLayout(cellWidth, cellHeight);
    }

    public static abstract class BaseLayout implements ILayout {

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

    public static class FixedLayout extends BaseLayout {

        protected FixedLayout() {}

        @Override
        public void onComponentPlaced(final IGraphicsComponent component) {
            super.onComponentPlaced(component);
        }

        @Override
        public void onComponentRemoved(final IGraphicsComponent component) {
            super.onComponentRemoved(component);
        }
    }

    public static class OffsetLayout extends BaseLayout {

        private final int xOffset;
        private final int yOffset;

        protected OffsetLayout() {
            this(0, 0);
        }

        protected OffsetLayout(final int xOffset, final int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
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

    public static class LinearLayout extends OffsetLayout {

        private final Plane plane;
        private final int interval;

        protected LinearLayout(final Plane plane, final int interval, final int xOffset, final int yOffset) {
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

    public static class CircularLayout extends BaseLayout {

        private final int radius;

        protected CircularLayout(final int radius) {
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

    public static class GridLayout extends OffsetLayout {

        private final int cellWidth;
        private final int cellHeight;

        protected GridLayout(final int cellWidth, final int cellHeight) {
            this(cellWidth, cellHeight, 0, 0);
        }

        protected GridLayout(final int cellWidth, final int cellHeight, final int xOffset, final int yOffset) {
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
