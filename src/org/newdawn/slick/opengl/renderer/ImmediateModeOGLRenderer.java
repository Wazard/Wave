/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.renderer;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.renderer.SGL;

public class ImmediateModeOGLRenderer
implements SGL {
    private int width;
    private int height;
    private float[] current = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    protected float alphaScale = 1.0f;

    public void initDisplay(int width, int height) {
        this.width = width;
        this.height = height;
        String extensions = GL11.glGetString(7939);
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(5888);
    }

    public void enterOrtho(int xsize, int ysize) {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, this.width, this.height, 0.0, 1.0, -1.0);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((this.width - xsize) / 2, (this.height - ysize) / 2, 0.0f);
    }

    public void glBegin(int geomType) {
        GL11.glBegin(geomType);
    }

    public void glBindTexture(int target, int id) {
        GL11.glBindTexture(target, id);
    }

    public void glBlendFunc(int src, int dest) {
        GL11.glBlendFunc(src, dest);
    }

    public void glCallList(int id) {
        GL11.glCallList(id);
    }

    public void glClear(int value) {
        GL11.glClear(value);
    }

    public void glClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    public void glClipPlane(int plane, DoubleBuffer buffer) {
        GL11.glClipPlane(plane, buffer);
    }

    public void glColor4f(float r, float g, float b, float a) {
        this.current = new float[]{r, g, b, a *= this.alphaScale};
        GL11.glColor4f(r, g, b, a);
    }

    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }

    public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
        GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
    }

    public void glDeleteTextures(IntBuffer buffer) {
        GL11.glDeleteTextures(buffer);
    }

    public void glDisable(int item) {
        GL11.glDisable(item);
    }

    public void glEnable(int item) {
        GL11.glEnable(item);
    }

    public void glEnd() {
        GL11.glEnd();
    }

    public void glEndList() {
        GL11.glEndList();
    }

    public int glGenLists(int count) {
        return GL11.glGenLists(count);
    }

    public void glGetFloat(int id, FloatBuffer ret) {
        GL11.glGetFloat(id, ret);
    }

    public void glGetInteger(int id, IntBuffer ret) {
        GL11.glGetInteger(id, ret);
    }

    public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels) {
        GL11.glGetTexImage(target, level, format, type, pixels);
    }

    public void glLineWidth(float width) {
        GL11.glLineWidth(width);
    }

    public void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    public void glNewList(int id, int option) {
        GL11.glNewList(id, option);
    }

    public void glPointSize(float size) {
        GL11.glPointSize(size);
    }

    public void glPopMatrix() {
        GL11.glPopMatrix();
    }

    public void glPushMatrix() {
        GL11.glPushMatrix();
    }

    public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
        GL11.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public void glRotatef(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public void glScalef(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public void glScissor(int x, int y, int width, int height) {
        GL11.glScissor(x, y, width, height);
    }

    public void glTexCoord2f(float u, float v) {
        GL11.glTexCoord2f(u, v);
    }

    public void glTexEnvi(int target, int mode, int value) {
        GL11.glTexEnvi(target, mode, value);
    }

    public void glTranslatef(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public void glVertex2f(float x, float y) {
        GL11.glVertex2f(x, y);
    }

    public void glVertex3f(float x, float y, float z) {
        GL11.glVertex3f(x, y, z);
    }

    public void flush() {
    }

    public void glTexParameteri(int target, int param, int value) {
        GL11.glTexParameteri(target, param, value);
    }

    public float[] getCurrentColor() {
        return this.current;
    }

    public void glDeleteLists(int list, int count) {
        GL11.glDeleteLists(list, count);
    }

    public void glClearDepth(float value) {
        GL11.glClearDepth(value);
    }

    public void glDepthFunc(int func) {
        GL11.glDepthFunc(func);
    }

    public void glDepthMask(boolean mask) {
        GL11.glDepthMask(mask);
    }

    public void setGlobalAlphaScale(float alphaScale) {
        this.alphaScale = alphaScale;
    }

    public void glLoadMatrix(FloatBuffer buffer) {
        GL11.glLoadMatrix(buffer);
    }
}

