package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class HardWitch extends GameObject {
   private Handler handler;
   Random r = new Random();
   private HUD hud;
   private float life = 210.0F;
   private int i = 0;
   private int a;
   private int x1;
   private int y1;
   private BufferedImage image;
   private int timer = 180;
   private int timer2 = 80;
   private int timer3 = 50;
   private boolean control = true;
   private boolean done = true;
   private int[] times = new int[2];

   public HardWitch(int x, int y, ID id, Handler handler, HUD hud) {
      super((float)x, (float)y, id);
      this.handler = handler;
      this.hud = hud;
      this.times[0] = 0;
      this.times[1] = 0;
      SpriteSheet ss = new SpriteSheet(Game.bossSheet);
      this.image = ss.grabBossImage(2, 1, 55, 72);
      this.velX = 0.0F;
      this.velY = -2.7F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.timer <= 0 && this.done) {
         AudioPlayer.getSound("WitchLaugh").play();
         this.velY = 0.0F;
         this.done = false;
      } else {
         --this.timer;
      }

      if (this.timer <= 0) {
         --this.timer2;
         this.control = false;
      }

      if (this.timer2 <= 0) {
         if (this.times[0] <= 0) {
            this.a = this.r.nextInt(4);
            this.handler.clearSmallEnemies();
            this.x = (float)(Game.WIDTH / 6 - 36);
            this.y = (float)(Game.HEIGHT + 140);
            this.times[0] = 1;
         } else if (this.times[0] == 1 && this.y < -72.0F) {
            this.x = (float)(Game.WIDTH * 2 / 6 - 36);
            this.y = (float)(Game.HEIGHT + 140);
            this.times[0] = 2;
         } else if (this.times[0] == 2 && this.y < -72.0F) {
            this.x = (float)(Game.WIDTH / 2 - 36);
            this.y = (float)(Game.HEIGHT + 140);
            this.times[0] = 3;
         } else if (this.times[0] == 3 && this.y < -72.0F) {
            this.x = (float)(Game.WIDTH * 4 / 6 - 36);
            this.y = (float)(Game.HEIGHT + 140);
            this.times[0] = 4;
         } else if (this.times[0] == 4 && this.y < -72.0F) {
            this.x = (float)(Game.WIDTH * 5 / 6 - 36);
            this.y = (float)(Game.HEIGHT + 140);
            this.times[0] = 5;
         }

         if (this.times[0] < 5) {
            this.velY = -15.0F;
         }

         if (this.times[0] == 5) {
            this.x = (float)(Game.WIDTH / 2) - 22.5F;
            this.y = (float)(Game.HEIGHT * 5 / 6 + 1);
            if (this.i <= 10) {
               this.control = true;
               this.velY = 0.0F;
               if (this.timer3 >= 0) {
                  --this.timer3;
               } else {
                  this.spawn();
                  this.handler.addObject(new FasterEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.FasterEnemy, this.handler));
                  this.timer3 = 70;
                  ++this.i;
               }
            } else {
               this.times[0] = 0;
               this.control = true;
               this.timer2 = 50;
               this.i = 0;
            }
         }

         this.collision();
         if (this.life <= 0.0F) {
            Spawn.bossFight = false;
            AudioPlayer.getMusic("Background").loop();
            this.hud.score(this.hud.getScore() + 3000);
            this.handler.clearEnemys();
            Spawn.scoreKeep = 399;
         }
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 55, 72);
   }

   private void collision() {
      if (this.control) {
         for(int i = 0; i < this.handler.object.size(); ++i) {
            GameObject tempObject = (GameObject)this.handler.object.get(i);
            if (tempObject.getId() == ID.AllyBullet && this.getBounds().intersects(tempObject.getBounds())) {
               AudioPlayer.getSound("Hit").play();
               --this.life;
               this.handler.removebullet();
            }
         }
      }

   }

   private void spawn() {
      this.x1 = 15;
      this.y1 = 15;
      if (this.a == 0) {
         do {
            this.handler.addObject(new MagicBullet((float)this.x1, -15.0F, ID.MagicEnemy, this.handler, 3));
            this.x1 += 60;
         } while(this.x1 < Game.WIDTH - 25);

         this.x1 = 15;
      } else if (this.a == 1) {
         do {
            this.handler.addObject(new MagicBullet(-15.0F, (float)this.y1, ID.MagicEnemy, this.handler, 4));
            this.y1 += 60;
         } while(this.y1 < Game.HEIGHT - 15);

         this.y1 = 15;
      } else if (this.a == 2) {
         do {
            this.handler.addObject(new MagicBullet(-15.0F, (float)this.y1, ID.MagicEnemy, this.handler, 4));
            this.handler.addObject(new MagicBullet((float)this.x1, -15.0F, ID.MagicEnemy, this.handler, 3));
            this.y1 += 60;
            this.x1 += 60;
         } while(this.x1 < Game.WIDTH - 15);

         this.x1 = 15;
         this.y1 = 15;
      } else if (this.a == 3) {
         this.handler.addObject(new MagicSphere(this.x, this.y + 36.0F, ID.MagicSphere, this.handler));
      }

   }
}
