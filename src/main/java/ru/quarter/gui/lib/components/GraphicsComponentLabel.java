package ru.quarter.gui.lib.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ru.quarter.gui.lib.api.IComponentBuilder;
import ru.quarter.gui.lib.utils.Graphics;
import ru.quarter.gui.lib.utils.GraphicsComponentInitializationException;

import java.awt.*;

public class GraphicsComponentLabel extends GraphicsComponentBasic {

    private String text;
    private int color;
    private FontRenderer fontRenderer;
    private float scale;

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

    public static class Builder implements IComponentBuilder<GraphicsComponentLabel> {

        private GraphicsComponentLabel instance;

        @Override
        public Builder create() {
            instance = new GraphicsComponentLabel();
            return this;
        }

        public Builder placeAt(int x, int y) {
            instance.x = x;
            instance.y = y;
            return this;
        }

        public Builder renderer(FontRenderer fontRenderer) {
            if (fontRenderer == null) {
                throw new GraphicsComponentInitializationException("FontRenderer instance mustn't be null");
            }
            instance.fontRenderer = fontRenderer;
            return this;
        }

        public Builder label(String text) {
            return label(text, Color.BLACK.getRGB());
        }

        public Builder label(String text, int color) {
            if (text == null) {
                throw new GraphicsComponentInitializationException("Given text mustn't be null");
            }
            instance.text = text;
            instance.color = color;
            scale(1.0F);
            return this;
        }

        public Builder scale(float scale) {
            if (instance.text == null) {
                throw new GraphicsComponentInitializationException("Trying to set scale before defining a text");
            }
            if (instance.fontRenderer == null) {
                instance.fontRenderer = Minecraft.getMinecraft().fontRenderer;
            }
            instance.scale = scale;
            instance.width = (int)(instance.fontRenderer.getStringWidth(instance.text) * scale);
            instance.height = (int)(instance.fontRenderer.FONT_HEIGHT * scale);
            return this;
        }

        public Builder setCentered() {
            if (instance.width == 0) {
                throw new GraphicsComponentInitializationException("Trying to set centered before defining a text");
            }
            instance.x -= instance.width / 2;
            return this;
        }

        @Override
        public GraphicsComponentLabel build() {
            return instance;
        }
    }
}
