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

/**
 * Represents actions what layout do with content.
 *
 * Instances of this defines the placing algorithms,
 * that can be combined with any graphics layout behaviour
 * @since 1.4
 */
public interface ILayoutType {

    void setTarget(IGraphicsLayout<? extends IGraphicsComponent> target);

    void onComponentPlaced(IGraphicsComponent component);

    void onComponentRemoved(IGraphicsComponent component);

    int getContentWidth();

    int getContentHeight();

    int getEfficientWidth();

    int getEfficientHeight();
}
