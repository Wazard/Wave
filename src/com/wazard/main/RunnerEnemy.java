package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class RunnerEnemy extends GameObject {
   private Handler handler;

   public RunnerEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = 2.0F;
      this.velY = 2.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y <= 0.0F || this.y >= (float)(Game.HEIGHT - 50)) {
         this.velY *= -1.15F;
      }

      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 50)) {
         this.velX *= -1.15F;
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.pink, 14, 14, 0.03F, this.handler));
      if (this.velX >= 11.0F) {
         this.velX = 2.0F;
      } else if (this.velX <= -11.0F) {
         this.velX = -2.0F;
      }

      if (this.velY >= 11.0F) {
         this.velY = 2.0F;
      } else if (this.velY <= -11.0F) {
         this.velY = -2.0F;
      }

   }

   public void render(Graphics g) {
      g.setColor(Color.pink);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
