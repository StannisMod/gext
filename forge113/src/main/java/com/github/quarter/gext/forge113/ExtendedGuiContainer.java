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

package com.github.quarter.gext.forge113;

import com.github.quarter.gext.GuiLib;
import com.github.quarter.gext.api.IGraphicsComponent;
import com.github.quarter.gext.api.IGraphicsLayout;
import com.github.quarter.gext.api.IRootLayout;
import com.github.quarter.gext.api.adapter.IScaledResolution;
import com.github.quarter.gext.components.container.BasicLayout;
import com.github.quarter.gext.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;

public abstract class ExtendedGuiContainer extends GuiContainer implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;
    private final Rectangle frame;
    private final IScaledResolution res;
    private int mouseX;
    private int mouseY;

    public ExtendedGuiContainer(Container containerIn) {
        super(containerIn);
        res = GuiLib.scaled();
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        this.frame = new Rectangle(0, 0, res.getScaledWidth(), res.getScaledHeight());
        GuiLib.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    @Override
    public void initGui() {
        super.initGui();
        initLayout();
        layout.init();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        if (this.mouseX != mouseX || this.mouseY != mouseY) {
            layout.onMouseMoved(mouseX, mouseY);
        }
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        FrameStack.getInstance().apply(frame);
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        boolean result = super.charTyped(typedChar, keyCode);
        layout.onKeyPressed(typedChar, keyCode);
        return result;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);
        layout.onMousePressed((int) mouseX, (int) mouseY, mouseButton);
        return result;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        boolean result = super.mouseReleased(mouseX, mouseY, mouseButton);
        layout.onMouseReleased((int) mouseX, (int) mouseY, mouseButton);
        return result;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseDragged, double xAmount, double yAmount) {
        boolean result = super.mouseDragged(mouseX, mouseY, mouseDragged, xAmount, yAmount);
        layout.onMouseDragged(mouseX, mouseY, mouseDragged, xAmount, yAmount);
        return result;
    }

    @Override
    public boolean mouseScrolled(final double amountScrolled) {
        boolean result = super.mouseScrolled(amountScrolled);
        layout.onMouseScrolled(mouseX, mouseY, amountScrolled);
        return result;
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
