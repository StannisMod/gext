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

package com.github.quarter.gui.lib.components;

import com.github.quarter.gui.lib.utils.ComponentBuilder;
import com.github.quarter.gui.lib.utils.StyleMap;

public class GProgressBar extends GBasic {

    private float progress;

    protected GProgressBar() {}

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
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
        StyleMap.current().drawProgressBar(getProgress(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder<SELF extends Builder<?, T>, T extends GProgressBar> extends ComponentBuilder<SELF, T> {

    }
}
