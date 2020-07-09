package ru.quarter.gui.lib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class TextureMapping {

    private final ResourceLocation location;
    private int u;
    private int v;
    private int textureX;
    private int textureY;
    private int textureWidth;
    private int textureHeight;

    public TextureMapping(ResourceLocation location) {
        this.location = location;
    }

    public TextureMapping(ResourceLocation location, int u, int v, int textureX, int textureY, int textureWidth, int textureHeight) {
        this(location);
        this.u = u;
        this.v = v;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public TextureMapping(ResourceLocation location, int u, int v, int textureX, int textureY) {
        this(location, u, v, textureX, textureY, 256, 256);
    }

    public void draw(int x, int y, int width, int height, float zLevel) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        GraphicsHelper.drawTexturedModalRect(x, y, width, height, u, v, textureX, textureY, textureWidth, textureHeight, zLevel);
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getTextureX() {
        return textureX;
    }

    public void setTextureX(int textureX) {
        this.textureX = textureX;
    }

    public int getTextureY() {
        return textureY;
    }

    public void setTextureY(int textureY) {
        this.textureY = textureY;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }
}
