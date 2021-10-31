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

package com.github.quarter.gui.lib.forge1710.adapter;

import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.api.adapter.IGraphicsHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class MinecraftGraphicsHelper implements IGraphicsHelper {

    public static final MinecraftGraphicsHelper INSTANCE = new MinecraftGraphicsHelper();

    private MinecraftGraphicsHelper() {}

    @Override
    public void drawString(IFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x, y, color);
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
        float f = 1.0F / textureSizeX;
        float f1 = 1.0F / textureSizeY;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, zLevel, u * f, (v + (float)textureHeight) * f1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + (float)textureWidth) * f, (v + (float)textureHeight) * f1);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + (float)textureWidth) * f, v * f1);
        tessellator.addVertexWithUV(x, y, zLevel, u * f, v * f1);
        tessellator.draw();
    }

    @Override
    public void drawColoredModalRect(final int x, final int y, final int width, final int height, final float r, final float g, final float b, final float a, final float zLevel) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
        GL11.glColor4f(r, g, b, a);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(x, y + height, zLevel);
        tessellator.addVertex(x + width, y + height, zLevel);
        tessellator.addVertex(x + width, y, zLevel);
        tessellator.addVertex(x, y, zLevel);
        tessellator.draw();
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
