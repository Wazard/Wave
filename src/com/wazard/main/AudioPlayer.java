package com.wazard.main;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
   public static Map<String, Sound> soundMap = new HashMap();
   public static Map<String, Music> musicMap = new HashMap();

   public AudioPlayer() {
   }

   public static void init() {
      try {
         soundMap.put("MouseClick", new Sound("res/Mouse Click.wav"));
         musicMap.put("Soundtrack", new Music("res/Menu_soundtrack.wav"));
         musicMap.put("Background", new Music("res/Soundtrack.wav"));
         musicMap.put("Final", new Music("res/BossBattle.wav"));
         musicMap.put("EyeBoss", new Music("res/Witch Soundtrack.wav"));
         musicMap.put("Witch", new Music("res/EyeBossTheme.wav"));
         soundMap.put("Denied", new Sound("res/Error.wav"));
         soundMap.put("Hit", new Sound("res/videoplayback.wav"));
         soundMap.put("SansPick", new Sound("res/SansPick.wav"));
         soundMap.put("Heal", new Sound("res/Heal.wav"));
         soundMap.put("Coin", new Sound("res/CoinPick.wav"));
         soundMap.put("WitchLaugh", new Sound("res/Witch Laugh.wav"));
      } catch (SlickException var1) {
         var1.printStackTrace();
      }

   }

   public static Music getMusic(String key) {
      return (Music)musicMap.get(key);
   }

   public static Sound getSound(String key) {
      return (Sound)soundMap.get(key);
   }
}
