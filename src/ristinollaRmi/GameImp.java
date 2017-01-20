package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class GameImp extends UnicastRemoteObject implements Game {
	
	// atribuutit ensiksi
	// helpgrid jossa on valmiit numerot jotta pelaaja saa valita paikan mihin laittaa x tai o
	private int[][] helpGrid = {{1,2,3},{4,5,6},{7,8,9}}; 
	private String[][] grid; // oikea pelialusta 
	private Player player1;
	private Player player2;
	private boolean gameOn;
	private Player turn;
	
	
	
	
	public GameImp(Player player1, Player player2) throws RemoteException {
		super();
		
		this.player1 = player1;
		this.player2 = player2;
		gameOn = true;
		this.player1.setMarker("X");
		this.player2.setMarker("O");
		
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

		
		
}// gameImp

	
	


