package org.barahi.service.gamelogic;

import java.util.List;

public class RoundLogicHelper {
  public static char generateRandomCharExcluding(List<Character> excludedLetters) {
    String allChars = "abcdefghijklmnopqrstuvwxyz";
    StringBuilder allowed = new StringBuilder();
    for (char c : allChars.toCharArray()) {
      if (!excludedLetters.contains(c)) {
        allowed.append(c);
      }
    }

    int randomIdx = (int) (Math.random() * allowed.length());
    return allowed.charAt(randomIdx);
  }
}
