package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Game extends Remote {
		
	public static final int MYTURN = 0;
	public static final int ENDGAME = 1;
	public static final int OPTURN = 2;
		

	//Palauttaa pelin tilan pelaajalle, jotta tiedet‰‰n milloin on oma vuoro.
	public boolean isItMyTurn(Player player) throws RemoteException;
	
	// Tekee muutoksen Game-objektin pelilautaan. Kutsutaan clientist‰. Palauttaa, onko siirto onnistunut.
	public boolean makeMove(Player player, int gridPosition) throws RemoteException;

	// palauttaa onko peli viel‰ k‰ynniss‰.
	public boolean isGameAlive() throws RemoteException;
	
	// Testausmetodi, selvitet‰‰n p‰ivittyykˆ pelilauta oikein. Formatointi katsottu kivaksi komentoriville
	public void printGrid() throws RemoteException;

	// Palauttaa pelilaudan yksiulotteisena arrayna.
	public int[] getGrid() throws RemoteException;

	/* Tarkistaa, onko jompikumpi pelaajista voittanut pelin, tai onko tullut tasapeli. 
	* Palauttaa kokonaisluvun joka kertoo tuloksen
	* Pit‰isi kyll‰kin m‰‰ritell‰ arvot luokan final-muuttujiksi, ettei tarttis arvailla mik‰ on voittonumero...
	*/
	public int getWinner() throws RemoteException;
	
	// Palauttaa pelaajan 1
	public Player getPlayerOne() throws RemoteException;

	// Palauttaa pelaajan 2
	public Player getPlayerTwo() throws RemoteException;
	
	// Palauttaa parametrina annettavan pelaajan vastustajan.
	public Player getOpponent(Player player) throws RemoteException;
	
	
}