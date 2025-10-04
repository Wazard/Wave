package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class EyeSphere extends GameObject {
   Random r = new Random();
   private Handler handler;
   private BufferedImage image;
   private GameObject player;

   public EyeSphere(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;

      for(int i = 0; i < handler.object.size(); ++i) {
         if (((GameObject)handler.object.get(i)).getId() == ID.Player) {
            this.player = (GameObject)handler.object.get(i);
         }
      }

      SpriteSheet ss = new SpriteSheet(Game.bossProjectyleImage);
      this.image = ss.grabBossProjectyleImage(1, 1, 28, 28);
      this.velX = 0.0F;
      this.velY = 5.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y >= this.player.getY()) {
         this.spawn();
         this.handler.removeObject(this);
      }

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

   public void spawn() {
      this.handler.addObject(new secondEyeSphere(this.x, this.y, ID.MagicSphere, this.handler, true));
      this.handler.addObject(new secondEyeSphere(this.x, this.y, ID.MagicSphere, this.handler, false));
   }
}
