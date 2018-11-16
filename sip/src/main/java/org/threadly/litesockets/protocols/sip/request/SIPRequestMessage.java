package org.threadly.litesockets.protocols.sip.request;

import java.util.List;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPHeaders;
import org.threadly.litesockets.protocols.sip.common.SIPPayload;
import org.threadly.litesockets.protocols.sip.common.SIPProtocolException;

public class SIPRequestMessage {

  private final SIPRequestHeader srh;
  private final SIPHeaders sh;
  private final SIPPayload payload;
  private volatile String fullRequest;

  public SIPRequestMessage(SIPRequestHeader srh, SIPHeaders sh) {
    this(srh, sh, SIPPayload.EMPTY_PAYLOAD);
  }
  
  public SIPRequestMessage(SIPRequestHeader srh, SIPHeaders sh, SIPPayload spl) {
    this.srh = srh;
    this.sh = sh;
    this.payload = spl;
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
  
  public SIPRequestHeader getRequestHeader() {
    return srh;
  }

  public SIPHeaders getHeaders() {
    return sh;
  }
  
  public static SIPRequestMessage fromString(String sip) throws SIPProtocolException {
    SIPRequestHeader srh = SIPRequestHeader.fromString(sip);
    SIPHeaders sh = SIPHeaders.fromString(sip);
    return new SIPRequestMessage(srh, sh);
  }
  
  @Override
  public String toString() {
    if(fullRequest == null) {
      String tmp = srh.toString()+SIPConstants.SIP_NEWLINE+sh.toString()+SIPConstants.SIP_NEWLINE+payload.getAsString();
      fullRequest = tmp;
    }
    return fullRequest;
  }
  
  @Override
  public int hashCode() {
    return toString().hashCode()+6;
  }
}
