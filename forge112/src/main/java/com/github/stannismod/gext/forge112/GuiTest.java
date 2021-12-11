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

package com.github.stannismod.gext.forge112;

import com.github.stannismod.gext.components.Controls;
import com.github.stannismod.gext.components.GLabel;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.components.container.GList;

import java.awt.*;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void initLayout() {
        final GList<GLabel> panel = Graphics.<GLabel>list().size(50, 200).placeAt(500, 300).build();
        panel.setScrollHandler(Controls.verticalScroll().barWidth(8).scrollFactor(0.25F).build());
        for (int i = 0; i < 1000; i++) {
            panel.addComponent(Graphics.label().text("Label " + i, Color.WHITE.getRGB()).build());
        }

        add(panel);
    }
}
