package ru.quarter.gui.lib.api.adapter;

public interface IResourceManager {

    IFramebuffer framebuffer(int width, int height);

    IFramebuffer defaultFramebuffer();

    IScaledResolution scaled();

    IResource resource(String name);

    IGraphicsHelper helper();

    IFontRenderer standardRenderer();
}
