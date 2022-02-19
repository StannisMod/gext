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

import com.github.stannismod.gext.api.adapter.IKeyboard;

public class Keyboard {

    public static int
            KEY_SPACE         ,
            KEY_APOSTROPHE    ,
            KEY_COMMA         ,
            KEY_MINUS         ,
            KEY_PERIOD        ,
            KEY_SLASH         ,
            KEY_0             ,
            KEY_1             ,
            KEY_2             ,
            KEY_3             ,
            KEY_4             ,
            KEY_5             ,
            KEY_6             ,
            KEY_7             ,
            KEY_8             ,
            KEY_9             ,
            KEY_SEMICOLON     ,
            KEY_EQUAL         ,
            KEY_A             ,
            KEY_B             ,
            KEY_C             ,
            KEY_D             ,
            KEY_E             ,
            KEY_F             ,
            KEY_G             ,
            KEY_H             ,
            KEY_I             ,
            KEY_J             ,
            KEY_K             ,
            KEY_L             ,
            KEY_M             ,
            KEY_N             ,
            KEY_O             ,
            KEY_P             ,
            KEY_Q             ,
            KEY_R             ,
            KEY_S             ,
            KEY_T             ,
            KEY_U             ,
            KEY_V             ,
            KEY_W             ,
            KEY_X             ,
            KEY_Y             ,
            KEY_Z             ,
            KEY_LBRACKET      ,
            KEY_BACKSLASH     ,
            KEY_RBRACKET      ,
            KEY_GRAVE_ACCENT  ,
            KEY_WORLD_1       ,
            KEY_WORLD_2       ;

    /** Function keys. */
    public static int 
            KEY_ESCAPE        ,
            KEY_ENTER         ,
            KEY_TAB           ,
            KEY_BACKSPACE     ,
            KEY_INSERT        ,
            KEY_DELETE        ,
            KEY_RIGHT         ,
            KEY_LEFT          ,
            KEY_DOWN          ,
            KEY_UP            ,
            KEY_PAGE_UP       ,
            KEY_PAGE_DOWN     ,
            KEY_HOME          ,
            KEY_END           ,
            KEY_CAPS_LOCK     ,
            KEY_SCROLL_LOCK   ,
            KEY_NUM_LOCK      ,
            KEY_PRINT_SCREEN  ,
            KEY_PAUSE         ,
            KEY_F1            ,
            KEY_F2            ,
            KEY_F3            ,
            KEY_F4            ,
            KEY_F5            ,
            KEY_F6            ,
            KEY_F7            ,
            KEY_F8            ,
            KEY_F9            ,
            KEY_F10           ,
            KEY_F11           ,
            KEY_F12           ,
            KEY_F13           ,
            KEY_F14           ,
            KEY_F15           ,
            KEY_F16           ,
            KEY_F17           ,
            KEY_F18           ,
            KEY_F19           ,
            KEY_F20           ,
            KEY_F21           ,
            KEY_F22           ,
            KEY_F23           ,
            KEY_F24           ,
            KEY_F25           ,
            KEY_KP_0          ,
            KEY_KP_1          ,
            KEY_KP_2          ,
            KEY_KP_3          ,
            KEY_KP_4          ,
            KEY_KP_5          ,
            KEY_KP_6          ,
            KEY_KP_7          ,
            KEY_KP_8          ,
            KEY_KP_9          ,
            KEY_KP_DECIMAL    ,
            KEY_KP_DIVIDE     ,
            KEY_KP_MULTIPLY   ,
            KEY_KP_SUBTRACT   ,
            KEY_KP_ADD        ,
            KEY_KP_ENTER      ,
            KEY_KP_EQUAL      ,
            KEY_LSHIFT        ,
            KEY_LCONTROL      ,
            KEY_LALT          ,
            KEY_LSUPER        ,
            KEY_RSHIFT        ,
            KEY_RCONTROL      ,
            KEY_RALT          ,
            KEY_RSUPER        ,
            KEY_MENU          ;
    
    public static final int KEY_CONTROL = -222;
    public static final int KEY_SHIFT = KEY_CONTROL - 1;

    private static IKeyboard keyboard;

    public static void setKeyboard(IKeyboard keyboard) {
        Keyboard.keyboard = keyboard;
        KEY_SPACE         = keyboard.getKey("SPACE");
        KEY_APOSTROPHE    = keyboard.getKey("APOSTROPHE");
        KEY_COMMA         = keyboard.getKey("COMMA");
        KEY_MINUS         = keyboard.getKey("MINUS");
        KEY_PERIOD        = keyboard.getKey("PERIOD");
        KEY_SLASH         = keyboard.getKey("SLASH");
        KEY_0             = keyboard.getKey("0");
        KEY_1             = keyboard.getKey("1");
        KEY_2             = keyboard.getKey("2");
        KEY_3             = keyboard.getKey("3");
        KEY_4             = keyboard.getKey("4");
        KEY_5             = keyboard.getKey("5");
        KEY_6             = keyboard.getKey("6");
        KEY_7             = keyboard.getKey("7");
        KEY_8             = keyboard.getKey("8");
        KEY_9             = keyboard.getKey("9");
        KEY_SEMICOLON     = keyboard.getKey("SEMICOLON");
        KEY_EQUAL         = keyboard.getKey("EQUAL");
        KEY_A             = keyboard.getKey("A");
        KEY_B             = keyboard.getKey("B");
        KEY_C             = keyboard.getKey("C");
        KEY_D             = keyboard.getKey("D");
        KEY_E             = keyboard.getKey("E");
        KEY_F             = keyboard.getKey("F");
        KEY_G             = keyboard.getKey("G");
        KEY_H             = keyboard.getKey("H");
        KEY_I             = keyboard.getKey("I");
        KEY_J             = keyboard.getKey("J");
        KEY_K             = keyboard.getKey("K");
        KEY_L             = keyboard.getKey("L");
        KEY_M             = keyboard.getKey("M");
        KEY_N             = keyboard.getKey("N");
        KEY_O             = keyboard.getKey("O");
        KEY_P             = keyboard.getKey("P");
        KEY_Q             = keyboard.getKey("Q");
        KEY_R             = keyboard.getKey("R");
        KEY_S             = keyboard.getKey("S");
        KEY_T             = keyboard.getKey("T");
        KEY_U             = keyboard.getKey("U");
        KEY_V             = keyboard.getKey("V");
        KEY_W             = keyboard.getKey("W");
        KEY_X             = keyboard.getKey("X");
        KEY_Y             = keyboard.getKey("Y");
        KEY_Z             = keyboard.getKey("Z");
        KEY_LBRACKET  = keyboard.getKey("LEFT_BRACKET");
        KEY_BACKSLASH     = keyboard.getKey("BACKSLASH");
        KEY_RBRACKET = keyboard.getKey("RIGHT_BRACKET");
        KEY_GRAVE_ACCENT  = keyboard.getKey("GRAVE_ACCENT");
        KEY_WORLD_1       = keyboard.getKey("WORLD_1");
        KEY_WORLD_2       = keyboard.getKey("WORLD_2");
        
        KEY_ESCAPE        = keyboard.getKey("ESCAPE");
        KEY_ENTER         = keyboard.getKey("ENTER");
        KEY_TAB           = keyboard.getKey("TAB");
        KEY_BACKSPACE     = keyboard.getKey("BACKSPACE");
        KEY_INSERT        = keyboard.getKey("INSERT");
        KEY_DELETE        = keyboard.getKey("DELETE");
        KEY_RIGHT         = keyboard.getKey("RIGHT");
        KEY_LEFT          = keyboard.getKey("LEFT");
        KEY_DOWN          = keyboard.getKey("DOWN");
        KEY_UP            = keyboard.getKey("UP");
        KEY_PAGE_UP       = keyboard.getKey("PAGE_UP");
        KEY_PAGE_DOWN     = keyboard.getKey("PAGE_DOWN");
        KEY_HOME          = keyboard.getKey("HOME");
        KEY_END           = keyboard.getKey("END");
        KEY_CAPS_LOCK     = keyboard.getKey("CAPS_LOCK");
        KEY_SCROLL_LOCK   = keyboard.getKey("SCROLL_LOCK");
        KEY_NUM_LOCK      = keyboard.getKey("NUM_LOCK");
        KEY_PRINT_SCREEN  = keyboard.getKey("PRINT_SCREEN");
        KEY_PAUSE         = keyboard.getKey("PAUSE");
        KEY_F1            = keyboard.getKey("F1");
        KEY_F2            = keyboard.getKey("F2");
        KEY_F3            = keyboard.getKey("F3");
        KEY_F4            = keyboard.getKey("F4");
        KEY_F5            = keyboard.getKey("F5");
        KEY_F6            = keyboard.getKey("F6");
        KEY_F7            = keyboard.getKey("F7");
        KEY_F8            = keyboard.getKey("F8");
        KEY_F9            = keyboard.getKey("F9");
        KEY_F10           = keyboard.getKey("F10");
        KEY_F11           = keyboard.getKey("F11");
        KEY_F12           = keyboard.getKey("F12");
        KEY_F13           = keyboard.getKey("F13");
        KEY_F14           = keyboard.getKey("F14");
        KEY_F15           = keyboard.getKey("F15");
        KEY_F16           = keyboard.getKey("F16");
        KEY_F17           = keyboard.getKey("F17");
        KEY_F18           = keyboard.getKey("F18");
        KEY_F19           = keyboard.getKey("F19");
        KEY_F20           = keyboard.getKey("F20");
        KEY_F21           = keyboard.getKey("F21");
        KEY_F22           = keyboard.getKey("F22");
        KEY_F23           = keyboard.getKey("F23");
        KEY_F24           = keyboard.getKey("F24");
        KEY_F25           = keyboard.getKey("F25");
        KEY_KP_0          = keyboard.getKey("KP_0");
        KEY_KP_1          = keyboard.getKey("KP_1");
        KEY_KP_2          = keyboard.getKey("KP_2");
        KEY_KP_3          = keyboard.getKey("KP_3");
        KEY_KP_4          = keyboard.getKey("KP_4");
        KEY_KP_5          = keyboard.getKey("KP_5");
        KEY_KP_6          = keyboard.getKey("KP_6");
        KEY_KP_7          = keyboard.getKey("KP_7");
        KEY_KP_8          = keyboard.getKey("KP_8");
        KEY_KP_9          = keyboard.getKey("KP_9");
        KEY_KP_DECIMAL    = keyboard.getKey("KP_DECIMAL");
        KEY_KP_DIVIDE     = keyboard.getKey("KP_DIVIDE");
        KEY_KP_MULTIPLY   = keyboard.getKey("KP_MULTIPLY");
        KEY_KP_SUBTRACT   = keyboard.getKey("KP_SUBTRACT");
        KEY_KP_ADD        = keyboard.getKey("KP_ADD");
        KEY_KP_ENTER      = keyboard.getKey("KP_ENTER");
        KEY_KP_EQUAL      = keyboard.getKey("KP_EQUAL");
        KEY_LSHIFT    = keyboard.getKey("LEFT_SHIFT");
        KEY_LCONTROL  = keyboard.getKey("LEFT_CONTROL");
        KEY_LALT      = keyboard.getKey("LEFT_ALT");
        KEY_LSUPER    = keyboard.getKey("LEFT_SUPER");
        KEY_RSHIFT   = keyboard.getKey("RIGHT_SHIFT");
        KEY_RCONTROL = keyboard.getKey("RIGHT_CONTROL");
        KEY_RALT     = keyboard.getKey("RIGHT_ALT");
        KEY_RSUPER   = keyboard.getKey("RSUPER");
        KEY_MENU          = keyboard.getKey("MENU");
    }

    public static boolean isKeyDown(int key) {
        switch (key) {
            case KEY_CONTROL:
                return keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            case KEY_SHIFT:
                return keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
            default:
                return keyboard.isKeyDown(key);
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
