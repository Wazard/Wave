package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class PatchNotes extends MouseAdapter {
   private Handler handler;
   Random r = new Random();
   private Font fnt;
   private Font fnt2;
   private Font fnt3;
   private Font fnt4;
   private int[] quit;

   public PatchNotes(Handler handler) {
      this.fnt = new Font("chiller", 1, Game.WIDTH / 11);
      this.fnt2 = new Font("chiller", 1, Game.WIDTH / 19);
      this.fnt3 = new Font("arial", 4, Game.WIDTH / 35);
      this.fnt4 = new Font("arial", 4, Game.WIDTH / 50);
      this.quit = new int[4];
      this.handler = handler;
   }

   public void render(Graphics g) {
      this.quit[0] = Game.WIDTH * 10 / 11 - this.getWidth(g, this.fnt2, "Back") * 3 / 4;
      this.quit[1] = Game.HEIGHT * 10 / 11 - g.getFontMetrics(this.fnt2).getAscent();
      this.quit[2] = this.getWidth(g, this.fnt2, "Back") * 3 / 2;
      this.quit[3] = g.getFontMetrics(this.fnt2).getHeight();
      g.setFont(this.fnt);
      g.setColor(Color.red);
      g.drawString("Patch Notes", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "Patch Notes") / 2, this.getHeight(g, this.fnt));
      g.setColor(Color.white);
      g.setFont(new Font("Chiller", 4, Game.WIDTH / 50));
      g.drawString(Game.Version, this.getHeight(g, new Font("Chiller", 4, Game.WIDTH / 50)) / 2, Game.HEIGHT - this.getHeight(g, new Font("Chiller", 4, Game.WIDTH / 50)) * 2);
      g.setFont(this.fnt3);
      g.drawString("Major Updates:", 30, Game.HEIGHT * 3 / 13);
      g.setFont(this.fnt4);
      g.drawString("-LVL 30 boss has been added for normal difficoulty. Play and try to beat him!", 30, Game.HEIGHT * 3 / 13 + this.getHeight(g, this.fnt4) + 5);
      g.drawString("-Added a new enemy.", 30, Game.HEIGHT * 3 / 13 + this.getHeight(g, this.fnt4) * 2 + 5);
      g.setFont(this.fnt3);
      g.drawString("Balances:", 30, Game.HEIGHT * 5 / 13);
      g.setFont(this.fnt4);
      g.drawString("-Red and reversed Vyverns' speed reduced.", 30, Game.HEIGHT * 5 / 13 + this.getHeight(g, this.fnt4) * 2 + 5);
      g.drawString("-Shop's costs are changed.", 30, Game.HEIGHT * 5 / 13 + this.getHeight(g, this.fnt4) * 3 + 5);
      g.drawString("-Heart heal reduced..", 30, Game.HEIGHT * 5 / 13 + this.getHeight(g, this.fnt4) + 5);
      g.setFont(this.fnt3);
      g.drawString("Bug Fixes:", 30, Game.HEIGHT * 7 / 11);
      g.setFont(this.fnt4);
      g.drawString("-Fixed a bug where final score wasn't correctly showed.", 30, Game.HEIGHT * 7 / 11 + this.getHeight(g, this.fnt4) + 5);
      g.drawString("-Fixed a bug where costs were increasing exponentially.", 30, Game.HEIGHT * 7 / 11 + this.getHeight(g, this.fnt4) * 2 + 5);
      g.setColor(Color.red);
      g.setFont(this.fnt2);
      g.drawString("Back", Game.WIDTH * 10 / 11 - this.getWidth(g, this.fnt2, "Back") / 2, Game.HEIGHT * 10 / 11);
      g.drawRect(this.quit[0], this.quit[1], this.quit[2], this.quit[3]);
   }

   public void mousePressed(MouseEvent e) {
      int my = e.getY();
      int mx = e.getX();
      if (this.mouseOver(mx, my, this.quit[0], this.quit[1], this.quit[2], this.quit[3])) {
         AudioPlayer.getSound("MouseClick").play();
         Game.gameState = STATE.Menu;

         for(int i = 0; i < 25; ++i) {
            this.handler.addObject(new MenuParticle(this.r.nextInt(Game.WIDTH - 18), this.r.nextInt(Game.HEIGHT - 18), ID.MenuParticle, this.handler));
         }

      }
   }

   public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
      if (mx > x && mx < x + width) {
         return my > y && my < y + height;
      } else {
         return false;
      }
   }

   public int getWidth(Graphics g, Font fnt, String s) {
      return g.getFontMetrics(fnt).stringWidth(s);
   }

   public int getHeight(Graphics g, Font fnt) {
      return g.getFontMetrics(fnt).getAscent();
   }
}
