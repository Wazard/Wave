package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class EnemyAzureBoss extends GameObject {
   private Handler handler;
   Random r = new Random();
   private HUD hud;
   private BufferedImage image;
   private float life = 75.0F;
   private int maxSpeed = 5;
   private int spawn;
   private int timer = 120;
   private int timer2 = 40;

   public EnemyAzureBoss(float x, float y, ID id, Handler handler, HUD hud) {
      super(x, y, id);
      this.handler = handler;
      this.hud = hud;
      SpriteSheet ss = new SpriteSheet(Game.bossSheet);
      this.image = ss.grabBossImage(1, 2, 72, 72);
      this.velX = 0.0F;
      this.velY = -2.0F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.timer <= 0) {
         this.velY = 0.0F;
      } else {
         --this.timer;
      }

      if (this.timer <= 0) {
         --this.timer2;
      }

      if (this.timer2 <= 0) {
         if (this.velX == 0.0F) {
            this.velX = 1.0F;
         }

         if (this.velX > 0.0F) {
            this.velX += 0.001F;
         } else {
            this.velX -= 0.01F;
         }

         this.velX = Game.clamp(this.velX, -this.maxSpeed, this.maxSpeed);
         if (this.life < 25.0F) {
            this.spawn = this.r.nextInt(4);
         } else {
            this.spawn = this.r.nextInt(5);
         }

         if (this.spawn == 0) {
            this.handler.addObject(new FireBall(this.x + 24.0F, this.y + 24.0F, ID.FireBall, this.handler, 1, 2));
         }
      }

      if (this.x <= 0.0F || this.x >= (float)(Game.WIDTH - 72)) {
         this.velX *= -1.0F;
      }

      this.collision();
      if (this.life < 35.0F) {
         this.maxSpeed += 2;
      }

      if (this.life <= 0.0F) {
         Spawn.bossFight = false;
         AudioPlayer.getMusic("Background").loop();
         this.hud.score(this.hud.getScore() + 750);
         this.handler.removeObject(this);
         Spawn.scoreKeep = 399;
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 72, 72);
   }

   private void collision() {
      for(int i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);
         if (tempObject.getId() == ID.AllyBullet && this.getBounds().intersects(tempObject.getBounds())) {
            AudioPlayer.getSound("Hit").play();
            --this.life;
            if (this.velX > 5.0F) {
               this.life -= 0.3F;
            }

            this.handler.removebullet();
         }
      }

   }
}
