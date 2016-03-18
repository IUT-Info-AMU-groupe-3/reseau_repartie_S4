package reseau_tp_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.GregorianCalendar;

public class ThreadServer extends Thread{
	private BufferedWriter out;
	private BufferedReader in;
	private String ligne;
	private Socket client;
	public ThreadServer(Socket client){
		this.client = client;
	}
	public void run(){
		try {
			this.out = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
			String date = new GregorianCalendar().getTime().toString();
			this.out.write(date);
			this.out.newLine();
			this.out.flush();
			this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));		
			while((this.ligne = this.in.readLine())!=null){
				this.out.write(ligne.toUpperCase());
				this.out.newLine();
				this.out.flush();
			}
			this.in.close();
			this.client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
