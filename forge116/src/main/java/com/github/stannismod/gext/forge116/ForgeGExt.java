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

package com.github.stannismod.gext.forge116;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.engine.BasicGraphicsEngine;
import com.github.stannismod.gext.engine.DeprecatedGlStateManager;
import com.github.stannismod.gext.forge116.adapter.LWJGL3Keyboard;
import com.github.stannismod.gext.forge116.adapter.MinecraftBufferBuilder;
import com.github.stannismod.gext.forge116.adapter.MinecraftResourceManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;

@Mod(value = ForgeGExt.MODID)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeGExt {

    public static final String MODID = "gext";
    public static final String NAME = "GExt";
    public static final String VERSION = GExt.VERSION;

    public static GExt core;
    private static MatrixStack currentStack;

    public static void init(FMLClientSetupEvent event) {
        core = new GExt(new MinecraftResourceManager(),
                new BasicGraphicsEngine<>(new MinecraftBufferBuilder(), new DeprecatedGlStateManager()),
                new LWJGL3Keyboard(),
                LogManager.getLogger(MODID));
        GExt.onStart();
        //#if DEBUG
        ClientRegistry.registerKeyBinding(EventListener.K);
        //#endif
    }

    public static void startRenderTick(@Nonnull MatrixStack stack) {
        currentStack = stack;
    }

    public static MatrixStack getCurrentMatrixStack() {
        return currentStack;
    }
}
