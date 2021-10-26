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

package com.github.quarter.gui.lib.components;

import com.github.quarter.gui.lib.api.IGraphicsComponent;
import com.github.quarter.gui.lib.components.container.GList;
import com.github.quarter.gui.lib.components.container.GPanel;
import com.github.quarter.gui.lib.components.container.GTabPanel;
import com.github.quarter.gui.lib.components.text.GTextBox;
import com.github.quarter.gui.lib.components.text.GTextPanel;

public class Graphics {

    public static GLabel.Builder<GLabel.Builder<?, GLabel>, GLabel> label() {
        return new GLabel.Builder<GLabel.Builder<?, GLabel>, GLabel>() {};
    }

    public static GLink.Builder<GLink.Builder<?, GLink>, GLink> link() {
        return new GLink.Builder<GLink.Builder<?, GLink>, GLink>() {};
    }

    public static GButton.Builder<GButton.Builder<?, GButton>, GButton> button() {
        return new GButton.Builder<GButton.Builder<?, GButton>, GButton>() {};
    }

    public static GImage.Builder<GImage.Builder<?, GImage>, GImage> image() {
        return new GImage.Builder<GImage.Builder<?, GImage>, GImage>() {};
    }

    public static GBackground.Builder<GBackground.Builder<?, GBackground>, GBackground> background() {
        return new GBackground.Builder<GBackground.Builder<?, GBackground>, GBackground>() {};
    }

    public static GTextPanel.Builder<GTextPanel.Builder<?, GTextPanel>, GTextPanel> textPanel() {
        return new GTextPanel.Builder<GTextPanel.Builder<?, GTextPanel>, GTextPanel>() {};
    }

    public static <T extends IGraphicsComponent> GPanel.Builder<GPanel.Builder<?, GPanel<T>>, GPanel<T>> panel() {
        return new GPanel.Builder<GPanel.Builder<?, GPanel<T>>, GPanel<T>>() {};
    }

    public static <K extends IGraphicsComponent, V extends IGraphicsComponent> GTabPanel.Builder<GTabPanel.Builder<?, K, V>, K, V> tabPanel() {
        return new GTabPanel.Builder<GTabPanel.Builder<?, K, V>, K, V>() {};
    }

    public static GTextBox.Builder<GTextBox.Builder<?, GTextBox>, GTextBox> textBox() {
        return new GTextBox.Builder<>() {};
    }

    public static <T extends IGraphicsComponent> GList.Builder<GList.Builder<?, GList<T>>, GList<T>> list() {
        return new GList.Builder<GList.Builder<?, GList<T>>, GList<T>>() {};
    }

    public static GCheckBox.Builder<GCheckBox.Builder<?, GCheckBox>, GCheckBox> checkbox() {
        return new GCheckBox.Builder<GCheckBox.Builder<?, GCheckBox>, GCheckBox>() {};
    }

    public static GProgressBar.Builder<GProgressBar.Builder<?, GProgressBar>, GProgressBar> progressBar() {
        return new GProgressBar.Builder<GProgressBar.Builder<?, GProgressBar>, GProgressBar>() {};
    }

    public static GVerticalScroll.Builder<GVerticalScroll.Builder<?, GVerticalScroll>, GVerticalScroll> verticalScroll() {
        return new GVerticalScroll.Builder<GVerticalScroll.Builder<?, GVerticalScroll>, GVerticalScroll>() {};
    }

    public static GRadioButton.Builder<GRadioButton.Builder<?, GRadioButton>, GRadioButton> radioButton() {
        return new GRadioButton.Builder<GRadioButton.Builder<?, GRadioButton>, GRadioButton>() {};
    }
}
