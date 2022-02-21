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

public final class GlStateManager {

    private static IGlStateManager delegate;

    static void setDelegate(IGlStateManager delegate) {
        GlStateManager.delegate = delegate;
    }

    public static void translate(float x, float y, float z) {
        delegate.translate(x, y, z);
    }

    public static void rotate(float angle, final float x, final float y, final float z) {
        delegate.rotate(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        delegate.scale(x, y, z);
    }

    public static void enableTexture() {
        delegate.enableTexture();
    }

    public static void disableTexture() {
        delegate.disableTexture();
    }

    public static void pushMatrix() {
        delegate.pushMatrix();
    }

    public static void popMatrix() {
        delegate.popMatrix();
    }

    public static void setUniforms() {
        delegate.setUniforms();
    }

    public static void loadIdentity() {
        delegate.loadIdentity();
    }
}
