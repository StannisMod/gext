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

package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.*;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import com.github.stannismod.gext.utils.LayoutContent;
import com.github.stannismod.gext.utils.TextureMapping;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GTabPanel<K extends IGraphicsComponent, V extends IGraphicsComponent> extends GList<K> implements ISelector {

    private static final LayoutContent<? extends IGraphicsComponent> EMPTY_CONTENT = LayoutContent.create();

    private String selected;
    private IGraphicsLayout<V> target;
    private final Map<String, LayoutContent<V>> contentMap = new HashMap<>();

    private boolean deselectionEnabled;

    public GTabPanel(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                     final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                     final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                     final List<IListener> listeners, final IGraphicsListener<? extends BasicLayout<K>> tooltip,
                     final ISelector selector, final IGraphicsComponentScroll scrollHandler, final int xOffset,
                     final int yOffset, final boolean wrapContent, final TextureMapping background,
                     final boolean drawBackground, final int interval, final IGraphicsLayout<V> target,
                     final Map<String, LayoutContent<V>> contentMap, final boolean deselectionEnabled) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners,
                tooltip, selector, scrollHandler, xOffset, yOffset, wrapContent, background, drawBackground, interval);
        this.setSelector(this);
        this.setTarget(target);
        this.contentMap.putAll(contentMap);
        this.deselectionEnabled = deselectionEnabled;
    }


    @Override
    public String getSelectedId() {
        return selected;
    }

    @Override
    public void select(String id) {
        this.selected = id;
        LayoutContent<? extends IGraphicsComponent> content = contentMap.get(id);
        if (content == null && id != null) {
            GExt.warn(this, "Selected unmapped component, setting empty content");
        }
        target.setContent(content == null ? EMPTY_CONTENT : content);
    }

    @Override
    public void onSelect(IGraphicsComponent component) {
        if (target == null) {
            GExt.warn(this, "GTabPanel used without target component, ignoring selection");
            return;
        }
        if (isDeselectionEnabled() && getSelectedId() != null && getSelectedId().equals(component.getID())) {
            this.onDeselect(getSelectedComponent());
        } else {
            if (getSelectedId() != null) {
                IGraphicsComponent selected = getSelectedComponent();
                if (selected instanceof ISelectable) {
                    ((ISelectable) selected).onDeselect();
                }
            }
            if (component instanceof ISelectable) {
                ((ISelectable) component).onSelect();
            }
            this.select(component);
        }
    }

    @Override
    public void onDeselect(IGraphicsComponent component) {
        target.setContent(EMPTY_CONTENT);
        if (component instanceof ISelectable) {
            ((ISelectable) component).onDeselect();
        }
        this.unselect();
    }

    @Override
    public @Nullable IGraphicsComponent getSelectedComponent() {
        return getComponent(getSelectedId());
    }

    public void setTarget(IGraphicsLayout<V> target) {
        this.target = target;
        this.unselect();
    }

    public boolean isDeselectionEnabled() {
        return deselectionEnabled;
    }

    public void setDeselectionEnabled(final boolean deselectionEnabled) {
        this.deselectionEnabled = deselectionEnabled;
    }

    public static abstract class Builder<SELF extends Builder<?, T, K, V>, T extends GTabPanel<K, V>,
            K extends IGraphicsComponent, V extends IGraphicsComponent> extends GList.Builder<SELF, GTabPanel<K, V>> {

        protected IGraphicsLayout<V> target;
        protected final Map<String, LayoutContent<V>> contentMap = new HashMap<>();
        protected boolean deselectionEnabled;

        public SELF target(IGraphicsLayout<V> target) {
            this.target = target;
            return self();
        }

        public SELF setContentMap(Map<String, Map<String, V>> contentMap) {
            contentMap.forEach(this::putContent);
            return self();
        }

        public SELF putContent(String selectedId, V component) {
            this.contentMap.compute(selectedId, (key, value) -> {
                if (value == null) {
                    value = LayoutContent.create();
                }
                value.putComponent(component);
                return value;
            });
            return self();
        }

        public SELF putContent(String selectedId, Map<String, V> content) {
            this.contentMap.put(selectedId, LayoutContent.withContent(content));
            return self();
        }

        public SELF enableDeselection() {
            this.deselectionEnabled = true;
            return self();
        }
    }
}
