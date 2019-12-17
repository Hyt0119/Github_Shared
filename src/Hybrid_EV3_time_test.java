/*
 * PC��Bluetooth�ʐM������EV3���̃v���O����
 * */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.hardware.lcd.LCD;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.BTConnection;
import lejos.utility.Delay;

public class Hybrid_EV3_time_test {

    public static void main(String[] args) {

        /* �ʐM�p�C���X�^���X�̕ϐ��̏��� */
        BTConnection connection = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        /* �R�l�N�V�����p�C���X�^���X�̍쐬 */
        BTConnector connector = new BTConnector();

        LCD.clear();
        LCD.drawString("wait connection", 0, 1);
        /* �R�l�N�V�����m�� */
        connection = connector.waitForConnection(0, BTConnection.RAW);

        /* �R�l�N�V�����m�����̃G���[���� */
        if (connection == null) {
            LCD.clear();
            LCD.drawString("connection error!", 0, 1);
            Delay.msDelay(5000);
            System.exit(1);
        }

        /* �f�[�^����M�p�X�g���[���̍쐬 */
        dis = connection.openDataInputStream();
        dos = connection.openDataOutputStream();

        int loop = 100;
//        int buffer = -1;
        String buffer = "";
        for (int i = 0; i < loop; i++) {
            try {
                /* �����̎�M */
                buffer = dis.readUTF();

            } catch (IOException ioe) {
                /* ��M���s���̃G���[���� */
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
//                buffer = "���~FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK/FUCK100000000000000000FUCK";
buffer = "��135-8548 �����s�]����L�F�R���ڂV-�T";
                /* �����̑��M */
//                dos.writeInt(buffer);
                dos.writeUTF(buffer);
                dos.flush();

            } catch (IOException ioe) {
                /* ���M���s���̃G���[���� */
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

            /* �f�[�^����M�p�X�g���[���̃N���[�Y */
            dos.close();
            dis.close();

            /* �R�l�N�V�����ؒf */
            connection.close();
            connector.close();

        } catch (IOException ioe) {
            /* �R�l�N�V�����ؒf���̃G���[���� */
            LCD.clear();
            LCD.drawString("closing error!", 0, 1);
            Delay.msDelay(5000);
            System.exit(1);
        }
    }

}