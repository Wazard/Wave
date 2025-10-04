package com.wazard.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Trail extends GameObject {
   private float alpha = 1.0F;
   private float life;
   private Handler handler;
   private Color color;
   private int width;
   private int height;

   public Trail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.color = color;
      this.height = height;
      this.width = width;
      this.life = life;
   }

   public void tick() {
      if (this.alpha >= this.life) {
         this.alpha -= this.life - 1.0E-4F;
      } else {
         this.handler.removeObject(this);
      }

   }

   public void render(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.setComposite(this.makeTransparent(this.alpha));
      g.setColor(this.color);
      g.fillRect((int)this.x, (int)this.y, this.width, this.height);
      g2d.setComposite(this.makeTransparent(1.0F));
   }

   private AlphaComposite makeTransparent(float alpha) {
      int type = 3;
      return AlphaComposite.getInstance(type, alpha);
   }

   public Rectangle getBounds() {
      return null;
   }
}
