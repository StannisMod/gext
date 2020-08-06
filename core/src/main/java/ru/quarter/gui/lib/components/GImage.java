package ru.quarter.gui.lib.components;

import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.utils.TextureMapping;

public class GImage extends GBasic {

    private TextureMapping mapping;

    @Override
    public boolean checkUpdates() {
        return false;
    }

    @Override
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw(int mouseX, int mouseY) {
        mapping.draw(0, 0, getWidth(), getHeight(), 0);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder {

        private final GImage instance = new GImage();

        public Builder texture(IResource location) {
            return texture(location, 256, 256);
        }

        public Builder texture(IResource location, int textureWidth, int textureHeight) {
            instance.mapping = new TextureMapping(location);
            instance.mapping.setTextureWidth(textureWidth);
            instance.mapping.setTextureHeight(textureHeight);
            return this;
        }

        public Builder uv(int startU, int startV, int u, int v) {
            instance.mapping.setU(startU);
            instance.mapping.setV(startV);
            instance.mapping.setTextureX(u);
            instance.mapping.setTextureY(v);
            return this;
        }

        public Builder size(int width, int height) {
            instance.width = width;
            instance.height = height;
            return this;
        }

        public Builder placeAt(int x, int y) {
            instance.x = x;
            instance.y = y;
            return this;
        }

        public GImage build() {
            return instance;
        }
    }
}
