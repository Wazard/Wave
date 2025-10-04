package com.wazard.main;

import java.util.Random;

public class Spawn {
   private Handler handler;
   private HUD hud;
   protected static boolean bossFight = false;
   protected static int scoreKeep;
   protected static int lastlevel = 1;
   private int a;
   private int b;
   private Random r;
   static boolean control;

   public Spawn(Handler handler, HUD hud) {
      this.handler = handler;
      this.hud = hud;
   }

   public void tick() {
      ++scoreKeep;
      this.r = new Random();
      if (scoreKeep > 470) {
         scoreKeep = 0;
         if (!bossFight) {
            this.hud.setLevel(this.hud.getLevel() + 1);
         }

         if (Game.diff && !bossFight) {
            if (this.hud.getLevel() == 10 && !bossFight) {
               control = true;
               AudioPlayer.getMusic("Final").loop();
               this.handler.clearEnemys();
               this.handler.addObject(new EnemyBoss((float)(Game.WIDTH / 2 - 48), -120.0F, ID.EnemyBoss, this.handler, this.hud));
               HUD.HEALTH += HUD.HEALTH * 1.0F / 3.0F;
               bossFight = true;
               lastlevel = 10;
            } else if (!bossFight && this.hud.getLevel() == lastlevel + 1 && this.hud.getLevel() % 10 != 0) {
               bossFight = false;
               this.spawnerNormal();
               lastlevel = this.hud.getLevel();
            } else if (this.hud.getLevel() == 20 && !bossFight) {
               control = true;
               AudioPlayer.getMusic("Witch").loop();
               this.handler.clearEnemys();
               this.handler.addObject(new WitchBoss(Game.WIDTH / 2 - 48, -300, ID.EnemyBoss, this.handler, this.hud));
               bossFight = true;
               HUD.HEALTH += HUD.HEALTH * 1.0F / 3.0F;
               bossFight = true;
               lastlevel = 20;
            } else if (this.hud.getLevel() == 30 && !bossFight) {
               control = true;
               AudioPlayer.getMusic("EyeBoss").loop();
               this.handler.clearEnemys();
               this.handler.addObject(new EyeBoss(Game.WIDTH / 2 - 48, -300, ID.EnemyBoss, this.handler, this.hud));
               bossFight = true;
               HUD.HEALTH += HUD.HEALTH * 1.0F / 3.0F;
               bossFight = true;
               lastlevel = 30;
            }
         } else if (this.hud.getLevel() == 10 && !bossFight) {
            control = true;
            AudioPlayer.getMusic("Final").loop();
            this.handler.clearEnemys();
            this.handler.addObject(new EnemyAzureBoss((float)(Game.WIDTH / 2 - 48), (float)(Game.HEIGHT + 120), ID.EnemyBoss, this.handler, this.hud));
            HUD.HEALTH += HUD.HEALTH * 1.0F / 3.0F;
            bossFight = true;
            lastlevel = 10;
         } else if (!bossFight && this.hud.getLevel() == lastlevel + 1 && this.hud.getLevel() % 10 != 0) {
            bossFight = false;
            this.hardSpawner();
            lastlevel = this.hud.getLevel();
         } else if (this.hud.getLevel() == 20 && !bossFight) {
            control = true;
            AudioPlayer.getMusic("Witch").loop();
            this.handler.clearEnemys();
            this.handler.addObject(new HardWitch(Game.WIDTH / 2 - 48, Game.HEIGHT + 300, ID.EnemyBoss, this.handler, this.hud));
            bossFight = true;
            HUD.HEALTH += HUD.HEALTH * 1.0F / 3.0F;
            bossFight = true;
            lastlevel = 20;
         }

         this.b = this.r.nextInt(5);
         if (this.b == 0 && HUD.HEALTH < HUD.maxHealth) {
            this.handler.addObject(new Buff(this.r.nextInt(Game.WIDTH - 30) + 15, this.r.nextInt(Game.HEIGHT - 80) + 30, ID.Healer, this.handler, 1, 2));
         } else if (this.b == 3) {
            this.handler.addObject(new Buff(this.r.nextInt(Game.WIDTH - 30) + 15, this.r.nextInt(Game.HEIGHT - 80) + 30, ID.Coin, this.handler, 1, 1));
         }
      }

   }

   public void setScoreKeep(int a) {
      scoreKeep = a;
   }

   private void spawnerNormal() {
      this.a = this.r.nextInt(8);
      if (this.a == 0) {
         this.handler.addObject(new BasicEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.BasicEnemy, this.handler));
      } else if (this.a == 1) {
         this.handler.addObject(new FastEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.FastEnemy, this.handler));
      } else if (this.a == 2) {
         this.handler.addObject(new RandomEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.BasicEnemy, this.handler));
      } else if (this.a == 3) {
         this.handler.addObject(new FlatEnemy((float)(this.r.nextInt(Game.WIDTH - 100) + 50), (float)(this.r.nextInt(Game.HEIGHT - 100) + 50), ID.FastEnemy, this.handler, true));
      } else if (this.a >= 4 && this.a <= 7) {
         if (control) {
            this.handler.addObject(new SmartEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.SmartEnemy, this.handler));
         } else {
            this.spawnerNormal();
         }

         control = false;
      }

   }

   private void hardSpawner() {
      this.a = this.r.nextInt(10);
      if (this.a == 0) {
         this.handler.addObject(new BasicEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.BasicEnemy, this.handler));
      } else if (this.a == 1) {
         this.handler.addObject(new FastEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.FastEnemy, this.handler));
      } else if (this.a == 2) {
         this.handler.addObject(new RandomEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.BasicEnemy, this.handler));
      } else if (this.a == 3) {
         this.handler.addObject(new FlatEnemy((float)(this.r.nextInt(Game.WIDTH - 100) + 50), (float)(this.r.nextInt(Game.HEIGHT - 100) + 50), ID.FastEnemy, this.handler, true));
      } else if (this.a == 4) {
         this.handler.addObject(new HardEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.HardEnemy, this.handler));
      } else if (this.a == 5) {
         this.handler.addObject(new FasterEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.FasterEnemy, this.handler));
      } else if (this.a == 6) {
         this.handler.addObject(new RandomEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.HardEnemy, this.handler));
      } else if (this.a >= 7 && this.a <= 10) {
         if (control) {
            this.handler.addObject(new HardSmartEnemy((float)(this.r.nextInt(Game.WIDTH / 2) + 200), (float)(this.r.nextInt(Game.HEIGHT / 2) + 200), ID.SmartEnemy, this.handler));
         } else {
            this.hardSpawner();
         }

         control = false;
      }

   }
}
