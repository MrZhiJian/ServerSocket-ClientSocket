package server.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 客户端
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
	 * 发送数据
	 * 
	 * @param b
	 */
	public void sendCommand(byte[] b) {
		if (isRun) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 预发送数据为封装好的且已加密的
						outputStream.write(b);
						outputStream.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();// 改为交由线程池管理
		}
	}

	/**
	 * 持续读取数据
	 */
	public void receiveData() {
		while (isRun) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 按字节读取数据
						int b = inputStream.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();// 介入线程池管理
		}
	}

	/**
	 * 关闭客户端连接
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
