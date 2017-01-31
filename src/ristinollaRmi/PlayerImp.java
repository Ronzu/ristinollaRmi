package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class PlayerImp extends UnicastRemoteObject implements Player{

	private static final long serialVersionUID = 1L;
	private String name;
	private String marker;
	private Game game;
	
	
	public PlayerImp(String name) throws RemoteException {
		this.name = name;
		this.game = null;
		this.marker = "x tai o";
	}
	
	// Apumetodi sen selvittämiseen, onko vuorossa oleva pelaaja Clientin pelaaja vai vastustaja.
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
	
	// Marker on pelaajan ns tunnus eli X tai O
	// tämä laitetaan sitten Clientin pelipöytään
	
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
	 * tämähän voi palauttaa aina true, sillä kun Clientti suljetaan, kuolee myös 
	 * pelaajaobjekti ja sieltä tulee RemoteException.
	 * Tällöin voidaan poikkeus hoitaa try-catch-blokilla.
	 */
	public boolean isAlive() throws RemoteException {
		return true;
	}

	@Override
	public void setGame(Game game) throws RemoteException {
		this.game = game;
	}

	@Override
	public Game getGame() throws RemoteException {
		return this.game;
	}


}
