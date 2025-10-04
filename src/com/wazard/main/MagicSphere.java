package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class MagicSphere extends GameObject {
   private GameObject player;
   private Handler handler;
   private BufferedImage image;

   public MagicSphere(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;
      this.velY = -8.0F;

      for(int i = 0; i < handler.object.size(); ++i) {
         if (((GameObject)handler.object.get(i)).getId() == ID.Player) {
            this.player = (GameObject)handler.object.get(i);
         }
      }

      SpriteSheet ss = new SpriteSheet(Game.bossProjectyleImage);
      this.image = ss.grabBossImage(1, 2, 55, 55);
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y < -55.0F) {
         this.handler.removeObject(this);
      }

      float diffX = this.x - this.player.getX();
      float distance = (float)Math.sqrt(Math.pow((double)(this.x - this.player.getX()), 2.0) + Math.pow((double)(this.y - this.player.getY()), 2.0));
      this.velX = -1.0F / distance * diffX * 3.0F * 2.5F;
   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 55, 55);
   }
}
