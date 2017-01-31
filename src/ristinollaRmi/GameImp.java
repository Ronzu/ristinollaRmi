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
	
	
	@Override
	public boolean makeMove(Player player, int gridPosition) throws RemoteException {
		
		if(grid[gridPosition] == 0 && playing.isMe(player) == true) {
			
			// Lautaan laitetaan 1 tai 2 riippuen pelaajasta.
			if (player.isMe(player1)) {
				grid[gridPosition] = 1;	
			} else if (player.isMe(player2)) {
				grid[gridPosition] = 2;		
			} else;
			
		} else {
			// Mikäli ruutu on täynnä, palautetaan false.
			return false;
		}
		
		// Lopuksi vaihdetaan vuoro.
		if(playing.isMe(player1)){
			playing = player2;
		}
		else if(playing.isMe(player2)){
			playing = player1;
		}
		return true;
	} // makeMove()

	
	@Override
	public boolean isItMyTurn(Player player) throws RemoteException {
		if (playing.isMe(player)){
			return true;
		}
		else {
			return false;
		}
	}
	
	
	@Override
	public boolean isGameAlive() throws RemoteException {
		return this.gameAlive;
	}
	
	/*
	 * GRIDIN MUOKKAUS
	 */


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
	
	
	// InitGrid alustaa Game-objektin pelilaudan tyhjäksi, täyteen nollia siis.
	 
	protected void initGrid() {
		this.grid = new int[9];
		for(int i = 0; i<9; i++) {
			this.grid[i] = 0;
		}
	}
	
	/*
	 * GETTERIT 
	 */


	@Override
	public int[] getGrid() throws RemoteException{
		return grid;
	}
	
	/*
	 * @Author Ronzu
	 * Tää on nyt tämmöinen purkkaviritys
	 * 
	 * pelaaja1:n voitto : return 1
	 * pelaaja2:n voitto : return 2
	 * pelaaja3:n voitto : return 3
	 */
	
	@Override
	public int getWinner() throws RemoteException{
		
			if(grid[0] == 1 && grid[1] == 1 && grid[2] == 1) return 1;
			if(grid[3] == 1 && grid[4] == 1 && grid[5] == 1) return 1;
			if(grid[6] == 1 && grid[7] == 1 && grid[8] == 1) return 1;
			if(grid[0] == 1 && grid[3] == 1 && grid[6] == 1) return 1;
			if(grid[1] == 1 && grid[4] == 1 && grid[7] == 1) return 1;
			if(grid[2] == 1 && grid[5] == 1 && grid[8] == 1) return 1;
			if(grid[0] == 1 && grid[4] == 1 && grid[8] == 1) return 1;
			if(grid[2] == 1 && grid[4] == 1 && grid[6] == 1) return 1;
		
			if(grid[0] == 2 && grid[1] == 2 && grid[2] == 2) return 2;
			if(grid[3] == 2 && grid[4] == 2 && grid[5] == 2) return 2;
			if(grid[6] == 2 && grid[7] == 2 && grid[8] == 2) return 2;
			if(grid[0] == 2 && grid[3] == 2 && grid[6] == 2) return 2;
			if(grid[1] == 2 && grid[4] == 2 && grid[7] == 2) return 2;
			if(grid[2] == 2 && grid[5] == 2 && grid[8] == 2) return 2;
			if(grid[0] == 2 && grid[4] == 2 && grid[8] == 2) return 2;
			if(grid[2] == 2 && grid[4] == 2 && grid[6] == 2) return 2;
			
			boolean full = true;
			
			for(int i = 0; i < grid.length; i++) {
				if(grid[i] == 0) {
					full = false;
				}
			}
			
			if(full) {
				return 3;
			}
		
			else return 0;
	}

	@Override
	public Player getPlayerOne() throws RemoteException {
		return this.player1;
	}

	@Override
	public Player getPlayerTwo() throws RemoteException {
		return this.player2;
	}
	
	@Override
	public Player getOpponent(Player player) throws RemoteException {
		if (player.isMe(player1))
			return this.player2;
		else if(player.isMe(player2))
			return this.player1;
		else
			return null;
	}
		
}// gameImp

	
	

