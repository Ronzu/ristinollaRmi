package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
	
	// otetaan k�ytt��n 0 1 2 jotta tiedet��n onko kenen vuoro ja
	// pelin p��ttyminen
	public static final int myturn = 0;
	public static final int endgame = 1;
	public static final int opturn = 2;
	
	public int getPlayer(Player player) throws RemoteException;

	public void makeMove(Player player, String sign) throws RemoteException;

}
