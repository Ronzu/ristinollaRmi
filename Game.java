package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
		
	// otetaan k‰yttˆˆn 0 1 2 jotta tiedet‰‰n onko kenen vuoro ja
	// pelin p‰‰ttyminen
	public static final int MYTURN = 0;
	public static final int ENDGAME = 1;
	public static final int OPTURN = 2;
		
	
	public int getPlayer(Player player) throws RemoteException;
	
	
	/*
	 * Palauttaa pelin tilan pelaajalle, jotta tiedet‰‰n milloin on oma vuoro.
	 */
	int isItMyTurn(Player player) throws RemoteException;
	
	/* 
	 * tekee muutoksen pelilautaan.
	 */
	public boolean makeMove(Player player, int gridPosition) throws RemoteException;

	public boolean isGameAlive() throws RemoteException;
	
	public void printGrid() throws RemoteException;

	int[] getGrid() throws RemoteException;

	void setGrid(int x) throws RemoteException;

	int getWinner() throws RemoteException;


	
	
}