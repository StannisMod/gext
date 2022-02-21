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

import org.joml.Matrix4f;

public class ModernGlStateManager implements IGlStateManager {

    private Matrix4f projection;
    private boolean matrixChanged;

    @Override
    public void glTranslatef(final float x, final float y, final float z) {

    }

    @Override
    public void glTranslated(final double x, final double y, final double z) {

    }

    @Override
    public void glRotatef(final float angle, final float x, final float y, final float z) {

    }

    @Override
    public void glRotated(final double angle, final double x, final double y, final double z) {

    }

    @Override
    public void glScalef(final float x, final float y, final float z) {

    }

    @Override
    public void glScaled(final double x, final double y, final double z) {

    }
}
