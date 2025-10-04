package com.wazard.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {
   private BufferedImage sprite;

   public SpriteSheet(BufferedImage ss) {
      this.sprite = ss;
   }

   public BufferedImage grabImage(int col, int row, int width, int height) {
      BufferedImage img = this.sprite.getSubimage(row * 28 - 28, col * 28 - 28, width, height);
      return img;
   }

   public BufferedImage grabBossImage(int col, int row, int width, int height) {
      BufferedImage img = this.sprite.getSubimage(row * 72 - 72, col * 72 - 72, width, height);
      return img;
   }

   public BufferedImage grabProjectyleImage(int col, int row, int width, int height) {
      BufferedImage img = this.sprite.getSubimage(row * 17 - 17, col * 17 - 17, width, height);
      return img;
   }

   public BufferedImage grabBossProjectyleImage(int col, int row, int width, int height) {
      BufferedImage img = this.sprite.getSubimage(row * 100 - 100, col * 100 - 100, width, height);
      return img;
   }
}
