package reseau_tp_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.GregorianCalendar;

public class ServerTCPEcho {
	private int port;
	
	public ServerTCPEcho(int port) throws IOException{
		this.port = port;
		run();
	}
	
	private void run() throws IOException{
		Socket client;
		BufferedWriter out;
		BufferedReader in;
		String ligne;
		ServerSocket server = new ServerSocket(this.port);
		for(;;){
			client = server.accept();
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			String date = new GregorianCalendar().getTime().toString();
			out.write(date);
			out.newLine();
			out.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			
			while((ligne = br.readLine())!=null){
				out.write(ligne.toUpperCase());
				out.newLine();
				out.flush();
			}
			br.close();
			client.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			ServerTCPEcho server = new ServerTCPEcho(5015);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
