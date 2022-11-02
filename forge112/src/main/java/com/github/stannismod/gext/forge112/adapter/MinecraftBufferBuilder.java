package com.github.stannismod.gext.forge112.adapter;

import com.github.stannismod.gext.engine.IBufferBuilder;
import com.github.stannismod.gext.engine.VertexFormat;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class MinecraftBufferBuilder implements IBufferBuilder<MinecraftBufferBuilder> {

    @Override
    public MinecraftBufferBuilder begin(final int mode, VertexFormat format) {
        net.minecraft.client.renderer.vertex.VertexFormat f;
        switch (format) {
            case POSITION_TEX:
                f = DefaultVertexFormats.POSITION_TEX;
                break;
            case POSITION_TEX_COLOR:
                f = DefaultVertexFormats.POSITION_TEX_COLOR;
                break;
            case POSITION_COLOR:
                f = DefaultVertexFormats.POSITION_COLOR;
                break;
            default:
                throw new IllegalArgumentException();
        }
        Tessellator.getInstance().getBuffer().begin(mode, f);
        return this;
    }

    @Override
    public MinecraftBufferBuilder pos(final float x, final float y, final float z) {
        Tessellator.getInstance().getBuffer().pos(x, y, z);
        return this;
    }

    @Override
    public MinecraftBufferBuilder tex(final float u, final float v) {
        Tessellator.getInstance().getBuffer().tex(u, v);
        return this;
    }

    @Override
    public MinecraftBufferBuilder color4(final float r, final float g, final float b, final float a) {
        Tessellator.getInstance().getBuffer().color(r, g, b, a);
        return this;
    }

    @Override
    public MinecraftBufferBuilder endVertex() {
        Tessellator.getInstance().getBuffer().endVertex();
        return this;
    }

    @Override
    public void draw() {
        Tessellator.getInstance().draw();
    }
}
