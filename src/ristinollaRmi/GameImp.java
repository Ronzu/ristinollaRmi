package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameImp extends UnicastRemoteObject implements Game {
	
	// atribuutit ensiksi
	// helpgrid jossa on valmiit numerot jotta pelaaja saa valita paikan mihin laittaa x tai o
	// private int[][] helpGrid = {{1,2,3},{4,5,6},{7,8,9}}; 
	
	/*
	 * 20.1.s
	 * Otin helpgridin veks, koska teen Swingillä graafisen käyttöliittymän. 
	 */
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] grid; // oikea pelialusta 
	private Player player1;
	private Player player2;
	private boolean gameAlive;
	private Player turn;
	
	
	
	
	public GameImp(Player player1, Player player2) throws RemoteException {
		super();
		
		this.player1 = player1;
		this.player2 = player2;
		gameOn = true;
		this.player1.setMarker("X");
		this.player2.setMarker("O");
		this.player1.setGame(this);
		this.player2.setGame(this);
		
		
	}// gamimp constructor
	
	@Override
	public int getPlayer(Player player) throws RemoteException{
		if (player.isMe(player1))
			return 1;
		else if(player.isMe(player2))
			return 2;
		else
			return 0;
	}//getplayer

	@Override
	public void makeMove(Player player, String sign) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGameState(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public boolean isGameAlive () {
		return this.gameAlive;
	}

	
		
		
}// gameImp

	
	


