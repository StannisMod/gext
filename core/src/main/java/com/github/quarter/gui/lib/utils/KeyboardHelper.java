package com.github.quarter.gui.lib.utils;

import org.lwjgl.input.Keyboard;

public class KeyboardHelper {

    public static final int KEY_CONTROL = -222;

    public static boolean isKeyDown(int key) {
        switch (key) {
            case KEY_CONTROL:
                return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
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
