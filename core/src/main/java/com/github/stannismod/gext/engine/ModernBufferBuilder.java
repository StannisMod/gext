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

public class ModernBufferBuilder implements IBufferBuilder<ModernBufferBuilder> {

    private ByteBuffer buf;
    private int vertexCount;
    private int mode;

    private final ByteBuffer vertexBuf;
    private final ModernGraphicsEngine engine;

    private ModernBufferBuilder(ModernGraphicsEngine engine, int size) {
        this.buf = GLAllocation.createDirectByteBuffer(size * 4);
        this.vertexBuf = GLAllocation.createDirectByteBuffer(ModernGraphicsEngine.VERTEX_SIZE * 4);
        this.engine = engine;
        // setting zeros to avoid native junk
        clearVertexBuffer();
    }

    public static ModernBufferBuilder empty(ModernGraphicsEngine engine) {
        return withSize(engine, 16);
    }

    public static ModernBufferBuilder withSize(ModernGraphicsEngine engine, int size) {
        return new ModernBufferBuilder(engine, size);
    }

    @Override
    public ModernBufferBuilder begin(final int mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public ModernBufferBuilder pos(float x, float y, float z) {
        vertexBuf.position(0);
        if (engine.normalizationEnabled()) {
            x = 2 * x / GExt.getView().getScaledWidth() - 1;
            y = 2 * y / GExt.getView().getScaledHeight() - 1;
        }
        vertexBuf.putFloat(x).putFloat(y).putFloat(z);
        return this;
    }

    @Override
    public ModernBufferBuilder tex(float u, float v) {
        vertexBuf.position((3 + 4) * 4);
        vertexBuf.putFloat(u).putFloat(v);
        return this;
    }

    @Override
    public ModernBufferBuilder color4(float r, float g, float b, float a) {
        vertexBuf.position(3 * 4);
        vertexBuf.putFloat(r).putFloat(g).putFloat(b).putFloat(a);
        return this;
    }

    @Override
    public ModernBufferBuilder endVertex() {
        vertexCount++;
        ensureCapacity();
        vertexBuf.rewind();
        buf.put(vertexBuf);
        vertexBuf.rewind();
//        if (vertexCount % 4 == 0 && mode == GL11.GL_QUADS) {
//            buf.get()
//        }
        return this;
    }

    private void ensureCapacity() {
        if (buf.capacity() <= vertexCount * ModernGraphicsEngine.VERTEX_SIZE * 4) {
            ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer((int)(1.5 * buf.capacity()));
            bytebuffer.put(buf);
            buf = bytebuffer;
        }
    }

    private void clearVertexBuffer() {
        for (int i = 0; i < 3 + 4 + 2; i++) {
            this.vertexBuf.putFloat(1.0F);
        }
        this.vertexBuf.rewind();
    }

    @Override
    public void draw() {
        buf.rewind();
        buf.limit(vertexCount * ModernGraphicsEngine.VERTEX_SIZE * 4);

        GlStateManager.setUniforms();

        engine.vbo().bufferData(buf);
        engine.vbo().drawArrays(mode, vertexCount);

        int error = GL11.glGetError();
        if (error > 0) {
            GExt.error("OpenGL error: " + error);
        }

        buf.rewind();
        buf.limit(buf.capacity());
        clearVertexBuffer();
        vertexCount = 0;
    }
}
