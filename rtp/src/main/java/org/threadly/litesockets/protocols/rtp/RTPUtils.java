package org.threadly.litesockets.protocols.rtp;

import java.nio.ByteBuffer;

public class RTPUtils {

  public static boolean isRtp(ByteBuffer bb) {
    return bb.remaining() >=12 && bb.getShort(bb.position()) == (short)0x8000;
  }
  
  public static boolean isProbablyRtcp(ByteBuffer bb) {
    return bb.remaining() > 2 && (bb.get(bb.position())&0xff) >> 6 == 2 && (bb.get(bb.position() + 1)&0xff) == 200;
  }
}
