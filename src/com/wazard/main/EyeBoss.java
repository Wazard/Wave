package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

public class EyeBoss extends GameObject {
   private Handler handler;
   Random r = new Random();
   private HUD hud;
   private float life = 30.0F;
   private BufferedImage image;
   private boolean control = true;
   private boolean targettable = false;
   private boolean attack = true;
   private int[] times = new int[10];
   private int[] i = new int[8];
   private GameObject player;

   public EyeBoss(int x, int y, ID id, Handler handler, HUD hud) {
      super((float)x, (float)y, id);
      this.handler = handler;
      this.hud = hud;
      this.times[0] = 0;
      this.times[1] = 40;
      this.times[2] = 30;
      this.times[3] = 6;
      this.times[4] = 0;
      this.times[5] = 0;
      SpriteSheet ss = new SpriteSheet(Game.bossSheet);
      this.image = ss.grabBossImage(2, 2, 72, 61);

      for(int i = 0; i < handler.object.size(); ++i) {
         if (((GameObject)handler.object.get(i)).getId() == ID.Player) {
            this.player = (GameObject)handler.object.get(i);
         }
      }

      this.i[4] = 0;
      this.i[2] = this.r.nextInt(8) + 18;
      this.velX = 0.0F;
      this.velY = 2.5F;
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      if (this.i[1] == 1) {
         this.x = this.player.getX() - 22.0F;
      }

      if (this.y >= (float)(Game.HEIGHT / 8 - 1)) {
         this.velY = 0.0F;
      }

      int var10002;
      if (this.velY == 0.0F && this.control) {
         if (this.times[2] <= 0) {
            this.handler.addObject(new PurpleProjectyle(20.0F, 27.0F, ID.OneShot, this.handler));
            this.handler.addObject(new PurpleProjectyle(20.0F, 52.0F, ID.OneShot, this.handler));
            var10002 = this.times[3]--;
            this.times[2] = 30;
         } else {
            var10002 = this.times[2]--;
         }

         if (this.times[3] < 0) {
            this.control = false;
            if (this.times[2] <= 0) {
               this.attack = true;
            }
         }
      }

      if (this.times[1] <= 0 && !this.control && this.attack) {
         if (this.times[4] < 1) {
            this.i[1] = this.r.nextInt(3);
            this.control();
         }

         if (this.i[6] % 3 != 0) {
            this.i[1] = 0;
         }

         if (this.i[1] == 1) {
            this.i[3] = 1;
            this.targettable = false;
            this.handler.addObject(new EyeSphere(this.x + 22.0F, this.y + 61.0F, ID.HardEnemy, this.handler));
            var10002 = this.times[4]++;
            if (this.times[4] >= this.i[2]) {
               this.attack = false;
               this.targettable = false;
               this.times[4] = 0;
               this.x = (float)(Game.WIDTH / 2 - 36);
               this.i[1] = 3;
            }
         } else if (this.i[1] != 2) {
            if (this.i[1] == 0) {
               this.i[3] = 0;
               this.targettable = true;
               this.x = (float)(Game.WIDTH / 2 - 36);
               this.handler.addObject(new EyeBullet(this.x + 31.0F, this.y + 30.0F, ID.HardEnemy, this.handler));
               var10002 = this.times[5]++;
               var10002 = this.times[4]++;
               if (this.times[5] >= 150) {
                  this.attack = false;
                  this.targettable = false;
                  this.times[5] = 0;
                  this.times[4] = 0;
                  var10002 = this.i[6]++;
                  var10002 = this.i[7]++;
                  this.i[1] = 3;
               }
            }
         } else {
            this.i[3] = 2;
            this.targettable = true;
            this.x = (float)(Game.WIDTH / 2 - 36);
            this.handler.addObject(new MagicBullet(this.x + 29.0F, this.y + 30.0F, ID.HardEnemy, this.handler, this.i[4]));
            if (this.i[5] < 8) {
               var10002 = this.i[4]++;
            } else if (this.i[5] >= 24 && this.i[5] < 40) {
               var10002 = this.i[4]++;
            } else if (this.i[5] >= 56) {
               var10002 = this.i[4]++;
            } else if (this.i[5] >= 8 && this.i[5] < 24) {
               var10002 = this.i[4]--;
            } else if (this.i[5] >= 40 && this.i[5] < 56) {
               var10002 = this.i[4]--;
            }

            var10002 = this.i[5]++;
            this.times[3] = 1;
            var10002 = this.times[4]++;
            if (this.i[5] > 64) {
               this.attack = false;
               this.targettable = false;
               this.i[5] = 0;
               var10002 = this.i[6]++;
               this.times[3] = -1;
               this.times[4] = 0;
               this.i[1] = 3;
            }
         }

         if (this.i[1] == 3) {
            this.times[1] = 180;
         } else if (this.i[1] == 2) {
            this.times[1] = 6;
         } else if (this.i[1] == 1) {
            this.times[1] = 50;
         } else {
            this.times[1] = 4;
         }

         if (this.life < 20.0F) {
            int[] var10000 = this.times;
            var10000[1] /= 2;
         }

         this.attack = true;
      } else {
         var10002 = this.times[1]--;
      }

      if (this.targettable) {
         this.collision();
      }

      if (this.life <= 0.0F) {
         this.i[2] = this.r.nextInt(8) + 18;
         Spawn.bossFight = false;
         AudioPlayer.getMusic("Background").loop();
         this.hud.score(this.hud.getScore() + 3000);
         this.handler.clearEnemys();
         Spawn.scoreKeep = 399;
      }

   }

   public void render(Graphics g) {
      g.drawImage(this.image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 72, 61);
   }

   private void collision() {
      for(int i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);
         if (tempObject.getId() == ID.AllyBullet && this.getBounds().intersects(tempObject.getBounds())) {
            AudioPlayer.getSound("Hit").play();
            this.life -= 0.9F;
            this.handler.removebullet();
         }
      }

   }

   private void control() {
      if (this.i[3] == this.i[1]) {
         this.i[1] = this.r.nextInt(3);
         this.control();
      }

   }
}
