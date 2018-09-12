package org.threadly.litesockets.protocols.sip.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable reference do all headers in a sip Message.
 * These can be headers in a Response or Request and basically
 * include all the headers except the first line in the Request/Response.
 * 
 */
public class SIPHeaders {
  private final List<String> keys;
  private final List<String> values;
  private final String rawHeaders;

  private SIPHeaders(final String rawHeaders, List<String> keys, List<String> values) {
    this.keys = Collections.unmodifiableList(new ArrayList<>(keys));
    this.values = Collections.unmodifiableList(new ArrayList<>(values));
    if(SIPUtils.saveMemory()) {
      this.rawHeaders = rawHeaders.intern();
    } else {
      this.rawHeaders = rawHeaders;
    }
  }

  /**
   * Returns the first header with this Key value.
   * 
   * @param key  The key to get the value for.
   * @return The value associated with the given key, or null if its not found.
   */
  public String getHeaderValue(String key) {
    int i=0;
    for(String s: keys) {
      if(s.equalsIgnoreCase(key)) {
        return values.get(i);
      }
      i++;
    }
    return null;
  }

  /**
   * Returns all values in order for the given sip header key.
   * 
   * @param key The key to get the values for.
   * @return a list of all values for the given key (or an empty list).
   */
  public List<String> getHeaderValueList(String key) {
    List<String> rvalues = new ArrayList<>();
    int i=0;
    for(String s: keys) {
      if(s.equalsIgnoreCase(key)) {
        rvalues.add(values.get(i));
      }
      i++;
    }
    return rvalues;
  }

  /**
   * Returns the Call-ID Key for this sip Header.
   * 
   * @return the Call-ID value or null if it does not exist.
   */
  public String getCallId() {
    return getHeaderValue(SIPConstants.SIP_HEADER_KEY_CALL_ID);
  }

  public String getTo() {
    return getHeaderValue(SIPConstants.SIP_HEADER_KEY_TO);
  }

  public String getFrom() {
    return getHeaderValue(SIPConstants.SIP_HEADER_KEY_FROM);
  }

  public String getCSeq() {
    return getHeaderValue(SIPConstants.SIP_HEADER_KEY_CALL_SEQUENCE);
  }

  public List<String> getVia() {
    return getHeaderValueList(SIPConstants.SIP_HEADER_KEY_VIA);
  }

  @Override
  public int hashCode() {
    return rawHeaders.hashCode()+4;
  }

  @Override
  public String toString() {
    return this.rawHeaders;
  }

  @Override
  public boolean equals(Object o) {
    if(o == this) {
      return true;
    }
    if(o instanceof SIPHeaders) {
      if(rawHeaders.equals(o.toString())) {
        return true;
      }
    }
    return false;
  }

  public static SIPHeaders fromString(String headerString) throws SIPProtocolException {
    String rawHeaders;
    List<String> keys = new ArrayList<>();
    List<String> values = new ArrayList<>();
    if(headerString.substring(0, headerString.indexOf(SIPConstants.SIP_NEWLINE)).contains(SIPConstants.SIP_20)) {
      headerString = SIPUtils.leftTrim(headerString.substring(headerString.indexOf(SIPConstants.SIP_NEWLINE)));
    }
    if(headerString.endsWith(SIPConstants.SIP_DOUBLE_NEWLINE)) {
      rawHeaders = headerString.substring(0, headerString.length()-2);
    } else if(!headerString.endsWith(SIPConstants.SIP_NEWLINE)) { 
      rawHeaders = (headerString+SIPConstants.SIP_NEWLINE);
    }
    rawHeaders = SIPUtils.leftTrim(headerString);

    String[] rows = headerString.split(SIPConstants.SIP_NEWLINE);
    String cid = null;
    String to = null;
    String from = null;
    String cseq = null;

    for(String h: rows) {
      if (h.isEmpty()) {
        continue;
      }
      int delim = h.indexOf(SIPConstants.SIP_KEY_VALUE_DELIMINATOR);
      if (delim < 0) {
        throw new SIPProtocolException("Header is missing key value delim: " + h);
      }
      String key = h.substring(0, delim).trim();
      String value = h.substring(delim+1).trim();
      switch(key) {
      case SIPConstants.SIP_HEADER_KEY_TO:
        if(to == null) {
          to = value;
        } else {
          throw new SIPProtocolException("Header has multipule To values!: "+to+":"+value );
        }
      case SIPConstants.SIP_HEADER_KEY_FROM:
        if(from == null) {
          from = value;
        } else {
          throw new SIPProtocolException("Header has multipule From values!: "+from+":"+value );
        }
      case SIPConstants.SIP_HEADER_KEY_CALL_ID:
        if(cid == null) {
          cid = value;
        } else {
          throw new SIPProtocolException("Header has multipule Call-ID values!: "+cid+":"+value );
        }
      case SIPConstants.SIP_HEADER_KEY_CALL_SEQUENCE:
        if(cseq == null) {
          cseq = value;
        } else {
          throw new SIPProtocolException("Header has multipule CSeq values!: "+cseq+":"+value );
        }
      default:

      }
      keys.add(key);
      values.add(value);
    }

    return new SIPHeaders(rawHeaders, keys, values);
  }

}
