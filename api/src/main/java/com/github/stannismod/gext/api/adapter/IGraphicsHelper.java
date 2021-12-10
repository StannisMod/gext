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

package com.github.stannismod.gext.api.adapter;

import org.jetbrains.annotations.NotNull;

public interface IGraphicsHelper {
    /**
     * Draws string without shadow
     */
    void drawString(@NotNull IFontRenderer fontRendererIn, String text, int x, int y, int color);

    /**
     * Draws TexturedModalRect with the screen coordinates and texture coordinates
     * With high resolution GUI textures please use this
     */
    void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int textureWidth, int textureHeight, int textureSizeX, int textureSizeY, float zLevel);

    /**
     * Draws colored modal rect with the screen coordinates and texture coordinates
     */
    void drawColoredModalRect(int x, int y, int width, int height, float r, float g, float b, float a, float zLevel);
}
