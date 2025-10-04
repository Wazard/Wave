/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.apple.eawt.Application
 *  com.apple.eawt.ApplicationAdapter
 *  com.apple.eawt.ApplicationEvent
 *  com.apple.eawt.ApplicationListener
 */
package org.lwjgl.opengl;

import com.apple.eawt.Application;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Robot;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.AWTUtil;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayImplementation;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.DrawableGL;
import org.lwjgl.opengl.DrawableLWJGL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.KeyboardEventQueue;
import org.lwjgl.opengl.MacOSXCanvasListener;
import org.lwjgl.opengl.MacOSXContextImplementation;
import org.lwjgl.opengl.MacOSXDisplayPeerInfo;
import org.lwjgl.opengl.MacOSXFrame;
import org.lwjgl.opengl.MacOSXMouseEventQueue;
import org.lwjgl.opengl.MacOSXPbufferPeerInfo;
import org.lwjgl.opengl.PeerInfo;
import org.lwjgl.opengl.PixelFormat;

final class MacOSXDisplay
implements DisplayImplementation {
    private static final int PBUFFER_HANDLE_SIZE = 24;
    private static final int GAMMA_LENGTH = 256;
    private MacOSXCanvasListener canvas_listener;
    private MacOSXFrame frame;
    private Canvas canvas;
    private Robot robot;
    private MacOSXMouseEventQueue mouse_queue;
    private KeyboardEventQueue keyboard_queue;
    private java.awt.DisplayMode requested_mode;
    private boolean close_requested;
    private static final IntBuffer current_viewport = BufferUtils.createIntBuffer(16);

    MacOSXDisplay() {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Object>(){

                @Override
                public Object run() throws Exception {
                    Application.getApplication().addApplicationListener((ApplicationListener)new ApplicationAdapter(){

                        public void handleQuit(ApplicationEvent event) {
                            MacOSXDisplay.this.doHandleQuit();
                        }
                    });
                    return null;
                }
            });
        }
        catch (Throwable e) {
            LWJGLUtil.log("Failed to register quit handler: " + e.getMessage());
        }
    }

    public void createWindow(DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
        boolean fullscreen = Display.isFullscreen();
        this.hideUI(fullscreen);
        this.close_requested = false;
        try {
            if (parent == null) {
                this.frame = new MacOSXFrame(mode, this.requested_mode, fullscreen, x, y);
                this.canvas = this.frame.getCanvas();
            } else {
                this.frame = null;
                this.canvas = parent;
            }
            this.canvas_listener = new MacOSXCanvasListener(this.canvas);
            this.robot = AWTUtil.createRobot(this.canvas);
        }
        catch (LWJGLException e) {
            this.destroyWindow();
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doHandleQuit() {
        MacOSXDisplay macOSXDisplay = this;
        synchronized (macOSXDisplay) {
            this.close_requested = true;
        }
    }

    public void destroyWindow() {
        if (this.canvas_listener != null) {
            this.canvas_listener.disableListeners();
            this.canvas_listener = null;
        }
        if (this.frame != null) {
            AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    if (MacOSXFrame.getDevice().getFullScreenWindow() == MacOSXDisplay.this.frame) {
                        MacOSXFrame.getDevice().setFullScreenWindow(null);
                    }
                    return null;
                }
            });
            if (this.frame.isDisplayable()) {
                this.frame.dispose();
            }
            this.frame = null;
        }
        this.hideUI(false);
    }

    public int getGammaRampLength() {
        return 256;
    }

    public native void setGammaRamp(FloatBuffer var1) throws LWJGLException;

    public String getAdapter() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    private static boolean equals(java.awt.DisplayMode awt_mode, DisplayMode mode) {
        return awt_mode.getWidth() == mode.getWidth() && awt_mode.getHeight() == mode.getHeight() && awt_mode.getBitDepth() == mode.getBitsPerPixel() && awt_mode.getRefreshRate() == mode.getFrequency();
    }

    public void switchDisplayMode(DisplayMode mode) throws LWJGLException {
        java.awt.DisplayMode[] awt_modes;
        for (java.awt.DisplayMode awt_mode : awt_modes = MacOSXFrame.getDevice().getDisplayModes()) {
            if (!MacOSXDisplay.equals(awt_mode, mode)) continue;
            this.requested_mode = awt_mode;
            return;
        }
        throw new LWJGLException(mode + " is not supported");
    }

    public void resetDisplayMode() {
        if (MacOSXFrame.getDevice().getFullScreenWindow() != null) {
            MacOSXFrame.getDevice().setFullScreenWindow(null);
        }
        this.requested_mode = null;
        this.restoreGamma();
    }

    private native void restoreGamma();

    private static DisplayMode createLWJGLDisplayMode(java.awt.DisplayMode awt_mode) {
        int awt_bit_depth = awt_mode.getBitDepth();
        int awt_refresh_rate = awt_mode.getRefreshRate();
        int bit_depth = awt_bit_depth != -1 ? awt_bit_depth : 32;
        int refresh_rate = awt_refresh_rate != 0 ? awt_refresh_rate : 0;
        return new DisplayMode(awt_mode.getWidth(), awt_mode.getHeight(), bit_depth, refresh_rate);
    }

    public DisplayMode init() throws LWJGLException {
        return MacOSXDisplay.createLWJGLDisplayMode(MacOSXFrame.getDevice().getDisplayMode());
    }

    public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
        java.awt.DisplayMode[] awt_modes = MacOSXFrame.getDevice().getDisplayModes();
        ArrayList<DisplayMode> modes = new ArrayList<DisplayMode>();
        for (java.awt.DisplayMode awt_mode : awt_modes) {
            if (awt_mode.getBitDepth() < 16) continue;
            modes.add(MacOSXDisplay.createLWJGLDisplayMode(awt_mode));
        }
        return modes.toArray(new DisplayMode[modes.size()]);
    }

    public void setTitle(String title) {
        if (this.frame != null) {
            this.frame.setTitle(title);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isCloseRequested() {
        boolean result;
        MacOSXDisplay macOSXDisplay = this;
        synchronized (macOSXDisplay) {
            result = this.close_requested || this.frame != null && this.frame.syncIsCloseRequested();
            this.close_requested = false;
        }
        return result;
    }

    public boolean isVisible() {
        return this.frame == null || this.frame.syncIsVisible();
    }

    public boolean isActive() {
        return this.canvas.isFocusOwner();
    }

    public Canvas getCanvas() {
        return this.canvas;
    }

    public boolean isDirty() {
        return this.frame != null && this.frame.getCanvas().syncIsDirty();
    }

    public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
        try {
            return new MacOSXDisplayPeerInfo(pixel_format, attribs, true);
        }
        catch (LWJGLException e) {
            return new MacOSXDisplayPeerInfo(pixel_format, attribs, false);
        }
    }

    public void update() {
        boolean should_update = this.canvas_listener.syncShouldUpdateContext();
        DrawableGL drawable = (DrawableGL)Display.getDrawable();
        if (Display.isFullscreen() && (this.frame != null && this.frame.getCanvas().syncCanvasPainted() || should_update)) {
            try {
                MacOSXContextImplementation.resetView(drawable.peer_info, drawable.context);
            }
            catch (LWJGLException e) {
                LWJGLUtil.log("Failed to reset context: " + e);
            }
        }
        if (should_update) {
            drawable.context.update();
            GL11.glGetInteger(2978, current_viewport);
            GL11.glViewport(current_viewport.get(0), current_viewport.get(1), current_viewport.get(2), current_viewport.get(3));
        }
        if (this.frame != null && this.mouse_queue != null) {
            if (this.frame.syncShouldReleaseCursor()) {
                MacOSXMouseEventQueue.nGrabMouse(false);
            }
            if (this.frame.syncShouldWarpCursor()) {
                this.mouse_queue.warpCursor();
            }
        }
    }

    private void hideUI(boolean hide) {
        if (!LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 4)) {
            this.nHideUI(hide);
        }
    }

    private native void nHideUI(boolean var1);

    public void reshape(int x, int y, int width, int height) {
        if (this.frame != null) {
            this.frame.resize(x, y, width, height);
        }
    }

    public boolean hasWheel() {
        return AWTUtil.hasWheel();
    }

    public int getButtonCount() {
        return AWTUtil.getButtonCount();
    }

    public void createMouse() throws LWJGLException {
        this.mouse_queue = new MacOSXMouseEventQueue(this.canvas);
        this.mouse_queue.register();
    }

    public void destroyMouse() {
        if (this.mouse_queue != null) {
            MacOSXMouseEventQueue.nGrabMouse(false);
            this.mouse_queue.unregister();
        }
        this.mouse_queue = null;
    }

    public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons_buffer) {
        this.mouse_queue.poll(coord_buffer, buttons_buffer);
    }

    public void readMouse(ByteBuffer buffer) {
        this.mouse_queue.copyEvents(buffer);
    }

    public void grabMouse(boolean grab) {
        this.mouse_queue.setGrabbed(grab);
    }

    public int getNativeCursorCapabilities() {
        return AWTUtil.getNativeCursorCapabilities();
    }

    public void setCursorPosition(int x, int y) {
        AWTUtil.setCursorPosition(this.canvas, this.robot, x, y);
    }

    public void setNativeCursor(Object handle) throws LWJGLException {
        Cursor awt_cursor = (Cursor)handle;
        if (this.frame != null) {
            this.frame.setCursor(awt_cursor);
        }
    }

    public int getMinCursorSize() {
        return AWTUtil.getMinCursorSize();
    }

    public int getMaxCursorSize() {
        return AWTUtil.getMaxCursorSize();
    }

    public void createKeyboard() throws LWJGLException {
        this.keyboard_queue = new KeyboardEventQueue(this.canvas);
        this.keyboard_queue.register();
    }

    public void destroyKeyboard() {
        if (this.keyboard_queue != null) {
            this.keyboard_queue.unregister();
        }
        this.keyboard_queue = null;
    }

    public void pollKeyboard(ByteBuffer keyDownBuffer) {
        this.keyboard_queue.poll(keyDownBuffer);
    }

    public void readKeyboard(ByteBuffer buffer) {
        this.keyboard_queue.copyEvents(buffer);
    }

    public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
        return AWTUtil.createCursor(width, height, xHotspot, yHotspot, numImages, images, delays);
    }

    public void destroyCursor(Object cursor_handle) {
    }

    public int getPbufferCapabilities() {
        if (LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 3)) {
            return 1;
        }
        return 0;
    }

    public boolean isBufferLost(PeerInfo handle) {
        return false;
    }

    public PeerInfo createPbuffer(int width, int height, PixelFormat pixel_format, ContextAttribs attribs, IntBuffer pixelFormatCaps, IntBuffer pBufferAttribs) throws LWJGLException {
        return new MacOSXPbufferPeerInfo(width, height, pixel_format, attribs);
    }

    public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
        throw new UnsupportedOperationException();
    }

    public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
        throw new UnsupportedOperationException();
    }

    public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
        throw new UnsupportedOperationException();
    }

    public int setIcon(ByteBuffer[] icons) {
        return 0;
    }

    public int getX() {
        return this.frame.getX();
    }

    public int getY() {
        return this.frame.getY();
    }

    public int getWidth() {
        return this.frame.getWidth();
    }

    public int getHeight() {
        return this.frame.getHeight();
    }

    public boolean isInsideWindow() {
        return true;
    }

    public void setResizable(boolean resizable) {
        this.frame.setResizable(resizable);
    }

    public boolean wasResized() {
        return this.canvas_listener.wasResized();
    }

    static /* synthetic */ void access$000(MacOSXDisplay x0) {
        x0.doHandleQuit();
    }

    static /* synthetic */ MacOSXFrame access$100(MacOSXDisplay x0) {
        return x0.frame;
    }
}

