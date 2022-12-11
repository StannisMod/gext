package com.github.stannismod.gext.testapp;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.IGraphicsLayout;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.testapp.input.TestInput;

public class TestFramework<T extends IGraphicsComponent> {

    private final TestInput input;
    private final IGraphicsLayout<T> root;
    private final IScaledResolution view;

    public TestFramework(IGraphicsLayout<T> root, IScaledResolution view) {
        // yes, this is test-unfriendly. But this is test.
        this.input = new TestInput(root);
        this.root = root;
        this.view = view;
    }

    public TestInput getInput() {
        return input;
    }

    public IScaledResolution getView() {
        return view;
    }

    public IGraphicsLayout<T> getRoot() {
        return root;
    }
}
