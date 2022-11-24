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
import com.github.stannismod.gext.components.GLabel;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.components.container.GPanel;
import com.github.stannismod.gext.components.container.GTabPanel;

import java.awt.*;

public class GuiTest extends ExtendedGuiScreen {

    @Override
    public void initLayout() {
        final GPanel<IGraphicsComponent> labelPanel = Graphics.panel().size(300, 300).placeAt(10, 10).build();
        labelPanel.setClippingEnabled(false);

        this.add(labelPanel);

        GLabel label1 = Graphics.label().text("Option 1").build();
        GLabel label2 = Graphics.label().text("Option 2").build();

        final GTabPanel<IGraphicsComponent, IGraphicsComponent> tabs = Graphics.tabPanel()
                .target(labelPanel)
                .putContent("o1", Graphics.label().text("Hello 1").build())
                .putContent("o2", Graphics.label().text("Hello 2").build())
                .putContent("o2", Graphics.background().size(100, 100).build())
                .size(200, 200)
                .placeAt(500, 10)
                .build();
        tabs.setClippingEnabled(false);

        tabs.addComponent("o1", label1);
        tabs.addComponent("o2", label2);
        tabs.setDeselectionEnabled(true);

        this.add(tabs);

        this.add(Graphics.button()
                .label("Log selected", Color.WHITE.getRGB())
                .action(b -> System.out.println(tabs.getSelectedId()))
                .size(60, 20)
                .placeAt(100, 100)
                .build());

        this.add(Graphics.textBox()
                .size(400, 400)
                .text("Place your text here")
                .enableSelection()
                .placeAt(200, 200)
                .build());

//        final GList<IGraphicsComponent> labelPanel = Graphics.list().size(50, 400).placeAt(200, 10).build(); // создали список
//        labelPanel.setScrollHandler(Controls.verticalScroll().barWidth(8).scrollFactor(0.25F).build());  // установили ему вертикальный скролл
//        for (int i = 0; i < 1000; i++) {   // добавили надписей
//            labelPanel.addComponent(Graphics.label().text("Label " + i, Color.WHITE.getRGB()).build());
//        }
//
//        this.add(labelPanel);   // присоединили к интерфейсу

//        GBackground background;
//        add(background = Graphics.background().size(600, 600).build());
        //background.setClippingEnabled(false);

//        final GPanel<IGraphicsComponent> labelPanel = Graphics.panel().size(300, 300).placeAt(10, 10).build();
//        //labelPanel.setScrollHandler(Controls.verticalScroll().barWidth(8).scrollFactor(0.25F).build());  // установили ему вертикальный скролл
//
//        labelPanel.addComponent(Graphics
//                .button()
//                .label("Button 1")
//                .size(60, 20)
//                .placeAt(50, 50)
//                .alignment(Alignment.CENTER)
//                .action(b -> System.out.println("1 Clicked!"))
//                .build());
//
//        labelPanel.addComponent(Graphics
//                .button()
//                .label("Button 2")
//                .size(60, 20)
//                .alignment(Alignment.LEFT, Alignment.TOP)
//                .action(b -> System.out.println("2 Clicked!"))
//                .build());
//
//        labelPanel.addComponent(Graphics
//                .button()
//                .label("Button 3")
//                .size(60, 20)
//                .alignment(Alignment.RIGHT, Alignment.TOP)
//                .action(b -> System.out.println("3 Clicked!"))
//                .build());
//
//        labelPanel.addComponent(Graphics
//                .button()
//                .label("Button 4")
//                .size(60, 20)
//                .alignment(Alignment.RIGHT, Alignment.BOTTOM)
//                .action(b -> System.out.println("4 Clicked!"))
//                .build());
//
//        labelPanel.addComponent(Graphics
//                .button()
//                .label("Button 5")
//                .size(60, 20)
//                .alignment(Alignment.LEFT, Alignment.BOTTOM)
//                .action(b -> System.out.println("5 Clicked!"))
//                .build());
//
//        this.add(labelPanel);

//        final GList<IGraphicsComponent> panel = Graphics.list().size(60, 400).placeAt(0, 10).build(); // создали список
//        panel.setScrollHandler(Controls.verticalScroll().barWidth(8).scrollFactor(0.25F).build());  // установили ему вертикальный скролл
//        for (int i = 0; i < 1000; i++) {   // добавили надписей
//            panel.addComponent(Graphics
//                    .button()
//                    .label("Label " + i, Color.WHITE.getRGB())
//                    .action(b -> System.out.println(b.getLabel().getText() + " clicked!"))
//                    .size(45, 20)
//                    .build());
//        }
//
//        this.add(panel);   // присоединили к интерфейсу


//        final int width = 300;
//        final int height = 300;
//        final GPanel<IGraphicsComponent> labels = Graphics.panel().size(width, height).placeAt(100, 100).build();
//
//        GBackground background;
//        labels.addComponent(background = Graphics.background().size(width, height).build());
//
//        labels.addComponent(1, Graphics
//                .label()
//                .text("Top", Color.WHITE.getRGB())
//                .placeAt(0, 0)
//                .alignment(Alignment.XCENTER, Alignment.TOP)
//                .padding(50, 50)
//                .build());
//
//        labels.addComponent(1, Graphics
//                .label()
//                .text("Bottom", Color.WHITE.getRGB())
//                .placeAt(0, 0)
//                .alignment(Alignment.XCENTER, Alignment.BOTTOM)
//                .padding(50, 50)
//                .build());
//
//        labels.addComponent(1, Graphics
//                .label()
//                .text("Left", Color.WHITE.getRGB())
//                .placeAt(0, 0)
//                .alignment(Alignment.LEFT, Alignment.YCENTER)
//                .padding(50, 50)
//                .build());
//
//        labels.addComponent(1, Graphics
//                .label()
//                .text("Right", Color.WHITE.getRGB())
//                .placeAt(0, 0)
//                .alignment(Alignment.RIGHT, Alignment.YCENTER)
//                .padding(50, 50)
//                .build());
//
//        labels.addComponent(1, Graphics
//                .label()
//                .text("Center", Color.WHITE.getRGB())
//                .placeAt(0, 0)
//                .alignment(Alignment.XCENTER, Alignment.YCENTER)
//                .padding(50, 50)
//                .build());
//
//        add(labels);
//
//        GButton btn1;
//        add(btn1 = Graphics.button()
//                .label("Change horizontal size", Color.WHITE.getRGB())
//                .action(btn -> {
//                    if (labels.getWidth() < 300) {
//                        labels.setWidth(300);
//                        background.setWidth(300);
//                    } else {
//                        labels.setWidth(50);
//                        background.setWidth(50);
//                    }
//                })
//                .size(100, 20)
//                .placeAt(10, 10)
//                .build());
//
//        add(Graphics.button()
//                .label("Change vertical size", Color.WHITE.getRGB())
//                .action(btn -> {
//                    if (labels.getHeight() < 300) {
//                        labels.setHeight(300);
//                        background.setHeight(300);
//                    } else {
//                        labels.setHeight(50);
//                        background.setHeight(50);
//                    }
//                })
//                .size(100, 20)
//                .bind(btn1, Bound.BOTTOM_LEFT)
//                .placeAt(0, 10)
//                .build());
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
