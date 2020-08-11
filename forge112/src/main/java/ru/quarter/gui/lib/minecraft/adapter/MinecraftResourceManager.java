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

package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import ru.quarter.gui.lib.api.adapter.*;

public class MinecraftResourceManager implements IResourceManager {

    public static MinecraftFramebuffer ROOT_FRAMEBUFFER;
    public static MinecraftFontRenderer DEFAULT_FONTRENDERER;

    @Override
    public IFramebuffer framebuffer(int width, int height) {
        return new MinecraftFramebuffer(new Framebuffer(width, height, true));
    }

    @Override
    public IFramebuffer defaultFramebuffer() {
        if (ROOT_FRAMEBUFFER == null) {
            ROOT_FRAMEBUFFER = new MinecraftFramebuffer(Minecraft.getMinecraft().getFramebuffer());
        }
        return ROOT_FRAMEBUFFER;
    }

    @Override
    public IScaledResolution scaled() {
        return new MinecraftScaledResolution(new ScaledResolution(Minecraft.getMinecraft()));
    }

    @Override
    public IResource resource(String name) {
        return new MinecraftResource(new ResourceLocation(name));
    }

    @Override
    public IGraphicsHelper helper() {
        return MinecraftGraphicsHelper.INSTANCE;
    }

    @Override
    public IFontRenderer standardRenderer() {
        if (DEFAULT_FONTRENDERER == null) {
            DEFAULT_FONTRENDERER = new MinecraftFontRenderer(Minecraft.getMinecraft().fontRenderer);
        }
        return DEFAULT_FONTRENDERER;
    }
}
