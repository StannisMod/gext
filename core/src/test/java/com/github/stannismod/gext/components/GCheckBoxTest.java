package com.github.stannismod.gext.components;

import org.junit.jupiter.api.Test;

public class GCheckBoxTest extends GBasicTest{

    @Test
    public void testChecked(){
        GCheckBox checkBox = Graphics.checkbox().build();
        checkBox.setChecked(false);
        assert !checkBox.isChecked();

        checkBox.setChecked(true);
        assert checkBox.isChecked();
    }
}
