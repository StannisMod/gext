package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.components.GSelector;
import com.github.stannismod.gext.components.Graphics;
import org.junit.jupiter.api.Test;

public class GTabPanelTest extends GListTest {

    @Test
    public void testGetSelected() {
        GSelector selector = new GSelector();
        GTabPanel<IGraphicsComponent, IGraphicsComponent> panel = Graphics.tabPanel().setSelector(selector).build();
        IGraphicsComponent component1 = Graphics.label().build();
        panel.addComponent("o1", component1);
        panel.select("o1");
        assert panel.isSelected();
        assertEquals("o1", panel.getSelectedId());
        assertEquals(component1, panel.getSelectedComponent());
    }

    @Test
    public void testOnSelected() {
        GSelector selector = new GSelector();
        IGraphicsLayout<IGraphicsComponent> target = Graphics.panel().build();
        GTabPanel<IGraphicsComponent, IGraphicsComponent> panel = Graphics.tabPanel().setSelector(selector).build();
        IGraphicsComponent component1 = Graphics.label().build();
        target.putComponent("o1", component1);
        panel.setTarget(target);
        panel.onSelect(component1);
        assertEquals("o1", panel.getSelectedId());

        IGraphicsComponent component2 = Graphics.label().build();
        target.putComponent("o2", component2);
        panel.onSelect(component2);
        assertEquals("o2", panel.getSelectedId());

        panel.onSelect(Graphics.label().build());
        assertNull(panel.getSelectedId());
    }

    @Test
    public void testDeselect() {
        GSelector selector = new GSelector();
        IGraphicsLayout<IGraphicsComponent> target = Graphics.panel().build();
        GTabPanel<IGraphicsComponent, IGraphicsComponent> panel = Graphics.tabPanel().setSelector(selector).build();
        IGraphicsComponent component1 = Graphics.label().build();
        panel.setTarget(target);
        target.addComponent("o1", component1);
        panel.onSelect(component1);
        assertEquals("o1", panel.getSelectedId());

        panel.onDeselect(component1);
        assertNull(panel.getSelectedId());
    }

    @Test
    public void testDeselectionEnabled() {
        GSelector selector = new GSelector();
        GTabPanel<IGraphicsComponent, IGraphicsComponent> panel = Graphics.tabPanel().setSelector(selector).build();
        panel.setDeselectionEnabled(true);
        assert panel.isDeselectionEnabled();

        panel.setDeselectionEnabled(false);
        assert !panel.isDeselectionEnabled();
    }
}
