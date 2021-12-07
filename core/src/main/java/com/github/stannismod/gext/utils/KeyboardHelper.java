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

package com.github.stannismod.gext.utils;

import org.lwjgl.input.Keyboard;

public class KeyboardHelper {

    public static final int KEY_CONTROL = -222;
    public static final int KEY_SHIFT = KEY_CONTROL - 1;

    public static boolean isKeyDown(int key) {
        switch (key) {
            case KEY_CONTROL:
                return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            case KEY_SHIFT:
                return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            default:
                return Keyboard.isKeyDown(key);
        }
    }

    public static boolean isKeysDown(int... keys) {
        for (int key : keys) {
            if (!isKeyDown(key)) {
                return false;
            }
        }
        return true;
    }
}
