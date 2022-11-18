package com.github.stannismod.gext.engine;

import com.github.stannismod.gext.api.resource.ITexture;

public interface IGraphicsEngine<BBType extends IBufferBuilder<BBType>> {

    int getMajorVersion();

    int getMinorVersion();

    default boolean shadersSupported() {
        return getMajorVersion() >= 3 && getMinorVersion() >= 3;
    }

    void init();

    void destroy();

    default BBType begin(int mode, VertexFormat format) {
        return getBuffer().begin(mode, format);
    }

    BBType getBuffer();

    default void bindTexture(ITexture texture) {
        getBuffer().bindTexture(texture);
    }

    default void run(Runnable r) {
        // no stuff here
    }
}
