package ru.quarter.gui.lib.components;

import ru.quarter.gui.lib.api.IGraphicsComponent;

public class Tooltip extends GTooltip {

    private int labelId;

    @Override
    public void initTooltip() {
        labelId = addComponent(0, Graphics.label().text("Primary text").placeAt(0, 0).build(), GLink.class);
    }

    @Override
    public void listen(IGraphicsComponent target) {
        
    }
}
