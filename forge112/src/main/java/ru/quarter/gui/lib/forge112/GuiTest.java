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

package ru.quarter.gui.lib.forge112;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.components.Graphics;
import ru.quarter.gui.lib.components.container.BasicLayout;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void init() {
        this.add(Graphics.label().text("Hello, world!").placeAt(50, 50).build());
        this.add(Graphics.label().text("Hello, world!").scale(2.0F).placeAt(100, 100).build());
        this.add(Graphics.label().text("Hello, world!").scale(4.0F).placeAt(150, 150).build());
        this.add(Graphics.link().text("Stanislav Batalenkov VK page").url("https://vk.com/batalenkov").color(0xffffff, 0x121212).scale(2.0F).placeAt(250, 200).setCentered().build());
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        final BasicLayout<IGraphicsComponent> panel = new BasicLayout<>(100, 100, 500, 500);
        this.add(panel);
        panel.addComponent(Graphics.background().size(100, 100).build());
        this.add(Graphics.label().text("The first perfect text should be here").placeAt(100, 100).setCentered().build());
        this.add(Graphics.label().text("The second perfect text should be here").placeAt(800, 100).setCentered().build());
    }
}
