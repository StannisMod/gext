package ru.quarter.gui.lib;

import ru.quarter.gui.lib.components.GraphicsComponentLabel;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void initGui() {
        super.initGui();
        this.add(0, new GraphicsComponentLabel(mc.fontRenderer, "Hello, world!", 50, 50));
        this.add(0, new GraphicsComponentLabel(mc.fontRenderer, "Hello, world!", 2.0F, 100, 100));
        this.add(0, new GraphicsComponentLabel(mc.fontRenderer, "Hello, world!", 4.0F, 150, 150));
    }
}
