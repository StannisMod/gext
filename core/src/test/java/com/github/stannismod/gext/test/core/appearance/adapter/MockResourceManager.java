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

import com.github.stannismod.gext.api.adapter.*;
import org.jetbrains.annotations.NotNull;

public class MockResourceManager implements IResourceManager {

    public static MockFontRenderer DEFAULT_FONTRENDERER;

    @Override
    public @NotNull IScaledResolution scaled() {
        return new MockScaledResolution();
    }

    @Override
    public @NotNull IResource resource(String name) {
        return new MockResource();
    }

    @Override
    public @NotNull IGraphicsHelper helper() {
        return MockGraphicsHelper.INSTANCE;
    }

    @Override
    public @NotNull IFontRenderer standardRenderer() {
        if (DEFAULT_FONTRENDERER == null) {
            DEFAULT_FONTRENDERER = new MockFontRenderer();
        }
        return DEFAULT_FONTRENDERER;
    }
}
