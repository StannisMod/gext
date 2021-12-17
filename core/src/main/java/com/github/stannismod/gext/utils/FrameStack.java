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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.Features;
import com.github.stannismod.gext.GExt;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.stannismod.gext.utils.GeometryHelper.*;

public class FrameStack {

    private static final FrameStack instance = new FrameStack();

    public static FrameStack getInstance() {
        return instance;
    }

    private final Stack<Rectangle2D> stack = Features.FAST_FRAME_STACK.isEnabled()
        ? new PushOnlyArrayStack<>(Rectangle::new)
        : new DequeStackAdapter<>(new ArrayDeque<>(), Rectangle::new);

    private FrameStack() {}

    public void apply(Rectangle2D frame) {
        stack.push(peek -> {
            if (isEmpty(peek)) {
                peek.setFrame(frame);
            } else {
                intersect(peek, frame);
            }
            normalize(peek);

            bind(frame);
        });
    }

    public Rectangle2D flush() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Trying to flush empty FrameStack");
        }
        Rectangle2D last = stack.pop();
        if (stack.peek() != null) {
            bind(stack.peek());
        }
        return last;
    }

    private void bind(Rectangle2D frame) {
        int x =      (int) frame.getX()      * GExt.getView().getScaleFactor();
        int y =      (int) frame.getY()      * GExt.getView().getScaleFactor();
        int width =  (int) frame.getWidth()  * GExt.getView().getScaleFactor();
        int height = (int) frame.getHeight() * GExt.getView().getScaleFactor();
        GraphicsHelper.glScissor(x, y, width, height);
    }

    /**
     * A data structure that provides a stack with minimum overhead by reusing existing
     * instances of underlying objects. This means this is an effective way to have
     * a seasonal-sized or small-depth stack with elements, which properties can be
     * easily reassigned.
     * @param <T> the type of underlying objects
     * @since 1.5
     */
    private static class PushOnlyArrayStack<T> implements FrameStack.Stack<T> {

        private final ArrayList<T> stack = new ArrayList<>();
        private final Supplier<? extends T> constructor;

        private int index = -1;

        public PushOnlyArrayStack(Supplier<? extends T> constructor) {
            this.constructor = constructor;
        }

        public void push(Consumer<T> user) {
            if (index == stack.size() - 1) {
                stack.add(constructor.get());
            }
            index++;
            user.accept(stack.get(index));
        }

        public T pop() {
            return stack.get(index--);
        }

        @Override
        public T peek() {
            return stack.get(index);
        }

        @Override
        public int size() {
            return index + 1;
        }
    }

    private static class DequeStackAdapter<T> implements FrameStack.Stack<T> {

        private final Deque<T> instance;
        private final Supplier<? extends T> constructor;

        public DequeStackAdapter(final Deque<T> instance, final Supplier<? extends T> constructor) {
            this.instance = instance;
            this.constructor = constructor;
        }

        @Override
        public void push(final Consumer<T> user) {
            T object = constructor.get();
            user.accept(object);
            instance.push(object);
        }

        @Override
        public T pop() {
            return instance.pop();
        }

        @Override
        public T peek() {
            return instance.pop();
        }

        @Override
        public int size() {
            return instance.size();
        }
    }

    private interface Stack<T> {
        void push(Consumer<T> element);

        T pop();

        T peek();

        int size();

        default boolean isEmpty() {
            return size() == 0;
        }
    }
}
