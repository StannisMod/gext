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

package com.github.stannismod.gext.testapp;

import com.github.stannismod.gext.api.adapter.IScaledResolution;

public class TestScaledResolution implements IScaledResolution {
    @Override
    public int getScaleFactor() {
        return 1;
    }

    @Override
    public int getScaledWidth() {
        return 800;
    }

    @Override
    public int getScaledHeight() {
        return 600;
    }
}