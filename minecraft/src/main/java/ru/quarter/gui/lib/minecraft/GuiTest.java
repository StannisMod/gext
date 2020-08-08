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

import ru.quarter.gui.lib.components.Graphics;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void init() {
        this.add(0, Graphics.label().text("Hello, world!").placeAt(50, 50).build());
        this.add(0, Graphics.label().text("Hello, world!").scale(2.0F).placeAt(100, 100).build());
        this.add(0, Graphics.label().text("Hello, world!").scale(4.0F).placeAt(150, 150).build());
        this.add(0, Graphics.link().text("Stanislav Batalenkov VK page").url("https://vk.com/batalenkov").color(0xffffff, 0x121212).scale(2.0F).placeAt(250, 200).setCentered().build());
    }
}
