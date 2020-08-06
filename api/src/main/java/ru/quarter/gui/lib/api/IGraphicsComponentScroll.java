package ru.quarter.gui.lib.api;

public interface IGraphicsComponentScroll extends IGraphicsComponent {

    void setTarget(IScrollable target);

    IScrollable getTarget();
}
