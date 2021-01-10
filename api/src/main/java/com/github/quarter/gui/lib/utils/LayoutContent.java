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

package com.github.quarter.gui.lib.utils;

import com.github.quarter.gui.lib.api.IGraphicsComponent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LayoutContent<T extends IGraphicsComponent> {

    private int nextID;
    private final Map<Integer, T> content = new HashMap<>();

    public static <T extends IGraphicsComponent> LayoutContent<T> create() {
        return new LayoutContent<>();
    }

    public static <T extends IGraphicsComponent> LayoutContent<T> withContent(Map<Integer, T> content) {
        LayoutContent<T> builder = LayoutContent.create();
        builder.content.putAll(content);
        return builder;
    }

    public LayoutContent<T> putComponent(int id, T component) {
        component.setID(id);
        content.put(id, component);
        nextID = Math.max(nextID, id + 1);
        return this;
    }

    public LayoutContent<T> putComponent(T component) {
        return putComponent(nextID, component);
    }

    public int getNextID() {
        return nextID;
    }

    public void clear() {
        content.clear();
        nextID = 0;
    }

    public T remove(int id) {
        T result = content.remove(id);
        if (id == nextID - 1 && result != null) {
            nextID--;
        }
        return result;
    }

    public T get(int id) {
        if (id <= nextID) {
            return null;
        }
        return content.get(id);
    }

    public Map<Integer, T> getContent() {
        return Collections.unmodifiableMap(content);
    }
}
