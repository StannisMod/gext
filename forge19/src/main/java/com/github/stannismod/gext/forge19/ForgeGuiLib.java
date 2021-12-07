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

package com.github.stannismod.gext.forge19;

import com.github.stannismod.gext.GuiLib;
import com.github.stannismod.gext.forge19.adapter.MinecraftResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ForgeGuiLib.MODID, name = ForgeGuiLib.NAME, version = ForgeGuiLib.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "1.9.4")
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

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
