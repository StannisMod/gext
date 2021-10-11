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

package com.github.quarter.gui.lib.forge1710;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.forge1710.adapter.MinecraftResourceManager;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = ForgeGuiLib.MODID, name = ForgeGuiLib.NAME, version = ForgeGuiLib.VERSION, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "1.7.10")
public class ForgeGuiLib {

    public static final String MODID = "guilib";
    public static final String NAME = "GuiLib";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static ForgeGuiLib instance;

    public static GuiLib core;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        core = new GuiLib(new MinecraftResourceManager(), event.getModLog());
        GuiLib.info(
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
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventListener());
        ClientRegistry.registerKeyBinding(EventListener.K);
    }
}
