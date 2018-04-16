package server.exe;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket("127.0.0.1", 9000);
		System.out.println(socket.getLocalPort());
		
	}

}
