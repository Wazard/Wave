package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BasicEnemy extends GameObject {
   private Handler handler;

   public BasicEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = 5.0F;
      this.velY = 5.0F;
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

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.red, 14, 14, 0.04F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.red);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
