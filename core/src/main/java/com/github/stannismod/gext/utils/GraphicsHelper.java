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
import org.lwjgl.opengl.GL11;

public class GraphicsHelper {

    public static void drawCenteredScaledString(String text, int x, int y, double scale, int color) {
        drawCenteredScaledString(GExt.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawCenteredScaledString(IFontRenderer fontRenderer, String text, int x, int y, double scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1.0F);
        drawCenteredString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GL11.glPopMatrix();
    }

    public static void drawScaledString(String text, int x, int y, float scale, int color) {
        drawScaledString(GExt.standardRenderer(), text, x, y, scale, color);
    }

    public static void drawScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, 1.0F);
        drawString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GL11.glPopMatrix();
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

    // TODO required in 1.5-RELEASE - VBO optimization(give up glBegin/glEnd)

    private static BufferBuilder tes;

    private static BufferBuilder tes() {
        if (tes == null) {
            tes = BufferBuilder.withSize(4 * 3 * 2 * 100);
        }
        return tes;
    }

    public static void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
        //GExt.getResourceManager().helper().drawTexturedModalRect(x, y, width, height, u, v, textureWidth, textureHeight, textureSizeX, textureSizeY, zLevel);
        float f = 1.0F / textureSizeX;
        float f1 = 1.0F / textureSizeY;

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        tes()
            .vertex3(x, y, zLevel).tex(u * f, v * f1).endVertex()
            .vertex3(x + width, y, zLevel).tex((u + textureWidth) * f, v * f1).endVertex()
            .vertex3(x + width, y + height, zLevel).tex((u + textureWidth) * f, (y + textureHeight) * f1).endVertex()
            .vertex3(x, y + height, zLevel).tex(u * f, (y + textureHeight) * f1)
        .draw(GL11.GL_QUADS);

//        GL11.glBegin(GL11.GL_QUADS);
//
//        GL11.glTexCoord2f(u * f, v * f1);
//        GL11.glVertex3f(x, y, zLevel);
//
//        GL11.glTexCoord2f((u + textureWidth) * f, v * f1);
//        GL11.glVertex3f(x + width, y, zLevel);
//
//        GL11.glTexCoord2f((u + textureWidth) * f, (y + textureHeight) * f1);
//        GL11.glVertex3f(x + width, y + height, zLevel);
//
//        GL11.glTexCoord2f(u * f, (y + textureHeight) * f1);
//        GL11.glVertex3f(x, y + height, zLevel);
//
//        GL11.glEnd();
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        drawTexturedModalRect(x, y, width, height, textureX, textureY, width, height, 256, 256, zLevel);
    }

    public static void drawColoredModalRect(int x, int y, int width, int height, float r, float g, float b, float a, float zLevel) {
        //GExt.getResourceManager().helper().drawColoredModalRect(x, y, width, height, r, g, b, a, zLevel);
//        GL11.glPushMatrix();
//        GL11.glTranslatef(0.0F, 0.0F, zLevel);
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
//        GL11.glPopMatrix();
    }
}