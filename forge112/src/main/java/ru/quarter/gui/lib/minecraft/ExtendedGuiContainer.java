/*
 * Copyright 2020 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.quarter.gui.lib.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IRootLayout;
import ru.quarter.gui.lib.components.container.BasicLayout;
import ru.quarter.gui.lib.utils.FramebufferStack;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGuiContainer extends GuiContainer implements IRootLayout {

    private final BasicLayout layout;

    public ExtendedGuiContainer(Container containerIn) {
        super(containerIn);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        this.layout = new BasicLayout(0, 0, res.getScaledWidth(), res.getScaledHeight());
    }

    @Override
    public IGraphicsLayout layout() {
        return layout;
    }

    @Override
    public void initGui() {
        super.initGui();
        init();
        layout.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        FramebufferStack.getInstance().apply(GuiLib.defaultFramebuffer());
        layout.render(mouseX, mouseY);
        FramebufferStack.getInstance().flush();
        GL11.glScalef(mc.gameSettings.guiScale, mc.gameSettings.guiScale, 1.0F);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        layout.onKeyPressed(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        layout.onMousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        super.onResize(mc, w, h);
        layout.onResize(w, h);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        layout.onClosed();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
}
