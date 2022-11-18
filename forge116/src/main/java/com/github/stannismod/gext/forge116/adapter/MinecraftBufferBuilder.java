package com.github.stannismod.gext.forge116.adapter;

import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.IBufferBuilder;
import com.github.stannismod.gext.engine.VertexFormat;
import com.mojang.blaze3d.systems.RenderSystem;
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
        Tessellator.getInstance().getBuilder().begin(mode, f);
        return this;
    }

    @Override
    public MinecraftBufferBuilder pos(final float x, final float y, final float z) {
        Tessellator.getInstance().getBuilder().vertex(x, y, z);
        return this;
    }

    @Override
    public MinecraftBufferBuilder tex(final float u, final float v) {
        Tessellator.getInstance().getBuilder().uv(u, v);
        return this;
    }

    @Override
    public MinecraftBufferBuilder color4(final int r, final int g, final int b, final int a) {
        Tessellator.getInstance().getBuilder().color(r, g, b, a);
        return this;
    }

    @Override
    public MinecraftBufferBuilder endVertex() {
        Tessellator.getInstance().getBuilder().endVertex();
        return this;
    }

    @Override
    public void draw() {
        Tessellator.getInstance().end();
    }

    @Override
    public void bindTexture(final ITexture texture) {
        RenderSystem.bindTexture(texture.getGlTextureId());
    }
}
