package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {
   private Handler handler;
   Random r = new Random();
   private Color color;
   int dir = 0;

   public MenuParticle(int x, int y, ID id, Handler handler) {
      super((float)x, (float)y, id);
      this.handler = handler;
      this.velX = (float)(this.r.nextInt(14) + -7);
      this.velY = (float)(this.r.nextInt(14) + -7);
      if (this.velX == 0.0F) {
         this.velX = 1.0F;
      }

      if (this.velY == 0.0F) {
         this.velY = 1.0F;
      }

      this.color = new Color(this.r.nextInt(255), this.r.nextInt(255), this.r.nextInt(255));
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

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, this.color, 14, 14, 0.05F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(this.color);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
