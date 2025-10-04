package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class HardEnemy extends GameObject {
   private Handler handler;
   private Random r = new Random();

   public HardEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = 4.0F;
      this.velY = 4.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y <= 0.0F || this.y >= (float)(Game.HEIGHT - 40)) {
         if (this.y <= 0.0F) {
            this.velY = (float)(this.r.nextInt(7) + 1);
         } else {
            this.velY = (float)(-(this.r.nextInt(7) + 1));
         }
      }

      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 24)) {
         if (this.x <= 0.0F) {
            this.velX = (float)(this.r.nextInt(7) + 1);
         } else {
            this.velX = (float)(-(this.r.nextInt(7) + 1));
         }
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.yellow, 14, 14, 0.04F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.yellow);
      g.fillRect((int)this.x, (int)this.y, 14, 14);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 14, 14);
   }
}
