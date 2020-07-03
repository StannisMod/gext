package ru.quarter.gui.lib.components.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.components.IGraphicsComponent;
import ru.quarter.gui.lib.utils.OffsetProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

public class BasicLayout implements IGraphicsLayout {

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
    IGraphicsLayout parent;
    boolean needUpdate;
    ScaledResolution res;

    // private
    private int nextID = 0;
    // for ID access
    private final Map<Integer, IGraphicsComponent> components = new HashMap<>();
    // for rendering
    private final NavigableSet<IGraphicsComponent> sorted = new TreeSet<>(((o1, o2) -> {
        if (o1.getDepth() == o2.getDepth()) {
            return o1.getID() - o2.getID();
        }
        return o1.getDepth() - o2.getDepth();
    }));
    private final Framebuffer framebuffer;

    public BasicLayout(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.res = new ScaledResolution(Minecraft.getMinecraft());
        this.framebuffer = new Framebuffer(width * res.getScaleFactor(), height * res.getScaleFactor(), true);
        this.framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public int addComponent(int depth, IGraphicsComponent component) {
        component.setDepth(depth);
        component.setID(nextID);
        component.setParent(this);
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
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        int relativeX = mouseX - getX();
        int relativeY = mouseY - getY();
        sorted.forEach(component -> component.onMousePressed(relativeX, relativeY, mouseButton));
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        sorted.forEach(component -> component.onKeyPressed(typedChar, keyCode));
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
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);

        int depth = 0;
        for (IGraphicsComponent component : sorted) {
            if (component.getDepth() != depth) {
                GL11.glTranslatef(0.0F, 0.0F, component.getDepth() - depth);
                depth = component.getDepth();
            }
            // TODO Write margin and padding offsets
            component.render();
        }

        //framebuffer.unbindFramebuffer();
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        framebuffer.framebufferRenderExt(getWidth() * res.getScaleFactor(), getHeight() * res.getScaleFactor(), false);
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
    public void onClosed() {
        framebuffer.deleteFramebuffer();
        sorted.forEach(IGraphicsComponent::onClosed);
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
        res = new ScaledResolution(mc);
        // TODO Write resize processing
    }
}
