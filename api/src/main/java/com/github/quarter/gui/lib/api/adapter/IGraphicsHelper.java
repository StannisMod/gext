/*
 * Copyright 2020 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.quarter.gui.lib.api.adapter;

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
