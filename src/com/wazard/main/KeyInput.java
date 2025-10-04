package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class KeyInput extends KeyAdapter {
   private Handler handler;
   private boolean[] keyDown = new boolean[4];
   Random r;
   HUD hud;
   protected static float speed;
   private static boolean shoot = false;

   public KeyInput(Handler handler, HUD hud) {
      this.handler = handler;
      this.hud = hud;
      this.keyDown[0] = false;
      this.keyDown[1] = false;
      this.keyDown[2] = false;
      this.keyDown[3] = false;
   }

   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();

      int i;
      for(i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);

         try {
            if (tempObject.getId() == ID.Player && Game.gameState != STATE.CharacterSelection) {
               if (key == 87) {
                  tempObject.setVelY(-this.handler.spd);
                  this.keyDown[0] = true;
                  this.keyDown[1] = false;
               }

               if (key == 83) {
                  tempObject.setVelY(this.handler.spd);
                  this.keyDown[1] = true;
                  this.keyDown[0] = false;
               }

               if (key == 68) {
                  tempObject.setVelX(this.handler.spd);
                  this.keyDown[2] = true;
                  this.keyDown[3] = false;
               }

               if (key == 65) {
                  tempObject.setVelX(-this.handler.spd);
                  this.keyDown[3] = true;
                  this.keyDown[2] = false;
               }
            }
         } catch (Exception var6) {
            var6.getMessage();
         }
      }

      if (key == 27) {
         if (Game.gameState == STATE.Game) {
            Game.gameState = STATE.Menu;
            this.handler.object.clear();
            HUD.HEALTH = 100.0F;
            HUD.level = 1;
            this.hud.score(0);
            this.handler.spd = 4.0F;
            Game.paused = false;
            Spawn.bossFight = false;
            AudioPlayer.getMusic("Soundtrack").loop();

            for(i = 0; i < 25; ++i) {
               this.r = new Random();
               this.handler.addObject(new MenuParticle(this.r.nextInt(Game.WIDTH - 18), this.r.nextInt(Game.HEIGHT - 18), ID.MenuParticle, this.handler));
            }
         } else {
            System.exit(1);
         }
      }

      if (key == 32 && Spawn.bossFight) {
         shoot = true;
      }

      if (key == 81) {
         if (Game.gameState == STATE.Game) {
            Game.gameState = STATE.Shop;
         } else if (Game.gameState == STATE.Shop) {
            Game.gameState = STATE.Game;
         }
      }

      if (key == 80 && (Game.gameState == STATE.Game || Game.gameState == STATE.Shop)) {
         Game.paused = !Game.paused;
      }

      if (key == 16) {
         this.handler.spd = speed / 2.0F;
      }

   }

   public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      if (key == 16) {
         this.handler.spd = speed;
      }

      if (key == 32 && Spawn.bossFight) {
         shoot = false;
      }

      for(int i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);

         try {
            if (tempObject.getId() == ID.Player) {
               if (key == 87) {
                  this.keyDown[0] = false;
               }

               if (key == 83) {
                  this.keyDown[1] = false;
               }

               if (key == 68) {
                  this.keyDown[2] = false;
               }

               if (key == 65) {
                  this.keyDown[3] = false;
               }

               if (!this.keyDown[0] && !this.keyDown[1]) {
                  tempObject.setVelY(0.0F);
               }

               if (!this.keyDown[2] && !this.keyDown[3]) {
                  tempObject.setVelX(0.0F);
               }
            }
         } catch (Exception var6) {
            var6.getMessage();
         }
      }

   }

   public static boolean getshoot() {
      return shoot;
   }
}
