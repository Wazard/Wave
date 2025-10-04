/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public strictfp class Circle
extends Ellipse {
    public float radius;

    public Circle(float centerPointX, float centerPointY, float radius) {
        this(centerPointX, centerPointY, radius, 50);
    }

    public Circle(float centerPointX, float centerPointY, float radius, int segmentCount) {
        super(centerPointX, centerPointY, radius, radius, segmentCount);
        this.x = centerPointX - radius;
        this.y = centerPointY - radius;
        this.radius = radius;
        this.boundingCircleRadius = radius;
    }

    public float getCenterX() {
        return this.getX() + this.radius;
    }

    public float getCenterY() {
        return this.getY() + this.radius;
    }

    public void setRadius(float radius) {
        if (radius != this.radius) {
            this.pointsDirty = true;
            this.radius = radius;
            this.setRadii(radius, radius);
        }
    }

    public float getRadius() {
        return this.radius;
    }

    public boolean intersects(Shape shape) {
        if (shape instanceof Circle) {
            float dy;
            Circle other = (Circle)shape;
            float totalRad2 = this.getRadius() + other.getRadius();
            if (Math.abs(other.getCenterX() - this.getCenterX()) > totalRad2) {
                return false;
            }
            if (Math.abs(other.getCenterY() - this.getCenterY()) > totalRad2) {
                return false;
            }
            float dx = Math.abs(other.getCenterX() - this.getCenterX());
            return (totalRad2 *= totalRad2) >= dx * dx + (dy = Math.abs(other.getCenterY() - this.getCenterY())) * dy;
        }
        if (shape instanceof Rectangle) {
            return this.intersects((Rectangle)shape);
        }
        return super.intersects(shape);
    }

    public boolean contains(float x, float y) {
        return this.intersects(new Circle(x, y, 0.0f));
    }

    protected void findCenter() {
        this.center = new float[2];
        this.center[0] = this.x + this.radius;
        this.center[1] = this.y + this.radius;
    }

    protected void calculateRadius() {
        this.boundingCircleRadius = this.radius;
    }

    private boolean intersects(Rectangle other) {
        Rectangle box = other;
        Circle circle = this;
        if (box.contains(this.x, this.y)) {
            return true;
        }
        float x1 = box.getX();
        float y1 = box.getY();
        float x2 = box.getX() + box.getWidth();
        float y2 = box.getY() + box.getHeight();
        Line[] lines = new Line[]{new Line(x1, y1, x2, y1), new Line(x2, y1, x2, y2), new Line(x2, y2, x1, y2), new Line(x1, y2, x1, y1)};
        float r2 = circle.getRadius() * circle.getRadius();
        Vector2f pos = new Vector2f(circle.getCenterX(), circle.getCenterY());
        for (int i = 0; i < 4; ++i) {
            float dis = lines[i].distanceSquared(pos);
            if (!(dis < r2)) continue;
            return true;
        }
        return false;
    }
}

