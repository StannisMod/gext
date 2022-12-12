package com.github.stannismod.gext.components;

import com.github.stannismod.gext.BaseTest;
import org.junit.jupiter.api.Test;

public class GTooltipTest extends BaseTest {

    @Test
    public void testTarget(){
        GTooltip tooltip = Graphics.tooltip().build();
        GButton button = Graphics.button().build();
        tooltip.setTarget(button);
        assert tooltip.getTarget() == button;
        tooltip.update();
    }

}
