/*
 *  Copyright 2020 Stanislav Batalenkov
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.resource.GLAllocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

public class BufferBuilder {

    private final FloatBuffer buf;
    private final int vbo;
    private int vertexCount;

    private BufferBuilder(int size) {
        this.buf = GLAllocation.createDirectFloatBuffer(size);
        this.vbo = GL15.glGenBuffers();
    }

    public static BufferBuilder empty() {
        return withSize(16);
    }

    public static BufferBuilder withSize(int size) {
        return new BufferBuilder(size);
    }

    public BufferBuilder vertex2(float x, float y) {
        return vertex3(x, y, 0.0F);
    }

    public BufferBuilder vertex3(float x, float y, float z) {
        buf.put(x).put(y).put(z);
        return this;
    }

    public BufferBuilder tex(float u, float v) {
        buf.put(u).put(v);
        return this;
    }

    public BufferBuilder color3(float r, float g, float b) {
        return color4(r, g, b, 1.0F);
    }

    public BufferBuilder color4(float r, float g, float b, float a) {
        buf.put(r).put(g).put(b).put(a);
        return this;
    }

    public BufferBuilder endVertex() {
        vertexCount++;
        return this;
    }

    public void draw(int mode) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
        GL11.glDrawArrays(mode, 0, vertexCount);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        int error = GL11.glGetError();
        if (error > 0) {
            GExt.error("OpenGL error: " + error);
        }

        buf.clear();
        vertexCount = 0;
    }
}
