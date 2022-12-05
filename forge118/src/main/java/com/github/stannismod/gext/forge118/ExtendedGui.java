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

package com.github.stannismod.gext.forge118;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IRootLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.components.container.BasicLayout;
import com.github.stannismod.gext.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class ExtendedGui extends Gui implements IRootLayout {

    private BasicLayout<IGraphicsComponent> layout;
    private IScaledResolution res;

    public ExtendedGui(Minecraft mc) {
        super(mc);
        GExt.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    public void init() {
        res = GExt.scaled();
        layout = Graphics.layout()
                .size(res.getScaledWidth(), res.getScaledHeight())
                .placeAt(0, 0)
                .build();
        layout.init();
        initLayout();
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        FrameStack.getInstance().apply(layout.getAbsoluteFrame());
        layout.render(mouseX, mouseY, partialTicks);
        FrameStack.getInstance().flush();
    }

    public void charTyped(char typedChar, int keyCode) {
        layout.onKeyPressed(typedChar, keyCode);
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        layout.onMousePressed((int) mouseX, (int) mouseY, mouseButton);
    }

    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        layout.onMouseReleased((int) mouseX, (int) mouseY, mouseButton);
    }

    public void mouseDragged(double mouseX, double mouseY, int mouseDragged, double xAmount, double yAmount) {
        layout.onMouseDragged(mouseX, mouseY, mouseDragged, xAmount, yAmount);
    }

    public void mouseScrolled(final double mouseX, final double mouseY, final double amountScrolled) {
        layout.onMouseScrolled((int) mouseX, (int) mouseY, amountScrolled);
    }

    public void mouseMoved(final double mouseX, final double mouseY) {
        layout.onMouseMoved((int) mouseX, (int) mouseY);
    }

    public void resize(@Nonnull Minecraft mc, int w, int h) {
        GExt.onResize();
        init();
    }

    public void onClose() {
        layout.onClosed();
    }
}
