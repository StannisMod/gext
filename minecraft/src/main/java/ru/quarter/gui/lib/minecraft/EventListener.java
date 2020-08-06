package ru.quarter.gui.lib.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
@net.minecraftforge.fml.common.Mod.EventBusSubscriber(value = Side.CLIENT, modid = ForgeGuiLib.MODID)
public class EventListener {

    public static final KeyBinding K = new KeyBinding("Opens test GUI", Keyboard.KEY_K, "guilib.test");

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        if (K.isKeyDown()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiTest());
        }
    }
}
