/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.DeferredTexture;
import org.newdawn.slick.opengl.EmptyImageData;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

public class InternalTextureLoader {
    private static final InternalTextureLoader loader = new InternalTextureLoader();
    private HashMap texturesLinear = new HashMap();
    private HashMap texturesNearest = new HashMap();
    private int dstPixelFormat = 32856;
    private boolean deferred;

    public static InternalTextureLoader get() {
        return loader;
    }

    private InternalTextureLoader() {
    }

    public void setDeferredLoading(boolean deferred) {
        this.deferred = deferred;
    }

    public boolean isDeferredLoading() {
        return this.deferred;
    }

    public void clear(String name) {
        this.texturesLinear.remove(name);
        this.texturesNearest.remove(name);
    }

    public void clear() {
        this.texturesLinear.clear();
        this.texturesNearest.clear();
    }

    public void set16BitMode() {
        this.dstPixelFormat = 32859;
    }

    public static int createTextureID() {
        IntBuffer tmp = InternalTextureLoader.createIntBuffer(1);
        GL11.glGenTextures(tmp);
        return tmp.get(0);
    }

    public Texture getTexture(File source, boolean flipped, int filter) throws IOException {
        String resourceName = source.getAbsolutePath();
        FileInputStream in = new FileInputStream(source);
        return this.getTexture(in, resourceName, flipped, filter, null);
    }

    public Texture getTexture(File source, boolean flipped, int filter, int[] transparent) throws IOException {
        String resourceName = source.getAbsolutePath();
        FileInputStream in = new FileInputStream(source);
        return this.getTexture(in, resourceName, flipped, filter, transparent);
    }

    public Texture getTexture(String resourceName, boolean flipped, int filter) throws IOException {
        InputStream in = ResourceLoader.getResourceAsStream(resourceName);
        return this.getTexture(in, resourceName, flipped, filter, null);
    }

    public Texture getTexture(String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
        InputStream in = ResourceLoader.getResourceAsStream(resourceName);
        return this.getTexture(in, resourceName, flipped, filter, transparent);
    }

    public Texture getTexture(InputStream in, String resourceName, boolean flipped, int filter) throws IOException {
        return this.getTexture(in, resourceName, flipped, filter, null);
    }

    public TextureImpl getTexture(InputStream in, String resourceName, boolean flipped, int filter, int[] transparent) throws IOException {
        TextureImpl tex;
        SoftReference ref;
        if (this.deferred) {
            return new DeferredTexture(in, resourceName, flipped, filter, transparent);
        }
        HashMap hash = this.texturesLinear;
        if (filter == 9728) {
            hash = this.texturesNearest;
        }
        String resName = resourceName;
        if (transparent != null) {
            resName = resName + ":" + transparent[0] + ":" + transparent[1] + ":" + transparent[2];
        }
        if ((ref = (SoftReference)hash.get(resName = resName + ":" + flipped)) != null) {
            tex = (TextureImpl)ref.get();
            if (tex != null) {
                return tex;
            }
            hash.remove(resName);
        }
        try {
            GL11.glGetError();
        }
        catch (NullPointerException e) {
            throw new RuntimeException("Image based resources must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
        tex = this.getTexture(in, resourceName, 3553, filter, filter, flipped, transparent);
        tex.setCacheName(resName);
        hash.put(resName, new SoftReference<TextureImpl>(tex));
        return tex;
    }

    private TextureImpl getTexture(InputStream in, String resourceName, int target, int magFilter, int minFilter, boolean flipped, int[] transparent) throws IOException {
        int textureID = InternalTextureLoader.createTextureID();
        TextureImpl texture = new TextureImpl(resourceName, target, textureID);
        GL11.glBindTexture(target, textureID);
        LoadableImageData imageData = ImageDataFactory.getImageDataFor(resourceName);
        ByteBuffer textureBuffer = imageData.loadImage(new BufferedInputStream(in), flipped, transparent);
        int width = imageData.getWidth();
        int height = imageData.getHeight();
        boolean hasAlpha = imageData.getDepth() == 32;
        texture.setTextureWidth(imageData.getTexWidth());
        texture.setTextureHeight(imageData.getTexHeight());
        int texWidth = texture.getTextureWidth();
        int texHeight = texture.getTextureHeight();
        IntBuffer temp = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(3379, temp);
        int max = temp.get(0);
        if (texWidth > max || texHeight > max) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        int srcPixelFormat = hasAlpha ? 6408 : 6407;
        int componentCount = hasAlpha ? 4 : 3;
        texture.setWidth(width);
        texture.setHeight(height);
        texture.setAlpha(hasAlpha);
        GL11.glTexParameteri(target, 10241, minFilter);
        GL11.glTexParameteri(target, 10240, magFilter);
        GL11.glTexImage2D(target, 0, this.dstPixelFormat, InternalTextureLoader.get2Fold(width), InternalTextureLoader.get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }

    public Texture createTexture(int width, int height) throws IOException {
        return this.createTexture(width, height, 9728);
    }

    public Texture createTexture(int width, int height, int filter) throws IOException {
        EmptyImageData ds = new EmptyImageData(width, height);
        return this.getTexture(ds, filter);
    }

    public Texture getTexture(ImageData dataSource, int filter) throws IOException {
        int target = 3553;
        int textureID = InternalTextureLoader.createTextureID();
        TextureImpl texture = new TextureImpl("generated:" + dataSource, target, textureID);
        int minFilter = filter;
        int magFilter = filter;
        boolean flipped = false;
        GL11.glBindTexture(target, textureID);
        ByteBuffer textureBuffer = dataSource.getImageBufferData();
        int width = dataSource.getWidth();
        int height = dataSource.getHeight();
        boolean hasAlpha = dataSource.getDepth() == 32;
        texture.setTextureWidth(dataSource.getTexWidth());
        texture.setTextureHeight(dataSource.getTexHeight());
        int texWidth = texture.getTextureWidth();
        int texHeight = texture.getTextureHeight();
        int srcPixelFormat = hasAlpha ? 6408 : 6407;
        int componentCount = hasAlpha ? 4 : 3;
        texture.setWidth(width);
        texture.setHeight(height);
        texture.setAlpha(hasAlpha);
        IntBuffer temp = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(3379, temp);
        int max = temp.get(0);
        if (texWidth > max || texHeight > max) {
            throw new IOException("Attempt to allocate a texture to big for the current hardware");
        }
        GL11.glTexParameteri(target, 10241, minFilter);
        GL11.glTexParameteri(target, 10240, magFilter);
        GL11.glTexImage2D(target, 0, this.dstPixelFormat, InternalTextureLoader.get2Fold(width), InternalTextureLoader.get2Fold(height), 0, srcPixelFormat, 5121, textureBuffer);
        return texture;
    }

    public static int get2Fold(int fold) {
        int ret;
        for (ret = 2; ret < fold; ret *= 2) {
        }
        return ret;
    }

    public static IntBuffer createIntBuffer(int size) {
        ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
        temp.order(ByteOrder.nativeOrder());
        return temp.asIntBuffer();
    }
}

