package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Menu extends MouseAdapter {
   private Handler handler;
   private HUD hud;
   private Shop shop;
   private Random r = new Random();
   private Font fnt;
   private Font fnt2;
   private Font fnt3;
   private Font fnt4;
   private int[] play;
   private int[] help;
   private int[] quit;
   private int[] btn;

   public Menu(Handler handler, HUD hud, Shop shop) {
      this.fnt = new Font("chiller", 1, Game.WIDTH / 11);
      this.fnt2 = new Font("chiller", 1, Game.WIDTH / 19);
      this.fnt3 = new Font("chiller", 4, Game.WIDTH / 23);
      this.fnt4 = new Font("chiller", 4, Game.WIDTH / 50);
      this.play = new int[4];
      this.help = new int[4];
      this.quit = new int[4];
      this.btn = new int[4];
      this.hud = hud;
      this.handler = handler;
      this.shop = shop;
   }

   public void mousePressed(MouseEvent e) {
      int mx = e.getX();
      int my = e.getY();
      if (Game.gameState == STATE.Menu) {
         if (this.mouseOver(mx, my, this.play[0], this.play[1], this.play[2], this.play[3])) {
            AudioPlayer.getSound("MouseClick").play();
            this.hud.score(0);
            this.hud.setLevel(1);
            HUD.maxHealth = 100.0F;
            this.shop.ResetCosts();
            this.handler.spd = 4.0F;
            Spawn.scoreKeep = 0;
            this.shop.setCont(0);
            this.hud.totalScore(0);
            Shop.bonusHealth = 75.0F;
            Spawn.lastlevel = 1;
            Spawn.control = true;
            Game.gameState = STATE.CharacterSelection;
            return;
         }

         if (this.mouseOver(mx, my, this.quit[0], this.quit[1], this.quit[2], this.quit[3])) {
            AudioPlayer.getSound("MouseClick").play();
            System.exit(1);
         }

         if (this.mouseOver(mx, my, this.help[0], this.help[1], this.help[2], this.help[3])) {
            AudioPlayer.getSound("MouseClick").play();
            Game.gameState = STATE.Help;
         }

         if (this.mouseOver(mx, my, this.btn[0], this.btn[1], this.btn[2], this.btn[3])) {
            AudioPlayer.getSound("MouseClick").play();
            Game.gameState = STATE.Patch;
            this.handler.clearEnemys();
         }
      }

      if (Game.gameState == STATE.Select) {
         if (this.mouseOver(mx, my, this.play[0], this.play[1], this.play[2], this.play[3])) {
            AudioPlayer.getSound("MouseClick").play();
            AudioPlayer.getMusic("Background").loop();
            Game.gameState = STATE.Game;
            this.handler.clearEnemys();
            this.handler.addObject(new Player((float)(Game.WIDTH / 2 - 28), (float)(Game.HEIGHT / 2 - 28), ID.Player, this.handler, this.hud));
            this.handler.addObject(new BasicEnemy((float)this.r.nextInt(Game.WIDTH - 18), (float)this.r.nextInt(Game.HEIGHT - 18), ID.BasicEnemy, this.handler));
            Game.diff = true;
         }

         if (this.mouseOver(mx, my, this.quit[0], this.quit[1], this.quit[2], this.quit[3])) {
            AudioPlayer.getSound("MouseClick").play();
            Game.gameState = STATE.CharacterSelection;
            return;
         }

         if (this.mouseOver(mx, my, this.help[0], this.help[1], this.help[2], this.help[3])) {
            AudioPlayer.getSound("MouseClick").play();
            AudioPlayer.getMusic("Background").loop();
            Game.gameState = STATE.Game;
            this.handler.clearEnemys();
            this.handler.addObject(new Player((float)(Game.WIDTH / 2 - 28), (float)(Game.HEIGHT / 2 - 28), ID.Player, this.handler, this.hud));
            this.handler.addObject(new HardEnemy((float)this.r.nextInt(Game.WIDTH - 18), (float)this.r.nextInt(Game.HEIGHT - 18), ID.BasicEnemy, this.handler));
            Game.diff = false;
         }

         if (this.mouseOver(mx, my, this.btn[0], this.btn[1], this.btn[2], this.btn[3])) {
            AudioPlayer.getSound("Denied").play();
         }
      }

      if (Game.gameState == STATE.Help) {
         if (this.mouseOver(mx, my, this.quit[0], this.quit[1], this.quit[2], this.quit[3])) {
            AudioPlayer.getSound("MouseClick").play();
            Game.gameState = STATE.Menu;
         }

      } else if (Game.gameState == STATE.End) {
         if (this.mouseOver(mx, my, this.quit[0], this.quit[1], this.quit[2], this.quit[3])) {
            AudioPlayer.getSound("MouseClick").play();
            AudioPlayer.getMusic("Soundtrack").loop();
            HUD.HEALTH = 100.0F;
            HUD.level = 1;
            this.hud.score(0);
            this.handler.spd = 4.0F;
            Spawn.bossFight = false;
            Game.gameState = STATE.Menu;
         }

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

   public void render(Graphics g) {
      if (Game.gameState == STATE.Help) {
         this.quit[0] = Game.WIDTH * 10 / 11 - this.getWidth(g, this.fnt2, "Back") * 3 / 4;
         this.quit[1] = Game.HEIGHT * 10 / 11 - g.getFontMetrics(this.fnt2).getAscent();
         this.quit[2] = this.getWidth(g, this.fnt2, "Back") * 6 / 4;
         this.quit[3] = g.getFontMetrics(this.fnt2).getHeight();
         g.setFont(this.fnt);
         g.setColor(Color.red);
         g.drawString("Help", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "WAVE") / 2, this.getHeight(g, this.fnt) * 3 / 2);
         g.setColor(Color.white);
         g.setFont(this.fnt4);
         g.drawString(Game.Version, this.getHeight(g, this.fnt4) / 2, Game.HEIGHT - this.getHeight(g, this.fnt4) * 2);
         g.setFont(this.fnt3);
         g.drawString("Use WASD to move the player and dodge the enemies.", 30, Game.HEIGHT * 3 / 10);
         g.drawString("Press SPACE to shoot (only availble during bossfight).", 30, Game.HEIGHT * 4 / 10);
         g.drawString("Press Q to open ingame Shop.", 30, Game.HEIGHT * 5 / 10);
         g.drawString("Press and hold SHIFT to halve your character's moovement speed.", 30, Game.HEIGHT * 6 / 10);
         g.drawString("Press P to pause the game.", 30, Game.HEIGHT * 7 / 10);
         g.setColor(Color.red);
         g.setFont(this.fnt2);
         g.drawString("Back", Game.WIDTH * 10 / 11 - this.getWidth(g, this.fnt2, "Back") / 2, Game.HEIGHT * 10 / 11);
         g.drawRect(this.quit[0], this.quit[1], this.quit[2], this.quit[3]);
      } else if (Game.gameState == STATE.Menu) {
         this.play[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "XXXXx");
         this.play[1] = Game.HEIGHT * 3 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.play[2] = this.getWidth(g, this.fnt2, "XXXxX") * 2;
         this.play[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.help[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "XXXXx");
         this.help[1] = Game.HEIGHT * 4 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.help[2] = this.getWidth(g, this.fnt2, "XXXXx") * 2;
         this.help[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.quit[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "XXXXx");
         this.quit[1] = Game.HEIGHT * 6 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.quit[2] = this.getWidth(g, this.fnt2, "XXXXx") * 2;
         this.quit[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.btn[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "XXXXx");
         this.btn[1] = Game.HEIGHT * 5 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.btn[2] = this.getWidth(g, this.fnt2, "XXXXx") * 2;
         this.btn[3] = g.getFontMetrics(this.fnt2).getHeight();
         g.setFont(this.fnt);
         g.setColor(Color.red);
         g.drawString("WAVE", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "WAVE") / 2, this.getHeight(g, this.fnt) * 3 / 2);
         g.setFont(this.fnt2);
         g.setColor(Color.white);
         g.drawRect(this.play[0], this.play[1], this.play[2], this.play[3]);
         g.drawString("Play", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Play") / 2, Game.HEIGHT * 3 / 8);
         g.drawRect(this.btn[0], this.btn[1], this.btn[2], this.btn[3]);
         g.drawString("Help", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Help") / 2, Game.HEIGHT * 4 / 8);
         g.drawRect(this.help[0], this.help[1], this.help[2], this.help[3]);
         g.drawString("Patch Notes", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Patch Notes") / 2, Game.HEIGHT * 5 / 8);
         g.setFont(this.fnt4);
         g.drawString(Game.Version, this.getHeight(g, this.fnt4) / 2, Game.HEIGHT - this.getHeight(g, this.fnt4) * 2);
         g.setColor(Color.red);
         g.setFont(this.fnt2);
         g.drawRect(this.quit[0], this.quit[1], this.quit[2], this.quit[3]);
         g.drawString("Quit", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Quit") / 2, Game.HEIGHT * 6 / 8);
      } else if (Game.gameState == STATE.End) {
         this.quit[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Try again");
         this.quit[1] = Game.HEIGHT * 4 / 5 - g.getFontMetrics(this.fnt2).getAscent();
         this.quit[2] = this.getWidth(g, this.fnt2, "Try again") * 2;
         this.quit[3] = g.getFontMetrics(this.fnt2).getHeight();
         g.setFont(this.fnt);
         g.setColor(Color.red);
         g.drawString("GAME OVER", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "GAME OVER") / 2, this.getHeight(g, this.fnt) * 3 / 2);
         g.setFont(this.fnt3);
         g.setColor(Color.white);
         if (this.hud.getLevel() <= 30) {
            g.drawString("You lost with a score of: " + this.hud.getTotalScore(), Game.WIDTH / 7, Game.HEIGHT * 3 / 8);
         } else {
            g.drawString("You won with a score of: " + this.hud.getTotalScore(), Game.WIDTH / 7, Game.HEIGHT * 3 / 8);
         }

         g.drawString("Level reached: " + this.hud.getLevel(), Game.WIDTH / 7, Game.HEIGHT * 4 / 8);
         g.setFont(this.fnt3);
         g.drawString("TRY AGAIN", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Try again") / 2, Game.HEIGHT * 4 / 5);
         g.drawRect(this.quit[0], this.quit[1], this.quit[2], this.quit[3]);
      } else if (Game.gameState == STATE.Select) {
         this.play[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Normal");
         this.play[1] = Game.HEIGHT * 3 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.play[2] = this.getWidth(g, this.fnt2, "Normal") * 2;
         this.play[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.help[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Normal");
         this.help[1] = Game.HEIGHT * 4 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.help[2] = this.getWidth(g, this.fnt2, "Normal") * 2;
         this.help[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.quit[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Normal");
         this.quit[1] = Game.HEIGHT * 6 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.quit[2] = this.getWidth(g, this.fnt2, "Normal") * 2;
         this.quit[3] = g.getFontMetrics(this.fnt2).getHeight();
         this.btn[0] = Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Normal");
         this.btn[1] = Game.HEIGHT * 5 / 8 - g.getFontMetrics(this.fnt2).getAscent();
         this.btn[2] = this.getWidth(g, this.fnt2, "Normal") * 2;
         this.btn[3] = g.getFontMetrics(this.fnt2).getHeight();
         g.setFont(this.fnt);
         g.setColor(Color.red);
         g.drawString("Select Difficulty", Game.WIDTH / 2 - this.getWidth(g, this.fnt, "Select Difficulty") / 2, this.getHeight(g, this.fnt) * 3 / 2);
         g.setFont(this.fnt2);
         g.setColor(Color.white);
         g.drawRect(this.play[0], this.play[1], this.play[2], this.play[3]);
         g.setColor(Color.green);
         g.drawString("Normal", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Normal") / 2, Game.HEIGHT * 3 / 8);
         g.setColor(Color.orange);
         g.drawString("Hard", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Hard") / 2, Game.HEIGHT * 4 / 8);
         g.setColor(Color.white);
         g.drawRect(this.help[0], this.help[1], this.help[2], this.help[3]);
         g.setColor(Color.magenta);
         g.drawString("Coming Soon", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Coming Soon") / 2, Game.HEIGHT * 5 / 8);
         g.setColor(Color.white);
         g.drawRect(this.btn[0], this.btn[1], this.btn[2], this.btn[3]);
         g.setColor(Color.red);
         g.drawRect(this.quit[0], this.quit[1], this.quit[2], this.quit[3]);
         g.drawString("Back", Game.WIDTH / 2 - this.getWidth(g, this.fnt2, "Back") / 2, Game.HEIGHT * 6 / 8);
         g.setColor(Color.white);
         g.setFont(this.fnt4);
         g.drawString(Game.Version, this.getHeight(g, this.fnt4) / 2, Game.HEIGHT - this.getHeight(g, this.fnt4) * 2);
      }

   }

   public void tick() {
   }

   public int getWidth(Graphics g, Font fnt, String s) {
      return g.getFontMetrics(fnt).stringWidth(s);
   }

   public int getHeight(Graphics g, Font fnt) {
      return g.getFontMetrics(fnt).getAscent();
   }
}
