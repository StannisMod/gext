package com.github.stannismod.gext.engine;

public class DeprecatedGraphicsEngine implements IGraphicsEngine<DeprecatedBufferBuilder> {

    private DeprecatedBufferBuilder buffer;

    @Override
    public int getMajorVersion() {
        return 2;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public void init() {
        // initialize deprecated components
        GlStateManager.setDelegate(new DeprecatedGlStateManager());
        buffer = new DeprecatedBufferBuilder();
    }

    @Override
    public void destroy() {
        // no stuff here
    }

    @Override
    public DeprecatedBufferBuilder begin(int mode) {
        return buffer.begin(mode);
    }
}
