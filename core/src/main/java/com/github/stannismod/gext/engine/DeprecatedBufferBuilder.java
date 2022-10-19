package com.github.stannismod.gext.engine;

import org.lwjgl.opengl.GL11;

public class DeprecatedBufferBuilder implements IBufferBuilder<DeprecatedBufferBuilder> {

    @Override
    public DeprecatedBufferBuilder begin(final int mode) {
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
}
