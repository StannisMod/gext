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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.StyleMap;
import org.jetbrains.annotations.NotNull;

public class GBackground extends GBasic {

    protected int borderSize = 5;
    protected int cornerSize = 15;

    protected GBackground() {}

    @Override
    public boolean checkUpdates() {
        return false;
    }

    @Override
    public void update() {}

    @Override
    public void init() {}

    @Override
    public void onClosed() {}

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        StyleMap.current().drawGUIBackground(0, 0, getWidth(), getHeight(), borderSize, cornerSize);
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    @Override
    public void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        this.setX((parent.getWidth() - this.getWidth()) / 2);
        this.setY((parent.getHeight() - this.getHeight()) / 2);
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GBackground> extends ComponentBuilder<SELF, T> {

        public SELF border(int thickness) {
            instance().borderSize = thickness;
            return self();
        }

        public SELF corners(int size) {
            instance().cornerSize = size;
            return self();
        }
    }
}
