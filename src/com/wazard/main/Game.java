// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.wazard.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Game extends Canvas implements Runnable {
   private static final long serialVersionUID = -7986286774350952004L;
   public static int WIDTH = 1180;
   public static int HEIGHT;
   private Thread thread;
   private boolean running = false;
   public static boolean paused;
   public static boolean diff;
   private Handler handler;
   private Random r;
   private HUD hud;
   private Spawn spawner;
   private Menu menu;
   private Shop shop;
   private PatchNotes patchNotes;
   private CharacterSelection characterSelection;
   private int timer = 4000;
   private int timer2 = 4000;
   private float a = 0.0F;
   public static String Version;
   public static STATE gameState;
   public static BufferedImage spriteSheet;
   public static BufferedImage bossSheet;
   public static BufferedImage projectileSheet;
   public static BufferedImage buffSheet;
   public static BufferedImage bossProjectyleImage;

   static {
      HEIGHT = WIDTH / 15 * 9;
      diff = true;
      Version = "Version: 0.2.0";
      gameState = com.wazard.main.Game.STATE.Menu;
   }

   public enum STATE {
      Menu,
      Game,
      Help,
      Shop,
      End,
      Select,
      Patch,
      CharacterSelection;

   private STATE() {
   }
   }


   public Game() {
      BufferedImageLoader loader = new BufferedImageLoader();
      spriteSheet = loader.loadImage("/SpreadSheet.png");
      bossSheet = loader.loadImage("/BossSpriteSheet.png");
      projectileSheet = loader.loadImage("/ProjectileSheet.png");
      buffSheet = loader.loadImage("/BuffSheet.png");
      bossProjectyleImage = loader.loadImage("/BossProjectyle.png");
      this.handler = new Handler();
      this.hud = new HUD();
      this.shop = new Shop(this.handler, this.hud);
      this.patchNotes = new PatchNotes(this.handler);
      this.characterSelection = new CharacterSelection(this.handler, this.hud);
      this.menu = new Menu(this.handler, this.hud, this.shop);
      this.addKeyListener(new KeyInput(this.handler, this.hud));
      this.addMouseListener(this.menu);
      this.addMouseListener(this.shop);
      this.addMouseListener(this.characterSelection);
      this.addMouseListener(this.patchNotes);
      AudioPlayer.init();
      AudioPlayer.getMusic("Soundtrack").loop();
      new Window(WIDTH, HEIGHT, "Wave", this);
      this.spawner = new Spawn(this.handler, this.hud);
      this.r = new Random();
      if (gameState != com.wazard.main.Game.STATE.Game) {
         for(int i = 0; i < 20; ++i) {
            this.handler.addObject(new MenuParticle(this.r.nextInt(WIDTH - 18) * 12 / 10, this.r.nextInt(HEIGHT - 18) * 12 / 10, ID.MenuParticle, this.handler));
         }
      }

   }

   public synchronized void start() {
      this.thread = new Thread(this);
      this.thread.start();
      this.running = true;
   }

   public synchronized void stop() {
      try {
         this.thread.join();
         this.running = false;
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void run() {
      this.requestFocus();
      long lastTime = System.nanoTime();
      double amountOfTicks = 72.0;
      double ns = 1.0E9 / amountOfTicks;
      double delta = 0.0;
      long timer = System.currentTimeMillis();

      while(this.running) {
         long now = System.nanoTime();
         delta += (double)(now - lastTime) / ns;

         for(lastTime = now; delta >= 1.0; --delta) {
            this.tick();
         }

         if (this.running) {
            this.render();
         }

         if (System.currentTimeMillis() - timer > 1000L) {
            timer += 1000L;
         }
      }

      this.stop();
   }

   private void tick() {
      if (gameState == com.wazard.main.Game.STATE.Game) {
         if (!paused) {
            if (this.handler.spd > this.a) {
               this.a = this.handler.spd;
            }

            KeyInput.speed = this.a;
            this.hud.tick();
            this.spawner.tick();
            this.handler.tick();
            if (HUD.HEALTH <= 0.0F || this.hud.getLevel() > 30) {
               gameState = com.wazard.main.Game.STATE.End;
               HUD.HEALTH = 100.0F;
               this.handler.object.clear();

               for(int i = 0; i < 25; ++i) {
                  this.handler.addObject(new MenuParticle(this.r.nextInt(WIDTH - 18), this.r.nextInt(HEIGHT - 18), ID.MenuParticle, this.handler));
               }
            }
         }
      } else if (gameState == com.wazard.main.Game.STATE.Menu || gameState == com.wazard.main.Game.STATE.End || gameState == com.wazard.main.Game.STATE.Select) {
         this.menu.tick();
         this.handler.tick();
      }

   }

   private void render() {
      BufferStrategy bs = this.getBufferStrategy();
      if (bs == null) {
         this.createBufferStrategy(3);
      } else {
         Graphics g = bs.getDrawGraphics();
         g.setColor(Color.black);
         g.fillRect(0, 0, WIDTH, HEIGHT);
         if (paused) {
            if (this.timer >= 0) {
               --this.timer;
               g.setColor(Color.black);
               g.drawString("PAUSED", WIDTH / 2 - 35, HEIGHT * 2 / 3);
            } else {
               g.setColor(Color.yellow);
               g.drawString("PAUSED", WIDTH / 2 - 35, HEIGHT * 2 / 3);
               if (this.timer2 >= 0) {
                  --this.timer2;
               } else {
                  this.timer = 3200;
                  this.timer2 = 4000;
               }
            }
         }

         if (gameState == com.wazard.main.Game.STATE.Game) {
            AffineTransform newTransform = new AffineTransform();
            newTransform.scale(2.0, 2.0);
            newTransform = AffineTransform.getTranslateInstance((double)(WIDTH - 28), (double)(HEIGHT - 28));
            this.handler.render(g);
            this.hud.render(g);
            ((Graphics2D)g).setTransform(newTransform);
         } else if (gameState == com.wazard.main.Game.STATE.Shop) {
            this.shop.render(g);
         } else if (gameState != com.wazard.main.Game.STATE.Menu && gameState != com.wazard.main.Game.STATE.Help && gameState != com.wazard.main.Game.STATE.End && gameState != com.wazard.main.Game.STATE.Select) {
            if (gameState == com.wazard.main.Game.STATE.CharacterSelection) {
               this.handler.delete();
               this.characterSelection.render(g);
               this.handler.render(g);
            } else if (gameState == com.wazard.main.Game.STATE.Patch) {
               this.patchNotes.render(g);
            }
         } else {
            this.handler.removePlayer();
            this.handler.render(g);
            this.menu.render(g);
         }

         g.dispose();
         bs.show();
      }
   }

   public static float clamp(float var, int min, int max) {
      if (var >= (float)max) {
         return var = (float)max;
      } else {
         return var <= (float)min ? (var = (float)min) : var;
      }
   }

   public static void main(String[] args) {
      new Game();
   }
}
