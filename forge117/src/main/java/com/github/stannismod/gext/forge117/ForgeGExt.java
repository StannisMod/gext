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

package com.github.stannismod.gext.forge117;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.engine.BasicGraphicsEngine;
import com.github.stannismod.gext.engine.DeprecatedGlStateManager;
import com.github.stannismod.gext.forge117.adapter.MinecraftBufferBuilder;
import com.github.stannismod.gext.forge117.adapter.MinecraftResourceManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;

@Mod(value = ForgeGExt.MODID)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeGExt {

    public static final String MODID = "gext";
    public static final String NAME = "GExt";
    public static final String VERSION = GExt.VERSION;

    public static GExt core;
    private static PoseStack currentStack;

    public static void init(FMLClientSetupEvent event) {
        core = new GExt(new MinecraftResourceManager(),
                new BasicGraphicsEngine<>(new MinecraftBufferBuilder(), new DeprecatedGlStateManager()),
                LogManager.getLogger(MODID));
        GExt.onStart();
        //#if DEBUG
        ClientRegistry.registerKeyBinding(EventListener.K);
        //#endif
    }

    public static void startRenderTick(@Nonnull PoseStack stack) {
        currentStack = stack;
    }

    public static PoseStack getCurrentMatrixStack() {
        return currentStack;
    }
}
