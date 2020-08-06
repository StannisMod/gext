package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.adapter.IFontRenderer;
import ru.quarter.gui.lib.api.adapter.IGraphicsHelper;

public class MinecraftGraphicsHelper implements IGraphicsHelper {

    public static final MinecraftGraphicsHelper INSTANCE = new MinecraftGraphicsHelper();

    private MinecraftGraphicsHelper() {}

    @Override
    public void drawCenteredScaledString(IFontRenderer fontRenderer, String text, int x, int y, double scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1.0F);
        drawCenteredString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GlStateManager.popMatrix();
    }

    @Override
    public void drawScaledString(IFontRenderer fontRenderer, String text, int x, int y, float scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 1.0F);
        drawString(fontRenderer, text, (int) (x / scale), (int) (y / scale), color);
        GlStateManager.popMatrix();
    }

    @Override
    public void drawCenteredString(IFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    @Override
    public void drawString(IFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x, y, color);
    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scale = mc.gameSettings.guiScale;
        GL11.glScissor(x * scale, mc.displayHeight - (y + height) * scale, width * scale, height * scale);
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
        float f = 1.0F / textureSizeX;
        float f1 = 1.0F / textureSizeY;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, zLevel).tex(u * f, (v + (float)textureHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, zLevel).tex((u + (float)textureWidth) * f, (v + (float)textureHeight) * f1).endVertex();
        bufferbuilder.pos(x + width, y, zLevel).tex((u + (float)textureWidth) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, zLevel).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        drawTexturedModalRect(x, y, width, height, textureX, textureY, width, height, 256, 256, zLevel);
    }
}
