package ristinollaRmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try {
			
			Registry registry = LocateRegistry.createRegistry(5099); // Luodaan registry porttiin 5099
			LobbyImp lobby = new LobbyImp(); // Uusi lobby, jonka kautta kaikki lopulta hoituu
			registry.rebind(Lobby.NAMING, lobby); // L‰hetet‰‰n Lobby kaikkien saataville.

			// Konsolista n‰hd‰‰n ett‰ hommat skulaa
			System.out.println("Lobby is up and running, in address rmi://localhost:5099/lobby"); 
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
