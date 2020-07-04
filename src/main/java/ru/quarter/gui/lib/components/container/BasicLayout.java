package ru.quarter.gui.lib.components.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.utils.FramebufferStack;
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
    private Framebuffer framebuffer;

    protected BasicLayout() {}

    public BasicLayout(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        onResize(Minecraft.getMinecraft(), width, height);
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
    public boolean checkUpdates() {
        boolean dirty = false;
        for (IGraphicsComponent component : sorted) {
            if (component.checkUpdates()) {
                component.markDirty();
                dirty = true;
            }
        }
        return dirty;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onMousePressed(mouseX - component.getX(), mouseY - component.getY(), mouseButton);
            }
        });
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onMouseReleased(mouseX - component.getX(), mouseY - component.getY(), mouseButton);
            }
        });
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
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onHover(mouseX - component.getX(), mouseY - component.getY());
            }
        });
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        //GL11.glDisable(GL11.GL_SCISSOR_TEST);

        framebuffer.framebufferClear();
        //framebuffer.bindFramebuffer(true);
        FramebufferStack.getInstance().apply(framebuffer);

        int depth = 0;
        for (IGraphicsComponent component : sorted) {
            if (component.getDepth() != depth) {
                GL11.glTranslatef(0.0F, 0.0F, component.getDepth() - depth);
                depth = component.getDepth();
            }
            component.render(mouseX, mouseY);
        }

        FramebufferStack.getInstance().flush();
        framebuffer.framebufferRenderExt(getWidth() * res.getScaleFactor(), getHeight() * res.getScaleFactor(), false);
    }

    @Override
    public void update() {
        sorted.forEach(component -> {
            if (component.needUpdate()) {
                component.update();
            }
        });
        needUpdate = false;
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
        this.res = new ScaledResolution(mc);
        this.framebuffer = new Framebuffer(width * res.getScaleFactor(), height * res.getScaleFactor(), true);
        this.framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        // TODO Write resize processing
    }
}
