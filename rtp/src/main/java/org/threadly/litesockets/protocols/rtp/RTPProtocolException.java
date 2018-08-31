package org.threadly.litesockets.protocols.rtp;

public class RTPProtocolException extends Exception {

  private static final long serialVersionUID = 831360820068763295L;
  
  RTPProtocolException() {
    super();
  }
  
  RTPProtocolException(Throwable t) {
    super(t);
  }
  RTPProtocolException(String msg) {
    super(msg);
  }
  RTPProtocolException(String msg, Throwable t) {
    super(msg, t);
  }
}
