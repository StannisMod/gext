package ru.quarter.gui.lib.api.adapter;

public interface IFramebuffer {

    void color(float r, float g, float b, float a);

    void bind();

    void unbind();

    void render(int width, int height);

    void delete();

    void clear();
}
