package com.github.stannismod.gext.components.container;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.components.Controls;
import com.github.stannismod.gext.components.GButton;
import com.github.stannismod.gext.components.GScrollBasic;
import com.github.stannismod.gext.components.Graphics;
import org.junit.jupiter.api.Test;

public class GPanelTest extends BasicLayoutTest{

    @Test
    public void testScrollHandler() {
        GPanel<IGraphicsComponent> panel = Graphics.panel().build();
        GScrollBasic component = Controls.verticalScroll().build();
        panel.setScrollHandler(component);
        assertEquals(component, panel.getScrollHandler());
    }

    @Test
    public void testScrollVertical(){
        GPanel<IGraphicsComponent> panel = Graphics.panel().placeAt(0,0).size(10,10).build();
        GButton button1 = Graphics.button().placeAt(5,5).size(10,10).build();
        panel.addComponent(button1);
        panel.setScrollVertical(1);
        assert panel.getScrollVertical() == 1;
    }

    @Test
    public void testScrollHorizontal(){
        GPanel<IGraphicsComponent> panel = Graphics.panel().placeAt(0,0).size(10,10).build();
        GButton button1 = Graphics.button().placeAt(5,5).size(10,10).build();
        panel.addComponent(button1);
        panel.setScrollHorizontal(1);
        assert panel.getScrollHorizontal() == 1;
    }
    @Test
    public void testContentWidthAndHeight(){
        GPanel<IGraphicsComponent> panel = Graphics.panel().placeAt(0,0).size(10,10).build();
        GButton button1 = Graphics.button().placeAt(5,5).size(10,10).build();
        panel.addComponent(button1);
        assert panel.getContentWidth() == 15;
        assert panel.getContentHeight() == 15;
    }

}
