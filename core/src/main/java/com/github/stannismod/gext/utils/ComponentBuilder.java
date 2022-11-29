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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.components.Graphics;

import java.util.List;

/**
 * The generalization of component builders.
 * Every builder should extend this to provide convenient usage.
 * @param <SELF> the pointer to the end implementation. By default, declared in {@link Graphics}
 * @param <T> the pointer to the target type that should be built. By default, declared in {@link Graphics}
 * @since 1.3
 */
@SuppressWarnings("unchecked")
public abstract class ComponentBuilder<SELF extends ComponentBuilder<SELF, T>, T extends IGraphicsComponent> {

    protected int x;
    protected int y;
    protected int depth;

    protected int width;
    protected int height;

    protected boolean clippingEnabled;
    protected boolean visibility;
    protected List<IListener> listeners;
    protected IGraphicsLayout<T> parent;


    protected int xPadding;
    protected int yPadding;

    protected IGraphicsComponent binding;
    protected Bound bound;

    protected Align alignment;

    public ComponentBuilder() {}

    /**
     * Constructs the end implementation of target component
     * @since 1.5.1
     * @return the build instance
     */
    protected abstract T create();

    /**
     * Checks that passed build information can be applied to data model
     * @since 1.5.1
     * @throws GInitializationException when build parameters are illegal
     */
    public void testBuildParameters() {
        if (binding != null) {
            if (alignment != Alignment.FIXED) {
                throw new GInitializationException("Alignment isn't compatible with binding!");
            }
        }
    }

    /**
     * Called after build parameters passed and instance constructed.
     * In this place some actions with instance itself can be performed.
     * @param instance the constructed instance
     * @since 1.5.1
     */
    protected void afterCreation(T instance) {
        if (parent != null) {
            parent.addComponent(instance);
        }

        if (alignment != Alignment.FIXED && x != 0 && y != 0) {
            GExt.warn(instance, "Component have manually set coordinates with alignment been set. " +
                    "It can be inferred behaviour, but in most cases indicates a broken component.");
        }
    }

    /**
     * Returns the result of the building process
     * @return the build instance
     */
    public T build() {
        testBuildParameters();

        T instance = create();

        afterCreation(instance);
        return instance;
    }

    /**
     * Returns {@code this} cast to the right type
     */
    protected SELF self() {
        return (SELF) this;
    }

    public SELF padding(int xPadding, int yPadding) {
        this.xPadding = xPadding;
        this.yPadding = yPadding;
        return self();
    }

    public SELF alignment(Align alignment) {
        return alignment(alignment, alignment);
    }

    public SELF alignment(Align xAlignment, Align yAlignment) {
        this.alignment = new Alignment.Compose(xAlignment, yAlignment);
        return self();
    }

    public SELF parent(IGraphicsLayout<T> parent) {
        this.parent = parent;
        return self();
    }

    public SELF addListener(IListener listener) {
        this.listeners.add(listener);
        return self();
    }

    public SELF setClipping(boolean clippingEnabled) {
        this.clippingEnabled = clippingEnabled;
        return self();
    }

    public SELF bind(IGraphicsComponent binding) {
        return bind(binding, Bound.LEFT_TOP);
    }

    public SELF bind(IGraphicsComponent binding, Bound bound) {
        this.binding = binding;
        this.bound = bound;
        return self();
    }

    public SELF visibility(boolean visibility) {
        this.visibility = visibility;
        return self();
    }

    public SELF size(int width, int height) {
        this.width = width;
        this.height = height;
        return self();
    }

    public SELF placeAt(int x, int y) {
        return placeAt(x, y, 0);
    }

    public SELF placeAt(int x, int y, int depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        return self();
    }
}
