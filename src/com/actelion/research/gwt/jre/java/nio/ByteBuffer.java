package java.nio;

import org.cheminfo.utils.JSException;

public class ByteBuffer {
  public static ByteBuffer allocate(int capacity) {
    JSException.throwUnimplemented();
    return null;
  }

  public final byte[] array() {
    JSException.throwUnimplemented();
    return null;
  }

  public static ByteBuffer wrap(byte[] array) {
    return wrap(array, 0, array.length);
  }

  public static ByteBuffer wrap(byte[] array, int offset, int length) {
    JSException.throwUnimplemented();
    return null;
  }

  public double getDouble() {
    JSException.throwUnimplemented();
    return 0.0;
  }

  public ByteBuffer putDouble(double value) {
    JSException.throwUnimplemented();
    return this;
  }

  public DoubleBuffer asDoubleBuffer() {
    JSException.throwUnimplemented();
    return null;
  }

  public int getInt() {
    JSException.throwUnimplemented();
    return 0;
  }

  public ByteBuffer putInt(int value) {
    JSException.throwUnimplemented();
    return this;
  }

  public IntBuffer asIntBuffer() {
    JSException.throwUnimplemented();
    return null;
  }
}