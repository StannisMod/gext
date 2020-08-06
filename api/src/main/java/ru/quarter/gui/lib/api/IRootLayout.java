package ru.quarter.gui.lib.api;

public interface IRootLayout {

    void add(int depth, IGraphicsComponent component);

    IGraphicsComponent get(int id);

    IGraphicsComponent remove(int id);

    /**
     * Method for components' initialization(e.g. using {@link IRootLayout#add(int, IGraphicsComponent)})
     * Should be in the final implementation
     */
    void init();
}
