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

import com.github.stannismod.gext.GExt;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class BufferBuilder {

    private ByteBuffer buf;
    private int vertexCount;
    private int vertexSize;

    private BufferBuilder(int size) {
        this.buf = GLAllocation.createDirectByteBuffer(size * 4);
    }

    public static BufferBuilder empty() {
        return withSize(16);
    }

    public static BufferBuilder withSize(int size) {
        return new BufferBuilder(size);
    }

    public BufferBuilder begin(int vertexSize) {
        this.vertexSize = vertexSize;
        return this;
    }

    public BufferBuilder vertex2(float x, float y) {
        return vertex3(x, y, 0.0F);
    }

    public BufferBuilder vertex3(float x, float y, float z) {
        buf.putFloat(x).putFloat(y).putFloat(z);
        return this;
    }

    public BufferBuilder tex(float u, float v) {
        buf.putFloat(u).putFloat(v);
        return this;
    }

    public BufferBuilder color3(float r, float g, float b) {
        return color4(r, g, b, 1.0F);
    }

    public BufferBuilder color4(float r, float g, float b, float a) {
        buf.putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        return this;
    }

    public BufferBuilder endVertex() {
        vertexCount++;
        ensureCapacity();
        return this;
    }

    private void ensureCapacity() {
        if (buf.capacity() <= (vertexCount + 1) * vertexSize) {
            ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer((int)(1.5 * buf.capacity()));
            bytebuffer.put(buf);
            buf = bytebuffer;
        }
    }

    public void draw(int mode) {
        buf.rewind();
        buf.limit(vertexCount * vertexSize * 4);

        GraphicsEngine.vbo().bufferData(buf);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, vertexSize * 4, 0);
        GraphicsEngine.vbo().drawArrays(mode, vertexCount);

        int error = GL11.glGetError();
        if (error > 0) {
            GExt.error("OpenGL error: " + error);
        }

        buf.clear();
        vertexCount = 0;
    }
}
