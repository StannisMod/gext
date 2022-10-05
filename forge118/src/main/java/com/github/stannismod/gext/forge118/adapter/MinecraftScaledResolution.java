/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.forge118.adapter;

import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.mojang.blaze3d.platform.Window;

public class MinecraftScaledResolution implements IScaledResolution {

    private final Window instance;

    public MinecraftScaledResolution(Window instance) {
        this.instance = instance;
    }

    @Override
    public int getScaleFactor() {
        return (int) instance.getGuiScale();
    }

    @Override
    public int getScaledWidth() {
        return instance.getGuiScaledWidth();
    }

    @Override
    public int getScaledHeight() {
        return instance.getGuiScaledHeight();
    }
}
