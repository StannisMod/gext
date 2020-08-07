package ru.quarter.gui.lib.components;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IListener;
import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.utils.GraphicsHelper;

import java.util.LinkedList;
import java.util.List;

public abstract class GBasic implements IGraphicsComponent {

    private int id;
    private int depth;
    protected boolean needUpdate;

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected IResource texture;

    private IGraphicsLayout parent;
    private IGraphicsComponent binding;

    private final List<IListener<? extends IGraphicsComponent>> listeners = new LinkedList<>();

    protected GBasic() {}

    public GBasic(int x, int y, int width, int height) {
        this(x, y, width, height, null);
    }

    public GBasic(int x, int y, int width, int height, IResource texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
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
        return texture;
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
    public IGraphicsLayout getParent() {
        return parent;
    }

    @Override
    public void setParent(IGraphicsLayout parent) {
        this.parent = parent;
    }

    @Override
    public void setBinding(IGraphicsComponent binding) {
        if (binding.getParent() != this.getParent()) {
            throw new IllegalArgumentException("The binding should have the same parent!");
        }
        this.binding = binding;
    }

    @Override
    public IGraphicsComponent getBinding() {
        return binding;
    }

    @Override
    public void addListener(IListener<? extends IGraphicsComponent> listener) {
        listeners.add(listener);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        int x = getX();
        int y = getY();
        if (getBinding() != null) {
            x += getBinding().getX();
            y += getBinding().getY();
            mouseX -= getBinding().getX();
            mouseY -= getBinding().getY();
        }
        if (intersects(mouseX, mouseY)) {
            onHover(mouseX, mouseY);
        }
        if (needUpdate() || checkUpdates()) {
            update();
        }

        listeners.forEach(IListener::listen);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GraphicsHelper.glScissor(x, y, getWidth(), getHeight());
        GL11.glTranslatef(x, y, getDepth());
        draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        IListener<IGraphicsComponent> tooltip = getParent().getTooltip();
        if (tooltip != null) {
            tooltip.setTarget(this);
        }
    }

    @Override
    public void markDirty() {
        needUpdate = true;
    }

    @Override
    public boolean needUpdate() {
         return needUpdate;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
