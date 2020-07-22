package ru.quarter.gui.lib.api;

public interface IScrollable extends IGraphicsComponent {

    void setScrollHandler(IGraphicsComponentScroll handler);

    IGraphicsComponentScroll getScrollHandler();

    default boolean scrollEnabled() {
        return getScrollHandler() != null;
    }

    int getScrollVertical();

    int getScrollHorizontal();

    void setScrollVertical(int value);

    void setScrollHorizontal(int value);

    int getContentWidth();

    int getContentHeight();

    default void addScrollVertical(int value) {
        setScrollVertical(getScrollVertical() + value);
    }

    default void addScrollHorizontal(int value) {
        setScrollHorizontal(getScrollHorizontal() + value);
    }
}
