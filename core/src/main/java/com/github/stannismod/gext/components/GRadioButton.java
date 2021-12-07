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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.Icon;
import com.github.stannismod.gext.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GRadioButton extends GBasic {

    protected int interval;
    protected int checkBoxOffset;
    protected int checkBoxSize = 8;

    private final List<GLabel> points = new ArrayList<>();
    private int selected = -1;

    protected GRadioButton() {}

    public int getNumberPoints() {
        return points.size();
    }

    public void select(int point) {
        selected = point;
    }

    public void unselect() {
        select(-1);
    }

    public boolean isSelected() {
        return getSelected() != -1;
    }

    public int getSelected() {
        return selected;
    }

    public int addLabel(GLabel label) {
        label.setX(checkBoxSize + checkBoxOffset);
        label.setY(0);
        points.add(label);
        return getNumberPoints();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        // Translation for drawing upper checkbox
        GL11.glTranslatef(0.0F, 0.1F, 0.0F);
        for (int i = 0; i < points.size(); i++) {
            GLabel label = points.get(i);
            label.draw(mouseX, mouseY);

            int checkBoxX = label.getX() - StyleMap.ICON_SIZE - checkBoxOffset;
            int checkBoxY = label.getY() + (label.getHeight() - checkBoxSize) / 2;
            StyleMap.current().drawIcon(Icon.RADIO_BUTTON, checkBoxX, checkBoxY, checkBoxSize);
            if (i == getSelected()) {
                StyleMap.current().drawIcon(Icon.APPROVE, checkBoxX, checkBoxY - 4, checkBoxSize);
            }

            GL11.glTranslatef(0.0F, label.getHeight() + interval, 0.0F);
        }
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        points.forEach(label -> {
            if (label.intersects(mouseX, mouseY)) {
                label.onMousePressed(mouseX, mouseY, mouseButton);
            }
        });
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (int i = 0; i < points.size(); i++) {
            GLabel label = points.get(i);
            if (label.intersects(mouseX, mouseY)) {
                label.onMouseReleased(mouseX, mouseY, mouseButton);
                if (mouseButton == 0) {
                    select(i);
                }
            } else if (mouseX < label.getX() && label.getY() <= mouseY && mouseY <= label.getY() + label.getHeight()) {
                if (mouseButton == 0) {
                    select(i);
                }
            }
        }
    }

    public static class Builder<SELF extends Builder<?, T>, T extends GRadioButton> extends ComponentBuilder<SELF, T> {

        public SELF checkBox(int size, int offset) {
            instance().checkBoxSize = size;
            instance().checkBoxOffset = offset;
            return self();
        }

        public SELF interval(int interval) {
            instance().interval = interval;
            return self();
        }
    }
}
