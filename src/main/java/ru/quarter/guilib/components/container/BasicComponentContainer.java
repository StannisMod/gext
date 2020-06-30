package ru.quarter.guilib.components.container;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.quarter.guilib.components.IGraphicsComponent;
import ru.quarter.guilib.utils.OffsetProperties;

import java.util.*;

public class BasicComponentContainer implements IGraphicsComponentContainer {

    // final
    int id;
    OffsetProperties margin;
    OffsetProperties padding;
    ResourceLocation texture;
    int x;
    int y;
    int width;
    int height;
    int depth;

    // dynamic
    IGraphicsComponentContainer parent;
    boolean needUpdate;

    // private
    private int nextID = 0;
    // for ID access
    private final Map<Integer, IGraphicsComponent> components = new HashMap<>();
    // for rendering
    private final NavigableSet<IGraphicsComponent> sorted = new TreeSet<>(Comparator.comparingInt(IGraphicsComponent::getDepth));

    @Override
    public int addComponent(int depth, IGraphicsComponent component) {
        component.setDepth(depth);
        component.setID(nextID);
        components.put(nextID, component);
        sorted.add(component);
        return nextID++;
    }

    @Override
    public IGraphicsComponent getComponent(int id) {
        return components.get(id);
    }

    @Override
    public IGraphicsComponent removeComponent(int id) {
        IGraphicsComponent removed = components.remove(id);
        sorted.remove(removed);
        return removed;
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
    public OffsetProperties getMargin() {
        return margin;
    }

    @Override
    public OffsetProperties getPadding() {
        return padding;
    }

    @Override
    public ResourceLocation getTexture() {
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
    public boolean mousePressed(int mouseX, int mouseY, int mouseButton) {
        int relativeX = mouseX - getX();
        int relativeY = mouseY - getY();
        sorted.forEach(component -> component.mousePressed(relativeX, relativeY, mouseButton));
        return true;
    }

    @Override
    public boolean keyPressed(char typedChar, int keyCode) {
        sorted.forEach(component -> component.keyPressed(typedChar, keyCode));
        return true;
    }

    @Override
    public IGraphicsComponentContainer getParent() {
        return parent;
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        int relativeX = mouseX - getX();
        int relativeY = mouseY - getY();
        sorted.forEach(component -> {
            if (component.intersects(relativeX, relativeY)) {
                component.onHover(relativeX, relativeY);
            }
        });
    }

    @Override
    public void draw() {
        int depth = 0;
        GL11.glTranslatef(getX(), getY(), getDepth());
        for (IGraphicsComponent component : sorted) {
            if (component.getDepth() != depth) {
                GL11.glTranslatef(0.0F, 0.0F, component.getDepth() - depth);
                depth = component.getDepth();
            }
            // TODO Write margin and padding offsets, and texture rendering
            GL11.glPushMatrix();
            component.draw();
            GL11.glPopMatrix();
        }
        //GL11.glTranslatef(0.0F, 0.0F, -depth);
    }

    @Override
    public void update() {
        needUpdate = false;
        sorted.forEach(IGraphicsComponent::update);
    }

    @Override
    public void init() {
        sorted.forEach(IGraphicsComponent::init);
    }

    @Override
    public void markDirty() {
        this.needUpdate = true;
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

    @Override
    public void onResize(Minecraft mc, int w, int h) {
        // TODO Write resize processing
    }
}
