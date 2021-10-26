/*
 * Copyright 2020 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.quarter.gui.lib.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.awt.*;

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
     * @return the visibility of the component
     */
    boolean visible();

    /**
     * Changes the visibility of the component
     * @param visibility the visibility to be set
     * @since 1.2
     */
    void setVisibility(boolean visibility);

    /**
     *
     * @return absolute X start of the element
     * @since 1.2
     */
    int getAbsoluteX();
    /**
     *
     * @return X start of the element
     * @since 1.0
     */
    int getX();

    void setX(int x);

    default void shiftX(int value) {
        setX(getX() + value);
    }

    /**
     *
     * @return absolute Y start of the element
     * @since 1.2
     */
    int getAbsoluteY();
    /**
     *
     * @return Y start of the element
     * @since 1.0
     */
    int getY();

    void setY(int y);

    default void shiftY(int value) {
        setY(getY() + value);
    }

    /**
     *
     * @return width of the element
     * @since 1.0
     */
    int getWidth();

    void setWidth(int width);

    default void growWidth(int value) {
        setWidth(getWidth() + value);
    }

    /**
     *
     * @return height of the element
     * @since 1.0
     */
    int getHeight();

    void setHeight(int height);

    default void growHeight(int value) {
        setHeight(getHeight() + value);
    }

    @NotNull
    Rectangle getFrame();

    @NotNull
    Rectangle getAbsoluteFrame();

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

    default boolean hasParent() {
        return getParent() != null;
    }

    IGraphicsLayout<? extends IGraphicsComponent> getParent();

    /**
     *
     * @param parent the parent of the element
     * @since 1.0
     */
    void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent);

    /**
     * Adds given listener to tick in render-thread
     * Attention: listener can be targeted to any other element,
     * this method should not force this object to be a target
     * @param listener the listener given
     * @since 1.1
     */
    void addListener(@NotNull IListener<? extends IGraphicsComponent> listener);

    /**
     * Sets the actual relative binding of the element. Should be in the same {@link IGraphicsLayout}
     * @param component the binding
     * @since 1.1
     */
    void setBinding(@NotNull IGraphicsComponent component);

    /**
     * Gets the actual relative binding of the element
     * @return the actual binding of the element
     * @since 1.1
     */
    @Nullable
    IGraphicsComponent getBinding();

    boolean clippingEnabled();

    void setClippingEnabled(boolean enabled);

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
     * Processes all OpenGL mouse events in general
     *
     * Parameters are not presented, so use {@link org.lwjgl.input.Mouse}
     * to get any information you need
     *
     * Implementing this should NOT deactivate calling of {@link #onMousePressed(int, int, int)},
     * {@link #onMouseReleased(int, int, int)}, {@link #onHover(int, int)}, etc.
     * @since 1.3
     */
    void onMouseInput(int mouseX, int mouseY, int mouseButton);

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

    /**
     * Intersects given relative (from this component) coordinates
     * @param innerMouseX x point for intersect
     * @param innerMouseY y point for intersect
     * @return {@code} true if intersects
     * @since 1.3
     */
    default boolean intersectsInner(int innerMouseX, int innerMouseY) {
        return intersects(innerMouseX + getX(), innerMouseY + getY());
    }
}
