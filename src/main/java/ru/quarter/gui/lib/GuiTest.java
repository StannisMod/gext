package ru.quarter.gui.lib;

import ru.quarter.gui.lib.components.Graphics;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void init() {
        this.add(0, Graphics.label().label("Hello, world!").placeAt(50, 50).build());
        this.add(0, Graphics.label().label("Hello, world!").scale(2.0F).placeAt(100, 100).build());
        this.add(0, Graphics.label().label("Hello, world!").scale(4.0F).placeAt(150, 150).build());
        this.add(0, Graphics.link().label("Stanislav Batalenkov VK page").link("https://vk.com/batalenkov").color(0xffffff, 0x121212).scale(2.0F).placeAt(250, 200).setCentered().build());
    }
}
