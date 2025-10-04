package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class PurpleProjectyle extends GameObject {
   Random r = new Random();
   private Handler handler;

   public PurpleProjectyle(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velX = 12.0F;
      this.velY = 0.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 24)) {
         this.velX *= -1.0F;
      }

      if (this.y >= (float)(Game.HEIGHT + 10) || this.y < -10.0F || this.x >= (float)(Game.WIDTH - 10) || this.x < -10.0F) {
         this.handler.removeObject(this);
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.magenta, 8, 8, 0.04F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.magenta);
      g.fillRect((int)this.x, (int)this.y, 8, 8);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 8, 8);
   }
}
