package org.threadly.litesockets.protocols.sip.request;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;

/**
 * Immutable reference to a SipRequestHeader (first line of a Sip Request Message).
 * 
 */
public class SIPRequestHeader {

  private final SIPRequest sr;
  private final String sip_uri;
  private final String sip_version;
  private final String fullRequest;
  
  public SIPRequestHeader(SIPRequest sr, String uri, String sip_version) {
    this.sr = sr;
    this.sip_uri = uri.trim();
    this.sip_version = sip_version.trim();
    this.fullRequest = (sr+" "+uri+" "+sip_version);
  }
  
  public SIPRequest getSIPRequest() {
    return sr;
  }

  public String getSIPUri() {
    return sip_uri;
  }

  public String getSIPVersion() {
    return sip_version;
  }

  @Override
  public int hashCode() {
    return fullRequest.hashCode()+4;
  }

  @Override
  public String toString() {
    return this.fullRequest;
  }

  @Override
  public boolean equals(Object o) {
    if(o == this) {
      return true;
    }
    if(o instanceof SIPRequestHeader) {
      if(fullRequest.equals(o.toString())) {
        return true;
      }
    }
    return false;
  }
  
  public static SIPRequestHeader fromString(final String header) {
    String s = header.trim();
    int np = s.indexOf(SIPConstants.SIP_NEWLINE);
    if(np != -1) {
      s = s.substring(0, np);
    }
    String[] ss = s.split(" ");
    return new SIPRequestHeader(SIPRequest.fromString(ss[0].trim()), ss[1].trim(), ss[2].trim());
  }
}
