package ru.quarter.gui.lib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.io.Closeable;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * A driver of glFramebuffer targeted to texture
 * @deprecated Was decided to use Minecraft Framebuffer driver, so this one doesn't work
 */
@Deprecated
public class Framebuffer implements Closeable {

    private final int framebuffer;
    private final int data;

    public Framebuffer(int width, int height) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        width = width * res.getScaleFactor();
        height = height * res.getScaleFactor();

        framebuffer = glGenFramebuffers();
        data = glGenTextures();
        attach();

        glBindTexture(GL_TEXTURE_2D, data);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (FloatBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, data, 0);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (FloatBuffer) null);

        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, data, 0);
//        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_INT_24_8, (IntBuffer) null);
//        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, data, 0);

        detach();
    }

    public void attach() {
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new RuntimeException("Trying to load not built framebuffer!");
        }
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
    }

    public void detach() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void clear() {
        glClear(0);
    }

    public void bindAsTexture() {
        glBindTexture(GL_TEXTURE_2D, data);
    }

    @Override
    public void close() {
        glDeleteFramebuffers(framebuffer);
    }
}
