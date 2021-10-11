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

package com.github.quarter.gui.lib.forge1710;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.IRootLayout;
import com.github.quarter.gui.lib.components.container.BasicLayout;
import com.github.quarter.gui.lib.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public abstract class ExtendedGuiScreen extends GuiScreen implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;

    public ExtendedGuiScreen() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
    }

    @Override
    public IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    @Override
    public void initGui() {
        super.initGui();
        initLayout();
        layout.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        FrameStack.getInstance().apply(new Rectangle(0, 0, layout().getWidth(), layout().getHeight()));
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        layout.onKeyPressed(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        layout.onMousePressed(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        layout.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int w, int h) {
        super.setWorldAndResolution(mc, w, h);
        layout.onResize(w, h);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        layout.onClosed();
    }
}
