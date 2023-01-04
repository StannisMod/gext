package com.github.stannismod.gext.end_to_end;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.components.GBasic;
import com.github.stannismod.gext.components.GButton;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.testapp.TestException;
import com.github.stannismod.gext.testapp.TestFramework;
import de.gofabian.jfixture.FixtureExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(FixtureExtension.class)
public class TestBasicLayout extends BaseFrameworkTest {

    @Mock
    private GBasic testComponentObj;

    private AutoCloseable mockitoObj;

    @BeforeEach
    public void prepareMocks() {
        mockitoObj = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void closeMocks() throws Exception {
        mockitoObj.close();
    }

    public static Stream<TestFramework<IGraphicsComponent>> frameworks() {
        return Stream.of(
                Graphics.layout(),
                Graphics.panel(),
                Graphics.list(),
                Graphics.tabPanel()
        ).map(l -> l.size(200, 200).build()).map(BaseFrameworkTest::framework);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testPureClickBroadcasting(TestFramework<IGraphicsComponent> framework) {
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

        // click on the first button
        framework.getInput().click(button1.getAbsoluteX() + 1, button1.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertFalse(clickDetector[1]);

        // click on the second button
        framework.getInput().click(button2.getAbsoluteX() + 1, button2.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertTrue(clickDetector[1]);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testRelationalClickBroadcasting(TestFramework<IGraphicsComponent> framework) {
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

        // outside the frame
        framework.getInput().click(300, 300, 1);
        assertFalse(clickDetector[0]);
        assertFalse(clickDetector[1]);

        // click on the first button
        framework.getInput().click(button1.getAbsoluteX() + 1, button1.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertFalse(clickDetector[1]);

        // click on the second button
        framework.getInput().click(button2.getAbsoluteX() + 1, button2.getAbsoluteY() + 1, 1);
        assertTrue(clickDetector[0]);
        assertTrue(clickDetector[1]);
    }

    @BeforeEach
    public void prepareTestComponent() {
        when(testComponentObj.getID()).thenReturn("0");
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testUpdateBroadcasting(TestFramework<IGraphicsComponent> framework) {
        doThrow(new TestException()).when(testComponentObj).update();

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        root.addComponent(testComponentObj.getID(), testComponentObj);

        assertDoesNotThrow(root::update);

        when(testComponentObj.needUpdate()).thenReturn(true);

        assertThrowsExactly(TestException.class, root::update);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testInitBroadcasting(TestFramework<IGraphicsComponent> framework) {
        doThrow(new TestException()).when(testComponentObj).init();

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        root.addComponent(testComponentObj.getID(), testComponentObj);

        assertThrowsExactly(TestException.class, root::init);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testOnClosedBroadcasting(TestFramework<IGraphicsComponent> framework) {
        doThrow(new TestException()).when(testComponentObj).onClosed();

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        root.addComponent(testComponentObj.getID(), testComponentObj);

        assertThrowsExactly(TestException.class, root::onClosed);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testCheckUpdatesBroadcasting(TestFramework<IGraphicsComponent> framework) {
        doThrow(new TestException()).when(testComponentObj).checkUpdates();

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        root.addComponent(testComponentObj.getID(), testComponentObj);

        assertThrowsExactly(TestException.class, root::checkUpdates);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testAbsoluteHoverBroadcasting(TestFramework<IGraphicsComponent> framework) {
        final boolean[] hoverDetector = new boolean[1];

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();

        IGraphicsComponent component = spy(
                Graphics.button()
                        .size(50, 50)
                        .placeAt(0, 0)
                        .build());

        doAnswer(ans -> {
            hoverDetector[0] = true;
            return null;
        }).when(component).onHover(anyInt(), anyInt());

        root.addComponent(component);

        // click on the first button
        framework.getInput().moveMouse(component.getAbsoluteX() + 1, component.getAbsoluteY() + 1, 1);
        framework.getInput().update();
        framework.runDrawing();
        assertTrue(hoverDetector[0]);
    }

    @ParameterizedTest
    @MethodSource("frameworks")
    public void testRelationalHoverBroadcasting(TestFramework<IGraphicsComponent> framework) {
        final boolean[] hoverDetector = new boolean[1];

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        IGraphicsLayout<IGraphicsComponent> buttons = Graphics.layout()
                .size(180, 180)
                .placeAt(10, 10)
                .build();
        root.addComponent(buttons);

        IGraphicsComponent component = spy(
                Graphics.button()
                        .size(50, 50)
                        .placeAt(0, 0)
                        .build());

        doAnswer(ans -> {
            hoverDetector[0] = true;
            return null;
        }).when(component).onHover(anyInt(), anyInt());

        buttons.addComponent(component);

        // move outside
        framework.getInput().moveMouse(300, 300, 1);
        framework.getInput().update();
        framework.runDrawing();

        assertFalse(hoverDetector[0]);

        // move to the component
        framework.getInput().moveMouse(component.getAbsoluteX() + 1, component.getAbsoluteY() + 1, 1);
        framework.getInput().update();
        framework.runDrawing();

        assertTrue(hoverDetector[0]);
    }
}
