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
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

public class GTextBox extends GTextPanel {

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
                Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String content = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        this.putText(cursorYPos, cursorXPos, content);
                        this.moveCursor(content);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_BACK)) {

        } else {
            if (isPrintable(typedChar)) {
                String content = String.valueOf(typedChar);
                this.putText(cursorYPos, cursorXPos, content);
                this.moveCursor(content);
            }
        }
    }

    public boolean isPrintable(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
                c != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }

    private void moveCursor(String content) {
        this.cursorXPos += content.length();
        while (cursorXPos > getMaxStringLength()) {
            cursorYPos++;
            cursorXPos -= getMaxStringLength();
        }
        this.cursorY = getContentHeight(cursorYPos);
        this.cursorX = renderer.getStringWidth(getText().get(cursorYPos).substring(0, cursorXPos));
    }

    @Override
    public void draw(int mouseXIn, int mouseYIn) {
        super.draw(mouseXIn, mouseYIn);

        System.out.println(cursorX + " " + cursorY + " " + mouseXIn + " " + mouseYIn);

        //if (System.currentTimeMillis() % 1000 >= 500) {
            StyleMap.current().drawIcon(StyleMap.Icon.DECLINE, cursorX, cursorY, 32);
        //}
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
