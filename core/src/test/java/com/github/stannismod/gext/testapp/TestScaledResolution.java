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

package com.github.stannismod.gext.testapp;

import com.github.stannismod.gext.api.adapter.IScaledResolution;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

public class TestScaledResolution implements IScaledResolution {

    private final int scaleFactor;
    private final int scaledWidth;
    private final int scaledHeight;

    public TestScaledResolution(final long window) {
        this(window, 1);
    }

    public TestScaledResolution(final long window, final int scaleFactor) {
        this.scaleFactor = scaleFactor;
        if (window == -1) {
            this.scaledWidth = 600;
            this.scaledHeight = 400;
        } else {
            int[] width = new int[1];
            int[] height = new int[1];
            glfwGetWindowSize(window, width, height);
            this.scaledWidth = width[0] / scaleFactor;
            this.scaledHeight = height[0] / scaleFactor;
        }
    }

    @Override
    public int getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public int getScaledWidth() {
        return scaledWidth;
    }

    @Override
    public int getScaledHeight() {
        return scaledHeight;
    }
}
