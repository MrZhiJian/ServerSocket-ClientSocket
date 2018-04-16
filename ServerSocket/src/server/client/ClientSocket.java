package server.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * �ͻ���
 * 
 * @author Together
 *
 */
public class ClientSocket {

	private Socket client;

	private boolean isRun;

	private OutputStream outputStream;

	private InputStream inputStream;

	public ClientSocket(Socket client) {
		try {
			this.client = client;
			isRun = true;
			outputStream = client.getOutputStream();
			inputStream = client.getInputStream();
			receiveData();
		} catch (Exception e) {
			e.printStackTrace();
			isRun = false;
		}
	}

	/**
	 * ��������
	 * 
	 * @param b
	 */
	public void sendCommand(byte[] b) {
		if (isRun) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// Ԥ��������Ϊ��װ�õ����Ѽ��ܵ�
						outputStream.write(b);
						outputStream.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();// ��Ϊ�����̳߳ع���
		}
	}

	/**
	 * ������ȡ����
	 */
	public void receiveData() {
		while (isRun) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// ���ֽڶ�ȡ����
						int b = inputStream.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();// �����̳߳ع���
		}
	}

	/**
	 * �رտͻ�������
	 */
	public void closeClient() {
		try {
			isRun = false;
			outputStream.close();
			inputStream.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
