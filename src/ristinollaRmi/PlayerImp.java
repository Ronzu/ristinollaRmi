package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * 20.1.
 * Pistet��n PlayerImp runnableksi, sill� silloin voidaan hyvin keskustella Gamen kanssa
 */

public class PlayerImp extends UnicastRemoteObject implements Player, Runnable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String marker;
	private Lobby lobby;
	private Game game;
	
	/*
	 * 20.1.
	 * Lis�tty uusi konstruktori joka ottaa my�s Lobbyn mukaan. 
	 */
	public PlayerImp(Lobby lobby, String name) throws RemoteException {
		this.lobby = lobby;
		this.name = name;
		this.game = null;
		this.marker = "x tai o";
	}
	
	@Override
	public void run(){

			System.out.println("The game is on.");
		
		
	}
	
	
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
	
	public void print(String smth){ // voidaan tulostaa tietoa pelaajalle
		System.out.println(smth);
	}
	
	/*
	 * Marker on pelaajan ns tunnus eli X tai O
	 * t�ll� voidaan katsoa kumpi laitetaan oikeaan pelip�yt��n
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
	
	public String echo() throws RemoteException {
		return "I'm alive";
	}
	
	/*
	 * 21.1.
	 * Pistet��n nyt t�mm�inen setGame-metodi t�nne, jota peliolio kutsuu laittamalla itsens� parametriksi.
	 * Sitten saadaan pelaajatkin tietoiseksi siit�, mink� peliolion kanssa ollaan tekemisiss�.
	 */

	@Override
	public void setGame(Game game) throws RemoteException {
		this.game = game;
	}

}
