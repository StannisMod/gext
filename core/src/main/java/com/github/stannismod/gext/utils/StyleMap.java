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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.GlStateManager;

import java.util.HashMap;
import java.util.Map;

public final class StyleMap {

    public static final int ICON_SIZE = 18;

    private static final TextureMapping progressBar = new TextureMapping(null, 94, 0, 183, 2);
    private static final TextureMapping scrollTrace = new TextureMapping(null, 0, 51, 127, 6);
    private static final TextureMapping scrollBar = scrollTrace.down();
    private static final TextureMapping button = new TextureMapping(null, 0, 19, 53, 16);
    private static final TextureMapping buttonActivated = button.down();
    private static final TextureMapping icon = new TextureMapping(null, 0, 98, ICON_SIZE, ICON_SIZE);
    private static final TextureMapping tooltip = new TextureMapping(null, 53, 19, 16, 16);

    // For background
    private static final TextureMapping corners = new TextureMapping(null, 0, 0, 19, 19);
    private static final TextureMapping frame = new TextureMapping(null, 19, 0, 19, 19);
    private static final TextureMapping selection = new TextureMapping(null, 69, 19, 16, 16);

    private static final Map<IResource, StyleMap> styles = new HashMap<>();
    private static StyleMap current;

    static {
        activate(register("gext", "default", 512));
    }

    private final ITexture location;
    private final int textureSize;

    private StyleMap(String domain, String name, int textureSize) {
        this.location = GExt.texture(domain, "textures/gui/style/" + name + ".png");
        this.textureSize = textureSize;
    }

    public static ITexture register(String domain, String name, int textureSize) {
        StyleMap result = new StyleMap(domain, name, textureSize);
        styles.put(result.location, result);
        return result.location;
    }

    public static ITexture register(String domain, String name) {
        return register(domain, name, 256);
    }

    public static StyleMap current() {
        return current;
    }

    public static void activate(ITexture map) {
        current = styles.get(map);
        if (current == null) {
            throw new IllegalArgumentException("Trying to activate unregistered StyleMap!");
        }
    }

    private void prepare(TextureMapping mapping) {
        mapping.setLocation(location);
        mapping.setTextureWidth(textureSize);
        mapping.setTextureHeight(textureSize);
    }

    public void drawProgressBar(float progress, int x, int y, int width, int height) {
        drawProgressBar(progress, x, y, width, height, 0.0F);
    }

    public void drawProgressBar(float progress, int x, int y, int width, int height, float zLevel) {
        prepare(progressBar);
        int progressWidth = (int)(progress * progressBar.getTextureX());
        progressBar.draw(x, y, width, height, zLevel);
        progressBar.draw(x, y, 0, progressBar.getTextureY(), progressWidth - progressBar.getTextureX(), 0, (int)(width * progress), height, zLevel);
    }

    public void drawHorizontalScrollTrace(int x, int y, int width, int height) {
        prepare(scrollTrace);
        scrollTrace.draw(x, y, width, height);
    }

    public void drawHorizontalScrollBar(int x, int y, int width, int height) {
        prepare(scrollBar);
        scrollBar.draw(x, y, width, height);
    }

    public void drawVerticalScrollTrace(int x, int y, int width, int height) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + width, y, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        drawHorizontalScrollTrace(0, 0, height, width);
        GlStateManager.popMatrix();
    }

    public void drawVerticalScrollBar(int x, int y, int width, int height) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + width, y, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        drawHorizontalScrollBar(0, 0, height, width);
        GlStateManager.popMatrix();
    }

    public void drawGUIBackground(int x, int y, int width, int height) {
        drawGUIBackground(x, y, width, height, 3);
    }

    public void drawGUIBackground(int x, int y, int width, int height, int borderSize) {
        drawGUIBackground(x, y, width, height, borderSize, borderSize);
    }

    public void drawGUIBackground(int x, int y, int width, int height, int borderSize, int cornerSize) {
        prepare(corners);
        
        drawFrame(x + 1, y + 1, width - 2, height - 2, borderSize);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);

        // Corners
        final int cornerSizeTexture = corners.getTextureX() / 2;
        corners.draw(0,                                    0,             0,             0, -cornerSizeTexture, -cornerSizeTexture, cornerSize, cornerSize, 0.0F);
        corners.draw(0,                  height - cornerSize,             0, cornerSizeTexture, -cornerSizeTexture, -cornerSizeTexture, cornerSize, cornerSize, 0.0F);
        corners.draw(width - cornerSize,                   0, cornerSizeTexture,             0, -cornerSizeTexture, -cornerSizeTexture, cornerSize, cornerSize, 0.0F);
        corners.draw(width - cornerSize, height - cornerSize, cornerSizeTexture, cornerSizeTexture, -cornerSizeTexture, -cornerSizeTexture, cornerSize, cornerSize, 0.0F);

        GlStateManager.popMatrix();
    }

    public void drawButton(boolean activated, int x, int y, int width, int height) {
        if (activated) {
            prepare(buttonActivated);
            buttonActivated.draw(x, y, width, height, 0.0F);
        } else {
            prepare(button);
            button.draw(x, y, width, height, 0.0F);
        }
    }

    public void drawTooltip(int x, int y, int width, int height) {
        prepare(tooltip);
        tooltip.draw(x, y, width, height, 0.0F);
    }

    public void drawFrame(int x, int y, int width, int height) {
        drawFrame(x, y, width, height, 3);
    }

    public void drawFrame(int x, int y, int width, int height, int borderSize) {
        prepare(frame);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);

        final int borderSizeTexture = 3;
        final int borderOffsetX = frame.getTextureX() - borderSizeTexture;
        final int borderOffsetY = frame.getTextureY() - borderSizeTexture;
        // Borders
        frame.draw(                 0,  0,         0,         0,       0, -borderOffsetY,      width,  borderSize, 0.0F);
        frame.draw(width - borderSize,  0, borderOffsetX,         0, -borderOffsetX,       0, borderSize,      height, 0.0F);
        frame.draw(0, height - borderSize,         0, borderOffsetY,       0, -borderOffsetY,      width,  borderSize, 0.0F);
        frame.draw(0,                   0,         0,         0, -borderOffsetX,       0, borderSize,      height, 0.0F);

        // frame
        frame.draw(
                borderSize, borderSize,
                borderSizeTexture, borderSizeTexture,
                -2 * borderSizeTexture, -2 * borderSizeTexture,
                width - 2 * borderSize, height - 2 * borderSize,
                0.0F
        );

        GlStateManager.popMatrix();
    }

    public void drawTextSelection(int x, int y, int width, int height) {
        prepare(selection);
        selection.draw(x, y, width, height, 0.0F);
    }

    public void drawIcon(Icon ico, int x, int y, int size) {
        prepare(icon);
        int dx = ico.nx * icon.getTextureX();
        int dy = ico.ny * icon.getTextureY();
        icon.draw(x, y, dx, dy, size, size, 0.0F);
    }
}
