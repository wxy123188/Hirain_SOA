// automatically generated by the FlatBuffers compiler, do not modify

package com.hirain.hirain.flaterbuffers.hsj;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ExtMirrorServicelnfo extends Struct {
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public ExtMirrorServicelnfo __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public int turnLightStatus() { return bb.get(bb_pos + 0) & 0xFF; }
  public int mirrorStatus() { return bb.get(bb_pos + 1) & 0xFF; }
  public int turnLightSetResult() { return bb.get(bb_pos + 2) & 0xFF; }
  public int mirrorSetResult() { return bb.get(bb_pos + 3) & 0xFF; }

  public static int createExtMirrorServicelnfo(FlatBufferBuilder builder, int turnLightStatus, int mirrorStatus, int turnLightSetResult, int mirrorSetResult) {
    builder.prep(1, 4);
    builder.putByte((byte)mirrorSetResult);
    builder.putByte((byte)turnLightSetResult);
    builder.putByte((byte)mirrorStatus);
    builder.putByte((byte)turnLightStatus);
    return builder.offset();
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public ExtMirrorServicelnfo get(int j) { return get(new ExtMirrorServicelnfo(), j); }
    public ExtMirrorServicelnfo get(ExtMirrorServicelnfo obj, int j) {  return obj.__assign(__element(j), bb); }
  }
}

