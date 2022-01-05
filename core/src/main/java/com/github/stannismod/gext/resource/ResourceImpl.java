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

package com.github.stannismod.gext.resource;

import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.api.resource.IResourceProvider;
import com.github.stannismod.gext.api.resource.ITexture;

public class ResourceImpl implements IResource {

    private final IResourceProvider provider;
    private final String domain;
    private final String path;
    private boolean cached;

    public ResourceImpl(final IResourceProvider provider, final String path) {
        this(provider, path.split(":")[0], path.split(":")[1]);
    }

    public ResourceImpl(final IResourceProvider provider, final String domain, final String path) {
        this.provider = provider;
        this.domain = domain;
        this.path = path;
    }

    @Override
    public IResourceProvider getProvider() {
        return provider;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ITexture toTexture() {
        return new TextureImpl(this);
    }

    @Override
    public boolean isCached() {
        return cached;
    }

    @Override
    public void setCached(final boolean cached) {
        this.cached = cached;
    }
}
