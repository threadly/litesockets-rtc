package org.threadly.litesockets.protocols.sip.request;

/**
 * Enum that represents different types of sipRequests. 
 * 
 */
public enum SIPRequest {
  INVITE, 
  OPTIONS, 
  ACK, 
  BYE, 
  CANCEL, 
  REGISTER, 
  PRACK, 
  SUBSCRIBE, 
  NOTIFY, 
  PUBLISH, 
  INFO, 
  REFER, 
  MESSAGE, 
  UPDATE;
  
  public static SIPRequest fromString(final String req) {
    try {
      return SIPRequest.valueOf(req);
    } catch(IllegalArgumentException  e) {
      for(SIPRequest s: SIPRequest.values()) {
        if(s.toString().equalsIgnoreCase(req)) {
          return s;
        }
      }
      throw e;
    }
  }
}