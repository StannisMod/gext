package ru.quarter.gui.lib.utils;

import net.minecraft.client.shader.Framebuffer;

import java.util.ArrayDeque;
import java.util.Deque;

public class FramebufferStack {

    private static final FramebufferStack instance = new FramebufferStack();

    public static FramebufferStack getInstance() {
        return instance;
    }

    private final Deque<Framebuffer> stack = new ArrayDeque<>();

    private FramebufferStack() {}

    public void apply(Framebuffer framebuffer) {
        framebuffer.bindFramebuffer(true);
        stack.push(framebuffer);
    }

    public Framebuffer flush() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Trying to flush empty FramebufferStack");
        }
        Framebuffer last = stack.pop();
        if (stack.peekFirst() != null) {
            stack.peekFirst().bindFramebuffer(true);
        }
        return last;
    }
}
