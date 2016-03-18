package reseau_tp_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCPEchoMulti {
	private int port;
	
	public ServerTCPEchoMulti(int port) throws IOException{
		this.port = port;
		run();
	}
	
	private void run() throws IOException{
		Socket client;
		ServerSocket server = new ServerSocket(this.port);
		for(;;){
			client = server.accept();
			ThreadServer thread = new ThreadServer(client);
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		try {
			ServerTCPEchoMulti server = new ServerTCPEchoMulti(5015);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
