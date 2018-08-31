package org.threadly.litesockets.protocols.rtp;

import java.nio.ByteBuffer;

public class RTPPacket {
  public static final int RTP_VERSION_FIELD_BIT_SHIFT = 6;
  public static final int RTP_PADDING_FIELD_BIT_SHIFT = 5;
  public static final int RTP_PADDING_FIELD_MASK = 0x20;
  public static final int RTP_EXTENTION_FIELD_BIT_SHIFT = 4;
  public static final int RTP_EXTENTION_FIELD_MASK = 0x10;
  public static final int RTP_MARK_FIELD_BIT_SHIFT = 7;
  public static final int RTP_MAX_BYTE = 0xff;
  public static final int RTP_REPORT_FIELD_MASK = 0x1f;
  public static final int RTP_CSRS_FIELD_MASK = 0xf;
  public static final int RTP_CSRS_BYTE_SIZE = 4;
  public static final int RTP_PAYLOAD_FIELD_MASK = 0x7f;
  public static final int RTP_SEQUENCE_NUMBER_FIELD_POS = 2;
  public static final int RTP_SEQUENCE_NUMBER_FIELD_SIZE = 2;
  public static final int RTP_TIMESTAMP_FIELD_POS = RTP_SEQUENCE_NUMBER_FIELD_POS+RTP_SEQUENCE_NUMBER_FIELD_SIZE;
  public static final int RTP_TIMESTAMP_FIELD_SIZE = 4;
  public static final int RTP_SSRC_FIELD_POS = RTP_TIMESTAMP_FIELD_POS+RTP_TIMESTAMP_FIELD_SIZE;
  public static final int RTP_SSRC_FIELD_SIZE = 4; 
  public static final int RTP_HEADER_SIZE = RTP_SSRC_FIELD_POS+RTP_SSRC_FIELD_SIZE;
  
  private final ByteBuffer bb;

  public RTPPacket(ByteBuffer bb) throws RTPProtocolException {
    this(bb,true);
  }

  public RTPPacket(ByteBuffer bb, boolean copyBuffer) throws RTPProtocolException {
    if(RTPUtils.isRtp(bb)) {
      if(copyBuffer) {
        byte[] ba = new byte[bb.remaining()];
        bb.duplicate().get(ba);
        this.bb = ByteBuffer.wrap(ba).asReadOnlyBuffer();
      } else {
        this.bb = bb.slice().asReadOnlyBuffer();
      }
    } else {
      throw new RTPProtocolException();
    }
  }

  public int getVersion() {
    return (bb.get(0) & RTP_MAX_BYTE) >> RTP_VERSION_FIELD_BIT_SHIFT;
  }

  public boolean hasPadding() {
    return ((bb.get(0) & RTP_PADDING_FIELD_MASK) >> RTP_PADDING_FIELD_BIT_SHIFT) == 1;
  }
  

  public boolean hasExtention() {
    return ((bb.get(0) & RTP_EXTENTION_FIELD_MASK) >> RTP_EXTENTION_FIELD_BIT_SHIFT) == 1;
  }
  
  public boolean hasMark() {
    return ((bb.get(1) & RTP_MAX_BYTE) >> RTP_MARK_FIELD_BIT_SHIFT) == 1;
  }

  public int getReportCount() {
    return (bb.get(0) & RTP_REPORT_FIELD_MASK);
  }

  public int getCSRSCount() {
    return (bb.get(0) & RTP_CSRS_FIELD_MASK);
  }

  public int getPayloadType() {
    return (bb.get(1) & RTP_PAYLOAD_FIELD_MASK);
  }
  
  public int getSequenceNumber() {
    return bb.getShort(RTP_SEQUENCE_NUMBER_FIELD_POS);
  }

  public int getTimeStamp() {
    return bb.getInt(RTP_TIMESTAMP_FIELD_POS);
  }

  public int getSSRC() {
    return bb.getInt(RTP_SSRC_FIELD_POS);
  }

  public byte[] getPayload() {
    int pls = bb.remaining() - (RTP_HEADER_SIZE+(this.getCSRSCount()*RTP_CSRS_BYTE_SIZE));
    byte[] ba = new byte[pls];
    ByteBuffer bb2 = bb.duplicate();
    bb2.position(pls);
    bb2.get(ba);
    return ba;
  }

  public ByteBuffer getPayloadBB() {
    int pls = bb.remaining() - (RTP_HEADER_SIZE+(this.getCSRSCount()*RTP_CSRS_BYTE_SIZE));
    ByteBuffer bb2 = bb.duplicate();
    bb2.position(pls);
    return bb2.slice().asReadOnlyBuffer();
  }

  public ByteBuffer getFullBuffer() {
    return bb.asReadOnlyBuffer();
  }

  public RTPPacket duplicate() {
    try {
      return new RTPPacket(bb, false);
    } catch (RTPProtocolException e) {
      throw new RuntimeException(e);
    }
  }

  public RTPPacket changeSequence(int seq) {
    return changeSequenceTimeStamp(seq, this.getTimeStamp());
  }

  public RTPPacket changeSequenceTimeStamp(int seq, int ts) {
    try {
      ByteBuffer nb = ByteBuffer.allocate(bb.remaining());
      nb.put(bb.duplicate());
      nb.flip();
      nb.putShort(RTP_SEQUENCE_NUMBER_FIELD_POS, (short)seq);
      nb.putInt(RTP_TIMESTAMP_FIELD_POS, ts);
      return new RTPPacket(nb);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static RTPPacket createRTPPacket(short seq, int ts, int ssrs, byte[] ba) {
    try {
      byte[] rtpHeader = new byte[RTP_HEADER_SIZE+ba.length];
      ByteBuffer bb = ByteBuffer.wrap(rtpHeader);
      rtpHeader[0] = (byte)(2<<RTP_VERSION_FIELD_BIT_SHIFT);
      bb.putShort(RTP_SEQUENCE_NUMBER_FIELD_POS, seq);
      bb.putInt(RTP_TIMESTAMP_FIELD_POS, ts);
      bb.putInt(RTP_SSRC_FIELD_POS, ssrs);
      System.arraycopy(ba, 0, rtpHeader, RTP_HEADER_SIZE, ba.length);
      return new RTPPacket(bb);
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}

