package ristinollaRmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try {
			
			Registry registry = LocateRegistry.createRegistry(5099);
			LobbyImp lobby = new LobbyImp();
			
			registry.rebind(Lobby.NAMING, lobby);
			System.out.println("Lobby is up and running");
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
