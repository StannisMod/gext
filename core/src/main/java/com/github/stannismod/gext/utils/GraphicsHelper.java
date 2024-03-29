/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.adapter.IFontRenderer;
import com.github.stannismod.gext.engine.GlStateManager;
import com.github.stannismod.gext.engine.GraphicsEngine;
import com.github.stannismod.gext.engine.VertexFormat;
import org.lwjgl.opengl.GL11;

public final class GraphicsHelper {

    public static void drawCenteredScaledString(String text, int x, int y, float scale, int color) {
        drawCenteredScaledString(GExt.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawCenteredScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1.0F);
        drawCenteredString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GlStateManager.popMatrix();
    }

    public static void drawScaledString(String text, int x, int y, float scale, int color) {
        drawScaledString(GExt.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1.0F);
        drawString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredString(String text, int x, int y, int color) {
        drawCenteredString(GExt.standardRenderer(), text, x, y, color);
    }

    public static void drawCenteredString(IFontRenderer fontRenderer, String text, int x, int y, int color) {
        drawString(text, x - fontRenderer.getStringWidth(text) / 2, y, color);
    }

    public static void drawString(String text, int x, int y, int color) {
        drawString(GExt.standardRenderer(), text, x, y, color);
    }

    public static void drawString(IFontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawString(text, x, y, color);
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
        GL11.glScissor(x, GExt.getView().getViewHeight() - (y + height), width, height);
    }

    public static void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
        float f = 1.0F / textureSizeX;
        float f1 = 1.0F / textureSizeY;

        GlStateManager.enableTexture();

        GraphicsEngine.begin(GL11.GL_QUADS, VertexFormat.POSITION_TEX)
            .pos(x, y + height, zLevel).tex(u * f, (v + textureHeight) * f1).endVertex()
            .pos(x + width, y + height, zLevel).tex((u + textureWidth) * f, (v + textureHeight) * f1).endVertex()
            .pos(x + width, y, zLevel).tex((u + textureWidth) * f, v * f1).endVertex()
            .pos(x, y, zLevel).tex(u * f, v * f1).endVertex()
        .draw();
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        drawTexturedModalRect(x, y, width, height, textureX, textureY, width, height, 256, 256, zLevel);
    }

    public static void drawColoredModalRect(int x, int y, int width, int height, float r, float g, float b, float a, float zLevel) {
        drawColoredModalRect(x, y, width, height, (int)(r * 255), (int)(g * 255), (int)(b * 255), (int)(a * 255), zLevel);
    }

    public static void drawColoredModalRect(int x, int y, int width, int height, int r, int g, int b, int a, float zLevel) {
        //GExt.getResourceManager().helper().drawColoredModalRect(x, y, width, height, r, g, b, a, zLevel);
        GraphicsEngine.begin(GL11.GL_QUADS, VertexFormat.POSITION_TEX)
            .pos(x, y + height, zLevel).color4(r, g, b, a).endVertex()
            .pos(x + width, y + height, zLevel).color4(r, g, b, a).endVertex()
            .pos(x + width, y, zLevel).color4(r, g, b, a).endVertex()
            .pos(x, y, zLevel).color4(r, g, b, a).endVertex()
        .draw();
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(0.0F, 0.0F, zLevel);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glColor4f(r, g, b, a) ;
//
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x + width, y);
//        GL11.glVertex2d(x + width, y + height);
//        GL11.glVertex2d(x, y + height);
//
//        GL11.glEnd();
//        GlStateManager.popMatrix();
    }
}