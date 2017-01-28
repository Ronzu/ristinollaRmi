package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameImp extends UnicastRemoteObject implements Game {
	
	// atribuutit ensiksi
	// helpgrid jossa on valmiit numerot jotta pelaaja saa valita paikan mihin laittaa x tai o
	// private int[][] helpGrid = {{1,2,3},{4,5,6},{7,8,9}}; 
	
	/*
	 * 20.1.
	 * Otin helpgridin veks, koska teen Swingillä graafisen käyttöliittymän. 
	 */
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] grid; // oikea pelialusta 
	private Player player1;
	private Player player2;
	private boolean gameAlive;
	private Player playing;
	private boolean turn;
	
	
	
	
	public GameImp(Player player1, Player player2) throws RemoteException {
		super();
		
		initGrid();
		
		this.player1 = player1;
		this.player2 = player2;
		
		gameAlive = true;
		
		this.player1.setMarker("X");
		this.player2.setMarker("O");
		this.player1.setGame(this);
		this.player2.setGame(this);
		
		this.playing = player1;
		this.player1.print("You are player 1");
		this.player2.print("you are player 2");
		
	}// gamimp constructor
	
	/*
	 * Antaa todellisuudessa pelivuoron.
	 */
	
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
	public boolean makeMove(Player player, int gridPosition) throws RemoteException {
		// TODO Auto-generated method stub
		
		if(grid[gridPosition] == 0 && playing.isMe(player) == true) {
			
			if (player.isMe(player1)) {
				grid[gridPosition] = 1;		
				
			}
		else if (player.isMe(player2)) {
				grid[gridPosition] = 2;
				
			}
		} 
		else {
			grid[gridPosition] = 0;
			return false;
		}
		
		if(playing.isMe(player1)){
			playing = player2;
		}
		else if(playing.isMe(player2)){
			playing = player1;
		}

		printGrid();
		return true;
		
	}

	@Override
	public int isItMyTurn(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		if (playing.isMe(player1)){
			return MYTURN;
		}
		else if(playing.isMe(player2)){
			return OPTURN;
		}
		else return ENDGAME;
	}
	
	@Override
	public boolean isGameAlive() throws RemoteException {
		return this.gameAlive;
	}

	public void initGrid() {
		this.grid = new int[9];
		for(int i = 0; i<9; i++) {
			this.grid[i] = 0;
		}
	}
	
	@Override
	public void printGrid() throws RemoteException {
		for(int i = 0;i<grid.length;i++){
			if(i%3 == 0){
				System.out.println();
				System.out.println("+-+-+-+");
				System.out.print("|");
			}
			System.out.print(grid[i] + "|");
		}
		System.out.println();
		System.out.println("+-+-+-+");
	}
	
	@Override
	public int[] getGrid() throws RemoteException{
		return grid;
	}
	
	@Override
	public void setGrid(int x) throws RemoteException{
		grid[x] = 0;
	}
	
		
}// gameImp

	
	

