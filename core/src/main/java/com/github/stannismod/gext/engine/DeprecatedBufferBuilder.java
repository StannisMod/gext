package com.github.stannismod.gext.engine;

import com.github.stannismod.gext.api.resource.ITexture;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class DeprecatedBufferBuilder implements IBufferBuilder<DeprecatedBufferBuilder> {

    @Override
    public DeprecatedBufferBuilder begin(final int mode, final VertexFormat format) {
        GL11.glBegin(mode);
        return this;
    }

    @Override
    public DeprecatedBufferBuilder pos(final float x, final float y, final float z) {
        GL11.glVertex3f(x, y, z);
        return this;
    }

    @Override
    public DeprecatedBufferBuilder tex(final float u, final float v) {
        GL11.glTexCoord2f(u, v);
        return this;
    }

    @Override
    public DeprecatedBufferBuilder color4(final float r, final float g, final float b, final float a) {
        GL11.glColor4f(r, g, b, a);
        return this;
    }

    @Override
    public DeprecatedBufferBuilder endVertex() {
        // no stuff here
        return this;
    }

    @Override
    public void draw() {
        GL11.glEnd();
    }

    @Override
    public void bindTexture(final ITexture texture) {
        glBindTexture(GL_TEXTURE_2D, texture.getGlTextureId());
    }
}
