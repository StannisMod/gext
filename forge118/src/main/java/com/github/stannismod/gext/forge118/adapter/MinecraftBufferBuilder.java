package com.github.stannismod.gext.forge118.adapter;

import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.IBufferBuilder;
import com.github.stannismod.gext.engine.VertexFormat;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import org.lwjgl.opengl.GL11;

public class MinecraftBufferBuilder implements IBufferBuilder<MinecraftBufferBuilder> {

    @Override
    public MinecraftBufferBuilder begin(final int mode, VertexFormat format) {
        com.mojang.blaze3d.vertex.VertexFormat f;
        switch (format) {
            case POSITION_TEX:
                f = DefaultVertexFormat.POSITION_TEX;
                break;
            case POSITION_TEX_COLOR:
                f = DefaultVertexFormat.POSITION_TEX_COLOR;
                break;
            case POSITION_COLOR:
                f = DefaultVertexFormat.POSITION_COLOR;
                break;
            default:
                throw new IllegalArgumentException();
        }
        com.mojang.blaze3d.vertex.VertexFormat.Mode m = switch (mode) {
            case GL11.GL_QUADS -> com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;
            case GL11.GL_TRIANGLES -> com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLES;
            case GL11.GL_TRIANGLE_STRIP -> com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLE_STRIP;
            case GL11.GL_TRIANGLE_FAN -> com.mojang.blaze3d.vertex.VertexFormat.Mode.TRIANGLE_FAN;
            case GL11.GL_LINES -> com.mojang.blaze3d.vertex.VertexFormat.Mode.LINES;
            case GL11.GL_LINE_STRIP -> com.mojang.blaze3d.vertex.VertexFormat.Mode.LINE_STRIP;
            default -> throw new IllegalArgumentException("Incorrect GL mode");
        };
        Tesselator.getInstance().getBuilder().begin(m, f);
        return this;
    }

    @Override
    public MinecraftBufferBuilder pos(final float x, final float y, final float z) {
        Tesselator.getInstance().getBuilder().vertex(x, y, z);
        return this;
    }

    @Override
    public MinecraftBufferBuilder tex(final float u, final float v) {
        Tesselator.getInstance().getBuilder().uv(u, v);
        return this;
    }

    @Override
    public MinecraftBufferBuilder color4(final int r, final int g, final int b, final int a) {
        Tesselator.getInstance().getBuilder().color(r, g, b, a);
        return this;
    }

    @Override
    public MinecraftBufferBuilder endVertex() {
        Tesselator.getInstance().getBuilder().endVertex();
        return this;
    }

    @Override
    public void draw() {
        Tesselator.getInstance().end();
    }

    @Override
    public void bindTexture(final ITexture texture) {
        RenderSystem.bindTexture(texture.getGlTextureId());
    }
}
