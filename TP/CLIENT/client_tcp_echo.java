package reseau_tp_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCPEcho {
	private String hostname;
	private int port;
	public ClientTCPEcho(String hostname,int port) throws IOException{
		this.hostname = hostname;
		this.port = port;
		lancerBW();
	}
	
	private void lancerBW() throws IOException{
		String ligne;
		Socket sockClient = new Socket();
		sockClient.connect(new InetSocketAddress(this.hostname, this.port));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sockClient.getOutputStream()));
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisir une chaîne : ");
		ligne = sc.nextLine();
		sc.close();
		bw.write(ligne);
		bw.newLine();
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
		while((ligne = br.readLine())!=null)
			System.out.println(ligne);
		br.close();
		sockClient.close();
	}
	
	public static void main(String[] args) {
		try {
			ClientTCPEcho client = new ClientTCPEcho("allegro",7);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
