package ru.quarter.gui.lib.minecraft.adapter;

import net.minecraft.client.gui.ScaledResolution;
import ru.quarter.gui.lib.api.adapter.IScaledResolution;

public class MinecraftScaledResolution implements IScaledResolution {

    private final ScaledResolution instance;

    public MinecraftScaledResolution(ScaledResolution instance) {
        this.instance = instance;
    }

    @Override
    public int getScaleFactor() {
        return instance.getScaleFactor();
    }

    @Override
    public int getScaledWidth() {
        return instance.getScaledWidth();
    }

    @Override
    public int getScaledHeight() {
        return instance.getScaledHeight();
    }
}
