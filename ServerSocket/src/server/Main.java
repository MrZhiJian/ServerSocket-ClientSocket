package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

		public static void main(String []args) throws IOException {
//			new Server().startServerSocket();
			
			ServerSocket serverSocket=new ServerSocket(9000);
			while(true) {
				Socket client=serverSocket.accept();
				System.out.println(client.getPort());
				System.out.println(client.getLocalPort());
			}
		}
}
