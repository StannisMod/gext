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

public interface IScrollable extends IGraphicsComponent {

    void setScrollHandler(IGraphicsComponentScroll handler);

    @Nullable
    IGraphicsComponentScroll getScrollHandler();

    default boolean scrollEnabled() {
        return getScrollHandler() != null;
    }

    int getScrollVertical();

    int getScrollHorizontal();

    void setScrollVertical(int value);

    void setScrollHorizontal(int value);

    int getContentWidth();

    int getContentHeight();

    default void addScrollVertical(int value) {
        setScrollVertical(getScrollVertical() + value);
    }

    default void addScrollHorizontal(int value) {
        setScrollHorizontal(getScrollHorizontal() + value);
    }
}
