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
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GraphicsEngine {

    private static ShaderProgram shader;
    private static VertexBuffer vbo;
    private static int vao;
    private static final BufferBuilder tes = BufferBuilder.withSize(2048);

    public static void init() {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = new VertexBuffer();
        vbo.bindBuffer();
        shader = new ShaderProgram("standard");

        glVertexAttribPointer(0, 3, GL_FLOAT, true, 3 * 4, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    public static void destroy() {
        vbo.deleteGlBuffers();
    }

    public static VertexBuffer vbo() {
        return vbo;
    }

    public static BufferBuilder tes() {
        return tes;
    }

    public static BufferBuilder begin(int vertexSize) {
        return tes.begin(vertexSize);
    }

    public static void run(Runnable r) {
        shader.bind();
        glBindVertexArray(vao);
        r.run();
        glBindVertexArray(0);
    }
}
