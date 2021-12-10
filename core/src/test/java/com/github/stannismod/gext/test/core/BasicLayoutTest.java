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

package com.github.stannismod.gext.test.core;

import com.github.stannismod.gext.components.Graphics;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ConstantConditions")
public class BasicLayoutTest extends BaseTest {

    @Test
    public void testAdd() {
        int[] ids = new int[1];
        create(root -> ids[0] = root.add(Graphics.label().text("Hello").build()))
                .init()
                .run(root -> assertEquals(ids[0], root.get(ids[0]).getID()));
    }
}
