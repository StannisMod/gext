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

import com.github.stannismod.gext.api.menu.IContextMenuComponent;
import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.utils.LayoutContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * API for containers. Implementations should store and manage components of type {@code T}.
 * Other functionality in this component should never been implemented.
 * @param <T>
 * @since 1.0
 */
public interface IGraphicsLayout<T extends IGraphicsComponent> extends IGraphicsComponent {

    /**
     * Adds the component to the container
     * @param depth the graphics depth the component should be displayed
     * @param component the component which should be added
     * @return the ID of the given component in the container
     * @since 1.0
     */
    int addComponent(int depth, @NotNull T component);

    default int addComponent(@NotNull T component) {
        return addComponent(0, component);
    }

    /**
     * Adds the component with given ID assigned
     * @param id the id that should be assigned
     * @param component provided component
     * @since 1.2.1
     */
    void putComponent(int id, @NotNull T component);

    /**
     * Finds the component with given ID
     * @param id the ID of element that should be get
     * @return found component
     * @since 1.0
     */
    @Nullable
    T getComponent(int id);

    /**
     * Removes the component with given ID
     * @param id the ID of element that should be removed
     * @return removed component
     * @since 1.0
     */
    @Nullable
    T removeComponent(int id);

    /**
     * Sets the provided content to layout
     * IDs of components provided by Content Builder should be saved
     * @param newContent the content to be set
     * @throws ClassCastException if the type provided can not be casted to {@code T}
     * @since 1.2.1
     */
    void setContent(@NotNull LayoutContent<? extends IGraphicsComponent> newContent) throws ClassCastException;

    /**
     * Returns the link to the actual content of the layout
     *
     * Note: Do not change this collection without big knowledge about your actions
     * @since 1.4
     */
    @NotNull
    LayoutContent<T> getContent();

    /**
     * Sets the provided content to layout
     * The IDs of the components should be reassigned
     * @param newContent the content to be set
     * @throws ClassCastException if the type provided can not be casted to {@code T}
     * @since 1.2.1
     */
    @SuppressWarnings("unchecked")
    default void setContent(@NotNull Collection<? extends IGraphicsComponent> newContent) {
        clear();
        newContent.forEach(component -> addComponent(component.getDepth(), (T) component));
    }

    /**
     * Returns the size of the layout
     * @return the number of components the layout contains
     * @since 1.1
     */
    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    void clear();

    /**
     * Sets the tooltip listener to layout. Given tooltip will be applied to all contents inside this layout
     * until inner container define it's own tooltip
     * @param tooltip specified tooltip
     * @since 1.1
     */
    void setTooltip(@NotNull IGraphicsListener tooltip);

    /**
     * Should return tooltip stored in this layout
     * @return own layout's tooltip
     * @since 1.1
     */
    @Nullable
    IGraphicsListener getOwnTooltip();

    /**
     * Should return the nearest tooltip in bottom-up hierarchy
     * @return the tooltip
     * @since 1.1
     */
    default IGraphicsListener getTooltip() {
        if (getOwnTooltip() != null || getParent() == null) {
            return getOwnTooltip();
        }
        return getParent().getTooltip();
    }

    /**
     * Sets active selector to layout
     * @param selector provided selector
     */
    void setSelector(@Nullable ISelector selector);

    /**
     *
     * @return the active selector
     */
    @Nullable
    ISelector getSelector();

    default boolean hasSelector() {
        return getSelector() != null;
    }

    /**
     * Checks targeted layout to be root
     * @since 1.4
     */
    default boolean isRoot() {
        return getRoot() == this;
    }

    /**
     * @since 1.4
     */
    default boolean hasActiveMenu() {
        return getActiveMenu() != null;
    }

    /**
     * Returns currently active menu
     * @since 1.4
     */
    IContextMenuComponent<? extends IContextMenuElement> getActiveMenu();

    /**
     * Sets actual active context menu.
     * Should destroy previously opened menu.
     * @since 1.4
     */
    void setActiveMenu(IContextMenuComponent<? extends IContextMenuElement> menu);
}
