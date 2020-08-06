package ru.quarter.gui.lib.components;

import org.lwjgl.input.Mouse;
import ru.quarter.gui.lib.api.IGraphicsComponentScroll;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IScrollable;
import ru.quarter.gui.lib.api.adapter.IResource;

public abstract class GScrollBasic implements IGraphicsComponentScroll {

    private int id;
    private int depth;
    private IGraphicsLayout parent;

    int x;
    int y;
    int width;
    int height;

    private IScrollable target;

    @Override
    public void setTarget(IScrollable target) {
        this.target = target;
    }

    @Override
    public IScrollable getTarget() {
        return target;
    }

    @Override
    public void init() {
        if (target == null) {
            throw new IllegalStateException("Rendering without target");
        }
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public IResource getTexture() {
        return null;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean checkUpdates() {
        return Mouse.getDWheel() != 0;
    }

    @Override
    public IGraphicsLayout getParent() {
        return parent;
    }

    @Override
    public void setParent(IGraphicsLayout parent) {
        this.parent = parent;
        this.x = 0;
        this.y = 0;
        this.width = parent.getWidth();
        this.height = parent.getHeight();
    }

    @Override
    public void onHover(int mouseX, int mouseY) {}

    @Override
    public void markDirty() {}

    @Override
    public boolean needUpdate() {
        return false;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void onResize(int w, int h) {}
}
