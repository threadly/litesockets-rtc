package org.threadly.litesockets.protocols.sip.request;

import java.util.List;

import org.threadly.litesockets.protocols.sip.common.SIPConstants;
import org.threadly.litesockets.protocols.sip.common.SIPHeaders;
import org.threadly.litesockets.protocols.sip.common.SIPPayload;
import org.threadly.litesockets.protocols.sip.common.SIPProtocolException;
import org.threadly.litesockets.protocols.sip.common.SIPUtils;

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
  
  public String getCSeq() {
    return sh.getCSeq();
  }
  
  public String getFrom() {
    return sh.getFrom();
  }
  
  public String getTo() {
    return sh.getTo();
  }
  
  public int getContentLength() {
    String x = sh.getHeaderValue(SIPConstants.SIP_HEADER_KEY_CONTENT_LENGTH);
    if(x == null) {
      return 0;
    } 
    return Integer.parseInt(x);
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
