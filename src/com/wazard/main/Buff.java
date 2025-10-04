package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Buff extends GameObject {
   private int timer = 700;
   private Handler handler;
   private BufferedImage healImage;

   public Buff(int x, int y, ID id, Handler handler, int row, int column) {
      super((float)x, (float)y, id);
      this.handler = handler;
      SpriteSheet ss = new SpriteSheet(Game.buffSheet);
      this.healImage = ss.grabImage(row, column, 28, 28);
   }

   public void tick() {
      this.collision();
      if (this.timer > 0) {
         --this.timer;
      } else {
         this.handler.removeObject(this);
         this.timer = 700;
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.healImage, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 28, 28);
   }

   private void collision() {
      for(int i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);
         if (tempObject.getId() == ID.Player && this.getBounds().intersects(tempObject.getBounds())) {
            if (this.id == ID.Healer) {
               AudioPlayer.getSound("Heal").play();
            } else {
               AudioPlayer.getSound("Coin").play();
            }

            this.handler.removeObject(this);
         }
      }

   }
}