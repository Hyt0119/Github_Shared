/*
 * EV3��Bluetooth�ʐM������PC���̃v���O����
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
         * address��EV3��Mac�A�h���X
         * �������Abluecove�̃A�h���X�w��ł�
         * Mac�A�h���X�̑O��"btspp://"�ƌ��":1"����������
         * (��)Mac�A�h���X : 00:16:53:4B:88:B9�̏ꍇ
         * �w�肷��A�h���X = "btspp://0016534B88B9:1"
         * */
        final String address = "btspp://0016535DE375:1";

        /* �ʐM�p�C���X�^���X�p�̕ϐ��̏��� */
        Connection con = null;
        DataOutputStream dos = null;
        DataInputStream dis = null;


        try {
            /* �w�肵���A�h���X�Ƃ̃R�l�N�V�����m�� */
            con = Connector.open(address);

            /* �f�[�^����M�p�̃X�g���[���쐬 */
            dos = new DataOutputStream(((OutputConnection) con).openOutputStream());
            dis = new DataInputStream(((InputConnection) con).openInputStream());

        } catch (IOException ioe) {
            /* �R�l�N�V�����m�����s���̃G���[���� */
            con = null;
            dos = null;
            dis = null;
            System.exit(1);
        }

        int loop = 100;
        for (int i = 0; i < loop; i++) {
            try {
                /* �����̑��M */
//                dos.writeInt(i);
                dos.writeUTF(String.valueOf(i * 10) + ":" + String.valueOf(get_time()));
                dos.flush();

            } catch (IOException ioe) {
                /* ���M���s���̃G���[���� */
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
                /* �����̎�M */
//                buffer = dis.readInt();
                buffer = dis.readUTF();

            } catch (IOException ioe) {
                /* ��M���s���̃G���[���� */
                System.err.println(ioe.getMessage());
                System.exit(1);
            }

            System.out.println("receive : " + String.valueOf(buffer));
            buffer = "";
        }

        try {
            /* �R�l�N�V�����ؒf */
            con.close();

            /* �f�[�^����M�p�X�g���[���̃N���[�Y */
            dos.close();
            dis.close();

        } catch (IOException ioe) {
            /* �R�l�N�V�����ؒf���̃G���[���� */
            System.err.println(ioe.getMessage());
            System.exit(1);
        }

    }

}