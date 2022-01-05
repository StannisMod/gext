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

import java.io.File;
import java.io.InputStream;

// TODO
public class CachingResourceProvider extends BasicResourceProvider {

    private final File cacheDir;
    private boolean cachingEnabled;

    public CachingResourceProvider(final String name, final File cacheDir) {
        super(name);
        this.cacheDir = cacheDir;
    }

    @Override
    public InputStream getInputStream(final IResource resource) {
        // TODO

        return null;
    }

    @Override
    public void setCachingEnabled(final boolean enabled) {
        this.cachingEnabled = enabled;
    }

    @Override
    public boolean isCachingEnabled() {
        return cachingEnabled;
    }

    @Override
    public boolean supportCaching() {
        return true;
    }
}
