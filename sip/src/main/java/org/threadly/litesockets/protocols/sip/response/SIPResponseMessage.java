package org.threadly.litesockets.protocols.sip.response;

import java.util.List;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPHeaders;
import org.threadly.litesockets.protocols.sip.common.SIPPayload;

/**
 * Immutable reference of a Sip Response Message.
 * 
 */
public class SIPResponseMessage {

  private final SIPResponseHeader srh;
  private final SIPHeaders sh;
  private final SIPPayload payload;
  private volatile String fullResponse;
  
  public SIPResponseMessage(SIPResponseHeader srh, SIPHeaders sh, SIPPayload payload) {
    this.srh = srh;
    this.sh = sh;
    this.payload = payload;
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
    
  public String getFullCSeq() {
    return sh.getFullCSeq();
  }
  
  public long getCSeqNumber() {
    return sh.getCSeqNumber();
  }
  
  public String getFullFrom() {
    return sh.getFullFrom();
  }
  
  public String getFromURI() {
    return sh.getFromURI();
  }
  
  public String getFromUser() {
    return sh.getFromUser();
  }
  
  public String getFromHost() {
    return sh.getFromHost();
  }
  
  public String getFullTo() {
    return sh.getFullTo();
  }
  
  public String getToURI() {
    return sh.getToURI();
  }
  
  public String getToUser() {
    return sh.getToUser();
  }
  
  public String getToHost() {
    return sh.getToHost();
  }
  
  public SIPPayload getPayload() {
    return payload;
  }
  
  public long getContentLength() {
    return sh.getContentLength();
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
      String tmp = srh.toString()+SIPConstants.SIP_NEWLINE+sh.toString()+SIPConstants.SIP_NEWLINE+payload.getAsString();
      fullResponse = tmp;
    }
    return fullResponse;
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode()+6;
  }
  
}
