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

package ru.quarter.gui.lib.utils;

import ru.quarter.gui.lib.api.adapter.IResource;

public class TextureMapping {

    private IResource location;
    private int u;
    private int v;
    private int textureX;
    private int textureY;
    private int textureWidth;
    private int textureHeight;

    protected TextureMapping() {
        this(null);
    }

    public TextureMapping(IResource location) {
        this.location = location;
    }

    public TextureMapping(IResource location, int u, int v, int textureX, int textureY, int textureWidth, int textureHeight) {
        this(location);
        this.setTextureWidth(textureWidth);
        this.setTextureHeight(textureHeight);
        this.setU(u);
        this.setV(v);
        this.setTextureX(textureX);
        this.setTextureY(textureY);
    }

    public TextureMapping up() {
        return new TextureMapping(location, u, v - textureY, textureX, textureY, textureWidth, textureHeight);
    }

    public TextureMapping down() {
        return new TextureMapping(location, u, v + textureY, textureX, textureY, textureWidth, textureHeight);
    }

    public TextureMapping left() {
        return new TextureMapping(location, u - textureX, v, textureX, textureY, textureWidth, textureHeight);
    }

    public TextureMapping right() {
        return new TextureMapping(location, u + textureX, v, textureX, textureY, textureWidth, textureHeight);
    }

    public TextureMapping(IResource location, int u, int v, int textureX, int textureY) {
        this(location, u, v, textureX, textureY, 256, 256);
    }

    public void draw(int x, int y, int width, int height, float zLevel) {
        location.bindAsTexture();
        GraphicsHelper.drawTexturedModalRect(x, y, width, height, u, v, textureX, textureY, textureWidth, textureHeight, zLevel);
    }

    public void draw(int x, int y, int dx, int dy, int width, int height, float zLevel) {
        location.bindAsTexture();
        GraphicsHelper.drawTexturedModalRect(x, y, width, height, u + dx, v + dy, textureX, textureY, textureWidth, textureHeight, zLevel);
    }

    public void draw(int x, int y, int dx, int dy, int texDX, int texDY, int width, int height, float zLevel) {
        location.bindAsTexture();
        GraphicsHelper.drawTexturedModalRect(x, y, width, height, u + dx, v + dy, textureX + texDX, textureY + texDY, textureWidth, textureHeight, zLevel);
    }

    protected void setLocation(IResource location) {
        this.location = location;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        if (u < 0 || u > textureWidth) {
            throw new IllegalArgumentException("U out of bounds: " + u);
        }
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        if (v < 0 || v > textureHeight) {
            throw new IllegalArgumentException("V out of bounds: " + v);
        }
        this.v = v;
    }

    public int getTextureX() {
        return textureX;
    }

    public void setTextureX(int textureX) {
        this.textureX = textureX;
    }

    public int getTextureY() {
        return textureY;
    }

    public void setTextureY(int textureY) {
        this.textureY = textureY;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }
}
