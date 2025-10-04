/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.awt.Canvas;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.AWTSurfaceLock;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.MacOSXPeerInfo;
import org.lwjgl.opengl.PixelFormat;

abstract class MacOSXCanvasPeerInfo
extends MacOSXPeerInfo {
    private final AWTSurfaceLock awt_surface = new AWTSurfaceLock();

    protected MacOSXCanvasPeerInfo(PixelFormat pixel_format, ContextAttribs attribs, boolean support_pbuffer) throws LWJGLException {
        super(pixel_format, attribs, true, true, support_pbuffer, true);
    }

    protected void initHandle(Canvas component) throws LWJGLException {
        boolean allowCALayer = (Display.getParent() != null && !Display.isFullscreen() || component instanceof AWTGLCanvas) && this.awt_surface.isApplet(component) && LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 6);
        MacOSXCanvasPeerInfo.nInitHandle(this.awt_surface.lockAndGetHandle(component), this.getHandle(), allowCALayer);
    }

    private static native void nInitHandle(ByteBuffer var0, ByteBuffer var1, boolean var2) throws LWJGLException;

    protected void doUnlock() throws LWJGLException {
        this.awt_surface.unlock();
    }
}

