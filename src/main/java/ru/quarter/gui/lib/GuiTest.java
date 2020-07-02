package ru.quarter.gui.lib;

import ru.quarter.gui.lib.components.GraphicsComponentLabel;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void initGui() {
        super.initGui();
        this.add(0, new GraphicsComponentLabel(mc.fontRenderer, "Hello, world!", 0, 0));
    }
}
