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

package ru.quarter.gui.lib.components;

import ru.quarter.gui.lib.components.container.GPanel;

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

    public static GPanel.Builder panel() {
        return new GPanel.Builder();
    }
}
