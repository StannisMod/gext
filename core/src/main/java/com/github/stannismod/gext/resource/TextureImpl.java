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

package com.github.stannismod.gext.resource;

import com.github.stannismod.gext.GExt;
import com.github.stannismod.gext.api.resource.IResource;
import com.github.stannismod.gext.api.resource.IResourceProvider;
import com.github.stannismod.gext.api.resource.ITexture;
import com.github.stannismod.gext.utils.TextureUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.*;

public class TextureImpl extends ResourceImpl implements ITexture {

    protected int glTextureId = -1;
    protected boolean blur;
    protected boolean mipmap;
    protected boolean blurLast;
    protected boolean mipmapLast;
    private int[] textureData;

    public TextureImpl(final IResourceProvider provider, final String domain, final String path, final boolean load) {
        super(provider, domain, path);
        if (load) {
            load();
        }
    }

    public TextureImpl(final IResourceProvider provider, final String domain, final String path) {
        this(provider, domain, path, true);
    }

    public TextureImpl(final IResource resource) {
        this(resource.getProvider(), resource.getDomain(), resource.getPath());
    }

    private void load() {
        try (InputStream is = getInputStream()) {
            BufferedImage img = ImageIO.read(is);
            int width = img.getWidth();
            int height = img.getHeight();

//            TextureUtil.allocateTexture(this.getGlTextureId(), width, height);
//
//            textureData = new int[img.getWidth() * img.getHeight()];
//            img.getRGB(0, 0, width, height, textureData, 0, width);
//
//            TextureUtil.uploadTexture(getGlTextureId(), textureData, width, height);
            TextureUtil.uploadTextureImage(getGlTextureId(), img);

//            bind();
//
//            IntBuffer buf = GLAllocation.createDirectIntBuffer(dynamicTextureData.length);
//            buf.put(dynamicTextureData);
////            for(int h = 0; h < img.getHeight(); h++) {
////                for(int w = 0; w < img.getWidth(); w++) {
////                    int pixel = dynamicTextureData[h * img.getWidth() + w];
////
////                    buf.put((byte) ((pixel >> 16) & 0xFF));
////                    buf.put((byte) ((pixel >> 8) & 0xFF));
////                    buf.put((byte) (pixel & 0xFF));
////                    buf.put((byte) ((pixel >> 24) & 0xFF));
////                }
////            }
//            buf.flip();
//
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
//
//            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV, buf);
//            glGenerateMipmap(GL_TEXTURE_2D);
//
//            TextureUtil.uploadTexture(getGlTextureId(), dynamicTextureData, width, height);

//            bind();

            System.out.println("Uploaded texture");
        } catch (IOException e) {
            GExt.error("Unable to load texture", e);
        }
    }

    @Override
    public void bind() {
//        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, getGlTextureId());
    }

//    public void setBlurMipmapDirect(boolean blurIn, boolean mipmapIn) {
//        this.blur = blurIn;
//        this.mipmap = mipmapIn;
//        int i;
//        short j;
//        if (blurIn) {
//            i = mipmapIn ? 9987 : 9729;
//            j = 9729;
//        } else {
//            i = mipmapIn ? 9986 : 9728;
//            j = 9728;
//        }
//
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10241, i);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, 10240, j);
//    }
//
//    public void setBlurMipmap(boolean blurIn, boolean mipmapIn) {
//        this.blurLast = this.blur;
//        this.mipmapLast = this.mipmap;
//        this.setBlurMipmapDirect(blurIn, mipmapIn);
//    }
//
//    public void restoreLastBlurMipmap() {
//        this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
//    }

    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = glGenTextures();
        }

        return this.glTextureId;
    }

    public void deleteGlTexture() {
        if (this.glTextureId != -1) {
            glDeleteTextures(this.glTextureId);
            this.glTextureId = -1;
        }
    }
}
