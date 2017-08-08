package metro.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.channels.SelectionKey.OP_ACCEPT;

/**
 * ノンブロッキングなTCP レシーバを実装したクラスです。
 */
public class AsyncTCPReceiver implements MultiReceiver, Closeable {

  protected static final ExecutorService RECEIVER_THREAD_POOL = Executors.newCachedThreadPool();

  private static final long TIMEOUT_TIME = 1000L;

  private ServerSocketChannel channel;
  private Selector selector;

  private final List<NetworkClient> clientList = new ArrayList<>();
  private final Map<NetworkClient, ServerSidePacketReceiver> receiverMap = new HashMap<>();

  private final AtomicBoolean alive = new AtomicBoolean(true);

  /**
   * 指定されたソケット アドレスで、新規 {@link AsyncTCPReceiver} を構築します。
   *
   * @param bindAddress 使用するソケット アドレス。
   * @throws IOException 構築と初期化時に問題が発生した場合。
   */
  public AsyncTCPReceiver(InetSocketAddress bindAddress) throws IOException {
    channel = ServerSocketChannel.open();
    channel.configureBlocking(false);
    channel.bind(bindAddress);

    selector = Selector.open();
    channel.register(selector, OP_ACCEPT);

    RECEIVER_THREAD_POOL.execute(this::selectInternal);
  }

  private void selectInternal() {
    try {
      while (alive.get()) {
        selector.select(TIMEOUT_TIME);
        Iterator<SelectionKey> selected = selector.selectedKeys().iterator();
        while (selected.hasNext()) {
          SelectionKey key = selected.next();
          selected.remove();
          if (key.isAcceptable()) {
            SocketChannel client = channel.accept();
            SocketAddress address = client.getRemoteAddress();
            NetworkClient clientData = new NetworkClient(address, UUID.nameUUIDFromBytes(address.toString().getBytes()));
            clientList.add(clientData);
            ServerSidePacketReceiver clientReceiver = new ServerSidePacketReceiver<>(client, selector);
            receiverMap.put(clientData, clientReceiver);
          }

          for (ServerSidePacketReceiver receiver : receiverMap.values()) {
            receiver.processKey(key);
          }
        }
      }
    } catch (ClosedChannelException | ClosedSelectorException expected) {
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void shutdown() {
    alive.set(false);
  }

  @Override
  public void close() throws IOException {
    shutdown();
    selector.wakeup();
    try (@SuppressWarnings("unused") Closeable ignored = selector) {}
    try (@SuppressWarnings("unused") Closeable ignored = channel) {}
  }

  @Override
  public Collection<NetworkClient> getIdentities() {
    return Collections.unmodifiableCollection(clientList);
  }

  @Override
  public Receiver getReceiver(NetworkClient identity) {
    return receiverMap.get(identity);
  }
}
