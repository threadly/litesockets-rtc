package org.threadly.litesockets.protocols.stun;

public enum StunMessageClass {
  REQUEST(0x0000),
  INDICATION(0x0010),
  SUCCESS(0x0100), 
  FAILURE(0x0110);

  public final int bits;
  private StunMessageClass(int bits) {
    this.bits = bits;
  }

  public static boolean isValidClass(int bits) {
    int mc = (bits & 0x0110);
    for(StunMessageClass ty: StunMessageClass.values()) {
      if(ty.bits == mc) {
        return true;
      }
    }
    return false;
  }
  
  public static StunMessageClass getClass(int bits) {
    int mc = (bits & 0x0110);
    for(StunMessageClass ty: StunMessageClass.values()) {
      if(ty.bits == mc) {
        return ty;
      }
    }
    return null;
  }
}