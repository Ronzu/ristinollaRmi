package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
		
	// otetaan käyttöön 0 1 2 jotta tiedetään onko kenen vuoro ja
	// pelin päättyminen
	public static final int MYTURN = 0;
	public static final int ENDGAME = 1;
	public static final int OPTURN = 2;
	
	
	public int getPlayer(Player player) throws RemoteException;
	
	
	public void makeMove(Player player, String sign) throws RemoteException;

}
