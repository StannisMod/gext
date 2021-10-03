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

package com.github.quarter.gui.lib.forge115;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.IRootLayout;
import com.github.quarter.gui.lib.components.container.BasicLayout;
import com.github.quarter.gui.lib.utils.FrameStack;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.awt.*;

public abstract class ExtendedGuiContainer<T extends Container> extends ContainerScreen<T> implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;

    public ExtendedGuiContainer(T containerIn, PlayerInventory inv, ITextComponent titleIn) {
        super(containerIn, inv, titleIn);
        MainWindow window = Minecraft.getInstance().getMainWindow();
        this.layout = new BasicLayout<>(0, 0, window.getScaledWidth(), window.getScaledHeight());
    }

    @Override
    public IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    @Override
    public void init() {
        super.init();
        initLayout();
        layout.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        FrameStack.getInstance().apply(new Rectangle(0, 0, layout().getWidth(), layout().getHeight()));
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        super.charTyped(typedChar, keyCode);
        layout.onKeyPressed(typedChar, keyCode);
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        layout.onMousePressed((int) mouseX, (int) mouseY, mouseButton);
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        layout.onMouseReleased((int) mouseX, (int) mouseY, mouseButton);
        return false;
    }

    @Override
    public void resize(@Nonnull Minecraft mc, int w, int h) {
        super.resize(mc, w, h);
        layout.onResize(w, h);
    }

    @Override
    public void onClose() {
        super.onClose();
        layout.onClosed();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
}