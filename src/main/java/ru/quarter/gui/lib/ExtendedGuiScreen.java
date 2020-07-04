package ru.quarter.gui.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IRootLayout;
import ru.quarter.gui.lib.components.container.BasicLayout;
import ru.quarter.gui.lib.utils.FramebufferStack;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGuiScreen extends GuiScreen implements IRootLayout {

    private final BasicLayout container;

    public ExtendedGuiScreen() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        this.container = new BasicLayout(0, 0, res.getScaledWidth(), res.getScaledHeight());
    }

    @Override
    public void add(int depth, IGraphicsComponent component) {
        container.addComponent(depth, component);
    }

    @Override
    public IGraphicsComponent remove(int id) {
        return container.removeComponent(id);
    }

    @Override
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
        FramebufferStack.getInstance().apply(Minecraft.getMinecraft().getFramebuffer());
        container.render(mouseX, mouseY);
        FramebufferStack.getInstance().flush();
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
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        container.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        super.onResize(mc, w, h);
        container.onResize(mc, w, h);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        container.onClosed();
    }
}
