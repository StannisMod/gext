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

public class DeprecatedGlStateManager implements IGlStateManager {

    @Override
    public void translate(final float x, final float y, final float z) {
        GL11.glTranslatef(x, y, z);
    }

    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    @Override
    public void scale(final float x, final float y, final float z) {
        GL11.glScalef(x, y, z);
    }

    @Override
    public void enableTexture() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void disableTexture() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public void pushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void popMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void setUniforms() {
        // no stuff here
    }

    @Override
    public void loadIdentity() {
        // no stuff here
    }
}
