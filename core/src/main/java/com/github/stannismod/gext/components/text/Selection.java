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

package com.github.stannismod.gext.components.text;

public class Selection {

    private boolean enabled;
    private int startX;
    private int startY;
    private int startXPos;
    private int startYPos;
    private int endX;
    private int endY;
    private int endXPos;
    private int endYPos;

    public boolean isEmpty() {
        return startXPos == endXPos && startYPos == endYPos;
    }

    public void moveTo(Cursor cursor) {
        startX = endX = cursor.x();
        startXPos = endXPos = cursor.xPos();
        startY = endY = cursor.y();
        startYPos = endYPos = cursor.yPos();
    }

    // TODO Bug: select area on the one line, now hold shift and press 'up arrow'. Then 'down arrow'. Firstly selected area should be reproduced...
    public void updateFrom(Cursor cursor) {
        if (endYPos < cursor.yPos()) {
            if (!cursor.rightTrapped()) {
                startToEnd();
            }
            updateEndFrom(cursor);
        } else if (endYPos == cursor.yPos()) {
            if (startYPos == endYPos) {
                // they all are on the same line
                if (startXPos < cursor.xPos()) {
                    if (cursor.xPos() < endXPos) {
                        if (cursor.rightTrapped()) {
                            updateEndFrom(cursor);
                        } else {
                            updateStartFrom(cursor);
                        }
                    } else {
                        if (cursor.yPos() < endYPos) {
                            startToEnd();
                        }
                        updateEndFrom(cursor);
                    }
                } else {
                    updateStartFrom(cursor);
                }
            } else if (startYPos < endYPos) {
                updateEndFrom(cursor);
            }
        } else if (startYPos < cursor.yPos()) {
            // now we have cursorYPos < endPos
            if (cursor.rightTrapped()) {
                endToStart();
            }
            updateStartFrom(cursor);
        } else if (cursor.yPos() <= startYPos) {
            if (cursor.rightTrapped()) {
                endToStart();
            }
            updateStartFrom(cursor);
        } else {
            // now we have cursorYPos == startYPos
            // variant startYPos == endYPos was handler earlier
            throw new IllegalStateException("Unhandled variant");
        }
    }

    public void drop() {
        startX = endX = 0;
        startY = endY = 0;
        startXPos = endXPos = 0;
        startYPos = endYPos = 0;
    }

    public void updateStartFrom(Cursor cursor) {
        startXPos = cursor.xPos();
        startYPos = cursor.yPos();
        startX = cursor.x();
        startY = cursor.y();
        cursor.setRightTrapped(false);
    }

    public void updateEndFrom(Cursor cursor) {
        endXPos = cursor.xPos();
        endYPos = cursor.yPos();
        endX = cursor.x();
        endY = cursor.y();
        cursor.setRightTrapped(true);
    }

    public void startToEnd() {
        startYPos = endYPos;
        startXPos = endXPos;
        startY = endY;
        startX = endX;
    }

    public void endToStart() {
        endYPos = startYPos;
        endXPos = startXPos;
        endY = startY;
        endX = startX;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public int startX() {
        return startX;
    }

    public void setStartX(final int startX) {
        this.startX = startX;
    }

    public int startY() {
        return startY;
    }

    public void setStartY(final int startY) {
        this.startY = startY;
    }

    public int startXPos() {
        return startXPos;
    }

    public void setStartXPos(final int startXPos) {
        this.startXPos = startXPos;
    }

    public int startYPos() {
        return startYPos;
    }

    public void setStartYPos(final int startYPos) {
        this.startYPos = startYPos;
    }

    public int endX() {
        return endX;
    }

    public void setEndX(final int endX) {
        this.endX = endX;
    }

    public int endY() {
        return endY;
    }

    public void setEndY(final int endY) {
        this.endY = endY;
    }

    public int endXPos() {
        return endXPos;
    }

    public void setEndXPos(final int endXPos) {
        this.endXPos = endXPos;
    }

    public int endYPos() {
        return endYPos;
    }

    public void setEndYPos(final int endYPos) {
        this.endYPos = endYPos;
    }
}
