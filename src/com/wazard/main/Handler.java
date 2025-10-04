package com.wazard.main;

import com.wazard.main.Game.STATE;
import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {
   ArrayList<GameObject> object = new ArrayList();
   public float spd = 4.0F;

   public Handler() {
   }

   public void tick() {
      for(int i = 0; i < this.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.object.get(i);
         tempObject.tick();
      }

   }

   public void render(Graphics g) {
      Game.STATE var10000 = Game.gameState;
      var10000 = STATE.Game;

      for(int i = 0; i < this.object.size(); ++i) {
         try {
            GameObject tempObject = (GameObject)this.object.get(i);
            tempObject.render(g);
         } catch (Exception var4) {
            var4.getMessage();
         }
      }

   }

   public void addObject(GameObject object) {
      this.object.add(object);
   }

   public void removeObject(GameObject object) {
      this.object.remove(object);
   }

   public void delete() {
      for(int i = 0; i < this.object.size(); ++i) {
         this.removeObject((GameObject)this.object.get(i));
      }

   }

   public void clearEnemys() {
      for(int i = 0; i < this.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.object.get(i);
         if (tempObject.getId() != ID.Player) {
            this.removeObject(tempObject);
            --i;
         }
      }

   }

   public void removePlayer() {
      for(int i = 0; i < this.object.size(); ++i) {
         if (((GameObject)this.object.get(i)).getId() == ID.Player) {
            this.removeObject((GameObject)this.object.get(i));
         }
      }

   }

   public void clearSmallEnemies() {
      for(int i = 0; i < this.object.size(); ++i) {
         GameObject tempObject = (GameObject)this.object.get(i);
         if (tempObject.getId() != ID.Player && tempObject.getId() != ID.EnemyBoss && tempObject.getId() != ID.Coin && tempObject.getId() != ID.Healer && tempObject.getId() != ID.AllyBullet) {
            this.removeObject(tempObject);
            --i;
         }
      }

   }

   public void removebullet() {
      int i = 0;

      do {
         if (((GameObject)this.object.get(i)).getId() == ID.AllyBullet) {
            this.removeObject((GameObject)this.object.get(i));
            break;
         }

         ++i;
      } while(i <= this.object.size());

   }
}
