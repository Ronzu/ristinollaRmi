package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
		
	// otetaan k‰yttˆˆn 0 1 2 jotta tiedet‰‰n onko kenen vuoro ja
	// pelin p‰‰ttyminen
	public static final int MYTURN = 0;
	public static final int ENDGAME = 1;
	public static final int OPTURN = 2;
	
	public static final int[] GAMEGRID = {1,2,3,4,5,6,7,8,9};
	public static final int[][] WINNING_POSITIONS = { 
		{0,1,2},
		{3,4,5},
		{6,7,8},
		
		{0,3,6},
		{1,4,7},
		{2,5,8},
		
		{0,4,8},
		{2,4,6}
	};
	
	
	
	public int getPlayer(Player player) throws RemoteException;
	
	
	/*
	 * Palauttaa pelin tilan pelaajalle, jotta tiedet‰‰n milloin on oma vuoro.
	 */
	public int getGameState(Player player) throws RemoteException;
	
	/* 
	 * tekee muutoksen pelilautaan.
	 */
	public void makeMove(Player player, String sign, int gridPosition) throws RemoteException;

	public boolean isGameAlive() throws RemoteException;
	
}
