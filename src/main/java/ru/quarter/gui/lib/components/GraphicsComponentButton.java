package ru.quarter.gui.lib.components;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.utils.GraphicsComponentInitializationException;
import ru.quarter.gui.lib.utils.TextureMapping;

public class GraphicsComponentButton extends GraphicsComponentBasic {

    private TextureMapping activeMapping;
    private TextureMapping inactiveMapping;
    private TextureMapping mapping;
    private IGraphicsComponentListener action;
    private GraphicsComponentLabel label;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    public boolean hasAction() {
        return action != null;
    }

    public boolean hasLabel() {
        return label != null;
    }

    @Override
    public boolean checkUpdates() {
        return hovered != prevHovered;
    }

    @Override
    public void update() {
        if (active && !hovered) {
            active = false;
            mapping = inactiveMapping;
        }
        needUpdate = false;
    }

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw(int mouseX, int mouseY) {
        prevHovered = hovered;
        hovered = false;

        mapping.draw(0, 0, getWidth(), getHeight(), 0);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        hovered = true;
        if (hasLabel()) {
            label.onHover(mouseX, mouseY);
        }
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        active = true;
        mapping = activeMapping;
        if (hasLabel()) {
            label.onMousePressed(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        active = false;
        mapping = inactiveMapping;
        if (hasAction()) {
            action.execute(this);
        }
        if (hasLabel()) {
            label.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(Minecraft mc, int w, int h) {}

    public static class Builder {

        private final GraphicsComponentButton instance = new GraphicsComponentButton();
        private boolean active;

        public Builder activeTexture(ResourceLocation location) {
            return activeTexture(location, 256, 256);
        }

        public Builder activeTexture(ResourceLocation location, int textureWidth, int textureHeight) {
            active = true;
            instance.activeMapping = new TextureMapping(location);
            instance.activeMapping.setTextureWidth(textureWidth);
            instance.activeMapping.setTextureHeight(textureHeight);
            return this;
        }

        public Builder inactiveTexture(ResourceLocation location) {
            return inactiveTexture(location, 256, 256);
        }

        public Builder inactiveTexture(ResourceLocation location, int textureWidth, int textureHeight) {
            active = true;
            instance.inactiveMapping = new TextureMapping(location);
            instance.inactiveMapping.setTextureWidth(textureWidth);
            instance.inactiveMapping.setTextureHeight(textureHeight);
            return this;
        }

        public Builder uv(int startU, int startV, int u, int v) {
            if (active) {
                instance.activeMapping.setU(startU);
                instance.activeMapping.setV(startV);
                instance.activeMapping.setTextureX(u);
                instance.activeMapping.setTextureY(v);
            } else {
                instance.inactiveMapping.setU(startU);
                instance.inactiveMapping.setV(startV);
                instance.inactiveMapping.setTextureX(u);
                instance.inactiveMapping.setTextureY(v);
            }
            return this;
        }

        public Builder action(IGraphicsComponentListener listener) {
            instance.action = listener;
            return this;
        }

        public Builder label(GraphicsComponentLabel label) {
            label.x = instance.getWidth() / 2;
            label.y = instance.getHeight() / 2;
            instance.label = label;
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

        public GraphicsComponentBasic build() {
            if (instance.inactiveMapping == null) {
                throw new GraphicsComponentInitializationException("Can't build GraphicsComponentButton without inactive texture");
            }
            if (instance.activeMapping == null) {
                instance.activeMapping = instance.inactiveMapping;
            }
            if (!instance.hasAction()) {
                GuiLib.warn("GraphicsComponentButton was built without an action. It can be inferred statement, but in most cases indicates a broken component");
            }
            return instance;
        }
    }
}
