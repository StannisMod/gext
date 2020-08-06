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
