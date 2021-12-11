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
import com.github.stannismod.gext.components.Graphics;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The generalization of component builders
 *
 * Every builder should extend this to provide convenient usage.
 * Components using this builder should have a default constructor
 * and the end implementation must be abstract. To create an instance
 * of a builder, anonymous class should be constructed. This provides
 * {@link #create()} to work.
 * @param <SELF> the pointer to the end implementation. By default, declared in {@link Graphics}
 * @param <T> the pointer to the target type that should be built. By default, declared in {@link Graphics}
 * @since 1.3
 */
@SuppressWarnings("unchecked")
public abstract class ComponentBuilder<SELF extends ComponentBuilder<?, T>, T extends IGraphicsComponent> {

    private final T instance;

    protected ComponentBuilder() {
        instance = create();
    }

    /**
     * Creates an empty instance
     * @return a new empty instance
     */
    protected T create() {
        try {
            ParameterizedType parameterizedType =
                    (ParameterizedType) getClass().getGenericSuperclass();
            Type type = parameterizedType.getActualTypeArguments()[1];
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

    /**
     * Provides internal access to the instance
     *
     * By semantics, please use this method for access instance, not {@link #build()}
     * @return the instance
     */
    protected T instance() {
        return instance;
    }

    /**
     * Returns the result of the building process
     * @return the build instance
     */
    public T build() {
        return instance();
    }

    /**
     * Returns {@code this} cast to the right type
     */
    protected SELF self() {
        return (SELF) this;
    }

    public SELF size(int width, int height) {
        instance().setWidth(width);
        instance().setHeight(height);
        return (SELF) this;
    }

    public SELF placeAt(int x, int y) {
        instance().setX(x);
        instance().setY(y);
        return (SELF) this;
    }
}
