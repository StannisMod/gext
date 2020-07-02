package ru.quarter.gui.lib.components;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.quarter.gui.lib.components.container.IGraphicsLayout;
import ru.quarter.gui.lib.utils.OffsetProperties;

public interface IGraphicsComponent {

    /**
     * Gets the component ID in it's container
     * @return the local area ID
     */
    int getID();

    /**
     * Sets the component ID in it's container to specified value
     * ONLY for internal use
     * @param id specified ID
     */
    void setID(int id);
    /**
     * Return HTML-like margin
     * @return the outer offset of the element
     */
    OffsetProperties getMargin();

    /**
     * Return HTML-like padding
     * @return the inner offset of the element
     */
    OffsetProperties getPadding();

    /**
     *
     * @return texture of the element
     */
    ResourceLocation getTexture();

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    /**
     * Processes mouse event
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param mouseButton pressed button
     * @return should mouse event be processed further
     */
    boolean mousePressed(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes keyboard event
     * @param typedChar
     * @param keyCode
     * @return should keyboard event be processed further
     */
    boolean keyPressed(char typedChar, int keyCode);

    IGraphicsLayout getParent();

    /**
     * Gets the actual relative binding of the element. Now is equal to {@link IGraphicsComponent#getParent()}.
     * Should be included in GuiLib 1.1
     * @return the actual binding of the element
     */
    default IGraphicsComponent getBinding() {
        return getParent();
    }

    /**
     * Processes hover event
     */
    void onHover(int mouseX, int mouseY);

    /**
     * Main drawing method
     */
    void draw();

    /**
     * Updating state when needed(see {@link IGraphicsComponent#needUpdate()})
     */
    void update();

    /**
     * Initializes component. Must be called once in root GUI container init.
     */
    void init();

    /**
     * Set component to 'need update' state
     */
    void markDirty();

    boolean needUpdate();

    int getDepth();

    void setDepth(int depth);

    /**
     * Called when GUI was resized
     * @param mc Minecraft instance
     * @param w new width
     * @param h new height
     */
    void onResize(Minecraft mc, int w, int h);

    default boolean intersects(int mouseX, int mouseY) {
        return getX() <= mouseX && mouseX <= getX() + getWidth() && getY() <= mouseY && mouseY <= getY() + getHeight();
    }
}
