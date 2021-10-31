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

package com.github.quarter.gui.lib.forge114;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.IRootLayout;
import com.github.quarter.gui.lib.api.adapter.IScaledResolution;
import com.github.quarter.gui.lib.components.container.BasicLayout;
import com.github.quarter.gui.lib.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;

public abstract class ExtendedGui extends AbstractGui implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;
    private final Rectangle frame;
    private final IScaledResolution res;

    public ExtendedGui() {
        res = GuiLib.scaled();
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        this.frame = new Rectangle(0, 0, res.getScaledWidth(), res.getScaledHeight());
        GuiLib.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    public void init() {
        initLayout();
        layout.init();
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        FrameStack.getInstance().apply(frame);
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();
    }

    public boolean charTyped(char typedChar, int keyCode) {
        layout.onKeyPressed(typedChar, keyCode);
        return false;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        layout.onMousePressed((int) mouseX, (int) mouseY, mouseButton);
        return false;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        layout.onMouseReleased((int) mouseX, (int) mouseY, mouseButton);
        return false;
    }

    public void handleMouseInput() throws IOException {
        int x = Mouse.getEventX() / res.getScaleFactor();
        int y = (res.getViewHeight() - Mouse.getEventY()) / res.getScaleFactor();
        int k = Mouse.getEventButton();
        layout.onMouseInput(x, y, k);
    }

    public void resize(@Nonnull Minecraft mc, int w, int h) {
        layout.onResize(w, h);
    }

    public void onClose() {
        layout.onClosed();
    }
}
