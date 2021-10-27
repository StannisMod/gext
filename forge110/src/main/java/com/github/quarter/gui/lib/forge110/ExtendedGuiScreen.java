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

package com.github.quarter.gui.lib.forge110;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.api.IGraphicsLayout;
import com.github.quarter.gui.lib.api.IRootLayout;
import com.github.quarter.gui.lib.api.adapter.IScaledResolution;
import com.github.quarter.gui.lib.components.container.BasicLayout;
import com.github.quarter.gui.lib.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGuiScreen extends GuiScreen implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;
    private final IScaledResolution res;
    private boolean initialClick;
    private int mouseX;
    private int mouseY;

    public ExtendedGuiScreen() {
        this.res = GuiLib.scaled();
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        FrameStack.getInstance().setScaled(res);
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
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        FrameStack.getInstance().apply(layout.getAbsoluteFrame());
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();

        this.mouseX = mouseX;
        this.mouseY = mouseY;
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
        initialClick = true;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        layout.onMouseReleased(mouseX, mouseY, mouseButton);
        initialClick = false;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int mouseX = Mouse.getEventX() / res.getScaleFactor();
        int mouseY = (res.getViewHeight() - Mouse.getEventY()) / res.getScaleFactor();
        int mouseButton = Mouse.getEventButton();
        layout.onMouseInput(mouseX, mouseY, mouseButton);

        int scrolled = Mouse.getEventDWheel();
        if (scrolled != 0) {
            layout.onMouseScrolled(mouseX, mouseY, scrolled);
        } else {
            if (!Mouse.getEventButtonState()) {
                if (initialClick) {
                    layout.onMouseDragged(mouseX, mouseY, mouseButton, mouseX - this.mouseX, mouseY - this.mouseY);
                } else {
                    layout.onMouseMoved(mouseX, mouseY);
                }
            }
        }
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
}
