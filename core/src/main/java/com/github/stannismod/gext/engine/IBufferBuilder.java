package com.github.stannismod.gext.engine;

import com.github.stannismod.gext.api.resource.ITexture;

public interface IBufferBuilder<T extends IBufferBuilder<T>> {

    T begin(int mode, VertexFormat format);

    T pos(float x, float y, float z);

    default T pos(float x, float y) {
        return pos(x, y, 0.0F);
    }

    T tex(float u, float v);

    T color4(int r, int g, int b, int a);

    default T color3(int r, int g, int b) {
        return color4(r, g, b, 255);
    }

    T endVertex();

    void draw();

    void bindTexture(ITexture texture);
}
