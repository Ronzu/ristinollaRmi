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
	
	// Apumetodi sen selvitt�miseen, onko vuorossa oleva pelaaja Clientin pelaaja vai vastustaja.
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
	// t�m� laitetaan sitten Clientin pelip�yt��n
	
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
	 * t�m�h�n voi palauttaa aina true, sill� kun Clientti suljetaan, kuolee my�s 
	 * pelaajaobjekti ja sielt� tulee RemoteException.
	 * T�ll�in voidaan poikkeus hoitaa try-catch-blokilla.
	 */
	@Override
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