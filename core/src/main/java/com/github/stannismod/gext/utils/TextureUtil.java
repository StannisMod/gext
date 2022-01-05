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

import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.resource.DynamicTexture;
import com.github.stannismod.gext.resource.GLAllocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

public class TextureUtil {

    private static final IntBuffer DATA_BUFFER = GLAllocation.createDirectIntBuffer(4194304);
    public static final DynamicTexture MISSING_TEXTURE = new DynamicTexture(16, 16);
    public static final int[] MISSING_TEXTURE_DATA;
    private static final float[] COLOR_GAMMAS;
    private static final int[] MIPMAP_BUFFER;

    public TextureUtil() {
    }

    private static float getColorGamma(int p_188543_0_) {
        return COLOR_GAMMAS[p_188543_0_ & 255];
    }

    public static int uploadTextureImage(int textureId, BufferedImage texture) {
        return uploadTextureImageAllocate(textureId, texture, false, false);
    }

    public static void uploadTexture(int textureId, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
        bindTexture(textureId);
        uploadTextureSub(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
    }

    public static int[][] generateMipmapData(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
        int[][] aint = new int[p_147949_0_ + 1][];
        aint[0] = p_147949_2_[0];
        if (p_147949_0_ > 0) {
            boolean flag = false;

            int l1;
            for(l1 = 0; l1 < p_147949_2_.length; ++l1) {
                if (p_147949_2_[0][l1] >> 24 == 0) {
                    flag = true;
                    break;
                }
            }

            for(l1 = 1; l1 <= p_147949_0_; ++l1) {
                if (p_147949_2_[l1] != null) {
                    aint[l1] = p_147949_2_[l1];
                } else {
                    int[] aint1 = aint[l1 - 1];
                    int[] aint2 = new int[aint1.length >> 2];
                    int j = p_147949_1_ >> l1;
                    if (j > 0) {
                        int k = aint2.length / j;
                        int l = j << 1;

                        for(int i1 = 0; i1 < j; ++i1) {
                            for(int j1 = 0; j1 < k; ++j1) {
                                int k1 = 2 * (i1 + j1 * l);
                                aint2[i1 + j1 * j] = blendColors(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
                            }
                        }
                    }

                    aint[l1] = aint2;
                }
            }
        }

        return aint;
    }

    private static int blendColors(int r, int g, int b, int a, boolean p_147943_4_) {
        if (p_147943_4_) {
            MIPMAP_BUFFER[0] = r;
            MIPMAP_BUFFER[1] = g;
            MIPMAP_BUFFER[2] = b;
            MIPMAP_BUFFER[3] = a;
            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            int i2;
            for(i2 = 0; i2 < 4; ++i2) {
                if (MIPMAP_BUFFER[i2] >> 24 != 0) {
                    f += getColorGamma(MIPMAP_BUFFER[i2] >> 24);
                    f1 += getColorGamma(MIPMAP_BUFFER[i2] >> 16);
                    f2 += getColorGamma(MIPMAP_BUFFER[i2] >> 8);
                    f3 += getColorGamma(MIPMAP_BUFFER[i2] >> 0);
                }
            }

            f /= 4.0F;
            f1 /= 4.0F;
            f2 /= 4.0F;
            f3 /= 4.0F;
            i2 = (int)(Math.pow((double)f, 0.45454545454545453D) * 255.0D);
            int j1 = (int)(Math.pow((double)f1, 0.45454545454545453D) * 255.0D);
            int k1 = (int)(Math.pow((double)f2, 0.45454545454545453D) * 255.0D);
            int l1 = (int)(Math.pow((double)f3, 0.45454545454545453D) * 255.0D);
            if (i2 < 96) {
                i2 = 0;
            }

            return i2 << 24 | j1 << 16 | k1 << 8 | l1;
        } else {
            int i = blendColorComponent(r, g, b, a, 24);
            int j = blendColorComponent(r, g, b, a, 16);
            int k = blendColorComponent(r, g, b, a, 8);
            int l = blendColorComponent(r, g, b, a, 0);
            return i << 24 | j << 16 | k << 8 | l;
        }
    }

    private static int blendColorComponent(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
        float f = getColorGamma(p_147944_0_ >> p_147944_4_);
        float f1 = getColorGamma(p_147944_1_ >> p_147944_4_);
        float f2 = getColorGamma(p_147944_2_ >> p_147944_4_);
        float f3 = getColorGamma(p_147944_3_ >> p_147944_4_);
        float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D)));
        return (int)((double)f4 * 255.0D);
    }

    public static void uploadTextureMipmap(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
        for(int i = 0; i < p_147955_0_.length; ++i) {
            int[] aint = p_147955_0_[i];
            if (p_147955_1_ >> i <= 0 || p_147955_2_ >> i <= 0) {
                break;
            }

            uploadTextureSub(i, aint, p_147955_1_ >> i, p_147955_2_ >> i, p_147955_3_ >> i, p_147955_4_ >> i, p_147955_5_, p_147955_6_, p_147955_0_.length > 1);
        }

    }

    private static void uploadTextureSub(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
        int i = 4194304 / p_147947_2_;
        setTextureBlurMipmap(p_147947_6_, p_147947_8_);
        setTextureClamped(p_147947_7_);

        int l;
        for(int j = 0; j < p_147947_2_ * p_147947_3_; j += p_147947_2_ * l) {
            int k = j / p_147947_2_;
            l = Math.min(i, p_147947_3_ - k);
            int i1 = p_147947_2_ * l;
            copyToBufferPos(p_147947_1_, j, i1);
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

    public static int uploadTextureImageSub(int textureId, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
        bindTexture(textureId);
        uploadTextureImageSubImpl(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
        return textureId;
    }

    private static void uploadTextureImageSubImpl(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
        int i = p_110993_0_.getWidth();
        int j = p_110993_0_.getHeight();
        int k = 4194304 / i;
        int[] aint = new int[k * i];
        setTextureBlurred(p_110993_3_);
        setTextureClamped(p_110993_4_);

        for(int l = 0; l < i * j; l += i * k) {
            int i1 = l / i;
            int j1 = Math.min(k, j - i1);
            int k1 = i * j1;
            p_110993_0_.getRGB(0, i1, i, j1, aint, 0, i);
            copyToBuffer(aint, k1);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, p_110993_1_, p_110993_2_ + i1, i, j1, 32993, 33639, DATA_BUFFER);
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

    private static void setTextureBlurred(boolean p_147951_0_) {
        setTextureBlurMipmap(p_147951_0_, false);
    }

    private static void setTextureBlurMipmap(boolean p_147954_0_, boolean p_147954_1_) {
        if (p_147954_0_) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10241, p_147954_1_ ? 9987 : 9729);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10240, 9729);
        } else {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10241, p_147954_1_ ? 9986 : 9728);
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

    public static int[] readImageData(IResource iresource) throws IOException {
        int[] aint1;
        try {
            BufferedImage bufferedimage = readBufferedImage(iresource.getInputStream());
            int i = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();
            int[] aint = new int[i * j];
            bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
            aint1 = aint;
        } finally {
            try {
                iresource.close();
            } catch (IOException ignored) {}
        }

        return aint1;
    }

    public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
        BufferedImage bufferedimage;
        try {
            bufferedimage = ImageIO.read(imageStream);
        } finally {
            try {
                imageStream.close();
            } catch (IOException ignored) {}
        }

        return bufferedimage;
    }

    public static int[] updateAnaglyph(int[] p_110985_0_) {
        int[] aint = new int[p_110985_0_.length];

        for(int i = 0; i < p_110985_0_.length; ++i) {
            aint[i] = anaglyphColor(p_110985_0_[i]);
        }

        return aint;
    }

    public static int anaglyphColor(int p_177054_0_) {
        int i = p_177054_0_ >> 24 & 255;
        int j = p_177054_0_ >> 16 & 255;
        int k = p_177054_0_ >> 8 & 255;
        int l = p_177054_0_ & 255;
        int i1 = (j * 30 + k * 59 + l * 11) / 100;
        int j1 = (j * 30 + k * 70) / 100;
        int k1 = (j * 30 + l * 70) / 100;
        return i << 24 | i1 << 16 | j1 << 8 | k1;
    }

    public static void processPixelValues(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
        int[] aint = new int[p_147953_1_];
        int i = p_147953_2_ / 2;

        for(int j = 0; j < i; ++j) {
            System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
            System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
            System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
        }

    }

    static {
        MISSING_TEXTURE_DATA = MISSING_TEXTURE.getTextureData();
        int i = -16777216;
        int j = -524040;
        int[] aint = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
        int[] aint1 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
        int k = aint.length;

        int i1;
        for(i1 = 0; i1 < 16; ++i1) {
            System.arraycopy(i1 < k ? aint : aint1, 0, MISSING_TEXTURE_DATA, 16 * i1, k);
            System.arraycopy(i1 < k ? aint1 : aint, 0, MISSING_TEXTURE_DATA, 16 * i1 + k, k);
        }

        MISSING_TEXTURE.updateDynamicTexture();
        COLOR_GAMMAS = new float[256];

        for(i1 = 0; i1 < COLOR_GAMMAS.length; ++i1) {
            COLOR_GAMMAS[i1] = (float)Math.pow((double)((float)i1 / 255.0F), 2.2D);
        }

        MIPMAP_BUFFER = new int[4];
    }
}