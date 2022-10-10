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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.resource.IResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FilesystemResourceProvider extends BasicResourceProvider {

    private final Path rootDir;

    public FilesystemResourceProvider(final String name, final Path rootDir) {
        super(name);
        this.rootDir = rootDir;
    }

    @Override
    public InputStream getInputStream(final IResource resource) {
        try {
            File file = rootDir.resolve(resource.getDomain()).resolve(resource.getPath()).toFile();
            return new FileInputStream(file);
        } catch (IOException e) {
            GExt.error("I/O exception caught while loading " + resource + " by provider " + resource, e);
        } catch (InvalidPathException e) {
            GExt.error(this + " unable to load resource " + resource + ": broken path", e);
        }
        return null;
    }

    @Override
    public void setCachingEnabled(final boolean enabled) {
        throw new UnsupportedOperationException("FilesystemResourceProvider doesn't support caching");
    }

    @Override
    public boolean isCachingEnabled() {
        return false;
    }

    @Override
    public boolean supportCaching() {
        return false;
    }
}
