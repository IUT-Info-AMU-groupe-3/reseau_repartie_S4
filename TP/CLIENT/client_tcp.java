package reseau_tp_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTcp {
	private String hostname;
	private int port;
	
	public ClientTcp(String hostname,int port) throws IOException{
		this.hostname = hostname;
		this.port = port;
		lancerBR();
	}
	
	public void lancerBR() throws IOException{
		String ligne;
		Socket sockClient = new Socket();
		sockClient.connect(new InetSocketAddress(this.hostname, this.port));
		BufferedReader br = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
		while((ligne = br.readLine())!=null)
			System.out.println(ligne);
		br.close();
		sockClient.close();
	}
	
	public static void main(String[] args) {
		try {
			ClientTcp client = new ClientTcp("139.124.187.79",5014);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
