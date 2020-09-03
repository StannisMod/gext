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

package ru.quarter.gui.lib.components;

import org.lwjgl.opengl.GL11;
import ru.quarter.gui.lib.api.IGraphicsComponent;
import ru.quarter.gui.lib.api.IGraphicsLayout;
import ru.quarter.gui.lib.api.IListener;
import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.utils.GraphicsHelper;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class GBasic implements IGraphicsComponent {

    private int id;
    private int depth;
    protected boolean needUpdate;
    private boolean visible = true;

    protected Rectangle frame;
    protected IResource texture;

    private IGraphicsLayout<? extends IGraphicsComponent> parent;
    private IGraphicsComponent binding;

    private final List<IListener<? extends IGraphicsComponent>> listeners = new LinkedList<>();

    protected GBasic() {}

    public GBasic(int x, int y, int width, int height) {
        this(x, y, width, height, null);
    }

    public GBasic(int x, int y, int width, int height, IResource texture) {
        this.frame = new Rectangle(x, y, width, height);
        this.texture = texture;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
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
    public int getX() {
        return getFrame().x;
    }

    @Override
    public void setX(int x) {
        getFrame().x = x;
    }

    @Override
    public int getY() {
        return getFrame().y;
    }

    @Override
    public void setY(int y) {
        getFrame().y = y;
    }

    @Override
    public int getWidth() {
        return getFrame().width;
    }

    @Override
    public void setWidth(int width) {
        getFrame().width = width;
    }

    @Override
    public int getHeight() {
        return getFrame().height;
    }

    @Override
    public void setHeight(int height) {
        getFrame().height = height;
    }

    @Override
    public Rectangle getFrame() {
        return frame;
    }

    @Override
    public IGraphicsLayout<? extends IGraphicsComponent> getParent() {
        return parent;
    }

    @Override
    public void setParent(IGraphicsLayout<? extends IGraphicsComponent> parent) {
        this.parent = parent;
    }

    @Override
    public void setBinding(IGraphicsComponent binding) {
        if (binding.getParent() != this.getParent()) {
            throw new IllegalArgumentException("The binding should have the same parent!");
        }
        this.binding = binding;
    }

    @Override
    public IGraphicsComponent getBinding() {
        return binding;
    }

    @Override
    public void addListener(IListener<? extends IGraphicsComponent> listener) {
        listeners.add(listener);
    }

    @Override
    public void render(int mouseX, int mouseY) {
        if (visible()) {
            int x = getX();
            int y = getY();
            if (getBinding() != null) {
                x += getBinding().getX();
                y += getBinding().getY();
                mouseX -= getBinding().getX();
                mouseY -= getBinding().getY();
            }
            if (intersects(mouseX, mouseY)) {
                onHover(mouseX, mouseY);
            }
            if (needUpdate() || checkUpdates()) {
                update();
            }

            listeners.forEach(IListener::listen);

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GraphicsHelper.glScissor(x, y, getWidth(), getHeight());
            GL11.glTranslatef(x, y, getDepth());
            draw(mouseX, mouseY);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onHover(int mouseX, int mouseY) {
        IListener<IGraphicsComponent> tooltip = getParent().getTooltip();
        if (tooltip != null) {
            tooltip.setTarget(this);
        }
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
