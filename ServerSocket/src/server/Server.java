package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.client.ClientSocket;

/**
 * socket�����
 * 
 * @author Together
 *
 * ʵ��spring�ļ����ӿڲ���д��ڷ���,����ڷ��������startServerSocket()
 */
public class Server {
	
	private ServerSocket serverSocket;
	
	boolean isRun;
	
	/**
	 * ��������
	 */
	public void startServerSocket() {
		try {
			this.serverSocket=new ServerSocket(9000);
			isRun=true;
			receiveClient();
		} catch (IOException e) {
			e.printStackTrace();
			isRun=false;
		}
	}
	
	/**
	 * �ر�����
	 */
	public void closeServerSocket() {
		try {
			//������տͻ��˵��̳߳�
			isRun=false;
			List<String> sns=findAllSnCode();
			for(String sn:sns) {
				Constant.clients.get(sn).closeClient();
				Constant.clients.remove(sn);
			}
			//����ͻ��˷�����Ϣ��������Ϣ���̳߳�
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> findAllSnCode() {
		return new ArrayList<String>(Constant.clients.keySet());
	}
	
	/**
	 * ���߳̽��տͻ�������
	 */
	private void receiveClient() {
		while(isRun) {
			Thread thread=new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Socket client=serverSocket.accept();
						InputStream inputStream=client.getInputStream();
						int b=inputStream.read();
						//���յ��ͻ��˺��Ƚ���sn�� У�飬�����Ƿ����ͻ��˻���
						Constant.clients.put("sn", new ClientSocket(client));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
			thread.start();//��Ϊ�����̳߳ع���
		}
	}

}
