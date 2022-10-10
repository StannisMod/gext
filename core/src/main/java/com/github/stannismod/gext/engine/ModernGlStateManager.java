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
import org.joml.Matrix4f;

import java.util.ArrayDeque;
import java.util.Deque;

public class ModernGlStateManager implements IGlStateManager {

    private static final Matrix4f ONE = new Matrix4f();

    private final Deque<Matrix4f> stack = new ArrayDeque<>();
    private Matrix4f transform = new Matrix4f();
    private boolean matrixChanged = true;

    @Override
    public void translate(float x, float y, final float z) {
        if (GraphicsEngine.normalizationEnabled()) {
            x = 2 * x / GExt.getView().getScaledWidth();
            y = 2 * y / GExt.getView().getScaledHeight();
        }
        transform.translate(x, y, z);
        matrixChanged = true;
    }

    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        transform.rotate(angle, x, y, z);
        matrixChanged = true;
    }

    @Override
    public void scale(final float x, final float y, final float z) {
        transform.scale(x, y, z);
        matrixChanged = true;
    }

    @Override
    public void enableTexture() {
        setTextureEnabled(1);
    }

    @Override
    public void disableTexture() {
        setTextureEnabled(0);
    }

    private void setTextureEnabled(int value) {
        GraphicsEngine.getShaderProgram().setUniform("isTextured", value);
    }

    @Override
    public void pushMatrix() {
        stack.push(transform);
        /*
        TODO Optimize allocations like this on merge with pipeline-optimization branch
             Use recycler arraylist
        */
        transform = new Matrix4f(transform);
    }

    @Override
    public void popMatrix() {
        if (stack.size() == 0) {
            throw new IllegalStateException("[GlStateManager] Trying to pop matrix from empty stack!");
        }
        transform = stack.pop();
        matrixChanged = true;
    }

    @Override
    public void setUniforms() {
        if (matrixChanged) {
            GraphicsEngine.getShaderProgram().setUniform("transform", transform);
            matrixChanged = false;
        }
    }

    @Override
    public void loadIdentity() {
        transform.set(ONE);
        matrixChanged = true;
    }
}
