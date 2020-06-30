package ru.quarter.guilib;

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

    @Mod.Instance(MODID)
    public static GuiLib instance;

//    @SidedProxy(clientSide = "ru.stannis.pas.proxy.ClientProxy", serverSide = "ru.stannis.pas.proxy.ServerProxy")
//    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Logger logger = event.getModLog();
//        proxy.preInit(event);

        logger.info(
                "[GUI_LIB] Gui Engine by Quarter v." + VERSION + " starting..." +
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
//        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
//        proxy.postInit(event);
    }
}
