package ru.quarter.gui.lib.api;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.adapter.IResource;

/**
 * The main interface of GuiLib API. Provides basic low-level functionality og GUI component.
 * @since 1.0
 */
public interface IGraphicsComponent {

    /**
     * Gets the component ID in it's container
     * @return the local area ID
     * @since 1.0
     */
    int getID();

    /**
     * Sets the component ID in it's container to specified value
     * ONLY for internal use
     * @param id specified ID
     * @since 1.0
     */
    void setID(int id);

    /**
     *
     * @return texture of the element
     * @since 1.0
     */
    IResource getTexture();

    /**
     *
     * @return X start of the element
     * @since 1.0
     */
    int getX();

    /**
     *
     * @return Y start of the element
     * @since 1.0
     */
    int getY();

    /**
     *
     * @return width of the element
     * @since 1.0
     */
    int getWidth();

    /**
     *
     * @return height of the element
     * @since 1.0
     */
    int getHeight();

    /**
     * Checks the element need update
     * @since 1.0
     */
    boolean checkUpdates();

    /**
     * Updating state when needed(see {@link IGraphicsComponent#needUpdate()})
     * @since 1.0
     */
    void update();

    /**
     * Initializes component. Must be called once in root GUI container init.
     * @since 1.0
     */
    void init();

    /**
     * Called when root container is closed
     * @since 1.0
     */
    void onClosed();

    IGraphicsLayout getParent();

    /**
     *
     * @param parent the parent of the element
     * @since 1.0
     */
    void setParent(IGraphicsLayout parent);

    /**
     * Adds given listener to tick in render-thread
     * Attention: listener can be targeted to any other element,
     * this method should not force this object to be a target
     * @param listener the listener given
     * @since 1.1
     */
    void addListener(IListener<? extends IGraphicsComponent> listener);

    /**
     * Sets the actual relative binding of the element. Should be in the same {@link IGraphicsLayout}
     * @param component the binding
     * @since 1.1
     */
    void setBinding(IGraphicsComponent component);

    /**
     * Gets the actual relative binding of the element
     * @return the actual binding of the element
     * @since 1.1
     */
    IGraphicsComponent getBinding();

    /**
     * Main drawing method
     * @since 1.0
     */
    void draw(int mouseX, int mouseY);

    /**
     * Standard render method
     * ONLY FOR INTERNAL USE - Do not override!
     * @since 1.0
     */
    default void render(int mouseX, int mouseY) {
        if (intersects(mouseX, mouseY)) {
            onHover(mouseX, mouseY);
        }
        if (needUpdate() || checkUpdates()) {
            update();
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(getX(), getY(), getDepth());
        draw(mouseX, mouseY);
        GL11.glPopMatrix();
    }

    /**
     * Processes hover event
     * @since 1.0
     */
    void onHover(int mouseX, int mouseY);

    /**
     * Processes mouse event
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param mouseButton pressed button
     * @since 1.0
     */
    void onMousePressed(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes mouse event
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param mouseButton pressed button
     * @since 1.0
     */
    void onMouseReleased(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes keyboard event
     * @param typedChar
     * @param keyCode
     * @since 1.0
     */
    void onKeyPressed(char typedChar, int keyCode);

    /**
     * Set component to 'need update' state
     */
    void markDirty();

    boolean needUpdate();

    int getDepth();

    void setDepth(int depth);

    /**
     * Called when GUI was resized
     * @param w new width
     * @param h new height
     * @since 1.0
     */
    void onResize(int w, int h);

    /**
     * Intersects given coordinates with this component
     * @param mouseX x point for intersect
     * @param mouseY y point for intersect
     * @return {@code true} if intersects
     * @since 1.0
     */
    default boolean intersects(int mouseX, int mouseY) {
        return getX() <= mouseX && mouseX <= getX() + getWidth() && getY() <= mouseY && mouseY <= getY() + getHeight();
    }
}
