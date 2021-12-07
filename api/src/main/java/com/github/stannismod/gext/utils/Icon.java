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

package com.github.stannismod.gext.utils;

public enum Icon {

        APPROVE(0, 0),
        DECLINE(1, 0),
        CHECKBOX(2, 0),
        RADIO_BUTTON(3, 0),
        // TODO Add this to main sprite
        RIGHT_ARROW(4, 0),
        LEFT_ARROW(5, 0);

        final int nx;
        final int ny;

        Icon(int nx, int ny) {
            this.nx = nx;
            this.ny = ny;
        }
    }