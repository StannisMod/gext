package ru.quarter.gui.lib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.GuiLib;

/**
 * The helper class represents more GUI rendering instruments
 */

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = GuiLib.MODID)
public class Graphics {

    /**
     * Draws centered scaled string without shadow
     * You must use it ONLY in GuiContainer#drawGuiForegroundLayer()
     */

    public static void drawCenteredScaledString(FontRenderer fontRendererIn, String text, int guiLeft, int guiTop, int x, int y, double scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-guiLeft, -guiTop, 0);
        GlStateManager.scale(scale, scale, scale);
        drawCenteredString(fontRendererIn, text, (int) ((guiLeft + x) / scale), (int) ((guiTop + y) / scale), color);
        GlStateManager.popMatrix();
    }

    /**
     * Draws scaled string without shadow
     * You must use it ONLY in GuiContainer#drawGuiForegroundLayer()
     */

    public static void drawScaledString(FontRenderer fontRendererIn, String text, int guiLeft, int guiTop, int x, int y, double scale, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-guiLeft, -guiTop, 0);
        GlStateManager.scale(scale, scale, scale);
        drawString(fontRendererIn, text, (int) ((guiLeft + x) / scale), (int) ((guiTop + y) / scale), color);
        GlStateManager.popMatrix();
    }

    /**
     * Draws centered string without shadow
     */

    public static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    /**
     * Draws string without shadow
     */
    public static void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, x, y, color);
    }

    /**
     * Represents the Minecraft GUI adaptation of glScissor mechanism
     *
     * @param x start X coordinate
     * @param y start Y coordinate
     * @param width new window width
     * @param height new window height
     */
    public static void glScissor(Minecraft mc, int x, int y, int width, int height) {
        int scale = mc.gameSettings.guiScale;
        GL11.glScissor(x * scale, mc.displayHeight - (y + height) * scale, width * scale, height * scale);
    }

    public static void drawPlayerFace(Minecraft mc, int x, int y, int width, int height, float zLevel) {
        mc.getRenderManager().renderEngine.bindTexture(mc.player.getLocationSkin());
        drawTexturedModalRect(x, y, width, height, 32, 32, 32, 32, 256, 256, zLevel);
    }

    /**
     * Draws TexturedModalRect with the screen coordinates and texture coordinates
     * With high resolution GUI textures please use this
     */
    public static void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel) {
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

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        drawTexturedModalRect(x, y, width, height, textureX, textureY, width, height, 256, 256, zLevel);
    }

    public static void drawItemStack(RenderItem render, ItemStack stack, int x, int y) {
        drawItemStack(render, stack, x, y, null);
    }

    public static void drawItemStack(RenderItem render, ItemStack stack, int x, int y, String altText) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        render.zLevel = 200.0F;
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        render.renderItemAndEffectIntoGUI(stack, x, y);
        render.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        render.zLevel = 0.0F;
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Draws an entity on the screen looking toward the cursor.
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, int offsetX, int offsetY, EntityLivingBase ent) {
        mouseX = offsetX + posX - mouseX;
        mouseY = offsetY + posY - mouseY;

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan((double) (mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float) Math.atan((double) (mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.setRenderOutlines(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    // Custom hooks for disabling player's nickname rendering with player model

    private static boolean enabled = false;
    private static String nickname;
    private static EntityPlayer player;

    public static void startRenderPlayer(EntityPlayer entity) {
        enabled = true;
        player = entity;
        nickname = entity.getDisplayName().getFormattedText();
        entity.refreshDisplayName();
    }

    public static void endRenderPlayer() {
        enabled = false;
        player.refreshDisplayName();
    }

    @SubscribeEvent
    public static void onDisplayNameChanged(PlayerEvent.NameFormat event) {
        if (nickname == null)
            nickname = event.getDisplayname();
        if (enabled)
            event.setDisplayname("");
        else
            event.setDisplayname(nickname);
    }
}