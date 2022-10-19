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

public final class ModernGraphicsEngine implements IGraphicsEngine<ModernBufferBuilder> {

    public static final int VERTEX_SIZE = 3 + 4 + 2;

    private ShaderProgram shader;
    private VertexBuffer vbo;
    private int vao;
    private ModernBufferBuilder tes;

    private int majorVersion;
    private int minorVersion;

    private boolean normalizationEnabled = true;

    @Override
    public int getMajorVersion() {
        return majorVersion;
    }

    @Override
    public int getMinorVersion() {
        return minorVersion;
    }

    public boolean normalizationEnabled() {
        return normalizationEnabled;
    }

    public void setNormalizationEnabled(boolean enabled) {
        normalizationEnabled = enabled;
    }

    @Override
    public void init() {
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

            int vertexSize = ModernGraphicsEngine.VERTEX_SIZE * 4;
            glVertexAttribPointer(0, 3, GL_FLOAT, true, vertexSize, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 4, GL_FLOAT, false, vertexSize, 3 * 4);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, 2, GL_FLOAT, false, vertexSize, (3 + 4) * 4);
            glEnableVertexAttribArray(2);

            glBindVertexArray(0);

            // initialize modern components
            GlStateManager.setDelegate(new ModernGlStateManager(this));
            tes = ModernBufferBuilder.withSize(this, 2048);
        } else {
            throw new IllegalStateException(
                    "Constructed modern graphics pipeline on hardware without shader support(OpenGL v. < 3.3)"
            );
        }
    }

    @Override
    public void destroy() {
        glDeleteBuffers(vao);
        vbo.deleteGlBuffers();
        shader.close();
    }

    public VertexBuffer vbo() {
        return vbo;
    }

    public ShaderProgram getShaderProgram() {
        return shader;
    }

    @Override
    public ModernBufferBuilder begin(int mode) {
        return tes.begin(mode);
    }

    @Override
    public void run(Runnable r) {
        shader.bind();
        glBindVertexArray(vao);
        GlStateManager.setUniforms();
        r.run();
        GlStateManager.loadIdentity();
        glBindVertexArray(0);
    }
}
