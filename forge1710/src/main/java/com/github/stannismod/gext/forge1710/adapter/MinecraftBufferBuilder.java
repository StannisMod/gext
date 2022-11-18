package com.github.stannismod.gext.forge1710.adapter;

import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.IBufferBuilder;
import com.github.stannismod.gext.engine.VertexFormat;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class MinecraftBufferBuilder implements IBufferBuilder<MinecraftBufferBuilder> {

    // cache all the passed values of current vertex to handle them all at #endVertex()
    // we should do this because of

    private float lastX = -1;
    private float lastY = -1;
    private float lastZ = -1;

    private float lastU = -1;
    private float lastV = -1;

    private int lastR = -1;
    private int lastG = -1;
    private int lastB = -1;
    private int lastA = -1;

    private VertexFormat format;

    @Override
    public MinecraftBufferBuilder begin(final int mode, VertexFormat format) {
        Tessellator.instance.startDrawing(mode);
        this.format = format;
        return this;
    }

    @Override
    public MinecraftBufferBuilder pos(final float x, final float y, final float z) {
        lastX = x;
        lastY = y;
        lastZ = z;
        return this;
    }

    @Override
    public MinecraftBufferBuilder tex(final float u, final float v) {
        lastU = u;
        lastV = v;
        return this;
    }

    @Override
    public MinecraftBufferBuilder color4(final int r, final int g, final int b, final int a) {
        lastR = r;
        lastG = g;
        lastB = b;
        lastA = a;
        return this;
    }

    @Override
    public MinecraftBufferBuilder endVertex() {
        switch (format) {
            case POSITION_TEX:
                Tessellator.instance.addVertexWithUV(lastX, lastY, lastZ, lastU, lastV);
                break;
            case POSITION_COLOR:
                Tessellator.instance.setColorRGBA(lastR, lastG, lastB, lastA);
                Tessellator.instance.addVertex(lastX, lastY, lastZ);
                break;
            case POSITION_TEX_COLOR:
                Tessellator.instance.setColorRGBA(lastR, lastG, lastB, lastA);
                Tessellator.instance.addVertexWithUV(lastX, lastY, lastZ, lastU, lastV);
                break;
        }
        return this;
    }

    @Override
    public void draw() {
        Tessellator.instance.draw();
    }

    @Override
    public void bindTexture(final ITexture texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getGlTextureId());
    }
}
