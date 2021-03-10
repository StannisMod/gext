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

import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.utils.KeyboardHelper;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class GTextBox extends GTextPanel {

    private int cursorXPos;
    private int cursorYPos;
    private int cursorX;
    private int cursorY;

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_CONTROL)) {
            if (KeyboardHelper.isKeyDown(Keyboard.KEY_C)) {
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(
                                new StringSelection(getSelectedText()),
                                null
                        );
            } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_V)) {
                // TODO Paste
            }
        }
    }

    public static class Builder extends GTextPanel.Builder {
        public Builder() {
            instance = new GTextBox();
        }

        @Override
        public Builder title(String title) {
            super.title(title);
            return this;
        }

        @Override
        public Builder text(String text) {
            super.text(text);
            return this;
        }

        @Override
        public Builder text(List<String> text) {
            super.text(text);
            return this;
        }

        @Override
        public Builder wrap() {
            super.wrap();
            return this;
        }

        @Override
        public Builder scale(float scale) {
            super.scale(scale);
            return this;
        }

        @Override
        public Builder offsets(int xOffset, int yOffset) {
            super.offsets(xOffset, yOffset);
            return this;
        }

        @Override
        public Builder enableBackground() {
            super.enableBackground();
            return this;
        }

        @Override
        public Builder interval(int interval) {
            super.interval(interval);
            return this;
        }

        @Override
        public Builder enableSelection() {
            super.enableSelection();
            return this;
        }

        @Override
        public Builder renderer(IFontRenderer renderer) {
            super.renderer(renderer);
            return this;
        }

        @Override
        public Builder size(int width, int height) {
            super.size(width, height);
            return this;
        }

        @Override
        public Builder placeAt(int x, int y) {
            super.placeAt(x, y);
            return this;
        }

        @Override
        public GTextBox build() {
            return (GTextBox) super.build();
        }
    }
}
