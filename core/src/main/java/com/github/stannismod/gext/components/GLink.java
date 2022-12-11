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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.api.adapter.IFontRenderer;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Bound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.net.URI;
import java.util.List;

public class GLink extends GLabel {

    protected int activeColor;
    protected int inactiveColor;
    protected URI uri;

    private boolean active;
    private boolean hovered;
    private boolean prevHovered;

    public GLink(final int x, final int y, final boolean clippingEnabled,
                 final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                 final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                 final List<IListener> listeners, final String text, final int color, final IFontRenderer fontRenderer,
                 final float scale, final boolean centered, final int activeColor, final int inactiveColor,
                 final URI uri) {
        super(x, y, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners, text, color,
                fontRenderer, scale, centered);
        this.activeColor = activeColor;
        this.inactiveColor = inactiveColor;
        this.color = inactiveColor;
        this.uri = uri;
    }


    public boolean isActive() {
        return active;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        super.onMousePressed(mouseX, mouseY, mouseButton);
        active = true;
        color = activeColor;
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        active = false;
        color = inactiveColor;
        openWebLink(uri);
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        super.onHover(mouseX, mouseY);
        hovered = true;
    }

    @Override
    public boolean checkUpdates() {
        return checkUpdates() || hovered != prevHovered;
    }

    @Override
    public void update() {
        super.update();
        if (active && !hovered) {
            active = false;
            color = inactiveColor;
        }
        needUpdate = false;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        prevHovered = hovered;
        hovered = false;
    }

    private void openWebLink(URI url) {
        try {
            Desktop.getDesktop().browse(url);
        } catch (Exception e) {
            GExt.error("Couldn't open link", e);
        }
    }

    public static abstract class Builder<SELF extends Builder<?, T>, T extends GLink> extends GLabel.Builder<SELF, T> {
        protected URI uri;
        protected int activeColor;
        protected int inactiveColor;

        @Override
        public SELF text(@NotNull String text) {
            this.text = text;
            return scale(1.0F);
        }

        @Override
        @Contract("_, _ -> fail")
        public SELF text(@NotNull String text, int color) {
            throw new UnsupportedOperationException("For drawing link with color use Builder#color(activeColor, inactiveColor)");
        }

        public SELF url(@NotNull String url) {
            this.uri = URI.create(url);
            return self();
        }

        public SELF color(int color) {
            return color(color, color);
        }

        public SELF color(int activeColor, int inactiveColor) {
            this.activeColor = activeColor;
            this.inactiveColor = inactiveColor;
            return self();
        }
    }
}
