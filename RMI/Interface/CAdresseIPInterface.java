package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CAdresseIPInterface extends Remote{

	public String getClasse() throws RemoteException;

	public String getBroadcast() throws RemoteException;
	
	public String getReseau() throws RemoteException;
}
