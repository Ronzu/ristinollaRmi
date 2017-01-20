package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerImp extends UnicastRemoteObject implements Player {

	private static final long serialVersionUID = 1L;
	private String name;
	private String marker;
	private Lobby lobby;
	
	/*
	 * 20.1.
	 * Lisätty uusi konstruktori joka ottaa myös Lobbyn mukaan. En tiedä vielä, miksi.
	 */
	public PlayerImp(Lobby lobby, String name) throws RemoteException {
		super();
		this.lobby = lobby;
		this.name = name;
	}
	
	
	
	protected PlayerImp(String name) throws RemoteException {
		super();
		this.name = name; //pelaajan otetaan talteen 
		this.marker = "x tai o"; //alustava merkki määritellään vasta peli alussa.
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
	 * tällä voidaan katsoa kumpi laitetaan oikeaan pelipöytään
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
		return "Täällä ollaan";
	}

}
