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

package com.github.stannismod.gext.forge116.adapter;

import com.github.stannismod.gext.api.adapter.IKeyboard;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class LWJGL3Keyboard implements IKeyboard {
    @Override
    public boolean isKeyDown(final int key) {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == 1;
    }

    @Override
    public int getKey(final String name) {
        switch (name) {
            case "SPACE"         : return GLFW.GLFW_KEY_SPACE;
            case "APOSTROPHE"    : return GLFW.GLFW_KEY_APOSTROPHE;
            case "COMMA"         : return GLFW.GLFW_KEY_COMMA;
            case "MINUS"         : return GLFW.GLFW_KEY_MINUS;
            case "PERIOD"        : return GLFW.GLFW_KEY_PERIOD;
            case "SLASH"         : return GLFW.GLFW_KEY_SLASH;
            case "0"             : return GLFW.GLFW_KEY_0;
            case "1"             : return GLFW.GLFW_KEY_1;
            case "2"             : return GLFW.GLFW_KEY_2;
            case "3"             : return GLFW.GLFW_KEY_3;
            case "4"             : return GLFW.GLFW_KEY_4;
            case "5"             : return GLFW.GLFW_KEY_5;
            case "6"             : return GLFW.GLFW_KEY_6;
            case "7"             : return GLFW.GLFW_KEY_7;
            case "8"             : return GLFW.GLFW_KEY_8;
            case "9"             : return GLFW.GLFW_KEY_9;
            case "SEMICOLON"     : return GLFW.GLFW_KEY_SEMICOLON;
            case "EQUAL"         : return GLFW.GLFW_KEY_EQUAL;
            case "A"             : return GLFW.GLFW_KEY_A;
            case "B"             : return GLFW.GLFW_KEY_B;
            case "C"             : return GLFW.GLFW_KEY_C;
            case "D"             : return GLFW.GLFW_KEY_D;
            case "E"             : return GLFW.GLFW_KEY_E;
            case "F"             : return GLFW.GLFW_KEY_F;
            case "G"             : return GLFW.GLFW_KEY_G;
            case "H"             : return GLFW.GLFW_KEY_H;
            case "I"             : return GLFW.GLFW_KEY_I;
            case "J"             : return GLFW.GLFW_KEY_J;
            case "K"             : return GLFW.GLFW_KEY_K;
            case "L"             : return GLFW.GLFW_KEY_L;
            case "M"             : return GLFW.GLFW_KEY_M;
            case "N"             : return GLFW.GLFW_KEY_N;
            case "O"             : return GLFW.GLFW_KEY_O;
            case "P"             : return GLFW.GLFW_KEY_P;
            case "Q"             : return GLFW.GLFW_KEY_Q;
            case "R"             : return GLFW.GLFW_KEY_R;
            case "S"             : return GLFW.GLFW_KEY_S;
            case "T"             : return GLFW.GLFW_KEY_T;
            case "U"             : return GLFW.GLFW_KEY_U;
            case "V"             : return GLFW.GLFW_KEY_V;
            case "W"             : return GLFW.GLFW_KEY_W;
            case "X"             : return GLFW.GLFW_KEY_X;
            case "Y"             : return GLFW.GLFW_KEY_Y;
            case "Z"             : return GLFW.GLFW_KEY_Z;
            case "LEFT_BRACKET"  : return GLFW.GLFW_KEY_LEFT_BRACKET;
            case "BACKSLASH"     : return GLFW.GLFW_KEY_BACKSLASH;
            case "RIGHT_BRACKET" : return GLFW.GLFW_KEY_RIGHT_BRACKET;
            case "GRAVE_ACCENT"  : return GLFW.GLFW_KEY_GRAVE_ACCENT;
            case "WORLD_1"       : return GLFW.GLFW_KEY_WORLD_1;
            case "WORLD_2"       : return GLFW.GLFW_KEY_WORLD_2;

            case "ESCAPE"        : return GLFW.GLFW_KEY_ESCAPE;
            case "ENTER"         : return GLFW.GLFW_KEY_ENTER;
            case "TAB"           : return GLFW.GLFW_KEY_TAB;
            case "BACKSPACE"     : return GLFW.GLFW_KEY_BACKSPACE;
            case "INSERT"        : return GLFW.GLFW_KEY_INSERT;
            case "DELETE"        : return GLFW.GLFW_KEY_DELETE;
            case "RIGHT"         : return GLFW.GLFW_KEY_RIGHT;
            case "LEFT"          : return GLFW.GLFW_KEY_LEFT;
            case "DOWN"          : return GLFW.GLFW_KEY_DOWN;
            case "UP"            : return GLFW.GLFW_KEY_UP;
            case "PAGE_UP"       : return GLFW.GLFW_KEY_PAGE_UP;
            case "PAGE_DOWN"     : return GLFW.GLFW_KEY_PAGE_DOWN;
            case "HOME"          : return GLFW.GLFW_KEY_HOME;
            case "END"           : return GLFW.GLFW_KEY_END;
            case "CAPS_LOCK"     : return GLFW.GLFW_KEY_CAPS_LOCK;
            case "SCROLL_LOCK"   : return GLFW.GLFW_KEY_SCROLL_LOCK;
            case "NUM_LOCK"      : return GLFW.GLFW_KEY_NUM_LOCK;
            case "PRINT_SCREEN"  : return GLFW.GLFW_KEY_PRINT_SCREEN;
            case "PAUSE"         : return GLFW.GLFW_KEY_PAUSE;
            case "F1"            : return GLFW.GLFW_KEY_F1;
            case "F2"            : return GLFW.GLFW_KEY_F2;
            case "F3"            : return GLFW.GLFW_KEY_F3;
            case "F4"            : return GLFW.GLFW_KEY_F4;
            case "F5"            : return GLFW.GLFW_KEY_F5;
            case "F6"            : return GLFW.GLFW_KEY_F6;
            case "F7"            : return GLFW.GLFW_KEY_F7;
            case "F8"            : return GLFW.GLFW_KEY_F8;
            case "F9"            : return GLFW.GLFW_KEY_F9;
            case "F10"           : return GLFW.GLFW_KEY_F10;
            case "F11"           : return GLFW.GLFW_KEY_F11;
            case "F12"           : return GLFW.GLFW_KEY_F12;
            case "F13"           : return GLFW.GLFW_KEY_F13;
            case "F14"           : return GLFW.GLFW_KEY_F14;
            case "F15"           : return GLFW.GLFW_KEY_F15;
            case "F16"           : return GLFW.GLFW_KEY_F16;
            case "F17"           : return GLFW.GLFW_KEY_F17;
            case "F18"           : return GLFW.GLFW_KEY_F18;
            case "F19"           : return GLFW.GLFW_KEY_F19;
            case "F20"           : return GLFW.GLFW_KEY_F20;
            case "F21"           : return GLFW.GLFW_KEY_F21;
            case "F22"           : return GLFW.GLFW_KEY_F22;
            case "F23"           : return GLFW.GLFW_KEY_F23;
            case "F24"           : return GLFW.GLFW_KEY_F24;
            case "F25"           : return GLFW.GLFW_KEY_F25;
            case "KP_0"          : return GLFW.GLFW_KEY_KP_0;
            case "KP_1"          : return GLFW.GLFW_KEY_KP_1;
            case "KP_2"          : return GLFW.GLFW_KEY_KP_2;
            case "KP_3"          : return GLFW.GLFW_KEY_KP_3;
            case "KP_4"          : return GLFW.GLFW_KEY_KP_4;
            case "KP_5"          : return GLFW.GLFW_KEY_KP_5;
            case "KP_6"          : return GLFW.GLFW_KEY_KP_6;
            case "KP_7"          : return GLFW.GLFW_KEY_KP_7;
            case "KP_8"          : return GLFW.GLFW_KEY_KP_8;
            case "KP_9"          : return GLFW.GLFW_KEY_KP_9;
            case "KP_DECIMAL"    : return GLFW.GLFW_KEY_KP_DECIMAL;
            case "KP_DIVIDE"     : return GLFW.GLFW_KEY_KP_DIVIDE;
            case "KP_MULTIPLY"   : return GLFW.GLFW_KEY_KP_MULTIPLY;
            case "KP_SUBTRACT"   : return GLFW.GLFW_KEY_KP_SUBTRACT;
            case "KP_ADD"        : return GLFW.GLFW_KEY_KP_ADD;
            case "KP_ENTER"      : return GLFW.GLFW_KEY_KP_ENTER;
            case "KP_EQUAL"      : return GLFW.GLFW_KEY_KP_EQUAL;
            case "LEFT_SHIFT"        : return GLFW.GLFW_KEY_LEFT_SHIFT;
            case "LEFT_CONTROL"      : return GLFW.GLFW_KEY_LEFT_CONTROL;
            case "LEFT_ALT"          : return GLFW.GLFW_KEY_LEFT_ALT;
            case "LEFT_SUPER"        : return GLFW.GLFW_KEY_LEFT_SUPER;
            case "RIGHT_SHIFT"        : return GLFW.GLFW_KEY_RIGHT_SHIFT;
            case "RIGHT_CONTROL"      : return GLFW.GLFW_KEY_RIGHT_CONTROL;
            case "RIGHT_ALT"          : return GLFW.GLFW_KEY_RIGHT_ALT;
            case "RIGHT_SUPER"        : return GLFW.GLFW_KEY_RIGHT_SUPER;
            case "MENU"          : return GLFW.GLFW_KEY_MENU;
            default              : throw new IllegalArgumentException("Unknown key with name: " + name);
        }
    }
}
