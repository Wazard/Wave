package com.wazard.main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class AllyBullet extends GameObject {
   Random r = new Random();
   private Handler handler;

   public AllyBullet(float f, float g, ID id, Handler handler) {
      super(f, g, id);
      this.handler = handler;
      this.velX = 0.0F;
      this.velY = -6.0F;
      if (!Game.diff) {
         this.velY = -this.velY;
      }

   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y <= 50.0F && Game.diff) {
         this.handler.removeObject(this);
      } else if (this.y > (float)(Game.HEIGHT - 70)) {
         this.handler.removeObject(this);
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.lightGray, 7, 7, 0.04F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.lightGray);
      g.fillRect((int)this.x, (int)this.y, 7, 7);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 7, 7);
   }
}

