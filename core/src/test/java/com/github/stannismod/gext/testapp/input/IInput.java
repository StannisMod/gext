package com.github.stannismod.gext.testapp.input;

import org.joml.Vector2f;

public interface IInput {

    boolean isKeyDown(int key);

    boolean isKeyPressed(int key);

    boolean isKeyReleased(int key);

    boolean isMouseButtonDown(int button);

    Vector2f getMousePosition();

    void update();
}
