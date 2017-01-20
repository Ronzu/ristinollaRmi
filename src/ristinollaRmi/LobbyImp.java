package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LobbyImp extends UnicastRemoteObject implements Lobby {
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> players;
	private ArrayList<Game> games;
	
	
	protected LobbyImp() throws RemoteException {
		super();
	}

	public void findGame(Player player) throws RemoteException {
		/*
		 * Tässä mietin että noi lobbysta löytyvät pelaajat voisi tallettaa jonomuodossa arraylistiin.
		 * 
		 * Sitten jää luuppaamaan, kunnes mestoille on saapunut toinenkin pelaaja. 
		 */
		players.add(player);
		
		
		if (players.size() >= 2) {
			
			games.add(new GameImp(players.remove(0), players.remove(1)));
			
		} else {
			
		}
		
		
	}
}
