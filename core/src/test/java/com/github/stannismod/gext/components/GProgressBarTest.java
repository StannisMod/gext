package com.github.stannismod.gext.components;

import org.junit.jupiter.api.Test;

public class GProgressBarTest extends GBasicTest{
    @Test
    public void testProgress(){
        GProgressBar progressBar = Graphics.progressBar().build();
        progressBar.setProgress(50);
        assert progressBar.getProgress() == 50;
    }
}
