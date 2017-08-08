package metro.network;

import java.util.Collection;

public interface MultiReceiver {

  /**
   * すべてのクライアントの識別子を取得します。
   *
   * @return すべてのクライアントの識別子。
   */
  Collection<NetworkClient> getIdentities();

  /**
   * 識別子から得られるクライアントのReceiverを取得します。
   *
   * @param identity クライアントの識別子。
   * @return クライアントのReceiver。
   */
  Receiver getReceiver(NetworkClient identity);

}
