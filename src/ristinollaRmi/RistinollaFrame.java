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
 * Tässä on nyt luokka peli-ikkunan muodostamista varten.
 * 
 */


class RistinollaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	GameButton [][] buttons = new GameButton[3][3]; // Buttonit vastaamaan ristinollaruudukkoa
	JTextField statusbar; // Tilapalkki kertoo pelin tilan
	GamePanel panel; // 
	PlayerImp player;
	Game game;
	GameListener listener; // Kuuntelee napsautukset ja päivittää pelin tilaa.
	
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
		
		Thread t = new Thread(player); 
		t.start(); // käynnistää pelaaja-olion 
		
		setLayout(new BorderLayout());
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		statusbar = new JTextField( " vuoro");
		statusbar.setEditable(false);
		add(statusbar, BorderLayout.SOUTH);
		setTitle("Ristinolla");
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400,400,400,400);
		
	}
	
	
	class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public GamePanel() {
			setLayout(new GridLayout(3,3));
			for(int i=0;i<3;i++)
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
	
	class GameButton extends JButton {

		private static final long serialVersionUID = 4747888981137584058L;
		private int indexX;
		private int indexY;
		private int gridHeight = 3; // Nyt tiedetään että on 3x3 taulu...
		
		public void setIndex(int x, int y) {
			this.indexX = x;
			this.indexY = y; 
		}
		
		/*
		 * tätä tarvitaan lähinnä game-objektin kanssa kommunikoimiseen, 
		 * sillä siellä on pelin tila yksiulotteisessa taulussa.
		 * tässä on buttonit kaksiulotteisesti, joten käytetään niiden indeksejä 
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
				if (LegalMove) {
					gamebutton.setText(player.getMarker());
					System.out.println(game.isItMyTurn(player));
				}
				else   {
					//TODO: pop-up joka ilmoittaa että näin ei saa tehdä, ja antaa pelaajan valita uudestaan
					
					JOptionPane.showMessageDialog(null,"Tile is already full","Try again",JOptionPane.WARNING_MESSAGE);
				}

			} catch (RemoteException ex) {
				System.out.println("Connection lost");			
			}
		}
	}
}
