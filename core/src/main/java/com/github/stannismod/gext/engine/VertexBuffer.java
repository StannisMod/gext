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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;

public class VertexBuffer {

    private int glBufferId;

    public VertexBuffer() {
        this.glBufferId = GL15.glGenBuffers();
    }

    public void bindBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.glBufferId);
    }

    public void bufferData(ByteBuffer data) {
        //this.bindBuffer();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        //this.unbindBuffer();
    }

    public void drawArrays(int mode, int count) {
        GL11.glDrawArrays(mode, 0, count);
    }

    public void unbindBuffer() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            GL15.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }
}
