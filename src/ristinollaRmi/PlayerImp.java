package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * 20.1.
 * Pistetään PlayerImp runnableksi, sillä silloin voidaan hyvin keskustella Gamen kanssa
 */

public class PlayerImp extends UnicastRemoteObject implements Player, Runnable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String marker;
	private Lobby lobby;
	private Game game;
	
	/*
	 * 20.1.
	 * Lisätty uusi konstruktori joka ottaa myös Lobbyn mukaan. 
	 */
	public PlayerImp(Lobby lobby, String name) throws RemoteException {
		this.lobby = lobby;
		this.name = name;
		this.game = null;
		this.marker = "x tai o";
	}
	
	@Override
	public void run(){
		
		System.out.println("The player is ok.");
		System.out.println(name);
	
		/*
		while (true) {
			try {
				if (game != null) {
					
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		*/
		
		
		/* TODO 
		 * 
		 * hae peli lobbysta OK
		 * odottele pelaajaa peliin OK
		 * saa pelimerkit OK, hoituu game-objektin konstruktorissa
		 * piirrä pelilauta OK, Game-objektin konstruktorissa.
		 * aloita peli
		 * 
		 * keksi lisää mitä pitää tehdä
		 */
		
		
	}
	
	/*
	 * 21.1.
	 * Pistetään nyt tämmöinen setGame-metodi tänne, jota peliolio kutsuu laittamalla itsensä parametriksi.
	 * Sitten saadaan pelaajatkin tietoiseksi siitä, minkä peliolion kanssa ollaan tekemisissä.
	 */

	@Override
	public void setGame(Game game) throws RemoteException {
		this.game = game;
	}
	
	@Override
	public Game getGame() throws RemoteException {
		return this.game;
	}
	
	/*
	 * Selvitetään tässä, onko vuorossa tämä pelaaja vai toinen.
	 * 
	 */
	
	@Override
	public boolean isMe(Player player) throws RemoteException{
		if(player == null){
			return false;
		}
		if(this.marker.equals(player.getMarker()) && this.name.equals(player.getName())){
			return true;
		}
		
		return false;
	}
	
	/*
	 * Voidaan tulostaa tietoa pelaajalle.
	 */
	
	@Override
	public void print(String smth) throws RemoteException{ 
		System.out.println(smth);
	}
	
	/*
	 * Marker on pelaajan ns tunnus eli X tai O
	 * tällä voidaan määrätä kumpi laitetaan oikeaan pelipöytään
	 */
	@Override
	public void setMarker(String m) throws RemoteException{
		marker = m;
	}
	
	@Override
	public String getMarker() throws RemoteException{
		return marker;
	}
	
	@Override
	public String getName() throws RemoteException{
		return name;
	}
	
	/*
	 * Testailua varten...
	 */
	
	public String echo() throws RemoteException {
		return this.name + "is alive";
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see ristinollaRmi.Player#isAlive()
	 * 
	 * tämähän voi palauttaa aina true, sillä kun Clientti suljetaan, kuolee myös 
	 * pelaajaobjekti eikä kutsua enää voida suorittaa.
	 * Tällöin voidaan poikkeus hoitaa try-catch-blokilla.
	 */
	public boolean isAlive() throws RemoteException {
		return true;
	}


}
