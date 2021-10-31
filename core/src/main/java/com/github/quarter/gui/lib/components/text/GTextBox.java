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

import com.github.quarter.gui.lib.api.menu.IContextMenuElement;
import com.github.quarter.gui.lib.api.menu.IContextMenuList;
import com.github.quarter.gui.lib.menu.MenuBuilder;
import com.github.quarter.gui.lib.utils.KeyboardHelper;
import com.github.quarter.gui.lib.utils.StyleMap;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static com.github.quarter.gui.lib.utils.KeyboardHelper.KEY_CONTROL;
import static com.github.quarter.gui.lib.utils.KeyboardHelper.KEY_SHIFT;
import static org.lwjgl.input.Keyboard.*;

/**
 * Represents fully editable multiline text box.
 * Inherits all features from {@link GTextPanel}
 * <p>
 * Note: this component is still in beta. Some functions can behave wrongly.
 * Please submit fatal errors on GitHub.
 * </p>
 * <p>
 * Supported features:
 * <ul>
 * <li>basic text editing</>
 * <li>working with transfer buffer (Ctrl+C, Ctrl+V)</li>
 * <li>selection by Shift + arrows</li>
 * <li>advanced navigation (arrows in selection, Home-End, PageUp-PageDown)</li>
 * </ul>
 *
 * Coming soon:
 * <ul>
 * <li>editing history (Ctrl+Z, Ctrl+Shift+Z)</li>
 * <li>scrolling feature</li>
 * </ul>
 * </p>
 * @see GTextPanel
 * @since 1.4
 */
public class GTextBox extends GTextPanel {

    private boolean initialShift;

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        super.onKeyPressed(typedChar, keyCode);
        if (!hasFocus()) {
            return;
        }

        if (KeyboardHelper.isKeyDown(KEY_CONTROL)) {
            if (KeyboardHelper.isKeyDown(KEY_V)) {
                pasteFromBuffer();
            } else if (KeyboardHelper.isKeyDown(KEY_UP)) {
                if (cursor.yPos() > 0) {
                    cursor.setYPos(cursor.yPos() - 1);
                    return;
                }
                cursor.setXPos(0);
            }
        } else if (KeyboardHelper.isKeyDown(KEY_BACK)) {
            if (selection.isEmpty()) {
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
                    this.moveCursorAndSelection(-1, 0, true);
                }
            } else {
                cutText(selection.startXPos(), selection.startYPos(), selection.endXPos(), selection.endYPos());
                cursor.moveToStart(selection);
                selection.drop();
            }
        } else if (KeyboardHelper.isKeyDown(KEY_HOME)) {
            if (KeyboardHelper.isKeyDown(KEY_SHIFT)) {
                this.moveCursorAndSelection(-cursor.xPos(), 0, true);
                return;
            }
            cursor.setXPos(0);
        } else if (KeyboardHelper.isKeyDown(KEY_END)) {
            if (KeyboardHelper.isKeyDown(KEY_SHIFT)) {
                this.moveCursorAndSelection(getLineLength(cursor.yPos()) - cursor.xPos(), 0, true);
                return;
            }
            cursor.setXPos(getLineLength(cursor.yPos()));
        } else if (KeyboardHelper.isKeyDown(KEY_PRIOR)) {
            if (KeyboardHelper.isKeyDown(KEY_SHIFT)) {
                this.moveCursorAndSelection(-cursor.xPos(), -cursor.yPos() + 1, true);
                return;
            }
            cursor.setPos(0, 0);
        } else if (KeyboardHelper.isKeyDown(KEY_NEXT)) {
            if (KeyboardHelper.isKeyDown(KEY_SHIFT)) {
                this.moveCursorAndSelection(getLineLength(getLinesCount() - 1) - cursor.xPos(), getLinesCount() - cursor.yPos(), true);
                return;
            }
            cursor.setPos(0, getLinesCount() - 1);
        } else if (KeyboardHelper.isKeyDown(KEY_RETURN)) {
            if (getLinesCount() >= getMaxLines()) {
                return;
            }
            if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT)) {
                getText().add(cursor.yPos() + 1, "");
                cursor.setPos(0, cursor.yPos() + 1);
                return;
            }
            String content = getText().get(cursor.yPos());
            getText().set(cursor.yPos(), content.substring(0, cursor.xPos()));
            getText().add(cursor.yPos() + 1, content.substring(cursor.xPos()));
            this.updateCursor(cursor.yPos() + 1, 0);
        } else if (KeyboardHelper.isKeyDown(KEY_UP)) {
            this.moveCursorAndSelection(0, -1, true);
        } else if (KeyboardHelper.isKeyDown(KEY_DOWN)) {
            this.moveCursorAndSelection(0, 1, true);
        } else if (KeyboardHelper.isKeyDown(KEY_LEFT)) {
            this.moveCursorAndSelection(-1, 0, true);
        } else if (KeyboardHelper.isKeyDown(KEY_RIGHT)) {
            this.moveCursorAndSelection(1, 0, true);
        } else {
            if (isPrintable(typedChar)) {
                String content = String.valueOf(typedChar);
                if (canAppendTo(content, cursor.yPos())) {
                    this.putText(cursor.yPos(), cursor.xPos(), content);
                    this.moveCursorAndSelection(content, false);
                }
                selection.drop();
            }
        }
    }

    public void pasteFromBuffer() {
        Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String content = (String) contents.getTransferData(DataFlavor.stringFlavor);
                this.putText(cursor.yPos(), cursor.xPos(), content);
                this.moveCursorAndSelection(content, true);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
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

    private void moveCursorAndSelection(String content, boolean updateSelection) {
        moveCursorAndSelection(content.length(), 0, updateSelection);
    }

    private void moveCursorAndSelection(int horizontal, int vertical, boolean updateSelection) {
        if (updateSelection) {
            if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT) && !initialShift) {
                initialShift = true;
                selection.moveTo(cursor);
            }

            if (!KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT) && !selection.isEmpty()) {
                if (horizontal == 1 || vertical == 1) { // right or down
                    if (!cursor.pointsEnd(selection)) {
                        cursor.moveToEnd(selection);
                    }
                } else {                                // left or up
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

        if (updateSelection) {
            if (KeyboardHelper.isKeyDown(KeyboardHelper.KEY_SHIFT)) {
                selection.updateFrom(cursor);
            } else {
                initialShift = false;
                selection.moveTo(cursor);
            }
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

    @Override
    public IContextMenuList<? extends IContextMenuElement> constructMenu() {
        return MenuBuilder.<GTextBox>create(50)
                .point("Copy",  (c, p) -> copyToBuffer())
                .point("Paste", (c, p) -> pasteFromBuffer())
                .build();
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GTextBox> extends GTextPanel.Builder<SELF, T> {
        
    }
}
