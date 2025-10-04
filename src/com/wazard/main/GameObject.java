package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
   protected float x;
   protected float y;
   protected ID id;
   protected float velX;
   protected float velY;

   public GameObject(float x2, float y2, ID id) {
      this.x = x2;
      this.y = y2;
      this.id = id;
   }

   public abstract void tick();

   public abstract void render(Graphics var1);

   public abstract Rectangle getBounds();

   public void setX(int x) {
      this.x = (float)x;
   }

   public void setY(int y) {
      this.y = (float)y;
   }

   public void setVelX(float velX) {
      this.velX = velX;
   }

   public void setVelY(float spd) {
      this.velY = spd;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public void setId(ID id) {
      this.id = id;
   }

   public ID getId() {
      return this.id;
   }
}
