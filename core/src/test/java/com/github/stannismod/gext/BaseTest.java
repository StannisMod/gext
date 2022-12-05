/*
 * Copyright 2022 Stanislav Batalenkov
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

package com.github.stannismod.gext;

import com.github.stannismod.gext.engine.DeprecatedGraphicsEngine;
import com.github.stannismod.gext.testapp.LWJGL3Keyboard;
import com.github.stannismod.gext.testapp.TestResourceManager;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest extends Assertions {

    @BeforeAll
    public static void constructGExt() {
        new GExt(new TestResourceManager(-1), new DeprecatedGraphicsEngine(), new LWJGL3Keyboard(0), LogManager.getLogger("GExt Test Env"));
        GExt.onStart();
    }
}
