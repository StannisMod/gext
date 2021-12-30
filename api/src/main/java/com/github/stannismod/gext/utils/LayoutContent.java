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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.api.IGraphicsComponent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LayoutContent<T extends IGraphicsComponent> {

    private final Map<String, T> content = new HashMap<>();

    public static <T extends IGraphicsComponent> LayoutContent<T> create() {
        return new LayoutContent<>();
    }

    public static <T extends IGraphicsComponent> LayoutContent<T> withContent(Map<String, T> content) {
        LayoutContent<T> builder = LayoutContent.create();
        builder.content.putAll(content);
        return builder;
    }

    public LayoutContent<T> putComponent(String id, T component) {
        component.setID(id);
        content.put(id, component);
        return this;
    }

    public LayoutContent<T> putComponent(T component) {
        return putComponent(UUID.randomUUID().toString(), component);
    }

    public void clear() {
        content.clear();
    }

    public T remove(String id) {
        return content.remove(id);
    }

    public T get(String id) {
        return content.get(id);
    }

    public Map<String, T> getContent() {
        return Collections.unmodifiableMap(content);
    }
}
