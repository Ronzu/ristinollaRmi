package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
		
	// otetaan k�ytt��n 0 1 2 jotta tiedet��n onko kenen vuoro ja
	// pelin p��ttyminen
	public static final int MYTURN = 0;
	public static final int ENDGAME = 1;
	public static final int OPTURN = 2;
		
	
	public int getPlayer(Player player) throws RemoteException;
	
	
	/*
	 * Palauttaa pelin tilan pelaajalle, jotta tiedet��n milloin on oma vuoro.
	 */
	public boolean isItMyTurn(Player player) throws RemoteException;
	
	/* 
	 * tekee muutoksen pelilautaan.
	 */
	public boolean makeMove(Player player, int gridPosition) throws RemoteException;

	public boolean isGameAlive() throws RemoteException;
	
	public void printGrid() throws RemoteException;

	public int[] getGrid() throws RemoteException;

	public void setGrid(int x) throws RemoteException;

	public int getWinner() throws RemoteException;
	
	public Player getPlayerOne() throws RemoteException;

	public Player getPlayerTwo() throws RemoteException;
	
	public Player getOpponent(Player player) throws RemoteException;
	
	
}