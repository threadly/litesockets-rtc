package org.threadly.litesockets.protocols.sip;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.threadly.litesockets.protocols.sip.common.SIPProtocolException;
import org.threadly.litesockets.protocols.sip.request.SIPRequestMessage;

public class SIPRequestsTests {

  public static final String SIMPLE_REGISTER = "REGISTER sip:ss2.wcom.com SIP/2.0\r\n"+
      "Via: SIP/2.0/UDP there.com:5060\r\n" + 
      "From: LittleGuy1 <sip:UserB1@there.com>\r\n" + 
      "To: LittleGuy <sip:UserB@there.com>\r\n" + 
      "Call-ID: 123456789@there.com\r\n" + 
      "CSeq: 1 REGISTER\r\n" + 
      "Contact: <sip:UserB@110.111.112.113>\r\n" + 
      "Contact: <sip:+1-972-555-2222@gw1.wcom.com;user=phone>\r\n" + 
      "Contact: tel:+1-972-555-2222\r\n" + 
      "Content-Length: 0\r\n\r\n";
  public static final String SIMPLE_REGISTER2  = "REGISTER sip:ss2.wcom.com SIP/2.0\r\n" + 
  "Via: SIP/2.0/UDP there.com:5060\r\n" + 
  "From: LittleGuy1 <sip:UserB1@there.com>\r\n" + 
  "To: LittleGuy <sip:UserB@there.com>\r\n" + 
  "Call-ID: 123456789@there.com\r\n" + 
  "CSeq: 2 REGISTER\r\n" + 
  "Contact: <sip:UserB@110.111.112.113>\r\n" + 
  "Contact: <sip:+1-972-555-2222@gw1.wcom.com;user=phone>\r\n" + 
  "Contact: tel:+1-972-555-2222\r\n" + 
  "Authorization:Digest username=\"UserB\",\r\n" + 
  "  realm=\"MCI WorldCom SIP\",\r\n" + 
  "  nonce=\"ea9c8e88df84f1cec4341ae6cbe5a359\", opaque=\"\",\r\n" + 
  "  uri=\"sip:ss2.wcom.com\", response=\"dfe56131d1958046689cd83306477ecc\"\r\n" + 
  "Content-Length: 0\r\n\r\n";
  
  public static final String SIMPLE_INVITE = "INVITE sip:UserB@there.com SIP/2.0\r\n" + 
  "Via: SIP/2.0/UDP here.com:5060\r\n" + 
  "From: BigGuy <sip:UserA@here.com>\r\n" + 
  "To: LittleGuy <sip:UserB@there.com>\r\n" + 
  "Call-ID: 12345601@here.com\r\n" + 
  "CSeq: 1 INVITE\r\n" + 
  "Contact: <sip:UserA@100.101.102.103>\r\n" + 
  "Content-Type: application/sdp\r\n" + 
  "Content-Length: 147\r\n" + 
  "\r\n" + 
  "v=0\n" + 
  "o=UserA 2890844526 2890844526 IN IP4 here.com\n" + 
  "s=Session SDP\n" + 
  "c=IN IP4 100.101.102.103\n" + 
  "t=0 0\n" + 
  "m=audio 49172 RTP/AVP 0\n" + 
  "a=rtpmap:0 PCMU/8000";
  
  @Test
  public void testRegisterParse() throws SIPProtocolException {
    SIPRequestMessage srm = SIPRequestMessage.fromString(SIMPLE_REGISTER);
    assertEquals("123456789@there.com", srm.getCallId());
    assertEquals("1 REGISTER", srm.getCSeq());
    assertEquals("LittleGuy <sip:UserB@there.com>", srm.getTo());
    assertEquals("LittleGuy1 <sip:UserB1@there.com>", srm.getFrom());
    assertEquals(0, srm.getContentLength());
  }
  
  @Test
  public void testRegisterParse2() throws SIPProtocolException {
    SIPRequestMessage srm = SIPRequestMessage.fromString(SIMPLE_REGISTER2);
    assertEquals("123456789@there.com", srm.getCallId());
    assertEquals("2 REGISTER", srm.getCSeq());
    assertEquals("LittleGuy <sip:UserB@there.com>", srm.getTo());
    assertEquals("LittleGuy1 <sip:UserB1@there.com>", srm.getFrom());
    assertEquals(0, srm.getContentLength());
    System.out.println(srm.getHeaderValue("Authorization"));
    System.out.println("----------");
    System.out.println(srm.toString());
  }
  
  
  @Test
  public void testInviteParse() throws SIPProtocolException {
    SIPRequestMessage srm = SIPRequestMessage.fromString(SIMPLE_INVITE);
    assertEquals("12345601@here.com", srm.getCallId());
    assertEquals("1 INVITE", srm.getCSeq());
    assertEquals("LittleGuy <sip:UserB@there.com>", srm.getTo());
    assertEquals("BigGuy <sip:UserA@here.com>", srm.getFrom());
    assertEquals(147, srm.getContentLength());
  }
}
