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

package com.github.quarter.gext.forge111.adapter;

import com.github.quarter.gext.api.adapter.IFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class MinecraftFontRenderer implements IFontRenderer {

    private final FontRenderer instance;

    public MinecraftFontRenderer(FontRenderer instance) {
        this.instance = instance;
    }

    @Override
    public void drawString(@NotNull String text, int x, int y, int color) {
        Color c = new Color(color);
        GL11.glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        instance.drawString(text, x, y, color);
    }

    @Override
    public int getStringWidth(@NotNull String text) {
        return instance.getStringWidth(text);
    }

    @Override
    public int getFontHeight() {
        return instance.FONT_HEIGHT;
    }

    @Override
    public @NotNull List<String> listTextToWidth(@NotNull String text, int width) {
        return instance.listFormattedStringToWidth(text, width);
    }
}
