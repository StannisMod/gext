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

public class GlStateManager {

    private static IGlStateManager delegate;

    protected static void setDelegate(IGlStateManager delegate) {
        GlStateManager.delegate = delegate;
    }

    public static void glTranslatef(float x, float y, float z) {
        delegate.glTranslatef(x, y, z);
    }

    public static void glTranslated(double x, double y, double z) {
        delegate.glTranslated(x, y, z);
    }

    public static void glRotatef(float angle, final float x, final float y, final float z) {
        delegate.glRotatef(angle, x, y, z);
    }

    public static void glRotated(double angle, final double x, final double y, final double z) {
        delegate.glRotated(angle, x, y, z);
    }

    public static void glScalef(float x, float y, float z) {
        delegate.glScalef(x, y, z);
    }

    public static void glScaled(double x, double y, double z) {
        delegate.glScaled(x, y, z);
    }
}
