package org.threadly.litesockets.protocols.stun;

public enum StunMessageMethod {
  RESERVED_1(0x0000),
  BINDING(0x0001),
  RESERVED_2(0x0002),
  ALLOCATE(0x0003),
  REFRESH(0x0004),
  SEND(0x0006),
  DATA(0x0007),
  CREATE_PERMISSION(0x0008),
  CHANNEL_BIND(0x0009);
  
  public final int bits;
  private StunMessageMethod(int bits) {
    this.bits = bits;
  }
  
  public static StunMessageMethod getMethod(int bits) {
    int mm = bits&0x3eef;
    for(StunMessageMethod smm: StunMessageMethod.values()) {
      if(smm.bits == mm) {
        return smm;
      }
    }
    return null;
  }

}
