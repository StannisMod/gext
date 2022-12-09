package com.github.stannismod.gext.components;


import com.github.stannismod.gext.BaseTest;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IListener;
import com.github.stannismod.gext.utils.Alignment;
import com.github.stannismod.gext.utils.Bound;
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

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testVisible(IGraphicsComponent component) {
        component.setVisibility(false);
        assert !component.visible();

        component.setVisibility(true);
        assert component.visible();
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetAbsoluteX(IGraphicsComponent component) {
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(component);
        assert component.getAbsoluteX() - component.getX() == layout.getAbsoluteX();
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetAbsoluteY(IGraphicsComponent component) {
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(component);
        assert component.getAbsoluteY() - component.getY() == layout.getAbsoluteY();
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetWidth(IGraphicsComponent component) {
        component.setWidth(50);
        assert component.getWidth() == 50;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetHeight(IGraphicsComponent component) {
        component.setHeight(50);
        assert component.getHeight() == 50;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetFrame(IGraphicsComponent component) {
        Rectangle frame = component.getFrame();
        component.setX(1);
        component.setY(1);
        component.setHeight(50);
        component.setWidth(50);
        frame = component.getFrame();
        assert frame.x == 1 && frame.y == 1;
        assert frame.height == 50 && frame.width == 50;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetParent(IGraphicsComponent component) {
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        component.setParent(layout);
        assert component.getParent() == layout;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetRoot(IGraphicsComponent component) {
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        component.setParent(layout);
        assert component.getRoot() == layout;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetAlignment(IGraphicsComponent component) {
        component.setAlignment(Alignment.TOP);
        assert component.getAlignment() == Alignment.TOP;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetXPadding(IGraphicsComponent component) {
        component.setPaddings(3, 98);
        assert component.getXPadding() == 3;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetYPadding(IGraphicsComponent component) {
        component.setPaddings(3, 98);
        assert component.getYPadding() == 98;
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testListener(IGraphicsComponent component) {
        boolean[] probe = new boolean[1];
        IListener listener = c -> probe[0] = true;
        component.addListener(listener);
        component.markDirty();
        component.tryUpdate();
        assert probe[0];
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testClippingEnabled(IGraphicsComponent component) {
        component.setClippingEnabled(false);
        assert !component.clippingEnabled();

        component.setClippingEnabled(true);
        assert component.clippingEnabled();
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testInit(IGraphicsComponent component) {
        IGraphicsLayout<IGraphicsComponent> layout = Graphics.panel().placeAt(2, 2).build();
        layout.addComponent(component);
        GBasic anotherComponent = Graphics.label().text("Ha-ha").placeAt(0, 0).build();
        component.setBinding(anotherComponent, Bound.LEFT_BOTTOM);
        assertThrowsExactly(IllegalArgumentException.class, component::init);
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testNeedUpdate(IGraphicsComponent component) {
        assert !component.needUpdate();
        component.markDirty();
        assert component.needUpdate();
    }

    @ParameterizedTest
    @MethodSource("componentsToTest")
    void testGetDepth(IGraphicsComponent component) {
        component.setDepth(97);
        assert component.getDepth() == 97;
    }
}
