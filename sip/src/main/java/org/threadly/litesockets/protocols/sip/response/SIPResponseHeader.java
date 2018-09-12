package org.threadly.litesockets.protocols.sip.response;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPProtocolException;
import org.threadly.litesockets.protocols.sip.common.SIPUtils;

/**
 * Immutable reference of a SipRequestHeader (first line of a Sip Request Message).
 * 
 */
public class SIPResponseHeader {

  private final SIPResponse sr;
  private final String sip_version;
  private final String fullHeader;
  
  public SIPResponseHeader(String sip_version, SIPResponse sr) {
    this.sr = sr;
    this.sip_version = sip_version;
    if(SIPUtils.saveMemory()) {
      this.fullHeader = (sip_version+" "+sr).intern();
    } else {
      this.fullHeader = sip_version+" "+sr;
    }
  }
  
  public SIPResponse getSIPResponse() {
    return sr;
  }

  public String getSIPVersion() {
    return sip_version;
  }

  @Override
  public int hashCode() {
    return fullHeader.hashCode()+6;
  }

  @Override
  public String toString() {
    return this.fullHeader;
  }

  @Override
  public boolean equals(Object o) {
    if(o == this) {
      return true;
    }
    if(o instanceof SIPResponseHeader) {
      if(fullHeader.equals(o.toString())) {
        return true;
      }
    }
    return false;
  }
  
  public static SIPResponseHeader fromString(final String header) throws SIPProtocolException {
    String s = header.trim();
    int np = s.indexOf(SIPConstants.SIP_NEWLINE);
    if(np != -1) {
      s = s.substring(0, np);
    }
    String[] ss = s.split(" ", 1);
    return new SIPResponseHeader(ss[0].trim(), SIPResponse.fromStringCode(ss[1].trim()));
  }
}
