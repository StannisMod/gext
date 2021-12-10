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

package com.github.stannismod.gext.test.core.appearance.adapter;

import com.github.stannismod.gext.api.adapter.IFontRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MockFontRenderer implements IFontRenderer {

    @Override
    public void drawString(@NotNull String text, int x, int y, int color) {
        // empty
    }

    @Override
    public int getStringWidth(@NotNull String text) {
        return text.length();
    }

    @Override
    public int getFontHeight() {
        return 8;
    }

    @Override
    public @NotNull List<String> listTextToWidth(@NotNull String text, int width) {
        List<String> lst = new ArrayList<>();
        lst.add(text);
        return lst;
    }
}
