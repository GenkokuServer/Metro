package metro.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ノンブロッキングなTCP レシーバを実装したクラスです。
 */
public class AsyncTCPReceiver extends AbstractReceiver {

  private SocketChannel channel;
  private ByteBuffer commonBuffer;

  /**
   * 指定されたソケット アドレスで、新規 {@link AsyncTCPReceiver} を構築します。
   *
   * @param bindAddress 使用するソケット アドレス。
   * @throws IOException 構築と初期化時に問題が発生した場合。
   */
  public AsyncTCPReceiver(InetSocketAddress bindAddress) throws IOException {
    channel = SocketChannel.open();
    channel.configureBlocking(false);
    channel.bind(bindAddress);

    commonBuffer = ByteBuffer.allocate(8);
  }

  @Override
  public int receiveOnce() throws IOException {
    channel.read(commonBuffer);
    return commonBuffer.get();
  }
}
