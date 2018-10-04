package org.threadly.litesockets.protocols.sip.common;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class SIPPayload {
  public static final SIPPayload EMPTY_PAYLOAD = new SIPPayload(new byte[0]);

  private final byte[] payload;
  
  SIPPayload(byte[] ba) {
    this.payload = ba;
  }
  
  public ByteBuffer getBytes() {
    return ByteBuffer.wrap(payload).asReadOnlyBuffer();
  }
  
  public String getAsString() {
    return getAsString(Charset.forName("UTF-8"));
  }
  
  public String getAsString(final Charset charSet) {
    return new String(payload, charSet);
  }

  @Override
  public String toString() {
    return "SIPPayload [size:" + payload.length + ", hashCode:"+this.hashCode()+"]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(payload);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SIPPayload other = (SIPPayload) obj;
    if (!Arrays.equals(payload, other.payload))
      return false;
    return true;
  }
}
