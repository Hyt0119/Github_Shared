/*
 * PCとBluetooth通信をするEV3側のプログラム
 * */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.BTConnection;
import lejos.utility.Delay;

public class Hybrid_EV3_new {

    public static void main(String[] args) {

        /* 通信用インスタンスの変数の準備 */
        BTConnection connection = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        /* コネクション用インスタンスの作成 */
        BTConnector connector = new BTConnector();

        LCD.clear();
        LCD.drawString("wait connection", 0, 1);
        /* コネクション確立 */
        connection = connector.waitForConnection(0, BTConnection.RAW);

        /* コネクション確立時のエラー処理 */
        if (connection == null) {
            LCD.clear();
            LCD.drawString("connection error!", 0, 1);
            Delay.msDelay(5000);
            System.exit(1);
        }

        /* データ送受信用ストリームの作成 */
        dis = connection.openDataInputStream();
        dos = connection.openDataOutputStream();

        int loop = 100;
//        int buffer = -1;
        String buffer = "";
        for (int i = 0; i < loop; i++) {
            try {
                /* 整数の受信 */
                buffer = dis.readUTF();

            } catch (IOException ioe) {
                /* 受信失敗時のエラー処理 */
                LCD.clear();
                LCD.drawString("reception error!", 0, 1);
                Delay.msDelay(5000);
                System.exit(1);
            }

            LCD.clear();
            LCD.drawString("receive : " + String.valueOf(buffer), 0, 2);
        }

        for (int i = 0; i < loop; i++) {
            try {
//                buffer = "性欲FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK";
//buffer = "〒135-8548 東京都江東区豊洲３丁目７-５";
            	buffer = "AAAAAAAAAAAAAAAAAAA";
                /* 整数の送信 */
//                dos.writeInt(buffer);
                dos.writeUTF(buffer);
                dos.flush();

            } catch (IOException ioe) {
                /* 送信失敗時のエラー処理 */
                LCD.clear();
                LCD.drawString("sending error!", 0, 1);
                Delay.msDelay(5000);
                System.exit(1);
            }

            LCD.clear();
            LCD.drawString("send : " + String.valueOf(buffer), 0, 2);
            Delay.msDelay(100);
        }

        try {

            /* データ送受信用ストリームのクローズ */
            dos.close();
            dis.close();

            /* コネクション切断 */
            connection.close();
            connector.close();

        } catch (IOException ioe) {
            /* コネクション切断時のエラー処理 */
            LCD.clear();
            LCD.drawString("closing error!", 0, 1);
            Delay.msDelay(5000);
            System.exit(1);
        }
    }

}