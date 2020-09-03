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

package ru.quarter.gui.lib.utils;

import java.awt.geom.Rectangle2D;
import java.util.ArrayDeque;
import java.util.Deque;

public class FrameStack {

    private static final FrameStack instance = new FrameStack();

    public static FrameStack getInstance() {
        return instance;
    }

    private final Deque<Rectangle2D> stack = new ArrayDeque<>();

    private FrameStack() {}

    public void apply(Rectangle2D frame) {
        frame = frame.createIntersection(stack.peekFirst());
        bind(frame);
        stack.push(frame);
    }

    public Rectangle2D flush() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Trying to flush empty FrameStack");
        }
        Rectangle2D last = stack.pop();
        if (stack.peekFirst() != null) {
            bind(stack.peekFirst());
        }
        return last;
    }

    private static void bind(Rectangle2D frame) {
        GraphicsHelper.glScissor((int) frame.getX(), (int) frame.getY(), (int) frame.getWidth(), (int) frame.getHeight());
    }
}
