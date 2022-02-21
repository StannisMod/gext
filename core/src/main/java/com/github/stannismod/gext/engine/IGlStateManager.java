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

public interface IGlStateManager {

    void glTranslatef(float x, float y, float z);

    void glTranslated(double x, double y, double z);

    void glRotatef(float angle, float x, float y, float z);

    void glRotated(double angle, double x, double y, double z);

    void glScalef(float x, float y, float z);

    void glScaled(double x, double y, double z);
}
