package metro.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * Receiver インターフェースは、既定のネットワーク読み込み操作を宣言します。
 * Receiver インターフェースを実装したクラスは、ネットワーク読み込みが実行できます。
 */
public interface Receiver {

  /**
   * パケットを読み込み、指定されたバッファに パケット データ をコピーします。
   *
   * @param buffer 使用するバッファ。
   * @return 読み込んだバイト数
   * @throws IOException 入力中に例外が発生した場合。
   */
  int receiveDirect(byte[] buffer) throws IOException;

  /**
   * パケットを読み込み、指定されたバッファに パケット データ を範囲コピーします。
   *
   * @param start  範囲の初期位置
   * @param end    範囲の終了位置
   * @param buffer 使用するバッファ。
   * @return 読み込まれたバイト数
   * @throws IOException               入力中に例外が発生した場合。
   * @throws IndexOutOfBoundsException 初期位置から終了位置 の範囲がバッファから溢れる場合。
   */
  int receiveDirectRange(int start, int end, byte[] buffer) throws IOException, IndexOutOfBoundsException;

  /**
   * パケットを読み込み、指定されたバッファに パケット データ を範囲コピーします。
   *
   * @param buffer 使用するバッファ。
   * @return 読み込まれたバイト数。
   * @throws IOException 入力中に例外が発生した場合。
   */
  int receiveDirect(ByteBuffer buffer) throws IOException;

  /**
   * パケットを読み込み、1バイト読み込みます。
   *
   * @return 読み込まれたデータ。(符号なし) 読み込めるデータが無い場合は -1。
   * @throws IOException 入出力中に例外が発生した場合。
   */
  int receiveOnce() throws IOException;

  /**
   * パケットを読み込み、ストリームを作成します。
   *
   * @return パケットのストリーム。
   * @throws Exception ストリームの作成時に例外が発生した場合。
   */
  IntStream stream() throws Exception;

}
