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

package com.github.stannismod.gext.utils;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.engine.GLAllocation;
import com.github.stannismod.gext.resource.TextureImpl;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

public final class TextureUtil {

    private static final IntBuffer DATA_BUFFER = GLAllocation.createDirectIntBuffer(4194304);
    private static final float[] COLOR_GAMMAS;
    private static final int[] MIPMAP_BUFFER;

    private static TextureImpl MISSING_TEXTURE;

    public static ITexture getMissingTexture() {
        if (MISSING_TEXTURE == null) {
            return GExt.texture("gext", "textures/missing.png");
        }
        return MISSING_TEXTURE;
    }
    
    private static float getColorGamma(int color) {
        return COLOR_GAMMAS[color & 255];
    }

    public static int uploadTextureImage(int textureId, BufferedImage texture) {
        return uploadTextureImageAllocate(textureId, texture, false, false);
    }

    public static void uploadTexture(int textureId, int[] textureData, int width, int height) {
        bindTexture(textureId);
        uploadTextureSub(0, textureData, width, height, 0, 0, false, false, false);
    }

    private static void uploadTextureSub(int p_147947_0_, int[] textureData, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean clamped, boolean p_147947_8_) {
        int i = 4194304 / p_147947_2_;
        //setTextureClamped(clamped);

        int l;
        for(int j = 0; j < p_147947_2_ * p_147947_3_; j += p_147947_2_ * l) {
            int k = j / p_147947_2_;
            l = Math.min(i, p_147947_3_ - k);
            int i1 = p_147947_2_ * l;
            copyToBufferPos(textureData, j, i1);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, p_147947_0_, p_147947_4_, p_147947_5_ + k, p_147947_2_, l, 32993, 33639, DATA_BUFFER);
        }

    }

    public static int uploadTextureImageAllocate(int textureId, BufferedImage texture, boolean blur, boolean clamp) {
        allocateTexture(textureId, texture.getWidth(), texture.getHeight());
        return uploadTextureImageSub(textureId, texture, 0, 0, blur, clamp);
    }

    public static void allocateTexture(int textureId, int width, int height) {
        allocateTextureImpl(textureId, 0, width, height);
    }

    public static void allocateTextureImpl(int glTextureId, int mipmapLevels, int width, int height) {
        // TODO
//        Class var4 = SplashProgress.class;
//        synchronized(SplashProgress.class) {
//            deleteTexture(glTextureId);
//            bindTexture(glTextureId);
//        }

        GL11.glDeleteTextures(glTextureId);
        bindTexture(glTextureId);

        if (mipmapLevels >= 0) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 33085, mipmapLevels);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 33082, 0);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 33083, mipmapLevels);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 34049, 0.0F);
        }

        for(int i = 0; i <= mipmapLevels; ++i) {
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, i, 6408, width >> i, height >> i, 0, 32993, 33639, (IntBuffer)null);
        }
    }

    public static int uploadTextureImageSub(int textureId, BufferedImage img, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
        bindTexture(textureId);
        uploadTextureImageSubImpl(img, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
        return textureId;
    }

    private static void uploadTextureImageSubImpl(BufferedImage img, int xOffset, int yOffset, boolean blur, boolean clamp) {
        int i = img.getWidth();
        int j = img.getHeight();
        int k = 4194304 / i;
        int[] aint = new int[k * i];
        setTextureBlurred(blur);
        setTextureClamped(clamp);

        for(int l = 0; l < i * j; l += i * k) {
            int i1 = l / i;
            int j1 = Math.min(k, j - i1);
            int k1 = i * j1;
            img.getRGB(0, i1, i, j1, aint, 0, i);
            copyToBuffer(aint, k1);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, xOffset, yOffset + i1, i, j1, 32993, 33639, DATA_BUFFER);
        }

    }

    private static void setTextureClamped(boolean p_110997_0_) {
        if (p_110997_0_) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10242, 10496);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10243, 10496);
        } else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10242, 10497);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10243, 10497);
        }

    }

    private static void setTextureBlurred(boolean blur) {
        setTextureBlurMipmap(blur, false);
    }

    private static void setTextureBlurMipmap(boolean alpha, boolean mipmap) {
        if (alpha) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10241, mipmap ? 9987 : 9729);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10240, 9729);
        } else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10241, mipmap ? 9986 : 9728);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10240, 9728);
        }

    }

    private static void copyToBuffer(int[] p_110990_0_, int p_110990_1_) {
        copyToBufferPos(p_110990_0_, 0, p_110990_1_);
    }

    private static void copyToBufferPos(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
        int[] aint = p_110994_0_;

        DATA_BUFFER.clear();
        DATA_BUFFER.put(aint, p_110994_1_, p_110994_2_);
        DATA_BUFFER.position(0).limit(p_110994_2_);
    }

    static void bindTexture(int textureId) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

//    public static int[] readImageData(IResource iresource) throws IOException {
//        int[] aint1;
//        try {
//            BufferedImage bufferedimage = readBufferedImage(iresource.getInputStream());
//            int i = bufferedimage.getWidth();
//            int j = bufferedimage.getHeight();
//            int[] aint = new int[i * j];
//            bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
//            aint1 = aint;
//        } finally {
//            try {
//                iresource.close();
//            } catch (IOException ignored) {}
//        }
//
//        return aint1;
//    }
//
//    public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
//        BufferedImage bufferedimage;
//        try {
//            bufferedimage = ImageIO.read(imageStream);
//        } finally {
//            try {
//                imageStream.close();
//            } catch (IOException ignored) {}
//        }
//
//        return bufferedimage;
//    }
//
//    public static int[] updateAnaglyph(int[] p_110985_0_) {
//        int[] aint = new int[p_110985_0_.length];
//
//        for(int i = 0; i < p_110985_0_.length; ++i) {
//            aint[i] = anaglyphColor(p_110985_0_[i]);
//        }
//
//        return aint;
//    }
//
//    public static int anaglyphColor(int p_177054_0_) {
//        int i = p_177054_0_ >> 24 & 255;
//        int j = p_177054_0_ >> 16 & 255;
//        int k = p_177054_0_ >> 8 & 255;
//        int l = p_177054_0_ & 255;
//        int i1 = (j * 30 + k * 59 + l * 11) / 100;
//        int j1 = (j * 30 + k * 70) / 100;
//        int k1 = (j * 30 + l * 70) / 100;
//        return i << 24 | i1 << 16 | j1 << 8 | k1;
//    }
//
//    public static void processPixelValues(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
//        int[] aint = new int[p_147953_1_];
//        int i = p_147953_2_ / 2;
//
//        for(int j = 0; j < i; ++j) {
//            System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
//            System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
//            System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
//        }
//
//    }

    static {
        COLOR_GAMMAS = new float[256];

        for(int i1 = 0; i1 < COLOR_GAMMAS.length; ++i1) {
            COLOR_GAMMAS[i1] = (float)Math.pow((double)((float)i1 / 255.0F), 2.2D);
        }

        MIPMAP_BUFFER = new int[4];
    }
}