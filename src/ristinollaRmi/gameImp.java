package ristinollaRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class gameImp extends UnicastRemoteObject implements game {
	
	// atribuutit ensiksi
	// helpgrid jossa on valmiit numerot jotta pelaaja saa valita paikan mihin laittaa x tai o
	private int[][] helpGrid = {{1,2,3},{4,5,6},{7,8,9}}; 
	private String[][] grid; // oikea pelialusta 
	private player player1;
	private player player2;
	private boolean gameOn;
	private player turn;
	
	
	
	
	public gameImp(player player1, player player2) throws RemoteException{
		super();
		
		this.player1 = player1;
		this.player2 = player2;
		gameOn = true;
		this.player1.setMarker("X");
		this.player2.setMarker("O");
		
		
		
		
		
	}// gamimp constructor
	
	@Override
	public int getPlayer(player player) throws RemoteException{
		if (player.isMe(player1))
			return 1;
		else if(player.isMe(player2))
			return 2;
		else
			return 0;
	}//getplayer

	public int[][] getHelpgrid() {
		return helpGrid;
	}
	
	@Override
	public void makeMove(player player, String marker) throws RemoteException{
		/* kysyt‰‰n pelaajalta mihin numeroon h‰n haluaa laittaa oman merkin
		 * ja sijoitetaan sen mukaan mihin laitetaan 
		 * helpGrid on sit‰ varten ett‰ pelaaja tiet‰‰ mit‰ numerot vastaa
		 */
		
		Scanner reader = new Scanner(System.in);
		for(int i = 0; i < helpGrid.length; i++){
			for(int j = 0; j < helpGrid.length - 1; j++){
				System.out.print(helpGrid[i][j] + " ");	
			}
			System.out.println();
		}
		System.out.println("Where do you want to put your mark? (choose from help grid)");
		int move = reader.nextInt();
		marker = player.getMarker();
		
		// && grid[move - 1][0] == null tarkoittaa ett‰ gridin paikka on ns.tyhj‰
		
		if(move <= 3 && grid[move - 1][0] == null){
			grid[move - 1][0] = marker;
		}
		else if(move <= 6 && move > 3 && grid[move - 1][0] == null){
			grid[move - 4][1] = marker;
		}
		else if(grid[move - 1][0] == null){
			grid[move - 7][2] = marker;
		}
		reader.close();
	}// makemove
			
		
		
}// gameImp

	
	


