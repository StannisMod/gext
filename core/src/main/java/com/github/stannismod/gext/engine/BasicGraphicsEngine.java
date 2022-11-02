package com.github.stannismod.gext.engine;

public class BasicGraphicsEngine<BBType extends IBufferBuilder<BBType>> implements IGraphicsEngine<BBType> {

    private final BBType buffer;

    public BasicGraphicsEngine(final BBType buffer, final IGlStateManager manager) {
        this.buffer = buffer;
        GlStateManager.setDelegate(manager);
    }

    @Override
    public int getMajorVersion() {
        // just basic stub here
        return 0;
    }

    @Override
    public int getMinorVersion() {
        // just basic stub here
        return 0;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public BBType begin(final int mode, final VertexFormat format) {
        return buffer.begin(mode, format);
    }
}
