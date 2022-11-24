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

import org.jetbrains.annotations.Nullable;

/**
 * API for selection controls
 * @since 1.2
 */
public interface ISelector extends IGraphicsComponent {

    /**
     * Getter for selection ID
     * @return the currently selected component ID
     */
    String getSelectedId();

    /**
     * Setter for selection ID
     * @param id the provided ID
     */
    void select(String id);

    /**
     * Selects the given component
     * @param component given component
     */
    default void select(IGraphicsComponent component) {
        select(component.getID());
    }

    @Nullable
    default IGraphicsComponent getSelectedComponent() {
        return getParent().getComponent(getSelectedId());
    }

    /**
     *
     * @return true if something is selected with this selector
     */
    default boolean isSelected() {
        return getSelectedId() != null;
    }

    /**
     * Deactivates selection
     */
    default void unselect() {
        select((String) null);
    }

    /**
     * Processes selection event
     * @param component the selected component
     */
    default void onSelect(IGraphicsComponent component) {
        // TODO Rewrite or remove `default`
        if (getSelectedId().equals(component.getID())) {
            this.onDeselect(getSelectedComponent());
        } else if (component instanceof ISelectable) {
            ISelectable selectable = (ISelectable) component;
            selectable.onSelect();
            this.select(selectable);
        } else {
            this.unselect();
        }
    }

    /**
     * Processes deselection event
     * @param component the deselected component
     */
    default void onDeselect(IGraphicsComponent component) {
        if (component instanceof ISelectable) {
            ((ISelectable) component).onDeselect();
        }
    }
}
