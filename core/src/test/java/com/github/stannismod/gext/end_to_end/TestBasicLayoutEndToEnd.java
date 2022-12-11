package com.github.stannismod.gext.end_to_end;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.Graphics;
import com.github.stannismod.gext.testapp.TestFramework;
import com.github.stannismod.gext.testapp.TestScaledResolution;
import de.gofabian.jfixture.FixtureExtension;
import de.gofabian.jfixture.api.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FixtureExtension.class)
public class TestBasicLayoutEndToEnd extends Assertions {

    @Fixture
    public TestFramework<IGraphicsComponent> framework() {
        IGraphicsLayout<IGraphicsComponent> root = Graphics.layout().size(200, 200).build();
        IScaledResolution view = new TestScaledResolution(800, 600);
        return new TestFramework<>(root, view);
    }

    @Test
    public void testBasicLayoutClickBroadcasting(TestFramework<IGraphicsComponent> framework) {
        final boolean[] clickDetector = new boolean[2];

        IGraphicsLayout<IGraphicsComponent> root = framework.getRoot();
        root.addComponent(Graphics.button()
                .action(b -> clickDetector[0] = true)
                .size(50, 50)
                .placeAt(0, 0)
                .build());
        root.addComponent(Graphics.button()
                .action(b -> clickDetector[1] = true)
                .size(50, 50)
                .placeAt(50, 50)
                .build());

        framework.getInput().setMouse(40, 40);
        framework.getInput().pressMouse(1);
        framework.getInput().update();
        framework.getInput().releaseMouse(1);
        framework.getInput().update();

        assertTrue(clickDetector[0]);
    }
}
