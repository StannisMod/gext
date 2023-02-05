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

package com.github.stannismod.gext.forge112.adapter;

import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.resource.ResourceImpl;
import com.github.stannismod.gext.resource.provider.NonCachingResourceProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class MinecraftResourceProvider extends NonCachingResourceProvider {

    public static final MinecraftResourceProvider INSTANCE = new MinecraftResourceProvider("MC");

    private MinecraftResourceProvider(final String name) {
        super(name);
    }

    @Override
    public @Nullable InputStream getInputStream(final IResource resource) {
        if (!(resource instanceof MinecraftResource)) {
            return null;
        }
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(
                    ((MinecraftResource) resource).getLocation()
                    ).getInputStream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static class MinecraftResource extends ResourceImpl {

        private final ResourceLocation location;

        public MinecraftResource(final ResourceLocation location) {
            super(INSTANCE, location.getResourceDomain(), location.getResourcePath());
            this.location = location;
        }

        public ResourceLocation getLocation() {
            return location;
        }
    }
}
