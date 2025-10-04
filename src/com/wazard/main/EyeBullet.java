package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EyeBullet extends GameObject {
   private Handler handler;
   private GameObject player;

   public EyeBullet(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;

      for(int i = 0; i < handler.object.size(); ++i) {
         if (((GameObject)handler.object.get(i)).getId() == ID.Player) {
            this.player = (GameObject)handler.object.get(i);
         }
      }

      float diffX = x - this.player.getX();
      float diffY = y - this.player.getY();
      float distance = (float)Math.sqrt(Math.pow((double)(x - this.player.getX()), 2.0) + Math.pow((double)(y - this.player.getY()), 2.0));
      this.velX = -1.0F / distance * diffX * 3.0F * 3.0F;
      this.velY = -1.0F / distance * diffY * 3.0F * 3.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.y <= 0.0F || this.y >= (float)(Game.HEIGHT - 40)) {
         this.handler.removeObject(this);
      }

      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 24)) {
         this.handler.removeObject(this);
      }

      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.magenta, 14, 14, 0.1F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.magenta);
      g.fillRect((int)this.x, (int)this.y, 10, 10);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 10, 10);
   }
}
