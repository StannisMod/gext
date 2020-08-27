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

package ru.quarter.gui.lib.forge112.adapter;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import ru.quarter.gui.lib.api.adapter.IFramebuffer;

public class MinecraftFramebuffer implements IFramebuffer {

    private final Framebuffer framebuffer;

    public MinecraftFramebuffer(Framebuffer framebuffer) {
        this.framebuffer = framebuffer;
    }

    @Override
    public void color(float r, float g, float b, float a) {
        framebuffer.setFramebufferColor(r, g, b, a);
    }

    @Override
    public void bind() {
        framebuffer.bindFramebuffer(true);
    }

    @Override
    public void unbind() {
        framebuffer.unbindFramebuffer();
    }

    @Override
    public void render(int width, int height) {
        render(width, height, false);
    }

    @Override
    public void render(int width, int height, boolean alpha) {
        GlStateManager.enableBlend();
        GlStateManager.enableColorMaterial();
        framebuffer.framebufferRenderExt(width, height, alpha);
    }

    @Override
    public void delete() {
        framebuffer.deleteFramebuffer();
    }

    @Override
    public void clear() {
        framebuffer.framebufferClear();
    }
}
