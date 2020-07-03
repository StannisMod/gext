package ru.quarter.gui.lib;

import ru.quarter.gui.lib.components.IGraphicsComponent;

public interface IRootLayout {

    void add(int depth, IGraphicsComponent component);

    IGraphicsComponent get(int id);

    IGraphicsComponent remove(int id);
}
