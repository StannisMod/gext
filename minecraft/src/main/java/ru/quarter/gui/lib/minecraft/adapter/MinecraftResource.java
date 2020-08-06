package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.quarter.gui.lib.api.adapter.IResource;

public class MinecraftResource implements IResource {

    private final ResourceLocation instance;

    public MinecraftResource(ResourceLocation instance) {
        this.instance = instance;
    }

    @Override
    public void bindAsTexture() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(instance);
    }
}
