package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.client.ClientSocket;

/**
 * socket服务端
 * 
 * @author Together
 *
 * 实现spring的监听接口并重写入口方法,在入口方法里调用startServerSocket()
 */
public class Server {
	
	private ServerSocket serverSocket;
	
	boolean isRun;
	
	/**
	 * 开启连接
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
	 * 关闭连接
	 */
	public void closeServerSocket() {
		try {
			//清掉接收客户端的线程池
			isRun=false;
			List<String> sns=findAllSnCode();
			for(String sn:sns) {
				Constant.clients.get(sn).closeClient();
				Constant.clients.remove(sn);
			}
			//清掉客户端发送消息、接收消息的线程池
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> findAllSnCode() {
		return new ArrayList<String>(Constant.clients.keySet());
	}
	
	/**
	 * 多线程接收客户端连接
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
						//接收到客户端后先解析sn码 校验，考虑是否加入客户端缓存
						Constant.clients.put("sn", new ClientSocket(client));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
			thread.start();//改为交由线程池管理
		}
	}

}
