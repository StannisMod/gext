/*
 * Copyright 2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.resource.provider;

import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.api.resource.IResourceProvider;
import com.github.stannismod.gext.resource.ResourceImpl;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicResourceProvider implements IResourceProvider {

    private final Map<String, IResource> resources = new HashMap<>();
    private final String name;

    public BasicResourceProvider(final String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull IResource getResource(final String path) {
        return resources.compute(path, (k, v) -> v != null ? v : new ResourceImpl(this, path));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + getName();
    }
}
