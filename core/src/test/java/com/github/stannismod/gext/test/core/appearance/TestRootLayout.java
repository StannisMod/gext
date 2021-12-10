/*
 * Copyright 2020 Stanislav Batalenkov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.stannismod.gext.test.core.appearance;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.IRootLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.components.container.BasicLayout;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TestRootLayout implements IRootLayout {

    private final BasicLayout<IGraphicsComponent> layout;
    private final IScaledResolution res;
    private final Consumer<IRootLayout> init;

    private boolean initialClick;
    private int mouseX;
    private int mouseY;

    public TestRootLayout(Consumer<IRootLayout> init) {
        this.res = GExt.scaled();
        this.init = init;
        this.layout = new BasicLayout<>(0, 0, res.getScaledWidth(), res.getScaledHeight());
        GExt.onResize();
    }

    @Override
    public @NotNull IGraphicsLayout<IGraphicsComponent> layout() {
        return layout;
    }

    @Override
    public void initLayout() {
        init.accept(this);
    }

    public TestRootLayout init() {
        initLayout();
        return this;
    }

    protected void keyType(char typedChar, int keyCode) {
        layout.onKeyPressed(typedChar, keyCode);
    }

    protected void mouseClick(int mouseX, int mouseY, int mouseButton) {
        layout.onMousePressed(mouseX, mouseY, mouseButton);
        initialClick = true;
    }

    protected void mouseRelease(int mouseX, int mouseY, int mouseButton) {
        layout.onMouseReleased(mouseX, mouseY, mouseButton);
        initialClick = false;
    }

    protected void mouseDrag(int mouseStartX, int mouseStartY, int mouseEndX, int mouseEndY, int mouseButton) {
        // TODO
        mouseClick(mouseStartX, mouseStartY, mouseButton);
        //layout.onMouseDragged(mouseX, mouseY);
    }

    protected void mouseMove() {
        // TODO
    }

    protected void mouseScroll(int mouseX, int mouseY, int amountScrolled) {
        layout.onMouseScrolled(mouseX, mouseY, amountScrolled);
    }

//    public void handleMouseInput() throws IOException {
//        int mouseX = Mouse.getEventX() / res.getScaleFactor();
//        int mouseY = (res.getViewHeight() - Mouse.getEventY()) / res.getScaleFactor();
//        int mouseButton = Mouse.getEventButton();
//        layout.onMouseInput(mouseX, mouseY, mouseButton);
//
//        int scrolled = Mouse.getEventDWheel();
//        if (scrolled != 0) {
//            layout.onMouseScrolled(mouseX, mouseY, scrolled);
//        } else {
//            if (!Mouse.getEventButtonState()) {
//                if (initialClick) {
//                    layout.onMouseDragged(mouseX, mouseY, mouseButton, mouseX - this.mouseX, mouseY - this.mouseY);
//                } else {
//                    layout.onMouseMoved(mouseX, mouseY);
//                }
//            }
//        }
//    }

    public void onResize(int w, int h) {
        layout.onResize(w, h);
    }

    public void onGuiClosed() {
        layout.onClosed();
    }

    public void run(Consumer<TestRootLayout> actions) {
        actions.accept(this);
    }
}
