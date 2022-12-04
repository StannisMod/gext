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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.api.ISelector;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Tooltip extends GTooltip {

    private String labelId;

    public Tooltip(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                   final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                   final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                   final List<IListener> listeners, final ISelector selector, final int xOffset, final int yOffset) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners,
                selector, xOffset, yOffset);
    }

    @Override
    public void initTooltip() {
        labelId = addComponent(0, Graphics.label().text("Primary text").placeAt(0, 0).build(), GLink.class);
    }

    @Override
    public void listen(@Nullable IGraphicsComponent target) {
        if (target == null) {
            setVisibility(false);
            return;
        }
        ((GLabel) getParent().getComponent(labelId)).text = "Secondary text";
        setVisibility(true);
    }
}
