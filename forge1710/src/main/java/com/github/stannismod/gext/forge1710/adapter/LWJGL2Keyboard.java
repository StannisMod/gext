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

package com.github.stannismod.gext.forge1710.adapter;

import com.github.stannismod.gext.api.adapter.IKeyboard;
import org.lwjgl.input.Keyboard;

public class LWJGL2Keyboard implements IKeyboard {
    @Override
    public boolean isKeyDown(final int key) {
        return Keyboard.isKeyDown(key);
    }

    @Override
    public int getKey(final String name) {
        switch (name) {
            case "SPACE"         : return Keyboard.KEY_SPACE;
            case "APOSTROPHE"    : return Keyboard.KEY_APOSTROPHE;
            case "COMMA"         : return Keyboard.KEY_COMMA;
            case "MINUS"         : return Keyboard.KEY_MINUS;
            case "PERIOD"        : return Keyboard.KEY_PERIOD;
            case "SLASH"         : return Keyboard.KEY_SLASH;
            case "0"             : return Keyboard.KEY_0;
            case "1"             : return Keyboard.KEY_1;
            case "2"             : return Keyboard.KEY_2;
            case "3"             : return Keyboard.KEY_3;
            case "4"             : return Keyboard.KEY_4;
            case "5"             : return Keyboard.KEY_5;
            case "6"             : return Keyboard.KEY_6;
            case "7"             : return Keyboard.KEY_7;
            case "8"             : return Keyboard.KEY_8;
            case "9"             : return Keyboard.KEY_9;
            case "SEMICOLON"     : return Keyboard.KEY_SEMICOLON;
            case "EQUAL"         : return Keyboard.KEY_EQUALS;
            case "A"             : return Keyboard.KEY_A;
            case "B"             : return Keyboard.KEY_B;
            case "C"             : return Keyboard.KEY_C;
            case "D"             : return Keyboard.KEY_D;
            case "E"             : return Keyboard.KEY_E;
            case "F"             : return Keyboard.KEY_F;
            case "G"             : return Keyboard.KEY_G;
            case "H"             : return Keyboard.KEY_H;
            case "I"             : return Keyboard.KEY_I;
            case "J"             : return Keyboard.KEY_J;
            case "K"             : return Keyboard.KEY_K;
            case "L"             : return Keyboard.KEY_L;
            case "M"             : return Keyboard.KEY_M;
            case "N"             : return Keyboard.KEY_N;
            case "O"             : return Keyboard.KEY_O;
            case "P"             : return Keyboard.KEY_P;
            case "Q"             : return Keyboard.KEY_Q;
            case "R"             : return Keyboard.KEY_R;
            case "S"             : return Keyboard.KEY_S;
            case "T"             : return Keyboard.KEY_T;
            case "U"             : return Keyboard.KEY_U;
            case "V"             : return Keyboard.KEY_V;
            case "W"             : return Keyboard.KEY_W;
            case "X"             : return Keyboard.KEY_X;
            case "Y"             : return Keyboard.KEY_Y;
            case "Z"             : return Keyboard.KEY_Z;
            case "LEFT_BRACKET"  : return Keyboard.KEY_LBRACKET;
            case "BACKSLASH"     : return Keyboard.KEY_BACKSLASH;
            case "RIGHT_BRACKET" : return Keyboard.KEY_RBRACKET;
            case "GRAVE_ACCENT"  : return Keyboard.KEY_GRAVE;

            case "ESCAPE"        : return Keyboard.KEY_ESCAPE;
            case "ENTER"         : return Keyboard.KEY_RETURN;
            case "TAB"           : return Keyboard.KEY_TAB;
            case "BACKSPACE"     : return Keyboard.KEY_BACK;
            case "INSERT"        : return Keyboard.KEY_INSERT;
            case "DELETE"        : return Keyboard.KEY_DELETE;
            case "RIGHT"         : return Keyboard.KEY_RIGHT;
            case "LEFT"          : return Keyboard.KEY_LEFT;
            case "DOWN"          : return Keyboard.KEY_DOWN;
            case "UP"            : return Keyboard.KEY_UP;
            case "PAGE_UP"       : return Keyboard.KEY_PRIOR;
            case "PAGE_DOWN"     : return Keyboard.KEY_NEXT;
            case "HOME"          : return Keyboard.KEY_HOME;
            case "END"           : return Keyboard.KEY_END;
            case "CAPS_LOCK"     : return Keyboard.KEY_CAPITAL;
            case "SCROLL_LOCK"   : return Keyboard.KEY_SCROLL;
            case "NUM_LOCK"      : return Keyboard.KEY_NUMLOCK;
            case "PAUSE"         : return Keyboard.KEY_PAUSE;
            case "F1"            : return Keyboard.KEY_F1;
            case "F2"            : return Keyboard.KEY_F2;
            case "F3"            : return Keyboard.KEY_F3;
            case "F4"            : return Keyboard.KEY_F4;
            case "F5"            : return Keyboard.KEY_F5;
            case "F6"            : return Keyboard.KEY_F6;
            case "F7"            : return Keyboard.KEY_F7;
            case "F8"            : return Keyboard.KEY_F8;
            case "F9"            : return Keyboard.KEY_F9;
            case "F10"           : return Keyboard.KEY_F10;
            case "F11"           : return Keyboard.KEY_F11;
            case "F12"           : return Keyboard.KEY_F12;
            case "F13"           : return Keyboard.KEY_F13;
            case "F14"           : return Keyboard.KEY_F14;
            case "F15"           : return Keyboard.KEY_F15;
            case "F16"           : return Keyboard.KEY_F16;
            case "F17"           : return Keyboard.KEY_F17;
            case "F18"           : return Keyboard.KEY_F18;
            case "F19"           : return Keyboard.KEY_F19;
            case "KP_DECIMAL"    : return Keyboard.KEY_DECIMAL;
            case "KP_DIVIDE"     : return Keyboard.KEY_DIVIDE;
            case "KP_MULTIPLY"   : return Keyboard.KEY_MULTIPLY;
            case "KP_SUBTRACT"   : return Keyboard.KEY_SUBTRACT;
            case "KP_ADD"        : return Keyboard.KEY_ADD;
            case "KP_ENTER"      : return Keyboard.KEY_NUMPADENTER;
            case "KP_EQUAL"      : return Keyboard.KEY_NUMPADEQUALS;
            case "LEFT_SHIFT"    : return Keyboard.KEY_LSHIFT;
            case "LEFT_CONTROL"  : return Keyboard.KEY_LCONTROL;
            case "LEFT_ALT"      : return Keyboard.KEY_LMENU;
            case "RIGHT_SHIFT"   : return Keyboard.KEY_RSHIFT;
            case "RIGHT_CONTROL" : return Keyboard.KEY_RCONTROL;
            case "RIGHT_ALT"     : return Keyboard.KEY_RMENU;
            default              : throw new IllegalArgumentException("Unknown key with name: " + name);
        }
    }
}
