package Serveur;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Properties;

public class Serveur {
	
	public static String getAdresseLocale() throws SocketException {
		InetAddress adr = null ;
		Enumeration<InetAddress> listAdr =
		NetworkInterface.getByName("eth0").getInetAddresses();
		while (listAdr.hasMoreElements())
		adr = listAdr.nextElement();
		return adr.getHostAddress() ;
	}
	
	public static void main(String[] args) throws SocketException, RemoteException, UnknownHostException {
		Properties prop = System.getProperties();
		prop.put("java.rmi.server.hostname", getAdresseLocale());
		
		Registry registre;
		registre = LocateRegistry.createRegistry(1099);

		CAdresseIP adresseIp = new CAdresseIP(getAdresseLocale());
		
		registre.rebind("adresseIp", adresseIp);
	}

}
