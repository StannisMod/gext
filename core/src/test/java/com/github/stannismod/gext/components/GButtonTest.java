package com.github.stannismod.gext.components;

import com.github.stannismod.gext.BaseTest;
import org.junit.jupiter.api.Test;

public class GButtonTest extends BaseTest {

    @Test
    public void testHasAction() {
        GButton button = Graphics.button().build();
        assert !button.hasAction(0);

        boolean [] active = new boolean[1];
        button.setAction(0, (c)-> active[0] = true);
        assert button.hasAction(0);

        button.setAction(1, null);
        assert !button.hasAction(1);
    }

    @Test
    public void testHasAnyAction() {
        GButton button = Graphics.button().build();
        assert !button.hasAnyAction();

        button.setAction(1, null);
        assert !button.hasAnyAction();

        boolean [] active = new boolean[1];
        button.setAction(0, (c)-> active[0] = true);
        assert button.hasAnyAction();
    }

    @Test
    public void testHasLabel() {
        GButton button = Graphics.button().build();
        assert !button.hasLabel();

        button = Graphics.button().label("label").build();
        assert button.hasLabel();
    }

    @Test
    public void testGetLabel() {
        GButton button = Graphics.button().build();
        assert button.getLabel() == null;

        GLabel label = Graphics.label().text("label").build();
        button = Graphics.button().label(label).build();
        assert button.getLabel() == label;
    }

    @Test
    public void testSwitchOn() {
        GButton button = Graphics.button().build();
        button.switchOn();
        assert button.isPressed();
    }

    @Test
    public void testSwitchOff() {
        GButton button = Graphics.button().build();
        button.switchOn();
        button.switchOff();
        assert !button.isPressed();
    }

    @Test
    public void testUpdate() {
        GButton button = Graphics.button().build();
        boolean [] active = new boolean[1];
        button.setAction(0, (c)-> active[0] = true);
        button.switchOn();
        button.update();
        assert !button.needUpdate();
    }


}
