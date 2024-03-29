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

package com.github.stannismod.gext.utils;

public class OffsetProperties {

    public static final OffsetProperties DEFAULT = new OffsetProperties(0, 0, 0, 0);

    private final float up;
    private final float down;
    private final float left;
    private final float right;

    public OffsetProperties(float up, float down, float left, float right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public float getUp() {
        return up;
    }

    public float getDown() {
        return down;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }
}
