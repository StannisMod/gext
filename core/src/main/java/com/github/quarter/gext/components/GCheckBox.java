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

package com.github.quarter.gext.components;

import com.github.quarter.gext.utils.ComponentBuilder;
import com.github.quarter.gext.utils.Icon;
import com.github.quarter.gext.utils.StyleMap;

public class GCheckBox extends GBasic {

    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

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
        StyleMap.current().drawIcon(Icon.CHECKBOX, getX(), getY(), getWidth());
        if (checked) {
            StyleMap.current().drawIcon(Icon.APPROVE, getX(), getY(), getWidth());
        }
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        checked = !checked;
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder<SELF extends Builder<?, T>, T extends GCheckBox> extends ComponentBuilder<SELF, T> {

    }
}
