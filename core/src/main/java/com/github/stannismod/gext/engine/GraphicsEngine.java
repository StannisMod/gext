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

package com.github.stannismod.gext.engine;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public final class GraphicsEngine {

    public static final int VERTEX_SIZE = 3 + 4 + 2;

    private static ShaderProgram shader;
    private static VertexBuffer vbo;
    private static int vao;
    private static final BufferBuilder tes = BufferBuilder.withSize(2048);

    private static int majorVersion;
    private static int minorVersion;

    public static int getMajorVersion() {
        return majorVersion;
    }

    public static int getMinorVersion() {
        return minorVersion;
    }

    public static boolean shadersSupported() {
        return majorVersion >= 3 && minorVersion >= 3;
    }

    public static void init() {
        int[] major = new int[1];
        int[] minor = new int[1];
        glGetIntegerv(GL_MAJOR_VERSION, major);
        glGetIntegerv(GL_MINOR_VERSION, minor);
        majorVersion = major[0];
        minorVersion = minor[0];

        if (shadersSupported()) {
            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = new VertexBuffer();
            vbo.bindBuffer();
            shader = new ShaderProgram("standard");

            int vertexSize = GraphicsEngine.VERTEX_SIZE * 4;
            glVertexAttribPointer(0, 3, GL_FLOAT, true, vertexSize, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 4, GL_FLOAT, false, vertexSize, 3 * 4);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, 2, GL_FLOAT, false, vertexSize, (3 + 4) * 4);
            glEnableVertexAttribArray(2);

            glBindVertexArray(0);

            GlStateManager.setDelegate(new ModernGlStateManager());
        } else {
            GlStateManager.setDelegate(new DeprecatedGlStateManager());
        }
    }

    public static void destroy() {
        vbo.deleteGlBuffers();
        shader.close();
    }

    public static VertexBuffer vbo() {
        return vbo;
    }

    public static ShaderProgram getShaderProgram() {
        return shader;
    }

    public static BufferBuilder begin() {
        return tes;
    }

    public static void run(Runnable r) {
        shader.bind();
        glBindVertexArray(vao);
        GlStateManager.setUniforms();
        r.run();
        GlStateManager.loadIdentity();
        glBindVertexArray(0);
    }
}
