package ru.quarter.gui.lib;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GuiLib.MODID, name = GuiLib.NAME, version = GuiLib.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "1.12.2")
public class GuiLib {

    public static final String MODID = "guilib";
    public static final String NAME = "GuiLib by Quarter";
    public static final String VERSION = "1.0.0";

    private static Logger logger;

    @Mod.Instance(MODID)
    public static GuiLib instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        info(
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

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String msg, Object... objects) {
        logger.info(String.format(msg, objects));
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warn(String msg, Object... objects) {
        logger.warn(String.format(msg, objects));
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Throwable cause) {
        logger.error(msg, cause);
    }

    public static void error(String msg, Object... objects) {
        logger.error(String.format(msg, objects));
    }

    public static void error(Throwable cause, String msg, Object... objects) {
        error(String.format(msg, objects), cause);
    }
}
