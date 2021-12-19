/*
 * Copyright 2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.api.IGraphicsComponent;

public class Alignment {

    public static final Align FIXED = new Fixed();

    public static final Align XCENTER = new XCenter();
    public static final Align YCENTER = new YCenter();
    public static final Align CENTER = new Compose(XCENTER, YCENTER);

    public static final Align LEFT = new Left();
    public static final Align RIGHT = new Right();
    public static final Align TOP = new Top();
    public static final Align BOTTOM = new Bottom();

    public static class Compose implements Align {
        private final Align first, second;

        public Compose(final Align first, final Align second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            first.transform(component, paddingX, paddingY);
            second.transform(component, paddingX, paddingY);
        }
    }

    private static class Fixed implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {}
    }

    private static class XCenter implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            // ignore paddings
            if (!component.hasParent()) {
                return;
            }
            component.setX((component.getParent().getWidth() - component.getWidth()) / 2);
        }
    }

    private static class YCenter implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            // ignore paddings
            if (!component.hasParent()) {
                return;
            }
            component.setY((component.getParent().getHeight() - component.getHeight()) / 2);
        }
    }

    // TODO Add alignment processing in case that parent length is less than component_length + padding * 2
    private static class Left implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            component.setX(findXLeftAlignmentPoint(paddingX, component));
        }
    }

    private static class Right implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            if (!component.hasParent()) {
                return;
            }
            component.setX(findXRightAlignmentPoint(component.getParent().getWidth() - component.getWidth() - paddingX, component));
        }
    }

    private static class Top implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            component.setY(findYLeftAlignmentPoint(paddingY, component));
        }
    }

    private static class Bottom implements Align {
        @Override
        public void transform(final IGraphicsComponent component, final int paddingX, final int paddingY) {
            if (!component.hasParent()) {
                return;
            }
            component.setY(findYRightAlignmentPoint( component.getParent().getHeight() - component.getHeight() - paddingY, component));
        }
    }

    private static int findXLeftAlignmentPoint(int alignmentDistance, IGraphicsComponent component) {
        return findLeftAlignmentPoint(alignmentDistance, component.getWidth(), component.hasParent() ? component.getParent().getWidth() : 0);
    }

    private static int findYLeftAlignmentPoint(int alignmentDistance, IGraphicsComponent component) {
        return findLeftAlignmentPoint(alignmentDistance, component.getHeight(), component.hasParent() ? component.getParent().getHeight() : 0);
    }

    private static int findLeftAlignmentPoint(int alignmentDistance, int componentLength, int parentLength) {
        if (alignmentDistance * 2 + componentLength < parentLength) {
            return alignmentDistance;
        } else {
            return Math.min(alignmentDistance, (parentLength - componentLength) / 2);
        }
    }

    private static int findXRightAlignmentPoint(int alignmentDistance, IGraphicsComponent component) {
        return findRightAlignmentPoint(alignmentDistance, component.getWidth(), component.hasParent() ? component.getParent().getWidth() : 0);
    }

    private static int findYRightAlignmentPoint(int alignmentDistance, IGraphicsComponent component) {
        return findRightAlignmentPoint(alignmentDistance, component.getHeight(), component.hasParent() ? component.getParent().getHeight() : 0);
    }

    private static int findRightAlignmentPoint(int alignmentDistance, int componentLength, int parentLength) {
        if (alignmentDistance * 2 + componentLength < parentLength) {
            return alignmentDistance;
        } else {
            return Math.max(alignmentDistance, (parentLength - componentLength) / 2);
        }
    }
}
