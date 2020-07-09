package ru.quarter.gui.lib.components;

import ru.quarter.gui.lib.api.IGraphicsComponent;

/**
 * Functional interface for all listeners in GuiLib
 * Useful because has access to package-private fields and methods in {@link ru.quarter.gui.lib.components}
 */

public interface IGraphicsComponentListener {

    void execute(IGraphicsComponent component);
}
