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
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import com.github.stannismod.gext.utils.ComponentBuilder;
import com.github.stannismod.gext.utils.StyleMap;

import java.util.List;

public class GProgressBar extends GBasic {

    private float progress;

    protected GProgressBar(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                           final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                           final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                           final List<IListener> listeners) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        StyleMap.current().drawProgressBar(getProgress(), getX(), getY(), getWidth(), getHeight());
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GProgressBar> extends ComponentBuilder<SELF, T> {

    }
}
