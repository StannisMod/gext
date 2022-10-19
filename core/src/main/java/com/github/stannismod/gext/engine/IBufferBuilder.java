package com.github.stannismod.gext.engine;

public interface IBufferBuilder<T extends IBufferBuilder<T>> {

    T begin(int mode);

    T pos(float x, float y, float z);

    default T pos(float x, float y) {
        return pos(x, y, 0.0F);
    }

    T tex(float u, float v);

    T color4(float r, float g, float b, float a);

    default T color3(float r, float g, float b) {
        return color4(r, g, b, 1.0F);
    }

    T endVertex();

    void draw();
}