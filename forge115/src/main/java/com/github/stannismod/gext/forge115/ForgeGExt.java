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

package com.github.stannismod.gext.forge115;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.forge115.adapter.MinecraftResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;

@Mod(value = ForgeGExt.MODID)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ForgeGExt {

    public static final String MODID = "gext";
    public static final String NAME = "GExt";
    public static final String VERSION = GExt.VERSION;

    public static GExt core;

    public void init(FMLClientSetupEvent event) {
        core = new GExt(new MinecraftResourceManager(), LogManager.getLogger(MODID));
        GExt.info(
                "Gui Engine by Quarter v." + VERSION + " starting..." +
                        "\n///////////////////////////////////////////////////////////////////////////////////////////\n"
                        + "///////////////////////////////////////////////////////////////////////////////////////////\n"
                        + "/////////////------//////----////----//------////////----/////////----//--------///////////\n"
                        + "///////////--/////---/////--//////--/////--///////////--///////////--////--////--//////////\n"
                        + "//////////--//////////////--//////--/////--///////////--///////////--////--////--//////////\n"
                        + "//////////--//////////////--//////--/////--///////////--///////////--////-------///////////\n"
                        + "//////////--/////------///--//////--/////--///////////--///////////--////-------///////////\n"
                        + "//////////--////////---///--//////--/////--///////////--///////////--////--////--//////////\n"
                        + "///////////--//////---/////--////--//////--///////////--//////--///--////--////--//////////\n"
                        + "////////////--------////////------/////------////////-----------//----//--------///////////\n"
                        + "///////////////////////////////////////////////////////////////////////////////////////////\n"
                        + "///////////////////////////////////////////////////////////////////////////////////////////"
        );
        ClientRegistry.registerKeyBinding(EventListener.K);
    }
}
