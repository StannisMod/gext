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

/**
 * A set of optional(or experimental) features of GExt
 * that can be configured in runtime. To avoid undefined behaviour
 * please manage enabled features immediately after initializing GExt.
 * @since 1.5
 */
public enum Feature {

    /**
     * An optimization feature that uses optimized
     * frame stack to avoid per-tick allocations
     * @since 1.5
     */
    FAST_FRAME_STACK(true);

    private boolean enabled;

    Feature(boolean enabledByDefault) {
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
