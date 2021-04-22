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

package com.github.quarter.gui.lib.forge112;

import com.github.quarter.gui.lib.GuiLib;
import com.github.quarter.gui.lib.forge112.adapter.MinecraftResourceManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ForgeGuiLib.MODID, name = ForgeGuiLib.NAME, version = ForgeGuiLib.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "1.12.2")
public class ForgeGuiLib {

    public static final String MODID = "guilib";
    public static final String NAME = "GuiLib";
    public static final String VERSION = "1.2.1";

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
        if (GuiLib.DEBUG) {
            ClientRegistry.registerKeyBinding(EventListener.K);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
