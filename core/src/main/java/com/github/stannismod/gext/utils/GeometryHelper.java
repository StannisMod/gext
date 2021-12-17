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

package com.github.stannismod.gext.utils;

import java.awt.geom.Rectangle2D;

/**
 * Some helper functions for basic geometry objects
 * @since 1.5
 */
public class GeometryHelper {

    public static boolean intersects(Rectangle2D first, Rectangle2D second) {
        return intersects(first, second, 0, 0);
    }

    public static boolean intersects(Rectangle2D first, Rectangle2D second, int shiftX, int shiftY) {
        return first.intersects(
                second.getMinX() + shiftX,
                second.getMinY() + shiftY,
                second.getMaxX() + shiftX,
                second.getMaxY() + shiftY
        );
    }

    public static boolean isEmpty(Rectangle2D frame) {
        return frame.getMinX() == frame.getMaxX() && frame.getMinY() == frame.getMaxY();
    }

    public static Rectangle2D intersect(Rectangle2D to, Rectangle2D from) {
        to.setFrame(
                Math.max((int) to.getMinX(), (int) from.getMinX()),
                Math.max((int) to.getMinY(), (int) from.getMinY()),
                Math.min((int) to.getMaxX(), (int) from.getMaxX()),
                Math.min((int) to.getMaxY(), (int) from.getMaxY())
        );
        return to;
    }

    public static Rectangle2D normalize(Rectangle2D frame) {
        frame.setFrame(
                Math.max(0, (int) frame.getX()),
                Math.max(0, (int) frame.getY()),
                Math.max(0, (int) frame.getWidth()),
                Math.max(0, (int) frame.getHeight())
        );
        return frame;
    }
}
