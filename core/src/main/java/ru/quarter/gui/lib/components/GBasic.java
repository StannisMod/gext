package ru.quarter.gui.lib.components;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.utils.GraphicsHelper;

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
    public void render(int mouseX, int mouseY) {
        if (intersects(mouseX, mouseY)) {
            onHover(mouseX, mouseY);
        }
        if (needUpdate() || checkUpdates()) {
            update();
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GraphicsHelper.glScissor(getX(), getY(), getWidth(), getHeight());
        GL11.glTranslatef(getX(), getY(), getDepth());
        draw(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
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
