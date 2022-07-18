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
import com.github.stannismod.gext.api.IScrollable;
import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.api.menu.IContextMenuList;
import com.github.stannismod.gext.menu.GContextMenu;
import com.github.stannismod.gext.utils.Align;
import com.github.stannismod.gext.utils.Alignment;
import com.github.stannismod.gext.utils.Bound;
import com.github.stannismod.gext.utils.FrameStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GBasic implements IGraphicsComponent {

    private String id;
    private int depth;
    protected boolean needUpdate;
    private boolean visible = true;
    private boolean clippingEnabled = true;

    protected Rectangle frame = new Rectangle();
    protected Rectangle absoluteFrame = new Rectangle();

    private IGraphicsLayout<? extends IGraphicsComponent> parent;
    private IGraphicsComponent binding;
    private Bound bound = Bound.LEFT_TOP;

    private Align alignment = Alignment.FIXED;
    private int xPadding;
    private int yPadding;

    private final List<IListener> listeners = new LinkedList<>();

    protected GBasic() {}

    public GBasic(int x, int y, int width, int height) {
        this.frame = new Rectangle(x, y, width, height);
        this.absoluteFrame = new Rectangle(x, y, width, height);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    @Override
    public void setVisibility(boolean visibility) {
        this.visible = visibility;
    }

    @Override
    public int getAbsoluteX() {
        return absoluteFrame.x;
    }

    @Override
    public int getX() {
        return getFrame().x;
    }

    @Override
    public void setX(int x) {
        getFrame().x = x + (int)(getBinding() != null
                ? getBinding().getX() + bound.getMultiplierX() * getBinding().getWidth()
                : 0);
        getAbsoluteFrame().x = getX() + (hasParent() ? getParent().getAbsoluteX() : 0);
    }

    @Override
    public int getAbsoluteY() {
        return absoluteFrame.y;
    }

    @Override
    public int getY() {
        return getFrame().y;
    }

    @Override
    public void setY(int y) {
        getFrame().y = y + (int)(getBinding() != null
                ? getBinding().getY() + bound.getMultiplierY() * getBinding().getHeight()
                : 0);
        getAbsoluteFrame().y = getY() + (hasParent() ? getParent().getAbsoluteY() : 0);
    }

    @Override
    public int getWidth() {
        return getFrame().width;
    }

    @Override
    public void setWidth(int width) {
        getFrame().width = width;
        getAbsoluteFrame().width = width;
    }

    @Override
    public int getHeight() {
        return getFrame().height;
    }

    @Override
    public void setHeight(int height) {
        getFrame().height = height;
        getAbsoluteFrame().height = height;
    }

    @Override
    public @NotNull Rectangle getFrame() {
        return frame;
    }

    @Override
    public @NotNull Rectangle getAbsoluteFrame() {
        return absoluteFrame;
    }

    @Override
    public IGraphicsLayout<? extends IGraphicsComponent> getParent() {
        return parent;
    }

    @Override
    public void setParent(@NotNull IGraphicsLayout<? extends IGraphicsComponent> parent) {
        this.parent = parent;
        alignment.transform(this, xPadding, yPadding);
        // refreshing absoluteFrame after updating parent
        getAbsoluteFrame().x = getX() + (hasParent() ? getParent().getAbsoluteX() : 0);
        getAbsoluteFrame().y = getY() + (hasParent() ? getParent().getAbsoluteY() : 0);
    }

    @Override
    public IGraphicsLayout<?> getRoot() {
        return getParent().getRoot();
    }

    @Override
    public void setRoot(final IGraphicsLayout<?> root) {
        throw new UnsupportedOperationException("Can't set root of the ending component; it's inherited from nearest container");
    }

    @Override
    public void setAlignment(final Align alignment) {
        if (binding != null) {
            throw new IllegalStateException("Alignment isn't compatible with binding!");
        }
        this.alignment = alignment;
    }

    @Override
    public Align getAlignment() {
        return alignment;
    }

    @Override
    public IContextMenuList<?> constructMenu() {
        // empty stub here, override if need
        return null;
    }

    @Override
    public void setPaddings(final int xPadding, final int yPadding) {
        if (binding != null) {
            throw new IllegalStateException("Paddings isn't compatible with binding!");
        }
        this.xPadding = xPadding;
        this.yPadding = yPadding;
    }

    @Override
    public int getXPadding() {
        return xPadding;
    }

    @Override
    public int getYPadding() {
        return yPadding;
    }

    @Override
    public void setBinding(IGraphicsComponent binding, Bound bound) {
        if (binding != null) {
            if (this.binding != null) {
                this.shiftX(-2 * this.binding.getX());
                this.shiftY(-2 * this.binding.getY());
            }
            this.binding = binding;
            this.bound = bound;
            this.setX(getX());
            this.setY(getY());
        } else {
            this.binding = null;
        }
    }

    @Override
    public IGraphicsComponent getBinding() {
        return binding;
    }

    @Override
    public void addListener(@NotNull IListener listener) {
        listeners.add(listener);
    }

    @Override
    public boolean clippingEnabled() {
        return clippingEnabled;
    }

    @Override
    public void setClippingEnabled(boolean enabled) {
        clippingEnabled = enabled;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (visible()) {
            int x = getX();
            int y = getY();
            if (intersects(mouseX, mouseY)) {
                onHover(mouseX, mouseY);
            }
            if (needUpdate() || checkUpdates()) {
                update();
                if (!listeners.isEmpty()) {
                    listeners.forEach(l -> l.listen(this));
                }
            }

            GL11.glPushMatrix();
            if (clippingEnabled()) {
                Rectangle frame = absoluteFrame;
                if (getParent() instanceof IScrollable) {
                    IScrollable scrollable = (IScrollable) getParent();
                    frame = frame.getBounds();
                    frame.add(-scrollable.getScrollHorizontal(), -scrollable.getScrollVertical());
                }
                FrameStack.getInstance().apply(frame);
            }
            GL11.glTranslatef(x, y, getDepth());
            draw(mouseX, mouseY, partialTicks);
            if (clippingEnabled()) {
                FrameStack.getInstance().flush();
            }
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        // empty stub here, override if need
    }

    @Override
    public void init() {
        if (binding != null && binding.getParent() != this.getParent()) {
            throw new IllegalArgumentException("The binding should have the same parent!");
        }
    }

    @Override
    public boolean checkUpdates() {
        // empty stub here, override if need
        return false;
    }

    @Override
    public void update() {
        // empty stub here, override if need
    }

    @Override
    public void onClosed() {
        // empty stub here, override if need
    }

    private static final int OFFSET = 5;

    @Override
    public void onMouseInput(int mouseX, int mouseY, int mouseButton) {
        // empty stub here, override if need
    }

    @Override
    public void onMouseDragged(final double mouseX, final double mouseY, final int mouseButton, final double xAmount, final double yAmount) {
        // empty stub here, override if need
    }

    @Override
    public void onMouseMoved(final int mouseX, final int mouseY) {
        // empty stub here, override if need
    }

    @Override
    public void onMouseScrolled(final int mouseX, final int mouseY, final double amountScrolled) {
        // empty stub here, override if need
    }

    @Override
    public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 1) { // right-click
            IContextMenuList<? extends IContextMenuElement> menuList = constructMenu();
            if (menuList != null) {
                GContextMenu<? extends IContextMenuElement> menu = new GContextMenu<>(menuList);
                menu.setX(this.getAbsoluteX() + mouseX + OFFSET);
                menu.setY(this.getAbsoluteY() + mouseY + OFFSET);
                menuList.setTarget(this);
                getRoot().setActiveMenu(menu);
            }
        }
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        // empty stub here, override if need
    }

    @Override
    public void onKeyPressed(char typedChar, int keyCode) {
        // empty stub here, override if need
    }

    @Override
    public void onResize(int w, int h) {
        // empty stub here, override if need
    }

    @Override
    public void markDirty() {
        needUpdate = true;
    }

    @Override
    public boolean needUpdate() {
         return needUpdate;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
