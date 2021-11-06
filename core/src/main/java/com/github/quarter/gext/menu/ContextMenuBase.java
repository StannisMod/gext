/*
 *  Copyright 2020 Stanislav Batalenkov
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.quarter.gext.menu;

import com.github.quarter.gext.api.IGraphicsComponent;
import com.github.quarter.gext.api.menu.IContextMenuElement;
import com.github.quarter.gext.api.menu.IContextMenuList;

public abstract class ContextMenuBase implements IContextMenuElement {

    private IContextMenuList<? extends ContextMenuBase> parent;
    private int height;

    @SuppressWarnings("unchecked")
    @Override
    public void setParent(final IContextMenuList<? extends IContextMenuElement> parent) {
        this.parent = (IContextMenuList<? extends ContextMenuBase>) parent;
    }

    @Override
    public IContextMenuList<? extends ContextMenuBase> getParent() {
        return parent;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(final int height) {
        this.height = height;
    }

    @Override
    public IGraphicsComponent getTarget() {
        return getParent().getTarget();
    }

    @Override
    public void setTarget(IGraphicsComponent target) {
        getParent().setTarget(target);
    }
}
