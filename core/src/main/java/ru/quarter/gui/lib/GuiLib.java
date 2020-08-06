package ru.quarter.gui.lib;

import org.apache.logging.log4j.Logger;
import ru.quarter.gui.lib.api.adapter.IResourceManager;

public class GuiLib {

    private static GuiLib instance;

    private final Logger logger;

    public static GuiLib instance() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use GuiLib Core before initialization");
        }

        return instance;
    }

    private final IResourceManager manager;

    public GuiLib(IResourceManager manager, Logger logger) {
        this.manager = manager;
        this.logger = logger;
        instance = this;
    }

    public IResourceManager getResourceManager() {
        return manager;
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
}
