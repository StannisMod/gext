/*
 *  Copyright 2020 Stanislav Batalenkov
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.stannismod.gext;

import com.github.stannismod.gext.api.IGraphicsComponent;

/**
 * A set of optional(or experimental) features of GExt
 * that can be configured in runtime. To avoid undefined behaviour
 * please manage enabled features immediately after initializing GExt.
 * @since 1.5
 */
public enum Features {

    /**
     * <p>
     * An optimization feature that uses optimized
     * frame stack to avoid per-tick allocations.
     * </p>
     * This feature is enabled by default.
     * @since 1.5
     */
    // TODO Test
    FAST_FRAME_STACK(true),

    /**
     * <p>
     * An optimization feature that determines which components
     * are really visible(e.g. inside scissor frame) and calls
     * {@link IGraphicsComponent#render(int, int)} only on it.
     * </p>
     * <p>
     * Pay attention that this feature can make your GUI slower.
     * It can happen if you really don't use need to crop components
     * with {@code glScissor}. In this case disabling this feature
     * can increase your performance.
     * </p>
     * This feature is enabled by default.
     * @since 1.5
     */
    // TODO Test
    ONLY_VISIBLE_DRAWING(true),

    /**
     * By disabling this feature you can switch off {@code glScissor}
     * clipping in the GExt pipeline. But I think you don't want to do it.
     * @since 1.5
     */
    CLIPPING(true);

    private boolean enabled;

    Features(boolean enabledByDefault) {
        this.enabled = enabledByDefault;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void enable() {
        this.setEnabled(true);
    }

    public void disable() {
        this.setEnabled(false);
    }

    public boolean isEnabled() {
        return enabled;
    }
}
