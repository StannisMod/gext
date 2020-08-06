package ru.quarter.gui.lib.components;

public class Graphics {

    public static GLabel.Builder label() {
        return new GLabel.Builder();
    }

    public static GLink.Builder link() {
        return new GLink.Builder();
    }

    public static GButton.Builder button() {
        return new GButton.Builder();
    }

    public static GImage.Builder image() {
        return new GImage.Builder();
    }
}
