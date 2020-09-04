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

package ru.quarter.gui.lib;

import org.apache.logging.log4j.Logger;
import ru.quarter.gui.lib.api.adapter.IFontRenderer;
import ru.quarter.gui.lib.api.adapter.IResource;
import ru.quarter.gui.lib.api.adapter.IResourceManager;
import ru.quarter.gui.lib.api.adapter.IScaledResolution;

public class GuiLib {

    private static GuiLib instance;

    private static GuiLib instance() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use GuiLib Core before initialization");
        }

        return instance;
    }

    private final IResourceManager manager;
    private final Logger logger;

    public GuiLib(IResourceManager manager, Logger logger) {
        this.manager = manager;
        this.logger = logger;
        set(this);
    }

    public static void set(GuiLib instance) {
        GuiLib.instance = instance;
    }

    public static IResourceManager getResourceManager() {
        return instance().manager;
    }

    public static void info(String msg) {
        instance().logger.info(msg);
    }

    public static void info(String msg, Object... objects) {
        instance().logger.info(String.format(msg, objects));
    }

    public static void warn(String msg) {
        instance().logger.warn(msg);
    }

    public static void warn(String msg, Object... objects) {
        instance().logger.warn(String.format(msg, objects));
    }

    public static void error(String msg) {
        instance().logger.error(msg);
    }

    public static void error(String msg, Throwable cause) {
        instance().logger.error(msg, cause);
    }

    public static void error(String msg, Object... objects) {
        instance().logger.error(String.format(msg, objects));
    }

    public static void error(Throwable cause, String msg, Object... objects) {
        error(String.format(msg, objects), cause);
    }

    public static IFontRenderer standardRenderer() {
        return getResourceManager().standardRenderer();
    }

    public static IResource resource(String domain, String name) {
        return resource(domain + ":" + name);
    }

    public static IResource resource(String name) {
        return getResourceManager().resource(name);
    }

    public static IScaledResolution scaled() {
        return getResourceManager().scaled();
    }
}
