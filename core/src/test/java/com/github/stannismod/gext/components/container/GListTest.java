package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.components.GSelector;
import com.github.stannismod.gext.components.Graphics;
import org.junit.jupiter.api.Test;

public class GListTest extends BasicLayoutTest {

    @Test
    public void testAddComponent() {
        GList<IGraphicsComponent> list = Graphics.list().build();
        IGraphicsComponent component1 = Graphics.label().build();
        String get = list.addComponent("o1", component1);
        assertEquals("o1", get);
    }

    @Test
    public void testInterval() {
        GList<IGraphicsComponent> list = Graphics.list().interval(1).build();
        assert list.getInterval() == 1;
    }

    @Test
    public void testGetComponent() {
        GList<IGraphicsComponent> list = Graphics.list().build();
        IGraphicsComponent component1 = Graphics.label().build();
        list.addComponent("o1", component1);
        IGraphicsComponent get = list.getByIndex(0);
        assertEquals(component1, get);
    }

    @Test
    public void testGetSelectedComponent() {
        GList<IGraphicsComponent> list = Graphics.list().build();
        IGraphicsComponent component1 = Graphics.label().build();
        list.addComponent("o1", component1);
        IGraphicsComponent get = list.getSelectedElement();
        assertNull(get);

        GSelector selector = new GSelector();
        list = Graphics.list().setSelector(selector).build();

        IGraphicsComponent component2 = Graphics.label().build();
        list.addComponent("o1", component1);
        list.addComponent("o2", component2);
        selector.select("o1");
        get = list.getSelectedElement();
        assertEquals(component1, get);
    }

    @Test
    public void testRemoveComponent() {
        GList<IGraphicsComponent> list = Graphics.list().build();
        IGraphicsComponent component1 = Graphics.label().build();
        list.addComponent("o1", component1);
        IGraphicsComponent get = list.removeComponent("o1");
        assertEquals(component1, get);
    }

    @Test
    public void testRemoveByIndexComponent() {
        GList<IGraphicsComponent> list = Graphics.list().setSelector(new GSelector()).build();
        IGraphicsComponent component1 = Graphics.label().build();
        IGraphicsComponent component2 = Graphics.label().build();
        list.addComponent("o1", component1);
        list.addComponent("o2", component2);
        IGraphicsComponent get = list.removeByIndex(0);
        assertEquals(component1, get);

        assertThrows(IndexOutOfBoundsException.class, () -> list.removeByIndex(1));

        list.addComponent("o1", component1);
        get = list.removeByIndex(1);
        assertEquals(component1, get);
    }
}
