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
            if (cursorXPos == 0) {
                if (cursorYPos == 0) {
                    return;
                }
                if (getText().size() > cursorYPos) {
                    getText().remove(cursorYPos);
                } else {
                    cursorYPos--;
                }
                cursorXPos = getText().get(cursorYPos).length();
            }
            String line = getText().get(cursorYPos);
            getText().set(cursorYPos, line.substring(0, cursorXPos - 1) + line.substring(cursorXPos));
            cursorXPos--;
            this.recalculateCursorFromPos();
        } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_UP)) {
            this.moveCursor(0, -1);
        } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_DOWN)) {
            this.moveCursor(0, 1);
        } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_LEFT)) {
            this.moveCursor(-1, 0);
        } else if (KeyboardHelper.isKeyDown(Keyboard.KEY_RIGHT)) {
            this.moveCursor(1, 0);
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
        moveCursor(content.length(), 0);
    }

    private void moveCursor(int horizontal, int vertical) {
        this.cursorXPos += horizontal;

        while (cursorXPos < 0) {
            if (cursorYPos <= 0) {
                cursorXPos = 0;
                break;
            }
            cursorYPos--;
            cursorXPos += getText().get(cursorYPos).length() + 1;
        }
        while (cursorXPos > getText().get(cursorYPos).length()) {
            if (cursorYPos == getLinesCount() - 1) {
                break;
            }
            cursorXPos -= getText().get(cursorYPos).length() + 1;
            cursorYPos++;
        }

        this.cursorYPos += vertical;

        if (cursorYPos < 0) {
            cursorYPos = 0;
        }
        if (cursorYPos >= getLinesCount()) {
            cursorYPos = getLinesCount() - 1;
        }

        if (cursorXPos > getText().get(cursorYPos).length()) {
            cursorXPos = getText().get(cursorYPos).length();
        }
        this.recalculateCursorFromPos();
    }

    @Override
    public void draw(int mouseXIn, int mouseYIn) {
        GL11.glPushMatrix();
        GL11.glTranslatef(getXOffset() - 0.5F + cursorX, getYOffset() + cursorY, 0.0F);
        GL11.glScalef(0.5F, 1.0F, 1.0F);

        if (System.currentTimeMillis() % 1000 >= 500) {
            StyleMap.current().drawProgressBar(1, 0, 0, 1, 8, 10.0F);
        }

        GL11.glPopMatrix();

        super.draw(mouseXIn, mouseYIn);
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
