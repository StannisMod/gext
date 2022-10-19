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

package com.github.stannismod.gext;

import com.github.stannismod.gext.api.IGraphicsComponent;
import com.github.stannismod.gext.api.adapter.IFontRenderer;
import com.github.stannismod.gext.api.adapter.IResourceManager;
import com.github.stannismod.gext.api.adapter.IScaledResolution;
import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.api.resource.IResourceProvider;
import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.IGraphicsEngine;
import com.github.stannismod.gext.resource.provider.AssetsResourceProvider;
import org.apache.logging.log4j.Logger;

public class GExt {

    public static final String VERSION = "@VERSION@";
    private static GExt instance;

    private static GExt instance() {
        if (instance == null) {
            throw new IllegalStateException("Trying to use GExt Core before initialization. The GExt constructor should be called first.");
        }

        return instance;
    }

    private final IResourceManager manager;
    private final IGraphicsEngine<?> engine;
    private IScaledResolution res;
    private final Logger logger;
    private final IResourceProvider assets = new AssetsResourceProvider("GExt");

    public GExt(IResourceManager manager, IGraphicsEngine<?> engine, Logger logger) {
        this.manager = manager;
        this.engine = engine;
        this.logger = logger;
        set(this);
    }

    public static void set(GExt instance) {
        GExt.instance = instance;
    }

    public static void onStart() {
        info(
            "\n////////////////////////////////////////////////////////////////////////////\n"
            + "////////////////////////////////////////////////////// " + VERSION + " ////\n"
            + "///////////------///////----------//////////////--//////////////////////////\n"
            + "/////////--/////---/////---/////////////////////--//////////////////////////\n"
            + "////////--//////////////--//////////////////////--//////////////////////////\n"
            + "////////--//////////////-------/////---//---//------////////////////////////\n"
            + "////////--/////------///-------///////----//////--//////////////////////////\n"
            + "////////--////////---///--/////////////--///////--//////////////////////////\n"
            + "/////////--//////---////---///////////----//////--///--/////////////////////\n"
            + "//////////--------//////----------//---//---/////-----//////////////////////\n"
            + "//////////////////////////////////////////////////////////// by Quarter ////\n"
            + "////////////////////////////////////////////////////////////////////////////"
        );
        instance().engine.init();
        onResize();
    }

    public static void onExit() {
        instance().engine.destroy();
    }

    public static IGraphicsEngine<?> getGraphicsEngine() {
        return instance().engine;
    }

    public static IResourceManager getResourceManager() {
        return instance().manager;
    }

    public static IResourceProvider getAssets() {
        return instance().assets;
    }

    public static void onResize() {
        instance().res = scaled();
    }

    public static IScaledResolution getView() {
        return instance().res;
    }

    public static void info(String msg) {
        instance().logger.info(msg);
    }

    public static void info(IGraphicsComponent source, String msg) {
        instance().logger.info(formatComponent(source), msg);
    }

    public static void info(String msg, Object... objects) {
        info(String.format(msg, objects));
    }

    public static void info(IGraphicsComponent source, String msg, Object... objects) {
        info(source, String.format(msg, objects));
    }

    public static void warn(String msg) {
        instance().logger.warn(msg);
    }

    public static void warn(IGraphicsComponent source, String msg) {
        warn(formatComponent(source) + " " + msg);
    }

    public static void warn(String msg, Object... objects) {
        warn(String.format(msg, objects));
    }

    public static void warn(IGraphicsComponent source, String msg, Object... objects) {
        warn(formatComponent(source), String.format(msg, objects));
    }

    public static void error(String msg) {
        instance().logger.error(msg);
    }

    public static void error(IGraphicsComponent source, String msg) {
        error(formatComponent(source) + msg);
    }

    public static void error(String msg, Throwable cause) {
        instance().logger.error(msg, cause);
    }

    public static void error(IGraphicsComponent source, String msg, Throwable cause) {
        error(formatComponent(source) + msg, cause);
    }

    public static void error(String msg, Object... objects) {
        error(String.format(msg, objects));
    }

    public static void error(IGraphicsComponent source, String msg, Object... objects) {
        error(formatComponent(source) + String.format(msg, objects));
    }

    public static void error(Throwable cause, String msg, Object... objects) {
        error(String.format(msg, objects), cause);
    }

    public static void error(IGraphicsComponent source, Throwable cause, String msg, Object... objects) {
        error(source, String.format(msg, objects), cause);
    }

    private static String formatComponent(IGraphicsComponent component) {
        return String.format("[%s, ID=%s]", component.getClass().getSimpleName(), component.getID());
    }

    public static IFontRenderer standardRenderer() {
        return getResourceManager().standardRenderer();
    }

    public static IResource resource(String domain, String name) {
        return resource(domain + ":" + name);
    }

    public static IResource resource(String name) {
        return getAssets().getResource(name);
    }

    public static ITexture texture(String domain, String name) {
        return resource(domain, name).toTexture();
    }

    public static ITexture texture(String name) {
        return resource(name).toTexture();
    }

    public static IScaledResolution scaled() {
        return getResourceManager().scaled();
    }
}
