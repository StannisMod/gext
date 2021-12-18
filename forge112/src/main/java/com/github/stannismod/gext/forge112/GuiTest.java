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

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.components.container.GPanel;
import com.github.stannismod.gext.utils.Alignment;

import java.awt.*;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void initLayout() {
        final GPanel<IGraphicsComponent> labels = Graphics.panel().size(400, 400).placeAt(100, 100).build();

        labels.addComponent(Graphics.background().size(400, 400).build());

        labels.addComponent(Graphics
                .label()
                .text("Top", Color.WHITE.getRGB())
                .placeAt(0, 0)
                .alignment(Alignment.XCENTER, Alignment.TOP)
                .padding(50, 50)
                .build());

        labels.addComponent(Graphics
                .label()
                .text("Bottom", Color.WHITE.getRGB())
                .placeAt(0, 0)
                .alignment(Alignment.XCENTER, Alignment.BOTTOM)
                .padding(50, 50)
                .build());

        labels.addComponent(Graphics
                .label()
                .text("Left", Color.WHITE.getRGB())
                .placeAt(0, 0)
                .alignment(Alignment.LEFT, Alignment.YCENTER)
                .padding(50, 50)
                .build());

        labels.addComponent(Graphics
                .label()
                .text("Right", Color.WHITE.getRGB())
                .placeAt(0, 0)
                .alignment(Alignment.RIGHT, Alignment.YCENTER)
                .padding(50, 50)
                .build());

        labels.addComponent(Graphics
                .label()
                .text("Center", Color.WHITE.getRGB())
                .placeAt(0, 0)
                .alignment(Alignment.XCENTER, Alignment.YCENTER)
                .padding(50, 50)
                .build());

        add(labels);
    }
}
