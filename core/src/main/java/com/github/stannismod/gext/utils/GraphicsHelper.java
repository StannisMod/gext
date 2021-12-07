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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.GuiLib;
import com.github.stannismod.gext.api.adapter.IFontRenderer;
import org.lwjgl.opengl.GL11;

public class GraphicsHelper {

    public static void drawCenteredScaledString(String text, int x, int y, double scale, int color) {
        drawCenteredScaledString(GuiLib.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawCenteredScaledString(IFontRenderer fontRenderer, String text, int x, int y, double scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1.0F);
        drawCenteredString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GL11.glPopMatrix();
    }

    public static void drawScaledString(String text, int x, int y, float scale, int color) {
        drawScaledString(GuiLib.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1.0F);
        drawString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GL11.glPopMatrix();
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        drawCenteredString(GuiLib.standardRenderer(), text, x, y, color);
    }

    public static void drawCenteredString(IFontRenderer fontRenderer, String text, int x, int y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawString(String text, int x, int y, int color) {
        drawString(GuiLib.standardRenderer(), text, x, y, color);
    }

    public static void drawString(IFontRenderer fontRenderer, String text, int x, int y, int color) {
        GuiLib.getResourceManager().helper().drawString(fontRenderer, text, x, y, color);
    }

    /**
     * Represents the GUI adaptation of glScissor mechanism
     * Given coordinates are from up-left corner
     * @param x start X coordinate
     * @param y start Y coordinate
     * @param width new window width
     * @param height new window height
     */
    public static void glScissor(int x, int y, int width, int height) {
        GL11.glScissor(x, GuiLib.getView().getViewHeight() - (y + height), width, height);
    }

    public static void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
        GuiLib.getResourceManager().helper().drawTexturedModalRect(x, y, width, height, u, v, textureWidth, textureHeight, textureSizeX, textureSizeY, zLevel);
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        drawTexturedModalRect(x, y, width, height, textureX, textureY, width, height, 256, 256, zLevel);
    }

    public static void drawColoredModalRect(int x, int y, int width, int height, float r, float g, float b, float a, float zLevel) {
        GuiLib.getResourceManager().helper().drawColoredModalRect(x, y, width, height, r, g, b, a, zLevel);
    }
}