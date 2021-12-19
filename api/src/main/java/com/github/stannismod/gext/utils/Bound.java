/*
 * Copyright 2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.utils;

public enum Bound {
    TOP_LEFT(0.0F, 0.0F),
    TOP_CENTER(0.5F, 0.0F),
    TOP_RIGHT(1.0F, 0.0F),
    BOTTOM_LEFT(0.0F, 1.0F),
    BOTTOM_CENTER(0.5F, 1.0F),
    BOTTOM_RIGHT(1.0F, 1.0F),
    LEFT_TOP(0.0F, 0.0F),
    LEFT_CENTER(0.0F, 0.5F),
    LEFT_BOTTOM(0.0F, 1.0F),
    RIGHT_TOP(1.0F, 0.0F),
    RIGHT_CENTER(1.0F, 0.5F),
    RIGHT_BOTTOM(1.0F, 1.0F),
    CENTER(0.5F, 0.5F);

    private final float multiplierX;
    private final float multiplierY;

    Bound(float multiplierX, float multiplierY) {
        this.multiplierX = multiplierX;
        this.multiplierY = multiplierY;
    }

    public float getMultiplierX() {
        return multiplierX;
    }

    public float getMultiplierY() {
        return multiplierY;
    }
}
