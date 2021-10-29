package com.github.quarter.gui.lib.utils;

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
