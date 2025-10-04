package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class FireBall extends GameObject {
   Random r = new Random();
   private Handler handler;
   private BufferedImage image;
   private int select;

   public FireBall(float x, float y, ID id, Handler handler, int row, int column) {
      super(x, y, id);
      this.handler = handler;
      SpriteSheet ss = new SpriteSheet(Game.projectileSheet);
      this.image = ss.grabProjectyleImage(row, column, 9, 17);
      this.select = column;
      this.velX = (float)(this.r.nextInt(4) + -2);
      if (column == 2) {
         this.velY = -6.0F;
      } else {
         this.velY = 6.0F;
      }

   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y >= (float)(Game.HEIGHT + 10)) {
         this.handler.removeObject(this);
      }

      if (this.select == 1) {
         this.handler.addObject(new Trail(this.x + 2.0F, this.y + 4.0F, ID.Trail, Color.orange, 4, 9, 0.04F, this.handler));
      } else if (this.select == 2) {
         this.handler.addObject(new Trail(this.x + 2.0F, this.y + 4.0F, ID.Trail, Color.cyan, 4, 9, 0.04F, this.handler));
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 9, 17);
   }
}
