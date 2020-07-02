package ru.quarter.gui.lib.utils;

public class OffsetProperties {

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
