package org.threadly.litesockets.protocols.sip.common;

public class SIPUtils {
  
  private static volatile boolean saveMemory = false;
  
  private SIPUtils() {}
  
  public static void enableReduceMemory() {
    saveMemory = true;
  }
  
  public static void disableReduceMemory() {
    saveMemory = false;
  }
  
  public static boolean saveMemory() {
    return saveMemory;
  }
  
  public static String leftTrim(String value) {
    int count = 0;
    while(Character.isWhitespace(value.charAt(count))) {
      count++;
    }
    return value.substring(count);
  }
}
