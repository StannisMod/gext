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

package com.github.quarter.gui.lib.components.text;

import com.github.quarter.gui.lib.api.adapter.IFontRenderer;
import com.github.quarter.gui.lib.utils.KeyboardHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import static org.lwjgl.input.Keyboard.*;

public class GTextBox extends GTextPanel {

    private boolean initialShift;

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        if (!hasFocus()) {
            return;
        }

        if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_CONTROL)) {
            if (KeyboardHelper.isKeyDown(KEY_C)) {
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(
                                new StringSelection(getSelectedText()),
                                null
                        );
            } else if (KeyboardHelper.isKeyDown(KEY_V)) {
                Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        String content = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        this.putText(cursor.yPos(), cursor.xPos(), content);
                        this.moveCursorAndSelection(content);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (KeyboardHelper.isKeyDown(KEY_UP)) {
                if (cursor.yPos() > 0) {
                    cursor.setYPos(cursor.yPos() - 1);
                    return;
                }
                cursor.setXPos(0);
                this.updateCursor(cursor.yPos(), cursor.xPos(), true);
            }
        } else if (KeyboardHelper.isKeyDown(KEY_BACK)) {
            if (cursor.xPos() == 0) {
                if (cursor.yPos() == 0) {
                    return;
                }
                int pos = getText().get(cursor.yPos() - 1).length();
                if (getText().size() > cursor.yPos()) {
                    String removed = getText().remove(cursor.yPos());
                    appendToLine(cursor.yPos() - 1, getText().get(cursor.yPos() - 1).length(), removed);
                }
                this.updateCursor(cursor.yPos() - 1, pos);
            } else {
                String line = getText().get(cursor.yPos());
                getText().set(cursor.yPos(), line.substring(0, cursor.xPos() - 1) + line.substring(cursor.xPos()));
                this.moveCursorAndSelection(-1, 0);
            }
        } else if (KeyboardHelper.isKeyDown(KEY_RETURN)) {
            if (getLinesCount() >= getMaxLines()) {
                return;
            }
            String content = getText().get(cursor.yPos());
            getText().set(cursor.yPos(), content.substring(0, cursor.xPos()));
            getText().add(cursor.yPos() + 1, content.substring(cursor.xPos()));
            this.updateCursor(cursor.yPos() + 1, 0);
        } else if (KeyboardHelper.isKeyDown(KEY_UP)) {
            this.moveCursorAndSelection(0, -1);
        } else if (KeyboardHelper.isKeyDown(KEY_DOWN)) {
            this.moveCursorAndSelection(0, 1);
        } else if (KeyboardHelper.isKeyDown(KEY_LEFT)) {
            this.moveCursorAndSelection(-1, 0);
        } else if (KeyboardHelper.isKeyDown(KEY_RIGHT)) {
            this.moveCursorAndSelection(1, 0);
        } else {
            if (isPrintable(typedChar)) {
                String content = String.valueOf(typedChar);
                if (canAppendTo(content, cursor.yPos())) {
                    this.putText(cursor.yPos(), cursor.xPos(), content);
                    this.moveCursorAndSelection(content);
                }
                selection.drop();
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

    private void moveCursorAndSelection(String content) {
        moveCursorAndSelection(content.length(), 0);
    }

    private void moveCursorAndSelection(int horizontal, int vertical) {
        if (vertical == 0 && Math.abs(horizontal) == 1) { // in that case we use arrows
            if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT) && !initialShift) {
                initialShift = true;
                selection.moveTo(cursor);
            }

            if (!KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT) && !selection.isEmpty()) {
                if (horizontal == 1) { // right
                    if (!cursor.pointsEnd(selection)) {
                        cursor.moveToEnd(selection);
                    }
                } else {               // left
                    if (!cursor.pointsStart(selection)) {
                        cursor.moveToStart(selection);
                    }
                }
                selection.moveTo(cursor);
                return;
            }
        }

        cursor.setXPos(cursor.xPos() + horizontal);

        while (cursor.xPos() < 0) {
            if (cursor.yPos() <= 0) {
                cursor.setXPos(0);
                break;
            }
            cursor.setYPos(cursor.yPos() - 1);
            cursor.setXPos(cursor.xPos() + getText().get(cursor.yPos()).length() + 1);
        }
        while (cursor.xPos() > getText().get(cursor.yPos()).length()) {
            if (cursor.yPos() == getLinesCount() - 1) {
                break;
            }
            cursor.setXPos(cursor.xPos() - getText().get(cursor.yPos()).length());
            cursor.setYPos(cursor.yPos() + 1);
        }

        cursor.setYPos(cursor.yPos() + vertical);

        if (cursor.yPos() < 0) {
            cursor.setYPos(0);
        }
        if (cursor.yPos() > getLinesCount() - 1) {
            cursor.setYPos(getLinesCount() - 1);
        }

        if (cursor.xPos() > getText().get(cursor.yPos()).length()) {
            cursor.setXPos(getText().get(cursor.yPos()).length());
        }
        this.recalculateCursorFromPos();

        if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT)) {
            selection.updateFrom(cursor);
        } else {
            initialShift = false;
            selection.moveTo(cursor);
        }
    }

    @Override
    public void draw(int mouseXIn, int mouseYIn) {
        if (hasFocus()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(getXOffset() - 0.5F + cursor.x(), getYOffset() + cursor.y(), 0.0F);
            GL11.glScalef(0.5F, 1.0F, 1.0F);

            if (System.currentTimeMillis() % 1000 >= 500) {
                StyleMap.current().drawProgressBar(1, 0, 0, 1, getTextHeight(), 10.0F);
            }

            GL11.glPopMatrix();
        }

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
