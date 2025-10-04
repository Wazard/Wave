package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class FlatEnemy extends GameObject {
   private Handler handler;
   private boolean a;

   public FlatEnemy(float x, float y, ID id, Handler handler, boolean a) {
      super(x, y, id);
      this.handler = handler;
      this.a = a;
      this.velX = 7.0F;
      if (a) {
         this.velY = 2.0F;
      } else {
         this.velY = -2.0F;
      }

   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.velY > 0.0F) {
         this.a = true;
      } else {
         this.a = false;
      }

      if (this.y <= 0.0F || this.y >= (float)(Game.HEIGHT - 40)) {
         this.velY *= -1.0F;
      }

      if (this.x <= 0.0F) {
         this.handler.removeObject(this);
         this.handler.addObject(new FlatEnemy(this.x + (float)Game.WIDTH + 28.0F, this.y, ID.FastEnemy, this.handler, this.a));
      }

      if (this.x >= (float)(Game.WIDTH - 24)) {
         this.handler.removeObject(this);
         this.handler.addObject(new FlatEnemy(14.0F, this.y, ID.FastEnemy, this.handler, this.a));
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.green, 14, 14, 0.06F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.green);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
