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

import com.github.stannismod.gext.api.*;
import com.github.stannismod.gext.api.menu.IContextMenuComponent;
import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.components.GBasic;
import com.github.stannismod.gext.engine.GlStateManager;
import com.github.stannismod.gext.utils.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

public class BasicLayout<T extends IGraphicsComponent> extends GBasic implements IGraphicsLayout<T> {

    // for ID access
    private final LayoutContent<T> content = new LayoutContent<>();
    // for rendering
    private final NavigableSet<T> sorted = new TreeSet<>(((o1, o2) -> {
        if (o1.getDepth() == o2.getDepth()) {
            return o1.getID().compareTo(o2.getID());
        }
        return o1.getDepth() - o2.getDepth();
    }));

    private IGraphicsListener<IGraphicsComponent> tooltip;
    private ISelector selector;

    private IGraphicsLayout<?> root;
    private IContextMenuComponent<? extends IContextMenuElement> menu;

    public BasicLayout(final int x, final int y, final int width, final int height, final boolean clippingEnabled,
                       final IGraphicsLayout<? extends IGraphicsComponent> parent, final IGraphicsComponent binding,
                       final Bound bound, final Align alignment, final int xPadding, final int yPadding,
                       final List<IListener> listeners, final IGraphicsListener<IGraphicsComponent> tooltip,
                       final ISelector selector) {
        super(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding, listeners);
        this.tooltip = tooltip;
        this.selector = selector;
    }

    @Override
    public void setWidth(final int width) {
        super.setWidth(width);
        sorted.forEach(c -> c.getAlignment().transform(c, c.getXPadding(), c.getYPadding()));
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);
        sorted.forEach(c -> c.getAlignment().transform(c, c.getXPadding(), c.getYPadding()));
    }

    @Override
    public void setParent(final @NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        super.setParent(parent);
        setRoot(parent.getRoot());
    }

    @Override
    public String addComponent(final int depth, final String id, @NotNull final T component) {
        component.setDepth(depth);
        this.putComponent(id, component);
        return component.getID();
    }

    @Override
    public void putComponent(String id, @NotNull T component) {
        component.setParent(this);
        content.putComponent(id, component);
        sorted.add(component);
    }

    @Override
    public T getComponent(String id) {
        return content.getContent().get(id);
    }

    @Override
    public T removeComponent(String id) {
        T removed = content.remove(id);
        sorted.remove(removed);
        return removed;
    }

    @Override
    public IGraphicsLayout<?> getRoot() {
        if (root == null) {
            return this;
        }
        return root;
    }

    @Override
    public void setRoot(final IGraphicsLayout<?> root) {
        this.root = root;
    }

    @Override
    public int size() {
        return content.getContent().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setContent(@NotNull LayoutContent<? extends IGraphicsComponent> newContent) {
        clear();
        newContent.getContent().forEach((id, component) -> putComponent(id, (T) component));
    }

    @Override
    public @NotNull LayoutContent<T> getContent() {
        return content;
    }

    @Override
    public void clear() {
        content.clear();
        sorted.clear();
    }

    @Override
    public void setTooltip(@NotNull IGraphicsListener tooltip) {
        if (tooltip == null) {
            throw new IllegalArgumentException("Tooltip mustn't be null!");
        }
        this.tooltip = tooltip;
    }

    @Override
    public IGraphicsListener getOwnTooltip() {
        return this.tooltip;
    }

    @Override
    public void setSelector(@Nullable ISelector selector) {
        this.selector = selector;
    }

    @Override
    public ISelector getSelector() {
        return selector;
    }

    @Override
    public IContextMenuComponent<? extends IContextMenuElement> getActiveMenu() {
        if (!isRoot()) {
            return getRoot().getActiveMenu();
        }
        return menu;
    }

    @Override
    public void setActiveMenu(final IContextMenuComponent<? extends IContextMenuElement> menu) {
        if (!isRoot()) {
            getRoot().setActiveMenu(menu);
            return;
        }
        this.menu = menu;
    }

    @Override
    public boolean checkUpdates() {
        boolean dirty = false;
        for (IGraphicsComponent component : sorted) {
            if (component.checkUpdates()) {
                component.markDirty();
                dirty = true;
            }
        }
        return dirty;
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        if (hasActiveMenu()) {
            boolean intersects = getActiveMenu().intersectsInner(mouseX - getActiveMenu().getAbsoluteX(), mouseY - getActiveMenu().getAbsoluteY());
            if (mouseButton == 0 && !intersects) {
                setActiveMenu(null);
                return;
            }
            if (intersects) {
                getActiveMenu().onMousePressed(mouseX - getActiveMenu().getX(), mouseY - getActiveMenu().getY(), mouseButton);
                return;
            }
        }
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onMousePressed(mouseX - component.getX(), mouseY - component.getY(), mouseButton);
            }
        });
        if (getOwnTooltip() != null) {
            getOwnTooltip().onMousePressed(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (hasActiveMenu() && getActiveMenu().intersectsInner(mouseX - getActiveMenu().getAbsoluteX(), mouseY - getActiveMenu().getAbsoluteY())) {
            getActiveMenu().onMouseReleased(mouseX - getActiveMenu().getX(), mouseY - getActiveMenu().getY(), mouseButton);
            return;
        }
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onMouseReleased(mouseX - component.getAbsoluteX(), mouseY - component.getAbsoluteY(), mouseButton);
                if (getSelector() != null) {
                    getSelector().onSelect(component);
                }
            }
        });
        if (getOwnTooltip() != null) {
            getOwnTooltip().onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        sorted.forEach(component -> component.onKeyPressed(typedChar, keyCode));
        if (getOwnTooltip() != null) {
            getOwnTooltip().onKeyPressed(typedChar, keyCode);
        }
        if (hasActiveMenu()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                setActiveMenu(null);
                return;
            }
            getActiveMenu().onKeyPressed(typedChar, keyCode);
        }
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        sorted.forEach(component -> {
            if (component.intersects(mouseX, mouseY)) {
                component.onHover(mouseX - component.getX(), mouseY - component.getY());
            }
        });
        if (hasActiveMenu() && getActiveMenu().intersectsInner(mouseX - getActiveMenu().getAbsoluteX(), mouseY - getActiveMenu().getAbsoluteY())) {
            getActiveMenu().onHover(mouseX - getActiveMenu().getAbsoluteX(), mouseY - getActiveMenu().getAbsoluteY());
        }
    }

    @Override
    public void onMouseInput(int mouseX, int mouseY, int mouseButton) {
        super.onMouseInput(mouseX, mouseY, mouseButton);
        sorted.forEach(component -> component.onMouseInput(
                mouseX - component.getX(), mouseY - component.getY(), mouseButton));
    }

    @Override
    public void onMouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double xAmount, final double yAmount) {
        super.onMouseDragged(mouseX, mouseY, mouseButton, xAmount, yAmount);
        sorted.forEach(component -> component.onMouseDragged(
                mouseX - component.getX(), mouseY - component.getY(), mouseButton, xAmount, yAmount));
    }

    @Override
    public void onMouseMoved(final int mouseX, final int mouseY) {
        super.onMouseMoved(mouseX, mouseY);
        sorted.forEach(component -> component.onMouseMoved(
                mouseX - component.getX(), mouseY - component.getY()));
    }

    @Override
    public void onMouseScrolled(final int mouseX, final int mouseY, final double amountScrolled) {
        super.onMouseScrolled(mouseX, mouseY, amountScrolled);
        sorted.forEach(component -> component.onMouseScrolled(
                mouseX - component.getX(), mouseY - component.getY(), amountScrolled));
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        int depth = 0;

        // TODO Optimization: draw only visible(in-frame) components
        for (IGraphicsComponent component : sorted) {
            if (component.getDepth() != depth) {
                GlStateManager.translate(0.0F, 0.0F, component.getDepth() - depth);
                depth = component.getDepth();
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //GL11.glEnable(GL11.GL_BLEND);
            component.render(mouseX - component.getX(), mouseY - component.getY(), partialTicks);
        }

        if (getOwnTooltip() != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 255);
            getOwnTooltip().render(mouseX, mouseY, partialTicks);
            GlStateManager.popMatrix();
        }
        if (hasActiveMenu()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(getActiveMenu().getAbsoluteX(), getActiveMenu().getAbsoluteY(), 255);
            getActiveMenu().draw(mouseX - getActiveMenu().getX(), mouseY - getActiveMenu().getY(), partialTicks);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void update() {
        sorted.forEach(component -> {
            if (component.needUpdate()) {
                component.update();
            }
        });
        needUpdate = false;
        if (getOwnTooltip() != null) {
            getOwnTooltip().update();
        }
    }

    @Override
    public void init() {
        sorted.forEach(IGraphicsComponent::init);
        if (getOwnTooltip() != null) {
            getOwnTooltip().init();
        }
    }

    @Override
    public void onClosed() {
        sorted.forEach(IGraphicsComponent::onClosed);
        if (getOwnTooltip() != null) {
            getOwnTooltip().onClosed();
        }
    }

    @Override
    public void onResize(int w, int h) {
        this.clear();
        this.setWidth(w);
        this.setHeight(h);
    }

    public static class Builder<SELF extends BasicLayout.Builder<SELF, T>, T extends BasicLayout<? extends IGraphicsComponent>> extends ComponentBuilder<SELF, T> {

        protected ISelector selector;
        protected IGraphicsListener<IGraphicsComponent> tooltip;

        @Override
        protected T create() {
            return new BasicLayout<>(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                    xPadding, yPadding, listeners, tooltip, selector);
        }
    }
}
