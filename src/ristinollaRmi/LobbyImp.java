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
	 * (non-Javadoc)
	 * @see ristinollaRmi.Lobby#findGame(ristinollaRmi.Player)
	 * 
	 * Eli t�ss�h�n t�m� "synchronized" pist�� metodin venailemaan ett� pelaajia on lopulta ainakin kaksi. 
	 * Kommentti l�hinn� omaa oppimista ja ymm�rryst� varten.
	 * 
	 * TODO: pit�isi tsekata, ett� pelaajat ovat mestoilla, eiv�tk� ole sulkeneet v�liss� ikkunaa. Kirjoitetaan
	 * siis pelaaja-oliolle joku "isAlive"-muuttuja.
	 * 
	 */
	
	public synchronized void findGame(Player player) throws RemoteException {

		players.add(player);
		
		if (players.size() >= 2 && players.get(0) != null && players.get(0) != null) {
			
			boolean playerIsAlive = false;
			
			try{
				playerIsAlive = player.isAlive();

			} catch (RemoteException e) {
				players.remove(0);
			}
			if (playerIsAlive) {
				games.add(new GameImp(players.remove(0), players.remove(0)));
				System.out.println("Peli alkanut.");
			}
			

		}
		
		//Poistetaan loppuneet pelit.
		for (Game game : games) {
			if(game.isGameAlive() == false) {
				games.remove(games.indexOf(game));
			}
		}
		
		
	}
}
