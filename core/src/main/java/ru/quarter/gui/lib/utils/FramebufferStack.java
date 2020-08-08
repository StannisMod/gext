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

import ru.quarter.gui.lib.api.adapter.IFramebuffer;

import java.util.ArrayDeque;
import java.util.Deque;

public class FramebufferStack {

    private static final FramebufferStack instance = new FramebufferStack();

    public static FramebufferStack getInstance() {
        return instance;
    }

    private final Deque<IFramebuffer> stack = new ArrayDeque<>();

    private FramebufferStack() {}

    public void apply(IFramebuffer framebuffer) {
        framebuffer.bind();
        stack.push(framebuffer);
    }

    public IFramebuffer flush() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Trying to flush empty FramebufferStack");
        }
        IFramebuffer last = stack.pop();
        if (stack.peekFirst() != null) {
            stack.peekFirst().bind();
        }
        return last;
    }
}
