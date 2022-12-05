package com.github.stannismod.gext.components;


import com.github.stannismod.gext.BaseTest;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.utils.Alignment;
import com.github.stannismod.gext.utils.Bound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

public class GBasicTest extends BaseTest {

    private GBasic gBasicCreator() {
        return gBasicCreator(0, 0);
    }

    private GBasic gBasicCreator(int x, int y) {
        return Graphics.label().text("Ha-ha").placeAt(x, y).build();
    }

    static Stream<IGraphicsComponent> componentsToTest() {
        final int x = 10;
        final int y = 10;
        final int width = 10;
        final int height = 10;

        return Stream.of(
                // single components
                Graphics.label(),
                Graphics.link(),
                Graphics.button(),
                Graphics.background(),
                Graphics.textBox(),
                Graphics.textPanel(),
                Graphics.checkbox(),
                Graphics.image(),
                Graphics.progressBar(),
                Graphics.radioButton(),
                // containers,
                Graphics.layout(),
                Graphics.panel(),
                Graphics.list(),
                Graphics.tabPanel()
        ).map(b -> b.size(width, height).placeAt(x, y).build());
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetId(IGraphicsComponent component) {
        component.setID("1");
        assert component.getID().equals("1");

        component.setID("2");
        assert component.getID().equals("2");
    }

    @Test
    void testVisible() {
        GBasic gBasic = gBasicCreator();
        gBasic.setVisibility(false);
        assert !gBasic.visible();

        gBasic.setVisibility(true);
        assert gBasic.visible();
    }

    @Test
    void testGetX() {
        GBasic gBasic = gBasicCreator();
        assert gBasic.getX() == 0;

        gBasic.setX(1);
        assert gBasic.getX() == 1;
    }

    @Test
    void testGetY() {
        GBasic gBasic = gBasicCreator();
        assert gBasic.getY() == 0;

        gBasic.setY(1);
        assert gBasic.getY() == 1;
    }

    @Test
    void testGetAbsoluteX() {
        GBasic gBasic = gBasicCreator(1, 1);
        assert gBasic.getX() == 1 && gBasic.getAbsoluteX() == 1;

        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(gBasic);
        assert gBasic.getX() == 1 && gBasic.getAbsoluteX() == 1 + 2;

    }

    @Test
    void testGetAbsoluteY() {
        GBasic gBasic = gBasicCreator(1, 1);
        assert gBasic.getY() == 1 && gBasic.getAbsoluteY() == 1;

        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(gBasic);
        assert gBasic.getY() == 1 && gBasic.getAbsoluteY() == 1 + 2;
    }

    @Test
    void testGetWidth() {
        GBasic gBasic = gBasicCreator();
        gBasic.setWidth(50);
        assert gBasic.getWidth() == 50;
    }

    @Test
    void testGetHeight() {
        GBasic gBasic = gBasicCreator();
        gBasic.setHeight(50);
        assert gBasic.getHeight() == 50;
    }

    @Test
    void testGetFrame() {
        GBasic gBasic = gBasicCreator();
        Rectangle frame = gBasic.getFrame();
        assert frame.x == 0 && frame.y == 0;

        gBasic.setX(1);
        gBasic.setY(1);
        gBasic.setHeight(50);
        gBasic.setWidth(50);
        frame = gBasic.getFrame();
        assert frame.x == 1 && frame.y == 1;
        assert frame.height == 50 && frame.width == 50;
    }

    @Test
    void testGetAbsoluteFrame() {
        GBasic gBasic = gBasicCreator(1, 1);
        Rectangle frame = gBasic.getAbsoluteFrame();
        assert frame.x == 1 && frame.y == 1;

        final int width = frame.width;
        final int height = frame.height;

        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(gBasic);
        frame = gBasic.getAbsoluteFrame();
        assert frame.x == 1 + 2 && frame.y == 1 + 2;
        assert frame.height == height && frame.width == width;
    }

    @Test
    void testGetParent() {
        GBasic gBasic = gBasicCreator();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        gBasic.setParent(layout);
        assert gBasic.getParent() == layout;
    }

    @Test
    void testGetRoot() {
        GBasic gBasic = gBasicCreator();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        gBasic.setParent(layout);
        assert gBasic.getRoot() == layout;
    }

    @Test
    void testGetAlignment() {
        GBasic gBasic = gBasicCreator();
        gBasic.setAlignment(Alignment.TOP);
        assert gBasic.getAlignment() == Alignment.TOP;
    }

    @Test
    void testGetXPadding() {
        GBasic gBasic = gBasicCreator();
        gBasic.setPaddings(3, 98);
        assert gBasic.getXPadding() == 3;
    }

    @Test
    void testGetYPadding() {
        GBasic gBasic = gBasicCreator();
        gBasic.setPaddings(3, 98);
        assert gBasic.getYPadding() == 98;
    }

    @Test
    void testListener() {
        GBasic gBasic = gBasicCreator();
        boolean[] probe = new boolean[1];
        IListener listener = c -> probe[0] = true;
        gBasic.addListener(listener);
        gBasic.markDirty();
        gBasic.tryUpdate();
        assert probe[0];
    }

    @Test
    void testClippingEnabled() {
        GBasic gBasic = gBasicCreator();
        gBasic.setClippingEnabled(false);
        assert !gBasic.clippingEnabled();

        gBasic.setClippingEnabled(true);
        assert gBasic.clippingEnabled();
    }

    @Test
    void testInit() {
        GBasic gBasic = gBasicCreator();
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(gBasic);
        GBasic notGBasic = gBasicCreator();
        gBasic.setBinding(notGBasic, Bound.LEFT_BOTTOM);
        assertThrowsExactly(IllegalArgumentException.class, gBasic::init);
    }

    @Test
    void testNeedUpdate() {
        GBasic gBasic = gBasicCreator();
        assert !gBasic.needUpdate();

        gBasic.markDirty();
        assert gBasic.needUpdate();
    }

    @Test
    void testGetDepth() {
        GBasic gBasic = gBasicCreator();
        gBasic.setDepth(97);
        assert gBasic.getDepth() == 97;
    }
}
