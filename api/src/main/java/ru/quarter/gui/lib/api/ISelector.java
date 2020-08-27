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

package ru.quarter.gui.lib.api;

/**
 * API for selection controls
 * @since 1.2
 */
public interface ISelector extends IGraphicsComponent {

    /**
     * Getter for selection ID
     * @return the currently selected component ID
     */
    int getSelectedId();

    /**
     * Setter for selection ID
     * @param id the provided ID
     */
    void select(int id);

    /**
     * Selects the given component
     * @param component given component
     */
    default void select(IGraphicsComponent component) {
        select(component.getID());
    }

    default IGraphicsComponent getSelectedComponent() {
        return getParent().getComponent(getSelectedId());
    }

    /**
     *
     * @return true if something is selected with this selector
     */
    default boolean isSelected() {
        return getSelectedId() != -1;
    }

    /**
     * Deactivates selection
     */
    default void unselect() {
        select(-1);
    }

    /**
     * Processes selection event
     * @param component the selected component
     */
    void onSelect(IGraphicsComponent component);

    /**
     * Processes deselection event
     * @param component the deselected component
     */
    void onDeselect(IGraphicsComponent component);
}
