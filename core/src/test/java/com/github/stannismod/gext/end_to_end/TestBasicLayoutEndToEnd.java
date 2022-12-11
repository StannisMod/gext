package com.github.stannismod.gext.end_to_end;

import com.github.stannismod.gext.BaseTest;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.GButton;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.testapp.TestFramework;
import com.github.stannismod.gext.testapp.TestScaledResolution;
import de.gofabian.jfixture.FixtureExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@ExtendWith(FixtureExtension.class)
public class TestBasicLayoutEndToEnd extends BaseTest {

    public static TestFramework<IGraphicsComponent> framework(IGraphicsLayout<IGraphicsComponent> root) {
        IScaledResolution view = new TestScaledResolution(800, 600);
        return new TestFramework<>(root, view);
    }

    public static Stream<TestFramework<IGraphicsComponent>> frameworks() {
        return Stream.of(
                Graphics.layout(),
                Graphics.panel(),
                Graphics.list(),
                Graphics.tabPanel()
        ).map(l -> l.size(200, 200).build()).map(TestBasicLayoutEndToEnd::framework);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testBasicLayoutPureClickBroadcasting(TestFramework<IGraphicsComponent> framework) {
        final boolean[] clickDetector = new boolean[2];

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        GButton button1, button2;
        root.addComponent(button1 = Graphics.button()
                .action(b -> clickDetector[0] = !clickDetector[0])
                .size(50, 50)
                .placeAt(0, 0)
                .build());
        root.addComponent(button2 = Graphics.button()
                .action(b -> clickDetector[1] = !clickDetector[1])
                .size(50, 50)
                .placeAt(50, 50)
                .build());

        framework.getInput().click(button1.getAbsoluteX() + 1, button1.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertFalse(clickDetector[1]);

        framework.getInput().click(button2.getAbsoluteX() + 1, button2.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertTrue(clickDetector[1]);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testBasicLayoutRelationalClickBroadcasting(TestFramework<IGraphicsComponent> framework) {
        final boolean[] clickDetector = new boolean[2];

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        IGraphicsLayout<GButton> buttons = Graphics.<GButton>layout()
                .size(180, 180)
                .placeAt(10, 10)
                .build();
        root.addComponent(buttons);

        GButton button1, button2;
        buttons.addComponent(button1 = Graphics.button()
                .action(b -> clickDetector[0] = !clickDetector[0])
                .size(50, 50)
                .placeAt(0, 0)
                .build());
        buttons.addComponent(button2 = Graphics.button()
                .action(b -> clickDetector[1] = !clickDetector[1])
                .size(50, 50)
                .placeAt(100, 100)
                .build());

        framework.getInput().click(300, 300, 1);
        assertFalse(clickDetector[0]);
        assertFalse(clickDetector[1]);

        framework.getInput().click(button1.getAbsoluteX() + 1, button1.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertFalse(clickDetector[1]);

        framework.getInput().click(button2.getAbsoluteX() + 1, button2.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertTrue(clickDetector[1]);
    }
}
