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

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.IRootLayout;
import com.github.stannismod.gext.test.core.appearance.TestRootLayout;
import com.github.stannismod.gext.test.core.appearance.adapter.MockResourceManager;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.util.function.Consumer;

public class BaseTest extends Assertions {

    @BeforeAll
    public static void libInit() {
        new GExt(new MockResourceManager(), LogManager.getLogger("test"));
    }

    public TestRootLayout create(Consumer<IRootLayout> init) {
        return new TestRootLayout(init);
    }
}
