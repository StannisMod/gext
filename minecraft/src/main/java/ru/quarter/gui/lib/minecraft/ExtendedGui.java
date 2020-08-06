package ru.quarter.gui.lib.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IRootLayout;
import ru.quarter.gui.lib.api.adapter.IScaledResolution;
import ru.quarter.gui.lib.components.container.BasicLayout;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGui extends Gui implements IRootLayout {

    private final BasicLayout container;

    public ExtendedGui() {
        IScaledResolution res = GuiLib.instance().getResourceManager().scaled();
        this.container = new BasicLayout(0, 0, res.getScaledWidth(), res.getScaledHeight());
    }

    public void add(int depth, IGraphicsComponent component) {
        container.addComponent(depth, component);
    }

    public IGraphicsComponent remove(int id) {
        return container.removeComponent(id);
    }

    public IGraphicsComponent get(int id) {
        return container.getComponent(id);
    }

    public void initGui() {
        init();
        container.init();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        container.render(mouseX, mouseY);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        container.onKeyPressed(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        container.onMousePressed(mouseX, mouseY, mouseButton);
    }

    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        container.onResize(w, h);
    }

    public void onGuiClosed() {
        container.onClosed();
    }
}
