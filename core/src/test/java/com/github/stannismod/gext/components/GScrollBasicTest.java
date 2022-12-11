package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IScrollable;
import org.junit.jupiter.api.Test;

public class GScrollBasicTest extends GBasicTest{

    @Test
    public void testGetTarget() {
        GScrollBasic component = Controls.verticalScroll().build();
        assertThrows(IllegalStateException.class, component::init);

        IScrollable target = Graphics.panel().placeAt(0,0).size(10,10).build();
        component.setTarget(target);
        assert component.getTarget() == target;

    }
}
