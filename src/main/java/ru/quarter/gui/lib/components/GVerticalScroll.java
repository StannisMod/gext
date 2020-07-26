package ru.quarter.gui.lib.components;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;
import ru.quarter.gui.lib.utils.StyleMap;

public class GVerticalScroll extends GScrollBasic {

    // final
    private float scrollFactor;
    private int scrollBarWidth = 8;

    // dynamic
    private int initialClick = -1;
    private int mouseX;
    private int mouseY;

    public boolean shouldRenderBar() {
        return getScrollable() > 0;
    }

    private int getScrollable() {
        return getTarget().getContentHeight() - getTarget().getHeight();
    }

    private int getScrollBarHeight() {
        return MathHelper.ceil(1.0F * getHeight() * getHeight() / getTarget().getContentHeight());
    }

    private int getScrollBarWidth() {
        return scrollBarWidth;
    }

    private boolean barHovered() {
        return mouseX >= getX() && mouseX <= getX() + getScrollBarWidth() && mouseY >= getY() && mouseY <= getY() + getScrollBarHeight();
    }

    private int getScrollBarPosition() {
        int scrolled = getTarget().getScrollVertical() + getScrollable() / 2;
        return (int)((getHeight() - getScrollBarHeight()) * (1.0F * scrolled / getScrollable()));
    }

    @Override
    public boolean checkUpdates() {
        return super.checkUpdates() && initialClick != -1;
    }

    @Override
    public void update() {
        if (!shouldRenderBar()) {
            return;
        }

        int scrolled;
        if (initialClick == -1) {
            scrolled = (int)(Mouse.getDWheel() * scrollFactor);
        } else {
            scrolled = mouseY - initialClick;
            initialClick = mouseY;
        }

        int scrollable = getScrollable() / 2;
        int result = getTarget().getScrollVertical() + scrolled;

        if (result < -scrollable) {
            result = -scrollable;
        } else if (result > scrollable) {
            result = scrollable;
        }
        getTarget().setScrollVertical(result);
    }

    @Override
    public void onClosed() {}

    @Override
    public void draw(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        StyleMap.current().drawVerticalScrollTrace(getX() + getWidth() - getScrollBarWidth(), getY(), getScrollBarWidth(), getHeight());
        StyleMap.current().drawVerticalScrollBar(getX() + getWidth() - getScrollBarWidth(), getScrollBarPosition(), getScrollBarWidth(), getScrollBarHeight());
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        if (barHovered()) {
            initialClick = mouseY;
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        initialClick = -1;
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {}

    public static class Builder {

        private final GVerticalScroll instance = new GVerticalScroll();

        public Builder barWidth(int width) {
            instance.scrollBarWidth = width;
            return this;
        }

        public Builder scrollFactor(float scrollFactor) {
            instance.scrollFactor = scrollFactor;
            return this;
        }

        public GVerticalScroll build() {
            return instance;
        }
    }
}
