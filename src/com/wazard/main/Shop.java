package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter {
   Handler handler;
   HUD hud;
   protected int[] B = new int[6];
   private int[] cont;
   protected static float bonusHealth = 75.0F;
   private Font fnt;
   private Font fnt3;
   private int[] btn1;
   private int[] btn2;
   private int[] btn3;

   public Shop(Handler handler, HUD hud) {
      this.cont = new int[this.B.length];
      this.fnt = new Font("chiller", 1, Game.WIDTH / 11);
      this.fnt3 = new Font("chiller", 4, Game.WIDTH / 40);
      this.btn1 = new int[4];
      this.btn2 = new int[4];
      this.btn3 = new int[4];
      this.handler = handler;
      this.hud = hud;
   }

   public void render(Graphics g) {
      if (Game.gameState == STATE.Shop) {
         this.btn1[0] = Game.WIDTH * 1 / 4 - this.getWidth(g, this.fnt3, "Upgrade Health lv:x") * 3 / 4;
         this.btn1[1] = Game.HEIGHT * 2 / 7 - g.getFontMetrics(this.fnt3).getAscent();
         this.btn1[2] = this.getWidth(g, this.fnt3, "Upgrade Health lv:x") * 3 / 2;
         this.btn1[3] = g.getFontMetrics(this.fnt3).getHeight() * 2;
         this.btn2[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt3, "Refill Health (xxx)") * 3 / 4;
         this.btn2[1] = Game.HEIGHT * 2 / 7 - g.getFontMetrics(this.fnt3).getAscent();
         this.btn2[2] = this.getWidth(g, this.fnt3, "Refill Health (xxx)") * 3 / 2;
         this.btn2[3] = g.getFontMetrics(this.fnt3).getHeight() * 2;
         this.btn3[0] = Game.WIDTH * 3 / 4 - this.getWidth(g, this.fnt3, "Upgrade Speed lv:x") * 3 / 4;
         this.btn3[1] = Game.HEIGHT * 2 / 7 - g.getFontMetrics(this.fnt3).getAscent();
         this.btn3[2] = this.getWidth(g, this.fnt3, "Upgrade Speed lv:x") * 3 / 2;
         this.btn3[3] = g.getFontMetrics(this.fnt3).getHeight() * 2;
         g.setColor(Color.white);
         g.setFont(this.fnt);
         g.drawString("SHOP", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "Shop") / 2, this.getHeight(g, this.fnt));
         g.setFont(this.fnt3);
         g.drawString("Upgrade Health lv: " + this.cont[0], Game.WIDTH * 1 / 4 - this.getWidth(g, this.fnt3, "Upgrade Health lv:0") / 2, Game.HEIGHT * 2 / 7);
         if (HUD.maxHealth < 500.0F) {
            g.drawString("Cost: " + this.B[0] + " points", Game.WIDTH * 1 / 4 - this.getWidth(g, this.fnt3, "Cost: xxxx points") / 2, Game.HEIGHT * 2 / 7 + this.getHeight(g, this.fnt3) + 5);
         } else {
            g.drawString("Max Level", Game.WIDTH * 1 / 4 - this.getWidth(g, this.fnt3, "Max level") / 2, Game.HEIGHT * 2 / 7 + this.getHeight(g, this.fnt3) + 5);
         }

         g.drawRect(this.btn1[0], this.btn1[1], this.btn1[2], this.btn1[3]);
         g.drawString("Refill Health (" + (int)bonusHealth + ")", Game.WIDTH / 2 - this.getWidth(g, this.fnt3, "Refill Health (xxx)") / 2, Game.HEIGHT * 2 / 7);
         g.drawString("Cost: " + this.B[1] + " points", Game.WIDTH / 2 - this.getWidth(g, this.fnt3, "Cost: xxxx points") / 2, Game.HEIGHT * 2 / 7 + this.getHeight(g, this.fnt3) + 5);
         g.drawRect(this.btn2[0], this.btn2[1], this.btn2[2], this.btn2[3]);
         g.drawString("Upgrade Speed lv: " + this.cont[2], Game.WIDTH * 3 / 4 - this.getWidth(g, this.fnt3, "Upgrade Speed lv:0") / 2, Game.HEIGHT * 2 / 7);
         if (this.handler.spd < 8.0F) {
            g.drawString("Cost: " + this.B[2] + " points", Game.WIDTH * 3 / 4 - this.getWidth(g, this.fnt3, "Cost: xxxx points") / 2, Game.HEIGHT * 2 / 7 + this.getHeight(g, this.fnt3) + 5);
         } else {
            g.drawString("Max level", Game.WIDTH * 3 / 4 - this.getWidth(g, this.fnt3, "Max level") / 2, Game.HEIGHT * 2 / 7 + this.getHeight(g, this.fnt3) + 5);
         }

         g.drawRect(this.btn3[0], this.btn3[1], this.btn3[2], this.btn3[3]);
         g.drawString("Points: " + this.hud.getScore(), Game.WIDTH / 2 - this.getWidth(g, this.fnt3, "Points: xxxx") / 2, Game.HEIGHT * 1 / 7 + this.getHeight(g, this.fnt3) + 5);
         g.drawString("Press Q to go back", Game.WIDTH / 2 - this.getWidth(g, this.fnt3, "Press Q to go back") / 2, Game.HEIGHT * 3 / 7);
      }

   }

   public void mousePressed(MouseEvent e) {
      if (Game.gameState == STATE.Shop) {
         int mx = e.getX();
         int my = e.getY();
         int[] var10000;
         int var10001;
         int var10002;
         if (this.mouseOver(mx, my, this.btn1[0], this.btn1[1], this.btn1[2], this.btn1[3])) {
            if (this.hud.getScore() >= this.B[0] && HUD.maxHealth < 500.0F) {
               AudioPlayer.getSound("MouseClick").play();
               this.hud.score(this.hud.getScore() - this.B[0]);
               var10000 = this.B;
               var10000[0] += this.B[this.B.length - 1];
               var10000 = this.B;
               var10001 = this.B.length - 1;
               var10000[var10001] += 150;
               HUD.maxHealth += 50.0F;
               HUD.HEALTH += 50.0F;
               var10002 = this.cont[0]++;
            } else {
               AudioPlayer.getSound("Denied").play();
            }
         }

         if (this.mouseOver(mx, my, this.btn2[0], this.btn2[1], this.btn2[2], this.btn2[3])) {
            if (this.hud.getScore() >= this.B[1]) {
               AudioPlayer.getSound("MouseClick").play();
               this.hud.score(this.hud.getScore() - this.B[1]);
               var10000 = this.B;
               var10000[1] += this.B[this.B.length - 2];
               var10000 = this.B;
               var10001 = this.B.length - 2;
               var10000[var10001] += 300;
               HUD.HEALTH += bonusHealth;
               bonusHealth += 25.0F;
               Game.clamp(bonusHealth, 0, 500);
            } else {
               AudioPlayer.getSound("Denied").play();
            }
         }

         if (this.mouseOver(mx, my, this.btn3[0], this.btn3[1], this.btn3[2], this.btn3[3])) {
            if (this.hud.getScore() >= this.B[2] && this.handler.spd < 8.0F) {
               AudioPlayer.getSound("MouseClick").play();
               this.hud.score(this.hud.getScore() - this.B[2]);
               var10000 = this.B;
               var10000[2] += this.B[this.B.length - 3];
               var10000 = this.B;
               var10001 = this.B.length - 3;
               var10000[var10001] += 100;
               ++this.handler.spd;
               var10002 = this.cont[2]++;
            } else {
               AudioPlayer.getSound("Denied").play();
            }
         }
      }

   }

   public void ResetCosts() {
      for(int i = 0; i < this.B.length; ++i) {
         this.B[0] = 1150;
         this.B[1] = 900;
         this.B[2] = 650;
         this.cont[i] = 0;
         this.B[i] = 200;
      }

   }

   public void mouseReleased(MouseEvent e) {
   }

   public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
      if (mx > x && mx < x + width) {
         return my > y && my < y + height;
      } else {
         return false;
      }
   }

   public void setCont(int x) {
      for(int i = 0; i < this.B.length; ++i) {
         this.cont[i] = x;
      }

   }

   public int getWidth(Graphics g, Font fnt, String s) {
      return g.getFontMetrics(fnt).stringWidth(s);
   }

   public int getHeight(Graphics g, Font fnt) {
      return g.getFontMetrics(fnt).getAscent();
   }
}
