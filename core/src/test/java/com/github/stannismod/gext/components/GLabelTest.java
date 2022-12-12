package com.github.stannismod.gext.components;

import org.junit.jupiter.api.Test;

import java.util.Objects;

public class GLabelTest extends GBasicTest {

    @Test
    public void testGetText() {
        GLabel label = Graphics.label().text("TEST").build();
        assert Objects.equals(label.getText(), "TEST");
    }
}
