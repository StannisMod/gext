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
import com.github.stannismod.gext.engine.GlStateManager;
import com.github.stannismod.gext.engine.GraphicsEngine;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestApplication {

    public static void main(String[] args) {
        Window.setCallbacks();

        if (!glfwInit()) {
            System.err.println("GLFW Failed to initialize!");
            System.exit(1);
        }

        Window window = new Window(800, 600);
        window.createWindow("GExt Test Environment");

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        TestGui gui = new TestGui();

        new GExt(new TestResourceManager(window.getWindow()), LogManager.getLogger("GExt Test Env"));
        GExt.onStart();
        GraphicsEngine.setNormalizationEnabled(true);

        //glLoadIdentity();
        glViewport(0, 0, window.getWidth(), window.getHeight());
        //glOrtho(0.0F, 800.0F, 0.0F, 600.0F, 0.1F, 100.0F);

        while (!window.shouldClose()) {
            if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(window.getWindow(), true);
            }

            window.update();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            GraphicsEngine.run(() -> {
//                GlStateManager.disableTexture();
//                GlStateManager.translate((System.currentTimeMillis() % 10000) / 100.0F, 0.0F, 0.0F);
//                GraphicsEngine.begin()
//                        .pos(0, 0).endVertex()
//                        .pos(100, 0).color3(1.0F, 0.0F, 0.0F).endVertex()
//                        .pos(0, 100).color3(0.0F, 0.0F, 1.0F).endVertex()
//                .draw(GL_TRIANGLES);
//
//                GlStateManager.scale(0.5F, 0.5F, 1.0F);
//                GlStateManager.rotate((System.currentTimeMillis() % 1000) / 1000.0F * 2 * (float) Math.PI, 0.0F, 0.0F, 1.0F);
//
//                GraphicsEngine.begin()
//                        .pos(200, 200).endVertex()
//                        .pos(100, 200).endVertex()
//                        .pos(200, 100).color4(1, 1, 1, 0.5F).endVertex()
//                .draw(GL_TRIANGLES);
                GlStateManager.enableTexture();
                gui.draw(0, 0, 0.0F);
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
