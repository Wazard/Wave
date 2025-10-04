package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject {
   private Handler handler;
   private GameObject player;

   public SmartEnemy(float x, float y, ID id, Handler handler) {
      super(x, y, id);
      this.handler = handler;

      for(int i = 0; i < handler.object.size(); ++i) {
         if (((GameObject)handler.object.get(i)).getId() == ID.Player) {
            this.player = (GameObject)handler.object.get(i);
         }
      }

   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      float diffX = this.x - this.player.getX();
      float diffY = this.y - this.player.getY();
      float distance = (float)Math.sqrt(Math.pow((double)(this.x - this.player.getX()), 2.0) + Math.pow((double)(this.y - this.player.getY()), 2.0));
      this.velX = -1.0F / distance * diffX * 3.0F * 0.85F;
      this.velY = -1.0F / distance * diffY * 3.0F * 0.85F;
      this.handler.addObject(new Trail(this.x, this.y, ID.Trail, Color.orange, 16, 16, 0.03F, this.handler));
   }

   public void render(Graphics g) {
      g.setColor(Color.orange);
      g.fillRect((int)this.x, (int)this.y, 16, 16);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 16, 16);
   }
}
