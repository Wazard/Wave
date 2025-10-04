package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class RandomEnemy extends GameObject {
   private Handler handler;
   Random r = new Random();

   public RandomEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = (float)(this.r.nextInt(3) + 5);
      this.velY = (float)(this.r.nextInt(3) + 5);
      if (this.velX == 0.0F) {
         ++this.velX;
      }

      if (this.velY == 0.0F) {
         ++this.velY;
      }

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

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.magenta, 14, 14, 0.03F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.magenta);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
