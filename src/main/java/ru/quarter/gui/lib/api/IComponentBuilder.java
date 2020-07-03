package ru.quarter.gui.lib.api;

public interface IComponentBuilder<T extends IGraphicsComponent> {

    /**
     * Constructs new inner component instance to build
     */
    IComponentBuilder<T> create();

    /**
     * Returns the current instance
     */
    T build();
}
