package org.threadly.litesockets.protocols.sip.response;

import java.util.List;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPHeaders;
import org.threadly.litesockets.protocols.sip.common.SIPUtils;

/**
 * Immutable reference of a Sip Response Message.
 * 
 */
public class SIPResponseMessage {

  private final SIPResponseHeader srh;
  private final SIPHeaders sh;
  private volatile String fullResponse;
  
  public SIPResponseMessage(SIPResponseHeader srh, SIPHeaders sh) {
    this.srh = srh;
    this.sh = sh;
  }

  public SIPResponse getResponse() {
    return srh.getSIPResponse();
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
  
  public SIPResponseHeader getResponseHeader() {
    return srh;
  }

  public SIPHeaders getHeaders() {
    return sh;
  }
  
  @Override
  public String toString() {
    if(fullResponse == null) {
      String tmp = srh.toString()+SIPConstants.SIP_NEWLINE+sh.toString()+SIPConstants.SIP_NEWLINE;
      if(SIPUtils.saveMemory()) {
        fullResponse = tmp.intern();
      } else {
        fullResponse = tmp;
      }
    }
    return fullResponse;
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode()+6;
  }
  
}
