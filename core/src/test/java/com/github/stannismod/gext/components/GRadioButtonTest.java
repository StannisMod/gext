package com.github.stannismod.gext.components;

import org.junit.jupiter.api.Test;

public class GRadioButtonTest extends GBasicTest {

    @Test
    public void testNumberOfButtons() {
        GRadioButton button = Graphics.radioButton().build();
        button.addLabel(Graphics.label().text("1").build());
        button.addLabel(Graphics.label().text("2").build());
        button.addLabel(Graphics.label().text("3").build());
        assert button.getNumberPoints() == 3;
    }

    @Test
    public void testSelected() {
        GRadioButton button = Graphics.radioButton().build();
        button.addLabel(Graphics.label().text("1").build());
        button.select(0);
        assert button.isSelected() && button.getSelected() == 0;

        button.unselect();
        assert !button.isSelected();
    }
}
