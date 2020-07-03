package ru.quarter.gui.lib.components;

import net.minecraft.client.gui.FontRenderer;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.utils.GraphicsComponentInitializationException;

import java.awt.*;
import java.net.URI;

public class GraphicsComponentLink extends GraphicsComponentLabel {

    private int activeColor;
    private int inactiveColor;
    private URI uri;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    public boolean isActive() {
        return active;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        active = true;
        color = activeColor;
        openWebLink(uri);
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        active = false;
        color = inactiveColor;
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        hovered = true;
    }

    @Override
    public boolean checkUpdates() {
        return hovered != prevHovered;
    }

    @Override
    public void update() {
        System.out.println(active + " " + hovered);
        if (active && !hovered) {
            active = false;
            color = inactiveColor;
        }
        needUpdate = false;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        prevHovered = hovered;
        hovered = false;
    }

    private void openWebLink(URI url) {
        try {
            Desktop.getDesktop().browse(url);
        } catch (Exception e) {
            GuiLib.error("Couldn't open link", e);
        }
    }

    public static class Builder extends GraphicsComponentLabel.Builder {

        @Override
        public Builder create() {
            instance = new GraphicsComponentLink();
            return this;
        }

        private GraphicsComponentLink getInstance() {
            return (GraphicsComponentLink) instance;
        }

        public Builder placeAt(int x, int y) {
            super.placeAt(x, y);
            return this;
        }

        public Builder renderer(FontRenderer fontRenderer) {
            super.renderer(fontRenderer);
            return this;
        }

        public Builder label(String text) {
            if (text == null) {
                throw new GraphicsComponentInitializationException("Given text mustn't be null");
            }
            instance.text = text;
            scale(1.0F);
            return this;
        }

        public Builder scale(float scale) {
            super.scale(scale);
            return this;
        }

        public Builder setCentered() {
            super.setCentered();
            return this;
        }

        public Builder link(String url) {
            getInstance().uri = URI.create(url);
            return this;
        }

        public Builder color(int activeColor, int inactiveColor) {
            getInstance().activeColor = activeColor;
            getInstance().inactiveColor = inactiveColor;
            getInstance().color = inactiveColor;
            return this;
        }

        public Builder label(String text, int color) {
            throw new UnsupportedOperationException("For drawing link with color use Builder#color(activeColor, inactiveColor)");
        }

        @Override
        public GraphicsComponentLink build() {
            return getInstance();
        }
    }
}
