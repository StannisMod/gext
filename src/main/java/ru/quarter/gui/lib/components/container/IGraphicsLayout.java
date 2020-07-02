package ru.quarter.gui.lib.components.container;

import ru.quarter.gui.lib.components.IGraphicsComponent;

public interface IGraphicsLayout extends IGraphicsComponent {

    /**
     * Adds the component to the container
     * @param depth the graphics depth the component should be displayed
     * @param component the component which should be added
     * @return the ID of the given component in the container
     */
    int addComponent(int depth, IGraphicsComponent component);

    /**
     * Finds the component with given ID
     * @param id the ID of element that should be get
     * @return found component
     */
    IGraphicsComponent getComponent(int id);

    /**
     * Removes the component with given ID
     * @param id the ID of element that should be removed
     * @return removed component
     */
    IGraphicsComponent removeComponent(int id);
}
