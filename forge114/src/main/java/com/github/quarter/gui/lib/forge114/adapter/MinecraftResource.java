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

package com.github.quarter.gui.lib.forge114.adapter;

import com.github.quarter.gui.lib.api.adapter.IResource;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class MinecraftResource implements IResource {

    private final ResourceLocation instance;

    public MinecraftResource(ResourceLocation instance) {
        this.instance = instance;
    }

    @Override
    public void bindAsTexture() {
        Minecraft.getInstance().getTextureManager().bindTexture(instance);
    }
}
