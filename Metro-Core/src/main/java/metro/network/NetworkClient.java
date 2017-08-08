package metro.network;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import metro.misc.NotNull;

import javax.annotation.Nonnull;
import java.net.SocketAddress;
import java.util.UUID;

@Value
public final class NetworkClient {

  @Getter(AccessLevel.PUBLIC)
  private final SocketAddress address;

  @Getter(AccessLevel.PUBLIC)
  private final UUID uuid;

  public NetworkClient(
      @NonNull @Nonnull @NotNull SocketAddress address,
      @NonNull @Nonnull @NotNull UUID uuid
  ) {
    this.address = address;
    this.uuid = uuid;
  }

}
