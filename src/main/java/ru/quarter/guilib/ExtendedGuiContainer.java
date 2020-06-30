package ru.quarter.guilib;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class ExtendedGuiContainer extends GuiContainer {

    public ExtendedGuiContainer(Container containerIn) {
        super(containerIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }
}
