package ru.quarter.gui.lib.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.quarter.gui.lib.utils.Graphics;

import java.awt.*;

public class GraphicsComponentLabel extends GraphicsComponentBasic {

    private final String text;
    private final int color;
    private final FontRenderer fontRenderer;

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, int x, int y) {
        this(fontRenderer, text, Color.BLACK.getRGB(), x, y);
    }

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, int color, int x, int y) {
        super(x, y, fontRenderer.getStringWidth(text), fontRenderer.FONT_HEIGHT);
        this.fontRenderer = fontRenderer;
        this.text = text;
        this.color = color;
    }

    @Override
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw() {
        Graphics.drawString(fontRenderer, text, 0, 0, color);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(Minecraft mc, int w, int h) {}
}
