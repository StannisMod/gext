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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import ru.quarter.gui.lib.GuiLib;
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
        FramebufferStack.getInstance().apply(GuiLib.getResourceManager().defaultFramebuffer());
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
        container.onResize(w, h);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        container.onClosed();
    }
}
