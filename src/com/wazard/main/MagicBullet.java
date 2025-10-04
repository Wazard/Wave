package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class MagicBullet extends GameObject {
   private Handler handler;
   private BufferedImage image;

   public MagicBullet(float x, float y, ID id, Handler handler, int a) {
      super(x, y, id);
      this.handler = handler;
      SpriteSheet ss;
      if (id == ID.MagicEnemy) {
         if (a != 0 && a != 3) {
            if (a != 1 && a != 4) {
               this.velX = 3.0F;
               this.velY = 3.0F;
            } else {
               this.velX = 3.0F;
               this.velY = 0.0F;
            }
         } else {
            this.velX = 0.0F;
            this.velY = 3.0F;
         }

         ss = new SpriteSheet(Game.projectileSheet);
         if (a < 2) {
            this.image = ss.grabProjectyleImage(1, 3, 11, 15);
         } else {
            this.image = ss.grabProjectyleImage(2, 1, 11, 15);
         }
      } else {
         this.velY = 4.0F;
         if (a == 0) {
            this.velX = 0.0F;
         } else if (a > 0) {
            this.velX = (float)a;
            if (a > 4) {
               this.velY -= (float)(a - 4);
               this.velX = 4.0F;
            }
         } else if (a < 0) {
            this.velX = (float)a;
            if (a < -4) {
               this.velY += (float)(a + 4);
               this.velX = -4.0F;
            }
         }

         ss = new SpriteSheet(Game.projectileSheet);
         this.image = ss.grabProjectyleImage(1, 3, 11, 15);
      }

   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.x >= (float)(Game.WIDTH - 24)) {
         if (this.id == ID.MagicEnemy) {
            this.handler.removeObject(this);
         } else {
            this.velX = -this.velX;
         }
      }

      if (this.x < 0.0F && this.id != ID.MagicEnemy) {
         this.velX = -this.velX;
      }

      if (this.y < (float)(Game.HEIGHT / 8) && this.id != ID.MagicEnemy) {
         this.handler.removeObject(this);
      }

      if (this.y >= (float)(Game.HEIGHT - 40) && this.id != ID.MagicEnemy) {
         this.velY = -this.velY;
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 11, 15);
   }
}
