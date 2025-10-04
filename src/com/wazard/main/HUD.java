package com.wazard.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
   public static float HEALTH = 100.0F;
   public static float maxHealth;
   private int score;
   private int totalScore;
   private int timer = 10;
   protected static int level;

   static {
      maxHealth = HEALTH;
   }

   public HUD() {
   }

   public void tick() {
      if (this.timer > 0) {
         --this.timer;
      } else if (!Spawn.bossFight) {
         this.score += 10;
         this.timer = 15;
      } else {
         this.score += 5;
         this.timer = 20;
      }

      ++this.totalScore;
      HEALTH = Game.clamp((float)((int)HEALTH), 0, (int)maxHealth);
   }

   public void render(Graphics g) {
      g.setColor(Color.gray);
      g.fillRect(15, 15, (int)maxHealth * 2, 32);
      g.setColor(Color.getHSBColor(1.0F * HEALTH / 360.0F, 1.0F, 1.0F));
      g.fillRect(15, 15, (int)HEALTH * 2, 32);
      g.setColor(Color.white);
      g.drawRect(15, 15, (int)maxHealth * 2, 32);
      g.setColor(Color.black);
      g.drawString((int)HEALTH + "/" + (int)maxHealth, 20, 35);
      g.setColor(Color.white);
      g.drawString("Money: " + this.score, 15, 64);
      g.drawString("Level: " + level, 15, 80);
      g.drawString("Press Q for shop", 15, 96);
      if (Spawn.bossFight) {
         g.drawString("Press and hold SPACE to shoot", 15, 112);
      }

   }

   public void score(int score) {
      this.score = score;
   }

   public int getScore() {
      return this.score;
   }

   public void setLevel(int level) {
      HUD.level = level;
   }

   public int getLevel() {
      return level;
   }

   public int getTotalScore() {
      return this.totalScore;
   }

   public void totalScore(int a) {
      this.totalScore = a;
   }
}
