package ru.quarter.gui.lib;

import ru.quarter.gui.lib.components.GraphicsComponentLabel;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void init() {
        GraphicsComponentLabel.Builder labelBuilder = new GraphicsComponentLabel.Builder();
        this.add(0, labelBuilder.create().label("Hello, world!").placeAt(50, 50).build());
        this.add(0, labelBuilder.create().label("Hello, world!").scale(2.0F).placeAt(100, 100).build());
        this.add(0, labelBuilder.create().label("Hello, world!").scale(4.0F).placeAt(150, 150).build());
        this.add(0, labelBuilder.create().label("Hello, world!").scale(4.0F).placeAt(150, 200).setCentered().build());
    }
}
