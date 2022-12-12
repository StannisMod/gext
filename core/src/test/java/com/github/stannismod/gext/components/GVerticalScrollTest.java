package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IScrollable;
import com.github.stannismod.gext.components.container.GPanel;
import org.junit.jupiter.api.Test;

public class GVerticalScrollTest extends GScrollBasicTest{
    @Test
    public void testSetParent(){
        GVerticalScroll scroll = Controls.verticalScroll().barWidth(10).build();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).size(10,10).build();
        layout.addComponent(scroll);
        assert scroll.getX() == 0 && scroll.getY() == 0;
        assert scroll.getWidth() == 10 && scroll.getHeight() == 10;
    }

    @Test
    public void testScrollTarget(){
        GVerticalScroll component = Controls.verticalScroll().build();
        GPanel<IGraphicsComponent> target = Graphics.panel().placeAt(0,0).size(10,10).build();
        GButton button1 = Graphics.button().placeAt(5,5).size(10,10).build();
        target.addComponent(button1);
        component.setTarget((IScrollable) target);
        assert component.shouldRenderBar();
    }
}
