package com.github.stannismod.gext.components;

import com.github.stannismod.gext.BaseTest;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import org.junit.jupiter.api.Test;

public class GBackgroundTest extends BaseTest {

    @Test
    void testCenteringBasic() {
        GBackground component = Graphics.background().build();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(0, 0).size(0, 0).build();
        component.setParent(layout);
        assert component.getX() == 0 && component.getY() == 0;
    }

    @Test
    void testCentering() {
        GBackground component = Graphics.background().build();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(0, 0).size(10, 20).build();
        component.setParent(layout);
        assert component.getX() == 5 && component.getY() == 10;
    }

    @Test
    void testCenteringResize() {
        GBackground component = Graphics.background().build();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(0, 0).size(5, 5).build();
        component.setParent(layout);
        layout.setHeight(10);
        layout.setWidth(20);
        assert component.getX() == 5 && component.getY() == 10;
    }


}
