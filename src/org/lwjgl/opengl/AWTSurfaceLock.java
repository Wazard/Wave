/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.opengl;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Container;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;

final class AWTSurfaceLock {
    private static final int WAIT_DELAY_MILLIS = 100;
    private final ByteBuffer lock_buffer = AWTSurfaceLock.createHandle();
    private boolean firstLockSucceeded;

    AWTSurfaceLock() {
    }

    private static native ByteBuffer createHandle();

    public ByteBuffer lockAndGetHandle(Canvas component) throws LWJGLException {
        while (!this.privilegedLockAndInitHandle(component)) {
            LWJGLUtil.log("Could not get drawing surface info, retrying...");
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException e) {
                LWJGLUtil.log("Interrupted while retrying: " + e);
            }
        }
        return this.lock_buffer;
    }

    private boolean privilegedLockAndInitHandle(final Canvas component) throws LWJGLException {
        boolean allowCALayer;
        boolean bl = allowCALayer = (Display.getParent() != null && !Display.isFullscreen() || component instanceof AWTGLCanvas) && this.isApplet(component) && LWJGLUtil.isMacOSXEqualsOrBetterThan(10, 6);
        if (this.firstLockSucceeded) {
            return AWTSurfaceLock.lockAndInitHandle(this.lock_buffer, component, allowCALayer);
        }
        try {
            this.firstLockSucceeded = AccessController.doPrivileged(new PrivilegedExceptionAction<Boolean>(){

                @Override
                public Boolean run() throws LWJGLException {
                    return AWTSurfaceLock.lockAndInitHandle(AWTSurfaceLock.this.lock_buffer, component, allowCALayer);
                }
            });
            return this.firstLockSucceeded;
        }
        catch (PrivilegedActionException e) {
            throw (LWJGLException)e.getException();
        }
    }

    private static native boolean lockAndInitHandle(ByteBuffer var0, Canvas var1, boolean var2) throws LWJGLException;

    void unlock() throws LWJGLException {
        AWTSurfaceLock.nUnlock(this.lock_buffer);
    }

    private static native void nUnlock(ByteBuffer var0) throws LWJGLException;

    public boolean isApplet(Canvas component) {
        for (Container parent = component.getParent(); parent != null; parent = parent.getParent()) {
            if (!(parent instanceof Applet)) continue;
            return true;
        }
        return false;
    }

    static /* synthetic */ ByteBuffer access$000(AWTSurfaceLock x0) {
        return x0.lock_buffer;
    }

    static /* synthetic */ boolean access$100(ByteBuffer x0, Canvas x1, boolean x2) throws LWJGLException {
        return AWTSurfaceLock.lockAndInitHandle(x0, x1, x2);
    }
}

