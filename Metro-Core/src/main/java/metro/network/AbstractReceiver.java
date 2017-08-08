package metro.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * デフォルトの読み込み動作 (ブロック操作) を定義します。
 */
public abstract class AbstractReceiver implements Receiver {

  @Override
  public int receiveDirect(byte[] buffer) throws IOException {
    return receiveDirectRange(0, buffer.length, buffer);
  }

  @Override
  public int receiveDirectRange(int start, int end, byte[] buffer) throws IOException {
    int total = end - start;
    if (total > buffer.length) throw new ArrayIndexOutOfBoundsException("overflow: buffer.length < " + (end - start));
    for (int i = start; i < end; ++i) {
      int data = receiveOnce();
      if (data == -1) break;
      buffer[i] = (byte) (data + Byte.MIN_VALUE);
    }
    return total;
  }

  @Override
  public int receiveDirect(ByteBuffer buffer) throws IOException {
    return receiveDirectRange(0, buffer.limit(), buffer.array());
  }

  @Override
  public abstract int receiveOnce() throws IOException;

  @Override
  public IntStream stream() throws IOException {
    // not implemented yet
    throw new NoSuchMethodError("not implemented");
  }

}
