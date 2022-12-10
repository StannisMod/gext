package com.github.stannismod.gext.testapp.input;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsLayout;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;

public class TestInput implements IInput {

    private static class InterpolatedEvent {
        private final Consumer<Float> runner;
        private final int length;
        private int curTick;

        public InterpolatedEvent(final int length, final Consumer<Float> runner) {
            this.length = length;
            this.runner = runner;
        }

        /**
         * @return true if this event ended
         */
        public boolean tick() {
            curTick++;
            runner.accept(1.0F * curTick / length);
            return curTick == length;
        }
    }

    private final boolean[] keysDown = new boolean[GLFW_KEY_LAST];
    private final boolean[] keysSetDown = new boolean[GLFW_KEY_LAST];

    private final boolean[] mouseDown = new boolean[3];
    private final boolean[] mouseSetDown = new boolean[3];
    private boolean mouseMoved;

    private final Vector2f mousePosition = new Vector2f();
    private final Vector2f prevMousePosition = new Vector2f();
    private final Queue<InterpolatedEvent> events = new LinkedList<>();

    private final IGraphicsLayout<?> root;

    /**
     * Should be constructed before the GUI.
     * @param root the root container of the GUI. Broadcasts all events to this layout.
     */
    public TestInput(@NotNull final IGraphicsLayout<?> root) {
        this.root = root;
    }

    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

    public boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keysSetDown[key];
    }

    public boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keysSetDown[key];
    }

    public boolean isMouseButtonDown(int button) {
        return mouseDown[button];
    }

    public boolean isMousePressed(int button) {
        return isMouseButtonDown(button) && !mouseSetDown[button];
    }

    public boolean isMouseReleased(int button) {
        return !isMouseButtonDown(button) && mouseSetDown[button];
    }

    @Override
    public Vector2f getMousePosition() {
        return mousePosition;
    }

    @Override
    public void update() {
        events.removeIf(InterpolatedEvent::tick);

        // generate keyboard updates
        for (int key = 0; key < keysDown.length; key++) {
            if (isKeyPressed(key)) {
                String typedString = GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
                if (typedString == null) {
                    GExt.warn("Pressed unknown key with keycode=", key);
                    continue;
                }
                char typedChar = typedString.charAt(0);
                root.onKeyPressed(typedChar, key);
            }
        }

        // generate mouse events
        for (int i = 0; i < mouseDown.length; i++) {
            int mouseX = (int) mousePosition.x();
            int mouseY = (int) mousePosition.y();
            if (isMousePressed(i)) {
                root.onMousePressed(mouseX, mouseY, i);
            } else if (isMouseReleased(i)) {
                root.onMouseReleased(mouseX, mouseY, i);
            }
            // moving and clicking can be performed simultaneously
            if (mouseMoved) {
                if (isMouseButtonDown(i)) {
                    Vector2f delta = mousePosition.sub(prevMousePosition);
                    root.onMouseDragged(mouseX, mouseY, i, delta.x(), delta.y());
                } else {
                    root.onMouseMoved(mouseX, mouseY);
                }
                // TODO Add scrolling support
                // this frame of moving processed
                mouseMoved = false;
            }
        }

        System.arraycopy(keysDown, 0, keysSetDown, 0, keysDown.length);
        System.arraycopy(mouseDown, 0, mouseSetDown, 0, mouseDown.length);
        prevMousePosition.set(mousePosition);
    }

    // ---------------- control methods begin ----------------
    // Note: all "set" methods only changes the state of the framework.
    // To broadcast events to the targeted layout, call #update().

    public void setMouse(float x, float y) {
        moveMouse(x, y, 1);
    }

    private static float interpolate(float from, float to, float partialTicks) {
        return from + (to - from) * partialTicks;
    }

    public void moveMouse(float x, float y, int ticks) {
        final float startX = mousePosition.x();
        final float startY = mousePosition.y();
        events.add(new InterpolatedEvent(ticks, t -> {
            mousePosition.set(
                    interpolate(startX, x, t),
                    interpolate(startY, y, t)
            );
            mouseMoved = true;
        }));
    }

    public void pressMouse(int button) {
        mouseDown[button] = true;
    }

    public void releaseMouse(int button) {
        mouseDown[button] = false;
    }

    public void pressKey(int key) {
        keysDown[key] = true;
    }

    public void releaseKey(int key) {
        keysDown[key] = false;
    }
}
