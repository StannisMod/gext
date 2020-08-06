package ru.quarter.gui.lib.minecraft;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.quarter.gui.lib.GuiLib;
import ru.quarter.gui.lib.minecraft.adapter.MinecraftResourceManager;

@Mod(modid = ForgeGuiLib.MODID, name = ForgeGuiLib.NAME, version = ForgeGuiLib.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "1.12.2")
public class ForgeGuiLib {

    public static final String MODID = "guilib";
    public static final String NAME = "GuiLib by Quarter";
    public static final String VERSION = "1.1.0";

    @Mod.Instance(MODID)
    public static ForgeGuiLib instance;

    // TODO Write instantiation of CORE
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
        ClientRegistry.registerKeyBinding(EventListener.K);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
