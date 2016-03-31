package Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Interface.CAdresseIPInterface;

public class Client {

	public static void main(String[] args) {
		try{
			String adrServeur = "0.0.0.0";
			Registry registre = LocateRegistry.getRegistry(adrServeur);
			CAdresseIPInterface stub = (CAdresseIPInterface) registre.lookup("adresseIp");
			String msg = "fail";
			String choice;
			System.out.println("saisissez broadcast, classe ou reseau");
			Scanner sc = new Scanner(System.in);
			choice = sc.next();
			if(choice.equals("broadcast")){
				msg = stub.getBroadcast();
			}
			
			if(choice.equals("classe")){
				msg = stub.getClasse();
			}
			
			if(choice.equals("reseau")){
				msg = stub.getReseau();
			}
			System.out.println(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
