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
import net.minecraft.client.gui.Gui;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IRootLayout;
import ru.quarter.gui.lib.api.adapter.IScaledResolution;
import ru.quarter.gui.lib.components.container.BasicLayout;

import javax.annotation.Nonnull;
import java.io.IOException;

public abstract class ExtendedGui extends Gui implements IRootLayout {

    private final BasicLayout container;

    public ExtendedGui() {
        IScaledResolution res = GuiLib.instance().getResourceManager().scaled();
        this.container = new BasicLayout(0, 0, res.getScaledWidth(), res.getScaledHeight());
    }

    public void add(int depth, IGraphicsComponent component) {
        container.addComponent(depth, component);
    }

    public IGraphicsComponent remove(int id) {
        return container.removeComponent(id);
    }

    public IGraphicsComponent get(int id) {
        return container.getComponent(id);
    }

    public void initGui() {
        init();
        container.init();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        container.render(mouseX, mouseY);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        container.onKeyPressed(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        container.onMousePressed(mouseX, mouseY, mouseButton);
    }

    public void onResize(@Nonnull Minecraft mc, int w, int h) {
        container.onResize(w, h);
    }

    public void onGuiClosed() {
        container.onClosed();
    }
}
