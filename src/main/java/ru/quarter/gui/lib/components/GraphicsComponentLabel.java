package ru.quarter.gui.lib.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.quarter.gui.lib.utils.Graphics;

import java.awt.*;

public class GraphicsComponentLabel extends GraphicsComponentBasic {

    private final String text;
    private final int color;
    private final FontRenderer fontRenderer;
    private final float scale;

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, int x, int y) {
        this(fontRenderer, text, Color.BLACK.getRGB(), 1.0F, x, y);
    }

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, int color, int x, int y) {
        this(fontRenderer, text, color, 1.0F, x, y);
    }

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, float scale, int x, int y) {
        this(fontRenderer, text, Color.BLACK.getRGB(), scale, x, y);
    }

    public GraphicsComponentLabel(FontRenderer fontRenderer, String text, int color, float scale, int x, int y) {
        super(x, y, (int)(fontRenderer.getStringWidth(text) * scale), (int)(fontRenderer.FONT_HEIGHT * scale));
        this.fontRenderer = fontRenderer;
        this.text = text;
        this.color = color;
        this.scale = scale;
    }

    @Override
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw() {
        Graphics.drawScaledString(fontRenderer, text, 0, 0, scale, color);
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
