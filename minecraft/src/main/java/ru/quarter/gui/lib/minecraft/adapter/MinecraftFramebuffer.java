package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.shader.Framebuffer;
import ru.quarter.gui.lib.api.adapter.IFramebuffer;

public class MinecraftFramebuffer implements IFramebuffer {

    private final Framebuffer framebuffer;

    public MinecraftFramebuffer(Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public void color(float r, float g, float b, float a) {
        framebuffer.setFramebufferColor(r, g, b, a);
    }

    @Override
    public void bind() {
        framebuffer.bindFramebuffer(true);
    }

    @Override
    public void unbind() {
        framebuffer.unbindFramebuffer();
    }

    @Override
    public void render(int width, int height) {
        framebuffer.framebufferRenderExt(width, height, true);
    }

    @Override
    public void delete() {
        framebuffer.deleteFramebuffer();
    }

    @Override
    public void clear() {
        framebuffer.framebufferClear();
    }
}
