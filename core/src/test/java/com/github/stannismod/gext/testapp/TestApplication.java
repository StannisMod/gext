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

package com.github.stannismod.gext.testapp;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.engine.GraphicsEngine;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestApplication {

    public static void main(String[] args) {
        new GExt(new TestResourceManager(), LogManager.getLogger("GExt Test Env"));
        Window.setCallbacks();

        if (!glfwInit()) {
            System.err.println("GLFW Failed to initialize!");
            System.exit(1);
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        Window window = new Window();
        window.setSize(800, 600);
        window.setFullscreen(false);
        window.createWindow("GExt Test Environment");

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        TestGui gui = new TestGui();

        GExt.onStart();

        //glLoadIdentity();
        glViewport(0, 0, window.getWidth(), window.getHeight());
        //glOrtho(0.0F, 800.0F, 0.0F, 600.0F, 0.1F, 100.0F);

        while (!window.shouldClose()) {
            if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(window.getWindow(), true);
            }

            window.update();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //glTranslatef(0.0F, -100.0F, 0.0F);
            GraphicsEngine.run(() -> {
//                GraphicsEngine.begin(3)
//                        .vertex2(-0.5f, -0.5f).endVertex()
//                        .vertex2(0.5f, -0.5f).endVertex()
//                        .vertex2(0.0f, 0.5f).endVertex()
//                .draw(GL_TRIANGLES);
                //glScalef(1.0F, -1.0F, 1.0F);
                //glTranslatef(0.0F, System.currentTimeMillis() / 10 % 100, 0.0F);
                GraphicsEngine.begin(3)
                        .vertex2(0, 0).endVertex()
                        .vertex2(1, 0).endVertex()
                        .vertex2(0, 1).endVertex()
                .draw(GL_TRIANGLES);
            });

//            gui.draw(
//                    (int) window.getInput().getMousePosition().x,
//                    (int) window.getInput().getMousePosition().y,
//                    0
//            );

            window.swapBuffers();
        }

        GraphicsEngine.destroy();
        glfwTerminate();
    }
}
