/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.forge116;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IRootLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.container.BasicLayout;
import com.github.stannismod.gext.utils.FrameStack;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class ExtendedGuiContainer<T extends Container> extends ContainerScreen<T> implements IRootLayout {

    private BasicLayout<IGraphicsComponent> layout;
    private IScaledResolution res;

    public ExtendedGuiContainer(T containerIn, PlayerInventory inv, ITextComponent titleIn) {
        super(containerIn, inv, titleIn);
        GExt.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    @Override
    public void init() {
        super.init();
        res = GExt.scaled();
        layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        layout.init();
        initLayout();
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        ForgeGExt.startRenderTick(matrixStack);
        FrameStack.getInstance().apply(layout.getAbsoluteFrame());
        layout.render(mouseX, mouseY, partialTicks);
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
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amountScrolled) {
        boolean result = super.mouseScrolled(mouseX, mouseY, amountScrolled);
        layout.onMouseScrolled((int) mouseX, (int) mouseY, amountScrolled);
        return result;
    }

    @Override
    public void mouseMoved(final double mouseX, final double mouseY) {
        super.mouseMoved(mouseX, mouseY);
        layout.onMouseMoved((int) mouseX, (int) mouseY);
    }

    @Override
    public void resize(@Nonnull Minecraft mc, int w, int h) {
        GExt.onResize();
        super.resize(mc, w, h);
    }

    @Override
    public void onClose() {
        super.onClose();
        layout.onClosed();
    }
}
