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

package com.github.stannismod.gext.forge18;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IRootLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.container.BasicLayout;
import com.github.stannismod.gext.utils.FrameStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;

public abstract class ExtendedGui extends Gui implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;
    private final Rectangle frame;
    private final IScaledResolution res;
    private boolean initialClick;
    private int mouseX;
    private int mouseY;

    public ExtendedGui() {
        res = GExt.scaled();
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        this.frame = new Rectangle(0, 0, res.getScaledWidth(), res.getScaledHeight());
        GExt.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    public void initGui() {
        initLayout();
        layout.init();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        FrameStack.getInstance().apply(frame);
        layout.render(mouseX, mouseY);
        FrameStack.getInstance().flush();

        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        layout.onKeyPressed(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        layout.onMousePressed(mouseX, mouseY, mouseButton);
        initialClick = true;
    }

    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        layout.onMouseReleased(mouseX, mouseY, mouseButton);
        initialClick = false;
    }

    public void handleMouseInput() throws IOException {
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

    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        GExt.onResize();
        layout.onResize(w, h);
    }

    public void onGuiClosed() {
        layout.onClosed();
    }
}
