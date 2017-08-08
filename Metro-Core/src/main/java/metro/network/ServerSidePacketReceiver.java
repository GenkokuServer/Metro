package metro.network;

import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.nio.channels.SelectionKey.OP_READ;

public class ServerSidePacketReceiver<T extends SelectableChannel & ReadableByteChannel> extends AbstractReceiver {

  @Getter
  private final T targetChannel;

  private final AtomicBoolean alive = new AtomicBoolean(true);

  public boolean isAlive() {
    return alive.get();
  }

  public void setAlive(boolean alive) {
    this.alive.set(alive);
  }

  public void shutdown() {
    this.alive.set(false);
  }

  private final ByteBuffer buffer;

  private final Selector selector;

  public ServerSidePacketReceiver(T channel) throws IOException {
    this.targetChannel = channel;
    this.selector = Selector.open();
    this.buffer = ByteBuffer.allocate(8192);

    channel.configureBlocking(false);
    channel.register(selector, OP_READ);
  }

  void selectInternal() throws IOException {
    int count = selector.selectNow();
    if (count <= 0) return;
    Iterator<SelectionKey> selected = selector.selectedKeys().iterator();
    while (selected.hasNext()) {
      SelectionKey key = selected.next();
      selected.remove();
      processKey(key);
    }
  }

  private void processKey(SelectionKey key) throws IOException {
    if (key.isReadable()) {
      targetChannel.read(buffer);
    }
  }

  @Override
  public int receiveOnce() throws IOException {
    if (!buffer.hasRemaining()) return -1;
    return buffer.get();
  }
}
