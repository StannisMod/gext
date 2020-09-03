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

package ru.quarter.gui.lib.components;

import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.utils.StyleMap;

public class GBackground extends GBasic {

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
    public void draw(int mouseX, int mouseY) {
        StyleMap.current().drawGUIBackground(0, 0, getWidth(), getHeight());
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
    public void setParent(IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        this.setX((parent.getWidth() - this.getWidth()) / 2);
        this.setY((parent.getHeight() - this.getHeight()) / 2);
    }

    public static class Builder {

        private final GBackground instance = new GBackground();

        public Builder size(int width, int height) {
            instance.setWidth(width);
            instance.setHeight(height);
            return this;
        }

        public Builder placeAt(int x, int y) {
            instance.setX(x);
            instance.setY(y);
            return this;
        }

        public GBackground build() {
            return instance;
        }
    }
}
