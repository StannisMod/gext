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

package com.github.stannismod.gext.api.adapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @since 1.0
 */
public interface IFontRenderer {

    void drawString(@NotNull String text, int x, int y, int color);

    int getStringWidth(@NotNull String text);

    int getFontHeight();

    @NotNull List<String> listTextToWidth(@NotNull String text, int width);
}
