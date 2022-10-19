package com.github.stannismod.gext.engine;

public interface IGraphicsEngine<BBType extends IBufferBuilder<BBType>> {

    int getMajorVersion();

    int getMinorVersion();

    default boolean shadersSupported() {
        return getMajorVersion() >= 3 && getMinorVersion() >= 3;
    }

    void init();

    void destroy();

    BBType begin(int mode);

    default void run(Runnable r) {
        // no stuff here
    }
}
