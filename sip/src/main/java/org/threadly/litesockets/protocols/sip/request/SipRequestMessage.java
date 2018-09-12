package org.threadly.litesockets.protocols.sip.request;

import java.util.List;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPHeaders;
import org.threadly.litesockets.protocols.sip.common.SIPUtils;

public class SipRequestMessage {

  private final SIPRequestHeader srh;
  private final SIPHeaders sh;
  private volatile String fullRequest;
  
  public SipRequestMessage(SIPRequestHeader srh, SIPHeaders sh) {
    this.srh = srh;
    this.sh = sh;
  }

  public SIPRequest getRequest() {
    return srh.getSIPRequest();
  }
  
  public String getSIPVersion() {
    return srh.getSIPVersion();
  }
  
  public String getCallId() {
    return sh.getCallId();
  }
  
  public String getCSeq() {
    return sh.getCSeq();
  }
  
  public String getFrom() {
    return sh.getFrom();
  }
  
  public String getTo() {
    return sh.getTo();
  }
  
  public String getHeaderValue(String key) {
    return sh.getHeaderValue(key);
  }
  
  public List<String> getHeaderValueList(String key) {
    return sh.getHeaderValueList(key);
  }
  
  public SIPRequestHeader getRequestHeader() {
    return srh;
  }

  public SIPHeaders getHeaders() {
    return sh;
  }
  
  @Override
  public String toString() {
    if(fullRequest == null) {
      String tmp = srh.toString()+SIPConstants.SIP_NEWLINE+sh.toString()+SIPConstants.SIP_NEWLINE;
      if(SIPUtils.saveMemory()) {
        fullRequest = tmp.intern();
      } else {
        fullRequest = tmp;
      }
    }
    return fullRequest;
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode()+6;
  }
}
