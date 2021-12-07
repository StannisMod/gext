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

package com.github.stannismod.gext.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IRootLayout {

    @NotNull IGraphicsLayout<IGraphicsComponent> layout();

    default int add(@NotNull IGraphicsComponent component) {
        return layout().addComponent(component);
    }

    default int add(int depth, @NotNull IGraphicsComponent component) {
        return layout().addComponent(depth, component);
    }

    @Nullable
    default IGraphicsComponent get(int id) {
        return layout().getComponent(id);
    }

    @Nullable
    default IGraphicsComponent remove(int id) {
        return layout().removeComponent(id);
    }

    /**
     * Method for components' initialization(e.g. using {@link IRootLayout#add(int, IGraphicsComponent)})
     * Should be in the final implementation
     */
    void initLayout();
}
