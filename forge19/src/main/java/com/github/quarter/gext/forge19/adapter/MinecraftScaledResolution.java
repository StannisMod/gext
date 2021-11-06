/*
 * Copyright 2020 Stanislav Batalenkov
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

package com.github.quarter.gext.forge19.adapter;

import com.github.quarter.gext.api.adapter.IScaledResolution;
import net.minecraft.client.gui.ScaledResolution;

public class MinecraftScaledResolution implements IScaledResolution {

    private final ScaledResolution instance;

    public MinecraftScaledResolution(ScaledResolution instance) {
        this.instance = instance;
    }

    @Override
    public int getScaleFactor() {
        return instance.getScaleFactor();
    }

    @Override
    public int getScaledWidth() {
        return instance.getScaledWidth();
    }

    @Override
    public int getScaledHeight() {
        return instance.getScaledHeight();
    }
}
