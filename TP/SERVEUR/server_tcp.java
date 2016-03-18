package reseau_tp_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;

public class ServerTcp {
	private int port;
	
	public ServerTcp(int port) throws IOException{
		this.port = port;
		run();
	}
	
	private void run() throws IOException{
		Socket client;
		BufferedWriter out;
		ServerSocket server = new ServerSocket(this.port);
		for(;;){
			client = server.accept();
			out = new BufferedWriter(new OutputStreamWriter(
					client.getOutputStream()));
			String date = new GregorianCalendar().getTime().toString();
			out.write(date);
			out.newLine();
			out.flush();
			client.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			ServerTcp tcp_server = new ServerTcp(5014);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
