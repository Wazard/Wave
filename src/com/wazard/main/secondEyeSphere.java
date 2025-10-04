package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class secondEyeSphere extends GameObject {
   Random r = new Random();
   private Handler handler;
   private BufferedImage image;
   boolean control;

   public secondEyeSphere(float x, float y, ID id, Handler handler, boolean control) {
      super(x, y, id);
      this.handler = handler;
      this.control = control;
      SpriteSheet ss = new SpriteSheet(Game.bossProjectyleImage);
      this.image = ss.grabBossProjectyleImage(1, 1, 28, 28);
      if (control) {
         this.velX = -4.0F;
      } else {
         this.velX = 4.0F;
      }

      this.velY = 0.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y >= (float)(Game.HEIGHT + 10)) {
         this.handler.removeObject(this);
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 28, 28);
   }
}
