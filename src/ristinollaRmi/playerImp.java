package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class playerImp  extends UnicastRemoteObject implements player {
	
	private String name;
	private String marker;
	
	
	
	protected playerImp(String name) throws RemoteException {
		super();
		this.name = name; //pelaajan otetaan talteen 
		this.marker = "x tai o"; //alustava merkki m‰‰ritell‰‰n vasta peli alussa.
	}

	
	@Override
	public boolean isMe(player player) throws RemoteException{
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
	 * t‰ll‰ voidaan katsoa kumpi laitetaan oikeaan pelipˆyt‰‰n
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

}
