package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class FastEnemy extends GameObject {
   private Handler handler;

   public FastEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = 9.0F;
      this.velY = 3.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y <= 0.0F || this.y >= (float)(Game.HEIGHT - 40)) {
         this.velY *= -1.0F;
      }

      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 24)) {
         this.velX *= -1.0F;
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.cyan, 14, 14, 0.03F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.cyan);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
