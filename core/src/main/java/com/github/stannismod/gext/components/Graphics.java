/*
 * Copyright 2020-2022 Stanislav Batalenkov
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

package com.github.stannismod.gext.components;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.components.container.BasicLayout;
import com.github.stannismod.gext.components.container.GList;
import com.github.stannismod.gext.components.container.GPanel;
import com.github.stannismod.gext.components.container.GTabPanel;
import com.github.stannismod.gext.components.text.GTextBox;
import com.github.stannismod.gext.components.text.GTextPanel;

public class Graphics {

    public static GLabel.Builder<GLabel.Builder<?, GLabel>, GLabel> label() {
        return new GLabel.Builder<GLabel.Builder<?, GLabel>, GLabel>() {
            @Override
            protected GLabel create() {
                return new GLabel(x, y, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding,
                        listeners, text, color, fontRenderer, scale, centered);
            }
        };
    }

    public static GLink.Builder<GLink.Builder<?, GLink>, GLink> link() {
        return new GLink.Builder<GLink.Builder<?, GLink>, GLink>() {
            @Override
            protected GLink create() {
                return new GLink(x, y, clippingEnabled, parent, binding, bound, alignment, xPadding, yPadding,
                        listeners, text, color, fontRenderer, scale, centered, activeColor, inactiveColor, uri);
            }
        };
    }

    public static GButton.Builder<GButton.Builder<?, GButton>, GButton> button() {
        return new GButton.Builder<GButton.Builder<?, GButton>, GButton>() {
            @Override
            protected GButton create() {
                return new GButton(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding,
                        yPadding, listeners, label, action);
            }
        };
    }

    public static GImage.Builder<GImage.Builder<?, GImage>, GImage> image() {
        return new GImage.Builder<GImage.Builder<?, GImage>, GImage>() {
            @Override
            protected GImage create() {
                return new GImage(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding,
                        yPadding, listeners, mapping);
            }
        };
    }

    public static GBackground.Builder<GBackground.Builder<?, GBackground>, GBackground> background() {
        return new GBackground.Builder<GBackground.Builder<?, GBackground>, GBackground>() {
            @Override
            protected GBackground create() {
                return new GBackground(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners, borderSize, cornerSize);
            }
        };
    }

    public static GTextPanel.Builder<GTextPanel.Builder<?, GTextPanel>, GTextPanel> textPanel() {
        return new GTextPanel.Builder<GTextPanel.Builder<?, GTextPanel>, GTextPanel>() {
            @Override
            protected GTextPanel create() {
                return new GTextPanel(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding,
                        yPadding, listeners, xOffset, yOffset, interval, text, textList, scale, title, titleScale,
                        backgroundDrawingEnabled, wrapContent, renderer, scrollHandler);
            }
        };
    }

    public static GTextBox.Builder<GTextBox.Builder<?, GTextBox>, GTextBox> textBox() {
        return new GTextBox.Builder<GTextBox.Builder<?, GTextBox>, GTextBox>() {
            @Override
            protected GTextBox create() {
                return new GTextBox(x, y, width, height, clippingEnabled, parent, binding, bound, alignment, xPadding,
                        yPadding, listeners, xOffset, yOffset, interval, text, textList, scale, title, titleScale,
                        backgroundDrawingEnabled, wrapContent, renderer, scrollHandler);
            }
        };
    }

    public static <T extends IGraphicsComponent> BasicLayout.Builder<BasicLayout.Builder<?, BasicLayout<T>>, BasicLayout<T>> layout() {
        return new BasicLayout.Builder<BasicLayout.Builder<?, BasicLayout<T>>, BasicLayout<T>>() {
            @Override
            protected BasicLayout<T> create() {
                return new BasicLayout<>(x, y, width, height, clippingEnabled, parent, binding,
                        bound, alignment, xPadding, yPadding, listeners, tooltip, selector);
            }
        };
    }

    public static <T extends IGraphicsComponent> GPanel.Builder<GPanel.Builder<?, GPanel<T>>, GPanel<T>> panel() {
        return new GPanel.Builder<GPanel.Builder<?, GPanel<T>>, GPanel<T>>() {
            @Override
            protected GPanel<T> create() {
                return new GPanel<>(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners, tooltip, selector, scrollHandler, xOffset, yOffset, wrapContent);
            }
        };
    }

    public static <T extends IGraphicsComponent> GList.Builder<GList.Builder<?, GList<T>>, GList<T>> list() {
        return new GList.Builder<GList.Builder<?, GList<T>>, GList<T>>() {
            @Override
            protected GList<T> create() {
                return new GList<>(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners, tooltip, selector, scrollHandler, xOffset, yOffset,
                        wrapContent, background, drawBackground, interval);
            }
        };
    }

    public static <K extends IGraphicsComponent, V extends IGraphicsComponent>
            GTabPanel.Builder<GTabPanel.Builder<?, GTabPanel<K, V>, K, V>, GTabPanel<K, V>, K, V> tabPanel() {
        return new GTabPanel.Builder<GTabPanel.Builder<?, GTabPanel<K, V>, K, V>, GTabPanel<K, V>, K, V>() {
            @Override
            protected GTabPanel<K, V> create() {
                return new GTabPanel<>(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners, tooltip, selector, scrollHandler, xOffset, yOffset,
                        wrapContent, background, drawBackground, interval, target, contentMap, deselectionEnabled);
            }
        };
    }

    public static GCheckBox.Builder<GCheckBox.Builder<?, GCheckBox>, GCheckBox> checkbox() {
        return new GCheckBox.Builder<GCheckBox.Builder<?, GCheckBox>, GCheckBox>() {
            @Override
            protected GCheckBox create() {
                return new GCheckBox(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners);
            }
        };
    }

    public static GProgressBar.Builder<GProgressBar.Builder<?, GProgressBar>, GProgressBar> progressBar() {
        return new GProgressBar.Builder<GProgressBar.Builder<?, GProgressBar>, GProgressBar>() {
            @Override
            protected GProgressBar create() {
                return new GProgressBar(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners);
            }
        };
    }

    public static GRadioButton.Builder<GRadioButton.Builder<?, GRadioButton>, GRadioButton> radioButton() {
        return new GRadioButton.Builder<GRadioButton.Builder<?, GRadioButton>, GRadioButton>() {
            @Override
            protected GRadioButton create() {
                return new GRadioButton(x, y, width, height, clippingEnabled, parent, binding, bound, alignment,
                        xPadding, yPadding, listeners, interval, checkBoxSize);
            }
        };
    }
}
