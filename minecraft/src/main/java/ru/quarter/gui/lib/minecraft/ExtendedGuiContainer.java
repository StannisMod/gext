package ru.quarter.gui.lib.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IRootLayout;
import ru.quarter.gui.lib.components.container.BasicLayout;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGuiContainer extends GuiContainer implements IRootLayout {

    private final BasicLayout container;

    public ExtendedGuiContainer(Container containerIn) {
        super(containerIn);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
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

    @Override
    public void initGui() {
        super.initGui();
        init();
        container.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        container.render(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        container.onKeyPressed(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        container.onMousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        super.onResize(mc, w, h);
        container.onResize(w, h);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        container.onClosed();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
}
