package tp4_reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientUdpdaytime {
	private String hostname;
	private int port;
	
	public ClientUdpdaytime(String hostname, int port){
		this.hostname = hostname;
		this.port = port;
		lancer();
	}
	
	public void lancer(){
		String ligne;
		byte[] buf = new byte[512];
		String msg = new String();
			try {
				DatagramSocket socket = new DatagramSocket();
				DatagramPacket packetSend = new DatagramPacket(msg.getBytes(),
						msg.length(), InetAddress.getByName(this.hostname),this.port);
				System.out.println("client daytime lancé vers : "+ this.hostname +":"+ this.port);
				socket.send(packetSend);
				socket.setSoTimeout(2000);
				DatagramPacket date = new DatagramPacket(buf, buf.length);
				socket.receive(date);
				System.out.println(new String(date.getData(),0,date.getLength()));
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	
	public static void main(String[] args) {
		ClientUdpdaytime client = new ClientUdpdaytime("139.124.187.154",5003);

	}

}
