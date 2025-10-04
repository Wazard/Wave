/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.MacOSXGLCanvas;

final class MacOSXFrame
extends Frame
implements WindowListener,
ComponentListener {
    private static final long serialVersionUID = -5823294716668988777L;
    private final MacOSXGLCanvas canvas;
    private boolean close_requested;
    private Rectangle bounds;
    private boolean active;
    private boolean minimized;
    private boolean should_warp_cursor;
    private boolean should_release_cursor;

    MacOSXFrame(DisplayMode mode, final java.awt.DisplayMode requested_mode, boolean fullscreen, int x, int y) throws LWJGLException {
        this.setResizable(Display.isResizable());
        this.addWindowListener(this);
        this.addComponentListener(this);
        this.canvas = new MacOSXGLCanvas();
        this.canvas.setFocusTraversalKeysEnabled(false);
        this.add((Component)this.canvas, "Center");
        boolean undecorated = Display.getPrivilegedBoolean("org.lwjgl.opengl.Window.undecorated");
        this.setUndecorated(fullscreen || undecorated);
        if (fullscreen) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Object>(){

                    @Override
                    public Object run() throws Exception {
                        MacOSXFrame.getDevice().setFullScreenWindow(MacOSXFrame.this);
                        MacOSXFrame.getDevice().setDisplayMode(requested_mode);
                        java.awt.DisplayMode real_mode = MacOSXFrame.getDevice().getDisplayMode();
                        if (requested_mode.getWidth() != real_mode.getWidth() || requested_mode.getHeight() != real_mode.getHeight()) {
                            MacOSXFrame.getDevice().setFullScreenWindow(null);
                            if (MacOSXFrame.this.isDisplayable()) {
                                MacOSXFrame.this.dispose();
                            }
                            throw new LWJGLException("AWT capped mode: requested mode = " + requested_mode.getWidth() + "x" + requested_mode.getHeight() + " but got " + real_mode.getWidth() + " " + real_mode.getHeight());
                        }
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException e) {
                throw new LWJGLException(e);
            }
        }
        this.pack();
        this.resize(x, y, mode.getWidth(), mode.getHeight());
        this.setVisible(true);
        this.requestFocus();
        this.canvas.requestFocus();
        this.updateBounds();
    }

    public void resize(int x, int y, int width, int height) {
        Insets insets = this.getInsets();
        this.setBounds(x, y, width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    public int getWidth() {
        Insets insets = this.getInsets();
        return super.getWidth() - insets.left - insets.right;
    }

    public int getHeight() {
        Insets insets = this.getInsets();
        return super.getHeight() - insets.top - insets.bottom;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Rectangle syncGetBounds() {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            return this.bounds;
        }
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateBounds() {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.bounds = this.getBounds();
        }
    }

    public void componentResized(ComponentEvent e) {
        this.updateBounds();
    }

    public void componentMoved(ComponentEvent e) {
        this.updateBounds();
    }

    public static GraphicsDevice getDevice() {
        GraphicsEnvironment g_env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = g_env.getDefaultScreenDevice();
        return device;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void windowIconified(WindowEvent e) {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.minimized = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void windowDeiconified(WindowEvent e) {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.minimized = false;
        }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void windowClosing(WindowEvent e) {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.close_requested = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void windowDeactivated(WindowEvent e) {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.active = false;
            this.should_release_cursor = true;
            this.should_warp_cursor = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void windowActivated(WindowEvent e) {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            this.active = true;
            this.should_warp_cursor = true;
            this.should_release_cursor = false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean syncIsCloseRequested() {
        boolean result;
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            result = this.close_requested;
            this.close_requested = false;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean syncIsVisible() {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            return !this.minimized;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean syncIsActive() {
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            return this.active;
        }
    }

    public MacOSXGLCanvas getCanvas() {
        return this.canvas;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean syncShouldReleaseCursor() {
        boolean result;
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            result = this.should_release_cursor;
            this.should_release_cursor = false;
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean syncShouldWarpCursor() {
        boolean result;
        MacOSXFrame macOSXFrame = this;
        synchronized (macOSXFrame) {
            result = this.should_warp_cursor;
            this.should_warp_cursor = false;
        }
        return result;
    }
}

