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

public class Graphics {

    public static GLabel.Builder label() {
        return new GLabel.Builder();
    }

    public static GLink.Builder link() {
        return new GLink.Builder();
    }

    public static GButton.Builder button() {
        return new GButton.Builder();
    }

    public static GImage.Builder image() {
        return new GImage.Builder();
    }

    public static GBackground.Builder background() {
        return new GBackground.Builder();
    }

    public static GTextPanel.Builder textPanel() {
        return new GTextPanel.Builder();
    }

    public static <T extends IGraphicsComponent> GPanel.Builder<GPanel<T>> panel() {
        return new GPanel.Builder<GPanel<T>>() {};
    }

    public static <K extends IGraphicsComponent, V extends IGraphicsComponent> GTabPanel.Builder<K, V> tabPanel() {
        return new GTabPanel.Builder<>();
    }

    public static <T extends IGraphicsComponent> GList.Builder<GList<T>> list() {
        return new GList.Builder<GList<T>>() {};
    }

    public static GCheckBox.Builder checkbox() {
        return new GCheckBox.Builder();
    }

    public static GProgressBar.Builder progressBar() {
        return new GProgressBar.Builder();
    }

    public static GVerticalScroll.Builder verticalScroll() {
        return new GVerticalScroll.Builder();
    }

    public static GRadioButton.Builder radioButton() {
        return new GRadioButton.Builder();
    }
}
