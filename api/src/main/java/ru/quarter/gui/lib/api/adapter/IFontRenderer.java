package ru.quarter.gui.lib.api.adapter;

public interface IFontRenderer {

    void drawString(String text, int x, int y, int color);

    int getStringWidth(String text);

    int getFontHeight();
}
