package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.gui.FontRenderer;
import ru.quarter.gui.lib.api.adapter.IFontRenderer;

public class MinecraftFontRenderer implements IFontRenderer {

    private final FontRenderer instance;

    public MinecraftFontRenderer(FontRenderer instance) {
        this.instance = instance;
    }

    @Override
    public void drawString(String text, int x, int y, int color) {
        instance.drawString(text, x, y, color);
    }

    @Override
    public int getStringWidth(String text) {
        return instance.getStringWidth(text);
    }

    @Override
    public int getFontHeight() {
        return instance.FONT_HEIGHT;
    }
}
