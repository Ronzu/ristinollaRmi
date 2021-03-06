package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class LobbyImp extends UnicastRemoteObject implements Lobby {
	private static final long serialVersionUID = 1L;
	private ArrayList<Player> players;
	private ArrayList<Game> games;
	
	/*
	 * T�ss� mietin ett� noi lobbysta l�ytyv�t pelaajat voisi tallettaa jonomuodossa arraylistiin.
	 * 
	 * Sitten j�� odottelemaan, kunnes mestoille on saapunut toinenkin pelaaja.
	 */
	
	public LobbyImp() throws RemoteException {
		super();
		players = new ArrayList<Player>();
		games = new ArrayList<Game>();
	}

	/*
	 * TODO: pit�isi tsekata, ett� pelaajat ovat mestoilla, eiv�tk� ole sulkeneet v�liss� ikkunaa. Kirjoitetaan
	 * siis pelaaja-oliolle joku "isAlive"-muuttuja.
	 * 
	 */
	
	public boolean findGame(Player player) throws RemoteException {

		players.add(player); // Pelaaja lis�t��n lobbyyn odottamaan
		
		System.out.println("Pelaaja " + player.getName() + " lis�tty Lobbyyn.");
		
		if (players.size() >= 2 && players.get(0) != null && players.get(0) != null) {
			
			boolean playerIsAlive = false;
			
			try{
				playerIsAlive = player.isAlive();
			} catch (RemoteException e) {
				players.remove(0);
			}
			
			if (playerIsAlive) {
				System.out.println("Peli alkaa. Pelaajat ovat: " + players.get(0).getName() + " ja " + players.get(1).getName());
				games.add(new GameImp(players.remove(0), players.remove(0)));
			}
		}
		
		//Poistetaan loppuneet pelit.
		for (Game game : games) {
			if(game.isGameAlive() == false) {
				games.remove(games.indexOf(game));
			}
		}
		
		return true;
	}
}
