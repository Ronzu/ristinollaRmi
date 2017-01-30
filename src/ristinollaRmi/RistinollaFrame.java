package ristinollaRmi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * 
 * T‰ss‰ on nyt luokka peli-ikkunan muodostamista varten.
 * 
 */


public class RistinollaFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	GameButton [][] buttons = new GameButton[3][3]; // Buttonit vastaamaan ristinollaruudukkoa
	JTextField statusbar; // Tilapalkki kertoo pelin tilan
	GamePanel panel; // 
	PlayerImp player;
	Game game;
	GameListener listener; // Kuuntelee napsautukset ja p‰ivitt‰‰ pelin tilaa.
	String status;
	
	/*
	 * 
	 * Konstruktori:
	 * 
	 */
	public RistinollaFrame(PlayerImp player) throws RemoteException {
		this.player = player;
		
		try {
			this.game = player.getGame();

		} catch(RemoteException e) {
			e.printStackTrace();
		}

		listener = new GameListener(game, player);
		
		setLayout(new BorderLayout());
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		statusbar = new JTextField(player.getName());
		statusbar.setEditable(false);
		add(statusbar, BorderLayout.SOUTH);
		setTitle("Ristinolla");
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400,400,400,400);	
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * T‰‰ll‰ p‰ivitell‰‰n sitten k‰yttˆliittym‰‰, jatkuvasti kyll‰kin, onko j‰rkev‰‰, no ei.
	 * 
	 */
	
	@Override
	public void run() {
		
		while(true) {
			try {
				updateView();
				checkWinner();
				Thread.sleep(100);
				
			} catch (RemoteException | InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	protected void updateView() throws RemoteException {
		panel.updateView(this.game.getGrid()); // P‰ivitt‰‰ nappulat
		
		boolean turn = this.game.isItMyTurn(this.player);
		
		
		if (turn) {
			status = "It's your turn.";
		} else {
			status = "It's your opponent's (" + this.game.getOpponent(this.player).getName() + ") turn.";
		}
		
		statusbar.setText(status);
	}
	
	/*
	 * 
	 */
	
	protected void checkWinner() throws RemoteException {
		int winner = game.getWinner();
		
		switch(winner) {
			case 1: JOptionPane.showMessageDialog(null,this.game.getPlayerOne().getName() + " has won!","Gongratulations",JOptionPane.OK_OPTION);
					System.exit(0);	
					break;
			case 2: JOptionPane.showMessageDialog(null,this.game.getPlayerOne().getName() + " has won!","Gongratulations",JOptionPane.OK_OPTION);
					System.exit(0);
					break;
			case 3: JOptionPane.showMessageDialog(null,"Stalemate!",null,JOptionPane.OK_OPTION);
					System.exit(0);
					break;
			default: 
		}
		
		

	}
	

	/*
	 * 
	 * Sis‰luokka GamePanel ristinollaruudukkoa varten.
	 * 
	 */
	class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public GamePanel() {
			setLayout(new GridLayout(3,3));
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++)   {
					buttons[i][j]=new GameButton();
					buttons[i][j].putClientProperty("INDEX", new Integer[]{i,j});
					buttons[i][j].putClientProperty("OWNER", null);
					buttons[i][j].addActionListener(listener);
					buttons[i][j].setIndex(i, j);
					add(buttons[i][j]);
				}			
			}
		}
		
		public void updateView(int[] grid) {
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++)   {
					int currentPosMarker = grid[i*3 + j];
					
					GameButton button = buttons[i][j];
					
					if(currentPosMarker == 1) {
						button.setText("X");
					} else if (currentPosMarker == 2) {
						button.setText("O");
					} 
					
				}			
			}
		}
		
	}
	
	class GameButton extends JButton {

		private static final long serialVersionUID = 4747888981137584058L;
		private int indexX;
		private int indexY;
		private int gridHeight = 3; // Nyt tiedet‰‰n ett‰ on 3x3 taulu...
		
		public void setIndex(int x, int y) {
			this.indexX = x;
			this.indexY = y; 
		}
		
		/*
		 * t‰t‰ tarvitaan l‰hinn‰ game-objektin kanssa kommunikoimiseen, 
		 * sill‰ siell‰ on pelin tila yksiulotteisessa taulussa.
		 * t‰ss‰ on buttonit kaksiulotteisesti, joten k‰ytet‰‰n niiden indeksej‰ 
		 * yksiulotteisen indeksin laskemiseen.
		 */
		public int getOneDimensionalIndex() {
			return indexY + indexX*gridHeight;
		}
		
	}
	
	class GameListener implements ActionListener {
		
		private Game game;
		private Player player;
		
		public GameListener(Game game, Player player) {
			this.game = game;
			this.player = player;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			GameButton gamebutton = null;
			int pressedButtonIndex = -1;
			
			Object source = e.getSource();
			
			
			if(source instanceof GameButton) {
				gamebutton = (GameButton) source;
				pressedButtonIndex = gamebutton.getOneDimensionalIndex();
			}
			
			System.out.println("A button " + pressedButtonIndex + " was pressed");
			
			try {
				boolean LegalMove = game.makeMove(player, pressedButtonIndex);
//				int Winner = game.getWinner();
				if (LegalMove) {
					System.out.println(game.getWinner());
					
				}
				
				else   {
					//TODO: pop-up joka ilmoittaa ett‰ n‰in ei saa tehd‰, ja antaa pelaajan valita uudestaan
					
					JOptionPane.showMessageDialog(null,"It's opponents turn or tile is full","Try again",JOptionPane.WARNING_MESSAGE);
				}

			} catch (RemoteException ex) {
				System.out.println("Connection lost");			
			}
			
		}
		
	}


}