package ristinollaRmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String... args) {
		try {
			
			Registry registry = LocateRegistry.createRegistry(5099);


				PlayerImp player1 = new PlayerImp("Player 1");
				PlayerImp player2 = new PlayerImp("Player 2");
				
			
				GameImp game = new GameImp(player1, player2);
				registry.rebind("player", player1);
				registry.rebind("player2", player2);
				
				System.out.println("Server is up and running");
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
