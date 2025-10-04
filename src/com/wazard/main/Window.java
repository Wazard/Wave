package com.wazard.main;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {
   private static final long serialVersionUID = -240840600533728354L;

   public Window(int width, int height, String title, Game game) {
      JFrame frame = new JFrame(title);
      frame.setPreferredSize(new Dimension(width, height));
      frame.setMaximumSize(new Dimension(width, height));
      frame.setMinimumSize(new Dimension(width, height));
      frame.setDefaultCloseOperation(3);
      frame.setResizable(false);
      frame.setLocationRelativeTo((Component)null);
      frame.add(game);
      frame.setVisible(true);
      game.start();
   }
}
