package com.wazard.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Player extends GameObject {
   Handler handler;
   HUD hud;
   private BufferedImage player_image;
   private int timer = 20;

   public Player(float x, float y, ID id, Handler handler, HUD hud) {
      super(x, y, id);
      this.hud = hud;
      this.handler = handler;
      SpriteSheet ss = new SpriteSheet(Game.spriteSheet);
      this.player_image = ss.grabImage(CharacterSelection.row, CharacterSelection.column, 28, 28);
   }

   public void tick() {
      this.x += this.velX;
      this.y += this.velY;
      this.x = (float)((int)Game.clamp(this.x, 0, Game.WIDTH - 35));
      this.y = (float)((int)Game.clamp(this.y, 0, Game.HEIGHT - 58));
      if (KeyInput.getshoot() && Spawn.bossFight) {
         if (this.timer > 0) {
            --this.timer;
         } else {
            this.handler.addObject(new AllyBullet(this.x + 13.5F, this.y, ID.AllyBullet, this.handler));
            this.timer = 20;
         }
      }

      this.collision();
   }

   public void render(Graphics g) {
      g.drawImage(this.player_image, (int)this.x, (int)this.y, (ImageObserver)null);
   }

   private void collision() {
      for(int i = 0; i < this.handler.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.handler.object.get(i);
         if (tempObject.getId() != ID.Player && tempObject.getId() != ID.Trail && this.getBounds().intersects(tempObject.getBounds())) {
            if (tempObject.getId() == ID.BasicEnemy) {
               HUD.HEALTH -= 3.6F;
            } else if (tempObject.getId() == ID.HardEnemy) {
               HUD.HEALTH -= 2.8F;
            } else if (tempObject.getId() == ID.FastEnemy) {
               HUD.HEALTH -= 2.2F;
            } else if (tempObject.getId() == ID.FasterEnemy) {
               HUD.HEALTH -= 2.1F;
            } else if (tempObject.getId() == ID.SmartEnemy) {
               HUD.HEALTH -= 2.4F;
            } else if (tempObject.getId() == ID.EnemyBoss) {
               HUD.HEALTH -= 10.0F;
            } else if (tempObject.getId() == ID.FireBall) {
               HUD.HEALTH -= 3.3F;
            } else if (tempObject.getId() == ID.MagicSphere) {
               HUD.HEALTH -= 6.5F;
            } else if (tempObject.getId() == ID.MagicEnemy) {
               HUD.HEALTH -= 3.2F;
            } else if (tempObject.getId() == ID.Healer) {
               if (HUD.HEALTH < HUD.maxHealth - 15.0F) {
                  HUD.HEALTH += 15.0F;
               } else {
                  HUD.HEALTH = HUD.maxHealth;
               }
            } else if (tempObject.getId() == ID.Coin) {
               this.hud.score(this.hud.getScore() + 100);
            } else if (tempObject.getId() == ID.OneShot) {
               HUD.HEALTH = 0.0F;
            }
         }
      }

   }

   public Rectangle getBounds() {
      return new Rectangle((int)this.x, (int)this.y, 28, 28);
   }
}
