package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class CharacterSelection extends MouseAdapter {
   Handler handler;
   HUD hud;
   Random r;
   protected static int column;
   protected static int row;
   private Font fnt4;
   private int[] Next;
   private int[] Previous;
   private int[] Select;
   private int[] back;

   public CharacterSelection(Handler handler, HUD hud) {
      this.fnt4 = new Font("chiller", 4, Game.WIDTH / 50);
      this.Next = new int[4];
      this.Previous = new int[4];
      this.Select = new int[4];
      this.back = new int[4];
      this.handler = handler;
      this.hud = hud;
      this.r = new Random();
      column = 1;
      row = 1;
   }

   public void render(Graphics g) {
      Font fnt = new Font("chiller", 1, Game.WIDTH / 11);
      Font fnt2 = new Font("chiller", 1, Game.WIDTH / 19);
      this.Select[0] = Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Select");
      this.Select[1] = Game.HEIGHT * 3 / 8 - g.getFontMetrics(fnt2).getAscent();
      this.Select[2] = this.getWidth(g, fnt2, "Select") * 2;
      this.Select[3] = g.getFontMetrics(fnt2).getHeight();
      this.Next[0] = Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Select");
      this.Next[1] = Game.HEIGHT * 4 / 8 - g.getFontMetrics(fnt2).getAscent();
      this.Next[2] = this.getWidth(g, fnt2, "Select") * 2;
      this.Next[3] = g.getFontMetrics(fnt2).getHeight();
      this.Previous[0] = Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Select");
      this.Previous[1] = Game.HEIGHT * 5 / 8 - g.getFontMetrics(fnt2).getAscent();
      this.Previous[2] = this.getWidth(g, fnt2, "Select") * 2;
      this.Previous[3] = g.getFontMetrics(fnt2).getHeight();
      this.back[0] = Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Select");
      this.back[1] = Game.HEIGHT * 6 / 8 - g.getFontMetrics(fnt2).getAscent();
      this.back[2] = this.getWidth(g, fnt2, "Select") * 2;
      this.back[3] = g.getFontMetrics(fnt2).getHeight();
      g.setColor(Color.red);
      g.setFont(fnt);
      g.drawString("Select Character", Game.WIDTH / 2 - this.getWidth(g, fnt, "Select Character") / 2, this.getHeight(g, fnt) * 3 / 2);
      g.setFont(fnt2);
      g.setColor(Color.white);
      g.drawString("Select", Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Select") / 2, Game.HEIGHT * 3 / 8);
      g.drawRect(this.Select[0], this.Select[1], this.Select[2], this.Select[3]);
      g.drawString("Next", Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Next") / 2, Game.HEIGHT * 4 / 8);
      g.drawRect(this.Next[0], this.Next[1], this.Next[2], this.Next[3]);
      g.drawString("Previous", Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Previous") / 2, Game.HEIGHT * 5 / 8);
      g.drawRect(this.Previous[0], this.Previous[1], this.Previous[2], this.Previous[3]);
      g.setColor(Color.red);
      g.drawRect(this.back[0], this.back[1], this.back[2], this.back[3]);
      g.drawString("Back", Game.WIDTH * 1 / 4 - this.getWidth(g, fnt2, "Back") / 2, Game.HEIGHT * 6 / 8);
      g.setColor(Color.white);
      g.setFont(this.fnt4);
      g.drawString(Game.Version, this.getHeight(g, this.fnt4) / 2, Game.HEIGHT - this.getHeight(g, this.fnt4) * 2);
      this.Character(g);
   }

   public void mousePressed(MouseEvent e) {
      int mx = e.getX();
      int my = e.getY();
      if (Game.gameState == STATE.CharacterSelection) {
         int i;
         if (this.mouseOver(mx, my, this.Select[0], this.Select[1], this.Select[2], this.Select[3])) {
            if (row >= 2) {
               AudioPlayer.getSound("Denied").play();
            } else {
               AudioPlayer.getSound("MouseClick").play();
               this.handler.removePlayer();
               Game.gameState = STATE.Select;

               for(i = 0; i < 22; ++i) {
                  this.handler.addObject(new MenuParticle(this.r.nextInt(Game.WIDTH - 18), this.r.nextInt(Game.HEIGHT - 18), ID.MenuParticle, this.handler));
               }
            }
         }

         if (this.mouseOver(mx, my, this.Next[0], this.Next[1], this.Next[2], this.Next[3])) {
            if (row >= 2) {
               AudioPlayer.getSound("Denied").play();
            } else if (row == 1) {
               if (column == 3) {
                  AudioPlayer.getSound("SansPick").play();
               }

               if (column < 4) {
                  ++column;
                  AudioPlayer.getSound("MouseClick").play();
               } else if (row < 4) {
                  AudioPlayer.getSound("MouseClick").play();
                  ++row;
                  column = 1;
               } else {
                  AudioPlayer.getSound("Denied").play();
               }
            }
         }

         if (this.mouseOver(mx, my, this.Previous[0], this.Previous[1], this.Previous[2], this.Previous[3])) {
            if (column > 1) {
               --column;
               AudioPlayer.getSound("MouseClick").play();
            } else if (row > 1) {
               --row;
               AudioPlayer.getSound("MouseClick").play();
               column = 4;
            } else {
               AudioPlayer.getSound("Denied").play();
            }
         }

         if (this.mouseOver(mx, my, this.back[0], this.back[1], this.back[2], this.back[3])) {
            AudioPlayer.getSound("MouseClick").play();
            Game.gameState = STATE.Menu;
            this.handler.removePlayer();

            for(i = 0; i < 25; ++i) {
               this.handler.addObject(new MenuParticle(this.r.nextInt(Game.WIDTH - 18), this.r.nextInt(Game.HEIGHT - 18), ID.MenuParticle, this.handler));
            }
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

   public void Character(Graphics g) {
      ((Graphics2D)g).scale(5.0, 5.0);
      this.handler.addObject(new Player((float)(Game.WIDTH * 3 / 24), (float)(Game.HEIGHT / 12), ID.Player, this.handler, this.hud));
   }
}
