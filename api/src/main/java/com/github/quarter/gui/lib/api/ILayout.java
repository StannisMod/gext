package com.github.quarter.gui.lib.api;

public interface ILayout {

    void setTarget(IGraphicsLayout<? extends IGraphicsComponent> target);

    void onComponentPlaced(IGraphicsComponent component);

    void onComponentRemoved(IGraphicsComponent component);
}
