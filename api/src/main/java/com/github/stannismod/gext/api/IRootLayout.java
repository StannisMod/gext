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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * API for root containers. This instances are platform-nested adaptations of {@link IGraphicsLayout}.
 * You can provide the different {@link IGraphicsLayout} in {@link IRootLayout#layout()} method
 * for change behaviour of the root container.
 * @since 1.0
 */
public interface IRootLayout {

    @NotNull IGraphicsLayout<IGraphicsComponent> layout();

    default String add(@NotNull IGraphicsComponent component) {
        return layout().addComponent(component);
    }

    default String add(int depth, @NotNull IGraphicsComponent component) {
        return layout().addComponent(depth, component);
    }

    @Nullable
    default IGraphicsComponent get(String id) {
        return layout().getComponent(id);
    }

    @Nullable
    default IGraphicsComponent remove(String id) {
        return layout().removeComponent(id);
    }

    /**
     * Method for components' initialization(e.g. using {@link IRootLayout#add(int, IGraphicsComponent)})
     * Should be in the final implementation
     */
    void initLayout();
}
