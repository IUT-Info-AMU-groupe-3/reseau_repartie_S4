package tp4_reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.GregorianCalendar;

public class ServerUdpdaytime {
	private int port;
	private int nbClients;
	
	public ServerUdpdaytime(int port, int nbClients) {
		this.port = port;
		this.nbClients = nbClients;
	}
	
	public ServerUdpdaytime() {
		this.port = 5003;
		this.nbClients = 100;
	}
	
	public void lancer() {
		byte[] buf = new byte[512];
		try {
			DatagramSocket socket = new DatagramSocket(port);
			DatagramPacket packetReceive = new DatagramPacket(buf,
			buf.length);
			String chaineDate;
			System.out.println("Serveur daytime lancé sur : " + port);
			for (int i = 1; i <= nbClients; i++) {
				socket.receive(packetReceive);
				chaineDate = new
				GregorianCalendar().getTime().toString();
				DatagramPacket packetSend = new DatagramPacket(
				chaineDate.getBytes(),
				chaineDate.getBytes().length,
				packetReceive.getAddress(),
				packetReceive.getPort());
				socket.send(packetSend);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ServerUdpdaytime server = new ServerUdpdaytime();
		server.lancer();
	}
}
