/*
 * EV3とBluetooth通信をするPC側のプログラム
 * */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;

public class Hybrid_PC {
	public static int get_time(){
		Date now = new Date();      
		Long longTime = new Long(now.getTime() / 1000);
		return longTime.intValue();
	}

    public static void main(String[] args) {

        /*
         * addressはEV3のMacアドレス
         * ただし、bluecoveのアドレス指定では
         * Macアドレスの前に"btspp://"と後に":1"を書き足す
         * (例)Macアドレス : 00:16:53:4B:88:B9の場合
         * 指定するアドレス = "btspp://0016534B88B9:1"
         * */
        final String address = "btspp://0016535DE375:1";

        /* 通信用インスタンス用の変数の準備 */
        Connection con = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;


        try {
            /* 指定したアドレスとのコネクション確立 */
            con = Connector.open(address);

            /* データ送受信用のストリーム作成 */
            dos = new DataOutputStream(((OutputConnection) con).openOutputStream());
            dis = new DataInputStream(((InputConnection) con).openInputStream());

        } catch (IOException ioe) {
            /* コネクション確立失敗時のエラー処理 */
            con = null;
            dos = null;
            dis = null;
            System.exit(1);
        }

        int loop = 100;
        for (int i = 0; i < loop; i++) {
            try {
                /* 整数の送信 */
//                dos.writeInt(i);
                dos.writeUTF(String.valueOf(i * 10) + ":" + String.valueOf(get_time()));
                dos.flush();

            } catch (IOException ioe) {
                /* 送信失敗時のエラー処理 */
                System.err.println(ioe.getMessage());
                System.exit(1);
            }

            System.out.println("send : " + "test" + String.valueOf(i * 10) + ":" + String.valueOf(get_time()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                System.err.println(ie.getMessage());
                System.exit(1);
            }
        }

        String buffer = "";
        for (int i = 0; i < loop; i++) {
            try {
                /* 整数の受信 */
//                buffer = dis.readInt();
                buffer = dis.readUTF();

            } catch (IOException ioe) {
                /* 受信失敗時のエラー処理 */
                System.err.println(ioe.getMessage());
                System.exit(1);
            }

            System.out.println("receive : " + String.valueOf(buffer));
            buffer = "";
        }

        try {
            /* コネクション切断 */
            con.close();

            /* データ送受信用ストリームのクローズ */
            dos.close();
            dis.close();

        } catch (IOException ioe) {
            /* コネクション切断時のエラー処理 */
            System.err.println(ioe.getMessage());
            System.exit(1);
        }

    }

}