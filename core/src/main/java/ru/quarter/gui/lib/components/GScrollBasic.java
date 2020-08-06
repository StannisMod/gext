package ru.quarter.gui.lib.components;

import org.lwjgl.input.Mouse;
import ru.quarter.gui.lib.api.IGraphicsComponentScroll;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IScrollable;

public abstract class GScrollBasic extends GBasic implements IGraphicsComponentScroll {

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
    public boolean checkUpdates() {
        return Mouse.getDWheel() != 0;
    }

    @Override
    public void setParent(IGraphicsLayout parent) {
        super.setParent(parent);
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
}
