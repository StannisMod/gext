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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ComponentBuilder<T extends IGraphicsComponent> {

    private final T instance;

    protected ComponentBuilder() {
        instance = create();
    }

    @SuppressWarnings("unchecked")
    protected T create() {
        try {
            ParameterizedType parameterizedType =
                    (ParameterizedType) getClass().getGenericSuperclass();
            Type type = parameterizedType.getActualTypeArguments()[0];
            Class<T> clazz;
            if (type instanceof Class) {
                clazz = (Class<T>) type;
            } else if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                clazz = (Class<T>) pt.getRawType();
            } else {
                throw new RuntimeException("Builder hierarchy problem");
            }
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected T instance() {
        return instance;
    }

    public T build() {
        return instance();
    }

    public ComponentBuilder<T> size(int width, int height) {
        instance().setWidth(width);
        instance().setHeight(height);
        return this;
    }

    public ComponentBuilder<T> placeAt(int x, int y) {
        instance().setX(x);
        instance().setY(y);
        return this;
    }
}
