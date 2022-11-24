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
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.ISelectable;
import com.github.stannismod.gext.api.ISelector;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.LayoutContent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GTabPanel<K extends IGraphicsComponent, V extends IGraphicsComponent> extends GList<K> implements ISelector {

    private static final LayoutContent<? extends IGraphicsComponent> EMPTY_CONTENT = LayoutContent.create();

    private String selected;
    private IGraphicsLayout<V> target;
    private final Map<String, LayoutContent<V>> contentMap = new HashMap<>();

    private boolean deselectionEnabled;

    private GTabPanel() {
        this.setSelector(this);
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

    public static class Builder<SELF extends Builder<?, T, K, V>, T extends GTabPanel<K, V>,
            K extends IGraphicsComponent, V extends IGraphicsComponent> extends ComponentBuilder<SELF, GTabPanel<K, V>> {

        public SELF target(IGraphicsLayout<V> target) {
            instance().target = target;
            return self();
        }

        public SELF setContentMap(Map<String, Map<String, V>> contentMap) {
            contentMap.forEach(this::putContent);
            return self();
        }

        public SELF putContent(String selectedId, V component) {
            instance().contentMap.compute(selectedId, (key, value) -> {
                if (value == null) {
                    value = LayoutContent.create();
                }
                value.putComponent(component);
                return value;
            });
            return self();
        }

        public SELF putContent(String selectedId, Map<String, V> content) {
            instance().contentMap.put(selectedId, LayoutContent.withContent(content));
            return self();
        }
    }
}
