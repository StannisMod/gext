package ru.quarter.gui.lib.api.adapter;

/**
 * The helper class represents more GUI rendering instruments
 */
public interface IGraphicsHelper {
    /**
     * Draws centered scaled string without shadow
     */
    void drawCenteredScaledString(IFontRenderer fontRenderer, String text, int x, int y, double scale, int color);

    /**
     * Draws scaled string without shadow
     */
    void drawScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color);

    /**
     * Draws centered string without shadow
     */
    void drawCenteredString(IFontRenderer fontRendererIn, String text, int x, int y, int color);

    /**
     * Draws string without shadow
     */
    void drawString(IFontRenderer fontRendererIn, String text, int x, int y, int color);

    /**
     * Represents the GUI adaptation of glScissor mechanism
     * Given coordinates are from up-left corner
     * @param x start X coordinate
     * @param y start Y coordinate
     * @param width new window width
     * @param height new window height
     */
    void glScissor(int x, int y, int width, int height);

    /**
     * Draws TexturedModalRect with the screen coordinates and texture coordinates
     * With high resolution GUI textures please use this
     */
    void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel);

    void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel);
}
