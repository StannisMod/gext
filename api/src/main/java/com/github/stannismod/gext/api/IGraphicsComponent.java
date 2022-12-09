/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.api;

import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.api.menu.IContextMenuList;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * The root API of the GExt. Represents the base methods that every graphics component should have.
 * Used for abstract rendering and other stuff. Implementations can be added to layouts and interact with other
 * components.
 * Basic implementation for graphics component can be found in {@code core} module in class {@code GBasic},
 * for control component - in class {@code GControl}.
 * @since 1.0
 */
public interface IGraphicsComponent {

    /**
     * <p>Gets the component ID in its container.</p>
     * <p>Please pay attention that this is <b>not</b> a global-unique ID.
     * It may be, but there are no guarantees of that state.</p>
     * <p>However, this method <b>should</b> return the value set
     * by the latest called {@link #setID(String)}.</p>
     *
     * @return the local area ID
     * @since 1.0
     */
    String getID();

    /**
     * <p>Sets the component ID in its container to specified value</p>
     * <p><b>ONLY</b> for internal use</p>
     * <p>Component should <b>never</b> change it's ID by itself.</p>
     *
     * @param id specified ID
     * @since 1.0
     */
    void setID(String id);

    /**
     * @return the visibility of the component
     * @since 1.0
     */
    boolean visible();

    /**
     * Changes the visibility of the component
     * @param visibility the visibility to be set
     * @since 1.2
     */
    void setVisibility(boolean visibility);

    /**
     * <p>Returns the absolute(e.g. from (0, 0) of screen) X coordinate.
     * This makes it independent of nesting order and so on.</p>
     *
     * <p>This can be used by some absolute OpenGL tools like scissors.</p>
     * @return absolute X start of the element
     * @since 1.2
     */
    int getAbsoluteX();
    /**
     * @return X start of the element. This is relative, e.g. coordinate in parent container.
     * @since 1.0
     */
    int getX();

    /**
     * <p>Setter X start of the element.</p>
     * <p>Observe that this is just setter. Component is not required to have
     * value set by this method returned by {@link #getX()}.</p>
     *
     * @param x value to be set
     * @since 1.0
     */
    void setX(int x);

    /**
     * Changes the component's X coordinate by {@code value}
     * @param value the value to be added to X coordinate
     * @since 1.3
     */
    default void shiftX(int value) {
        setX(getX() + value);
    }

    /**
     * <p>Returns the absolute(e.g. from (0, 0) of screen) Y coordinate.
     * This makes it independent of nesting order and so on.</p>
     *
     * <p>This can be used by some absolute OpenGL tools like scissors.</p>
     * @return absolute Y start of the element
     * @since 1.2
     */
    int getAbsoluteY();
    /**
     * @return Y start of the element. This is relative, e.g. coordinate in parent container.
     * @since 1.0
     */
    int getY();

    /**
     * <p>Setter Y start of the element.</p>
     * <p>Observe that this is just setter. Component is not required to have
     * value set by this method returned by {@link #getY()}.</p>
     *
     * @param y value to be set
     * @since 1.0
     */
    void setY(int y);

    /**
     * Changes the component's X coordinate by {@code value}
     * @param value the value to be added to X coordinate
     * @since 1.3
     */
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

    /**
     * Returns the geometry frame of the component
     * @since 1.2
     */
    @NotNull
    Rectangle getFrame();

    /**
     * Returns the absolute (e.g. from the (0, 0) of the root) frame of the component
     * @since 1.3
     */
    @NotNull
    Rectangle getAbsoluteFrame();

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

    /**
     * Returns the hierarchical parent of the component
     * @since 1.0
     */
    @Nullable
    IGraphicsLayout<? extends IGraphicsComponent> getParent();

    /**
     * Determines the new parent for this component
     * @param parent the parent of the component
     * @since 1.0
     */
    void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent);

    /**
     * Returns the link to the root container(not {@link IRootLayout})
     * @since 1.4
     */
    @NotNull
    IGraphicsLayout<?> getRoot();

    /**
     * Sets the root container of the component
     * DO NOT OVERRIDE, ONLY FOR INTERNAL USE
     * @since 1.4
     */
    void setRoot(@NotNull IGraphicsLayout<?> root);

    /**
     * Setter for alignment
     * @since 1.4
     */
    void setAlignment(@NotNull Align alignment);

    /**
     * Setter for paddings
     * @since 1.4
     */
    void setPaddings(int xPadding, int yPadding);

    /**
     * @return component's x-axis padding
     * @since 1.4
     */
    int getXPadding();

    /**
     * @return component's y-axis padding
     * @since 1.4
     */
    int getYPadding();

    /**
     * @return the actual alignment constraint
     * @since 1.4
     */
    @NotNull
    Align getAlignment();

    /**
     * Constructs the context menu, which should be showed when
     * right-clicking on the component
     * @since 1.4
     */
    @Nullable
    IContextMenuList<? extends IContextMenuElement> constructMenu();

    /**
     * Adds given listener to tick in render-thread
     * Attention: listener can be targeted to any other element,
     * this method should not force this object to be a target
     * @param listener the listener given
     * @since 1.1
     */
    void addListener(@NotNull IListener listener);

    /**
     * Sets the actual relative binding of the element. Should be in the same {@link IGraphicsLayout}
     * @param component the binding
     * @since 1.1
     */
    void setBinding(@Nullable IGraphicsComponent component, Bound bound);

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
    void draw(int mouseX, int mouseY, float partialTicks);

    /**
     * The old version of drawing method, added for backwards compatibility
     * @deprecated the new version with partial ticks support is introduced.
     * This will be removed in 1.6
     * @see #draw(int, int, float)
     */
    @Deprecated
    default void draw(int mouseX, int mouseY) {
        draw(mouseX, mouseY, 0.0F);
    }

    /**
     * Standard render method
     * ONLY FOR INTERNAL USE - Do not override!
     * @since 1.0
     */
    default void render(int mouseX, int mouseY, float partialTicks) {
        if (intersects(mouseX, mouseY)) {
            onHover(mouseX, mouseY);
        }
        if (needUpdate() || checkUpdates()) {
            update();
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(getX(), getY(), getDepth());
        draw(mouseX, mouseY, partialTicks);
        GL11.glPopMatrix();
    }

    /**
     * <p>Processes all OpenGL mouse events in general.</p>
     *
     * <p>Parameters are not presented, so use {@link org.lwjgl.input.Mouse}
     * to get any information you need.</p>
     *
     * <p>Implementing this should NOT deactivate calling of other mouse-input handling methods.</p>
     *
     * @deprecated this method is for backwards compatibility with LWJGL 2. In LWJGL 3 there is no way
     * to maintain calling this method, so use other split methods to implement this functionality.
     * It should not be removed in any future, but marked deprecated to point that implementing it is
     * the wrong way to get all-platform-support.
     *
     * @see #onMousePressed(int, int, int)
     * @see #onMouseReleased(int, int, int)
     * @see #onMouseDragged(double, double, int, double, double)
     * @see #onMouseMoved(int, int)
     * @see #onMouseScrolled(int, int, double)
     * @since 1.4
     */
    @Deprecated
    void onMouseInput(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes mouse drag event(e.g. click-move-release)
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @since 1.4
     */
    void onMouseDragged(double mouseX, double mouseY, int mouseButton, double xAmount, double yAmount);

    /**
     * Processes only mouse moving, not dragging
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @since 1.4
     */
    void onMouseMoved(int mouseX, int mouseY);

    /**
     * Processes mouse scrolling
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param amountScrolled amount scrolled
     * @since 1.4
     */
    void onMouseScrolled(int mouseX, int mouseY, double amountScrolled);

    /**
     * Processes hover event
     * @since 1.0
     */
    void onHover(int mouseX, int mouseY);

    /**
     * Processes mouse press event.
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param mouseButton pressed button
     * @since 1.0
     */
    void onMousePressed(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes mouse release event
     * @param mouseX scaled relative X mouse coordinate
     * @param mouseY scaled relative Y mouse coordinate
     * @param mouseButton pressed button
     * @since 1.0
     */
    void onMouseReleased(int mouseX, int mouseY, int mouseButton);

    /**
     * Processes keyboard event
     * @param typedChar the printable representation of {@code keyCode}
     * @param keyCode pressed key
     * @since 1.0
     */
    void onKeyPressed(char typedChar, int keyCode);

    /**
     * Set component to 'need update' state
     * @since 1.0
     */
    void markDirty();

    boolean needUpdate();

    /**
     * Checks the element need update
     * @since 1.0
     */
    boolean checkUpdates();

    /**
     * Updating state
     * @since 1.0
     */
    void update();

    /**
     * Checks {@link IGraphicsComponent#needUpdate()} and calls {@link IGraphicsComponent#update()}
     * when needed
     * @since 1.5.1
     */
    void tryUpdate();

    int getDepth();

    void setDepth(int depth);

    /**
     * <p>Called when GUI was resized.</p>
     * <p>This can be used to resize component when GUI resizes, e.g. scale.</p>
     *
     * @param w new view width
     * @param h new view height
     * @since 1.0
     */
    void onResize(int w, int h);

    /**
     * <p>Intersects given coordinates with this component.</p>
     * <p>Note that in this method receives mouse coordinates in parent's coordinate system.
     * To use self coordinate system, use {@link #intersectsInner(int, int)}</p>
     *
     * @param mouseX x point for intersect
     * @param mouseY y point for intersect
     *
     * @return {@code true} if intersects
     * @since 1.0
     *
     * @see #intersectsInner(int, int)
     */
    default boolean intersects(int mouseX, int mouseY) {
        return getX() <= mouseX && mouseX <= getX() + getWidth() && getY() <= mouseY && mouseY <= getY() + getHeight();
    }

    /**
     * <p>Intersects given relative (from this component) coordinates.</p>
     * <p>Note that in this method receives mouse coordinates in self coordinate system.
     * To use parent's coordinate system, use {@link #intersects(int, int)}</p>
     *
     * @param innerMouseX x point for intersect
     * @param innerMouseY y point for intersect
     *
     * @return {@code} true if intersects
     * @since 1.3
     *
     * @see #intersects(int, int)
     */
    default boolean intersectsInner(int innerMouseX, int innerMouseY) {
        return intersects(innerMouseX + getX(), innerMouseY + getY());
    }
}
