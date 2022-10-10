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

import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final long window;

    private final boolean[] keys;

    private static final Vector2f mousePos = new Vector2f();
    private static final double[] x = new double[1], y = new double[1];
    private static final int[] winWidth = new int[1], winHeight = new int[1];

    public Input(long window) {
        this.window = window;
        this.keys = new boolean[GLFW_KEY_LAST];
        for (int i = 0; i < GLFW_KEY_LAST; i++)
            keys[i] = false;
    }

    public boolean isKeyDown(int key) {
        return glfwGetKey(window, key) == 1;
    }

    public boolean isKeyPressed(int key) {
        return (isKeyDown(key) && !keys[key]);
    }

    public boolean isKeyReleased(int key) {
        return (!isKeyDown(key) && keys[key]);
    }

    public boolean isMouseButtonDown(int button) {
        return glfwGetMouseButton(window, button) == 1;
    }

    public Vector2f getMousePosition() {
        glfwGetCursorPos(window, x, y);

        glfwGetWindowSize(window, winWidth, winHeight);

        mousePos.set((float) x[0] - (winWidth[0] / 2.0f), -((float) y[0] - (winHeight[0] / 2.0f)));

        return mousePos;
    }

    public void update() {
        for (int i = 32; i < GLFW_KEY_LAST; i++)
            keys[i] = isKeyDown(i);
    }
}
