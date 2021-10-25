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

public class Cursor {

    private int xPos;
    private int yPos;
    private int x;
    private int y;
    private boolean rightTrapped;

    public int xPos() {
        return xPos;
    }

    public void setXPos(final int xPos) {
        this.xPos = xPos;
    }

    public int yPos() {
        return yPos;
    }

    public void setYPos(final int yPos) {
        this.yPos = yPos;
    }

    public int x() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public boolean rightTrapped() {
        return rightTrapped;
    }

    public void setRightTrapped(final boolean rightTrapped) {
        this.rightTrapped = rightTrapped;
    }
}
