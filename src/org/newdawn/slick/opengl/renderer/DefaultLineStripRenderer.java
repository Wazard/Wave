/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.renderer;

import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class DefaultLineStripRenderer
implements LineStripRenderer {
    private static SGL GL = Renderer.get();

    public void end() {
        GL.glEnd();
    }

    public void setAntiAlias(boolean antialias) {
        if (antialias) {
            GL.glEnable(2848);
        } else {
            GL.glDisable(2848);
        }
    }

    public void setWidth(float width) {
        GL.glLineWidth(width);
    }

    public void start() {
        GL.glBegin(3);
    }

    public void vertex(float x, float y) {
        GL.glVertex2f(x, y);
    }

    public void color(float r, float g, float b, float a) {
        GL.glColor4f(r, g, b, a);
    }

    public void setLineCaps(boolean caps) {
    }

    public boolean applyGLLineFixes() {
        return true;
    }
}

