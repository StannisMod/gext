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

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.api.adapter.IResource;

import java.util.HashMap;
import java.util.Map;

public final class StyleMap {
    // TODO Bind texture mappings to real StyleMap texture
    private static final TextureMapping progressBar = null;
    private static final TextureMapping scrollBar = null;
    private static final TextureMapping scrollTrace = null;
    private static final TextureMapping button = null;
    private static final TextureMapping icon = null;
    private static final TextureMapping tooltip = null;
    private static final TextureMapping frame = null;

    // For background
    private static final TextureMapping corners = null;
    private static final TextureMapping canvas = null;

    private static final Map<IResource, StyleMap> styles = new HashMap<>();
    private static StyleMap current;

    static {
        activate(register("guilib", "default", 256));
    }

    private final IResource location;
    private final int textureSize;

    private StyleMap(String domain, String name, int textureSize) {
        this.location = GuiLib.resource(domain + ":textures/gui/style/" + name + ".png");
        this.textureSize = textureSize;
    }

    public static IResource register(String domain, String name, int textureSize) {
        StyleMap result = new StyleMap(domain, name, textureSize);
        styles.put(result.location, result);
        return result.location;
    }

    public static IResource register(String domain, String name) {
        return register(domain, name, 256);
    }

    public static StyleMap current() {
        return current;
    }

    public static void activate(IResource map) {
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
        prepare(progressBar);
        int progressWidth = (int)(progress * progressBar.getTextureX());
        progressBar.draw(x, y, width, height, 0.0F);
        progressBar.draw(x, y, 0, progressBar.getTextureY(), progressWidth - progressBar.getTextureX(), 0, progressWidth, height, 0.0F);
    }

    // TODO
    public void drawHorizontalScrollTrace(int x, int y, int width, int height) {
        prepare(scrollTrace);

    }

    public void drawHorizontalScrollBar(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
        drawVerticalScrollBar(y, x, height, width);
        GL11.glPopMatrix();
    }

    // TODO
    public void drawVerticalScrollTrace(int x, int y, int width, int height) {
        prepare(scrollTrace);

    }

    // TODO
    public void drawVerticalScrollBar(int x, int y, int width, int height) {
        prepare(scrollBar);
    }

    public void drawGUIBackground(int x, int y, int width, int height) {
        prepare(corners);
        prepare(canvas);
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);

        final int borderSize = 2;
        // Borders
        canvas.draw(        1, 1, 0, 0, borderSize, borderSize,  width - 2,         borderSize, 0.0F);
        canvas.draw(        1, 1, 0, 0, borderSize, borderSize,        borderSize, height - 2, 0.0F);
        canvas.draw(        1, 0, 0, 0, borderSize, borderSize, height - 4,  width - 2, 0.0F);
        canvas.draw(width - 4, 1, 0, 0, borderSize, borderSize, height - 2,         borderSize, 0.0F);

        // Canvas
        canvas.draw(4, 4, 0, 0, 3, 3, width - 8, height - 8, 0.0F);

        // Corners
        final int cornerSize = corners.getTextureX() / 2;
        corners.draw(0,                                    0, 0, 0,   0,   0, cornerSize, cornerSize, 0.0F);
        corners.draw(0,                  height - cornerSize, 0, 0,   0, cornerSize, cornerSize, cornerSize, 0.0F);
        corners.draw(width - cornerSize,                   0, 0, 0, cornerSize,   0, cornerSize, cornerSize, 0.0F);
        corners.draw(width - cornerSize, height - cornerSize, 0, 0, cornerSize, cornerSize, cornerSize, cornerSize, 0.0F);

        GL11.glPopMatrix();
    }

    public void drawButton(boolean activated, int x, int y, int width, int height) {
        prepare(button);
        button.draw(x, y, 0, activated ? button.getTextureY() : 0, width, height, 0.0F);
    }

    public void drawTooltip(int x, int y, int width, int height) {
        prepare(tooltip);

    }

    public void drawFrame(int x, int y, int width, int height) {
        prepare(frame);

    }

    public void drawIcon(Icon ico, int x, int y, int size) {
        prepare(icon);
        x = x + ico.nx * icon.getTextureX();
        y = y + ico.ny * icon.getTextureY();
        icon.draw(x, y, size, size, 0.0F);
    }

    public enum Icon {

        APPROVE(0, 0),
        DECLINE(1, 0);

        private final int nx;
        private final int ny;

        Icon(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }
    }
}