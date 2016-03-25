package tp4_reseau;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class ClientChatMulticast {
	final public static int PORT = 2000;
	final public static String HOST = "238.0.0.25";
	
	public static void main(String[] args) throws IOException {

        final FenetreChat fc = new FenetreChat("La folie du chat");
        String pseudo = JOptionPane.showInputDialog("Pseudo", "Poireau Volant") + " : ";
        Scanner sc = new Scanner(System.in);
        final MulticastSocket socket = new MulticastSocket(PORT);
        socket.joinGroup(InetAddress.getByName(HOST));
        
        fc.capturerEntree(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    byte[] msg = (pseudo + fc.getMessage()).getBytes();
                    try {
                        DatagramPacket packet = new DatagramPacket(
                                msg,
                                msg.length,
                                InetAddress.getByName(HOST),
                                PORT
                        );
                        socket.send(packet);
                        fc.effacerChampEnvoi();
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(ClientChatMulticast.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientChatMulticast.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        byte[] buff = new byte[256];
        DatagramPacket packet = new DatagramPacket(buff, buff.length);
        
        for(;;){
            socket.receive(packet);
            String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
            fc.afficherMessage(msg);
        }
    }

}
