package java.util;

import org.cheminfo.utils.JSException;

public class Base64 {
  public static Encoder getEncoder() {
    JSException.throwUnimplemented();
    return null;
  }

  public static Decoder getDecoder() {
    JSException.throwUnimplemented();
    return null;
  }

  public class Decoder {
    public byte[] decode(byte[] src) {
      JSException.throwUnimplemented();
      return null;
    }

    public byte[] decode(String src) {
      JSException.throwUnimplemented();
      return null;
    }
  }

  public class Encoder {
    public byte[] encode(byte[] src) {
      JSException.throwUnimplemented();
      return null;
    }

    public String encodeToString(byte[] src) {
      JSException.throwUnimplemented();
      return null;
    }
  }
}
