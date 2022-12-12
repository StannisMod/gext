/*
 * Copyright 2022 Stanislav Batalenkov
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

import com.github.stannismod.gext.BaseTest;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IGraphicsListener;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.api.menu.IContextMenuComponent;
import com.github.stannismod.gext.api.menu.IContextMenuElement;
import com.github.stannismod.gext.api.menu.IContextMenuList;
import com.github.stannismod.gext.components.GTooltip;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.menu.ContextMenuBase;
import com.github.stannismod.gext.menu.ContextMenuList;
import com.github.stannismod.gext.menu.GContextMenu;
import com.github.stannismod.gext.utils.LayoutContent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class BasicLayoutTest extends BaseTest {

    static Stream<IGraphicsLayout<IGraphicsComponent>> componentsToTest() {
        final int x = 10;
        final int y = 10;
        final int width = 10;
        final int height = 10;

        return Stream.of(
                // containers,
                Graphics.layout(),
                Graphics.panel(),
                Graphics.list(),
                Graphics.tabPanel()
        ).map(b -> b.size(width, height).placeAt(x, y).build());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testAddComponent(IGraphicsLayout<IGraphicsComponent> layout) {
        IGraphicsComponent component = Graphics.label().build();
        layout.addComponent("o1", component);

        assertEquals("o1", component.getID());
        assert component.getParent() == layout;

        component = Graphics.label().build();
        String assignedId = layout.addComponent(component);
        assertEquals(assignedId, component.getID());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testGetComponent(IGraphicsLayout<IGraphicsComponent> layout) {
        IGraphicsComponent component = Graphics.label().build();
        layout.addComponent("o1", component);

        IGraphicsComponent got = layout.getComponent("o1");
        assertEquals(component, got);
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testRemoveComponent(IGraphicsLayout<IGraphicsComponent> layout) {
        IGraphicsComponent component = Graphics.label().build();
        layout.addComponent("o1", component);
        layout.getComponent("o1");
        IGraphicsComponent got = layout.removeComponent("o1");
        assertEquals(component, got);
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testGetContent(IGraphicsLayout<IGraphicsComponent> layout) {
        LayoutContent<IGraphicsComponent> layoutContent = new LayoutContent<>();
        IGraphicsComponent component = Graphics.label().build();
        layoutContent.putComponent("o1", component);
        layout.setContent(layoutContent);
        assert layout.getContent().getContent().entrySet()
                .stream().allMatch((value) -> layoutContent.get(value.getKey()) == value.getValue());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testClear(IGraphicsLayout<IGraphicsComponent> layout) {
        LayoutContent<IGraphicsComponent> layoutContent = new LayoutContent<>();
        IGraphicsComponent component = Graphics.label().build();
        layoutContent.putComponent("o1", component);
        layout.setContent(layoutContent);
        assertEquals(1, layout.size());
        layout.clear();
        assertEquals(0, layout.size());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testGetTooltip(IGraphicsLayout<IGraphicsComponent> layout) {
        IGraphicsListener<IGraphicsComponent> tooltip = Graphics.tooltip().build();
        layout.setTooltip(tooltip);
        assertEquals(tooltip, layout.getTooltip());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    public void testSetMenu(IGraphicsLayout<IGraphicsComponent> layout) {
        IContextMenuComponent<IContextMenuElement> menu = new GContextMenu<>(new ContextMenuList<>());
        layout.setActiveMenu(menu);
        assertEquals(menu, layout.getActiveMenu());
    }
}
