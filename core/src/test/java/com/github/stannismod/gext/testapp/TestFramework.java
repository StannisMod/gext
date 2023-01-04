package com.github.stannismod.gext.testapp;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.engine.ModernGraphicsEngine;
import com.github.stannismod.gext.testapp.input.TestInput;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL;

import java.io.Closeable;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public class TestFramework<T extends IGraphicsComponent> implements Closeable {

    private final TestInput input;
    private final IGraphicsLayout<T> root;
    private final IScaledResolution view;

    public TestFramework(IGraphicsLayout<T> root, IScaledResolution view) {
        // initialize test OpenGL context (LWJGL v.3)
        Window.setCallbacks();

        if (!glfwInit()) {
            System.err.println("GLFW Failed to initialize!");
            System.exit(1);
        }

        Window window = new Window(view.getViewWidth(), view.getViewHeight());
        window.createWindow("GExt Test Environment");

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        ModernGraphicsEngine e = new ModernGraphicsEngine();
        e.setNormalizationEnabled(true);
        new GExt(new TestResourceManager(window.getWindow()), e, new LWJGL3Keyboard(window.getWindow()), LogManager.getLogger("GExt Test Env"));
        GExt.onStart();

        // yes, this is test-unfriendly. But this is test.
        this.input = new TestInput(root);
        this.root = root;
        this.view = view;
    }

    @Override
    public void close() {
        GExt.onExit();
        glfwTerminate();
    }

    public TestInput getInput() {
        return input;
    }

    public IScaledResolution getView() {
        return view;
    }

    public IGraphicsLayout<T> getRoot() {
        return root;
    }

    public void runDrawing() {
        this.getRoot().render(
                (int) getInput().getMousePosition().x(),
                (int) getInput().getMousePosition().y(),
                0.0F
        );
    }

    @Override
    public String toString() {
        return "TestFramework on " + root;
    }
}
