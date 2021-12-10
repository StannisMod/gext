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

import com.github.stannismod.gext.api.adapter.IResource;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.TextureMapping;

public class GImage extends GBasic {

    protected TextureMapping mapping;

    protected GImage() {}

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
        mapping.draw(0, 0, getWidth(), getHeight(), 0);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {}

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    @Override
    public void onResize(int w, int h) {}

    public static class Builder<SELF extends Builder<?, T>, T extends GImage> extends ComponentBuilder<SELF, T> {

        public SELF texture(IResource location) {
            return texture(location, 256, 256);
        }

        public SELF texture(IResource location, int textureWidth, int textureHeight) {
            instance().mapping = new TextureMapping(location);
            instance().mapping.setTextureWidth(textureWidth);
            instance().mapping.setTextureHeight(textureHeight);
            return self();
        }

        public SELF uv(int startU, int startV, int u, int v) {
            instance().mapping.setU(startU);
            instance().mapping.setV(startV);
            instance().mapping.setTextureX(u);
            instance().mapping.setTextureY(v);
            return self();
        }
    }
}
