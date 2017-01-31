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
 * T�ss� on nyt luokka peli-ikkunan muodostamista varten.
 * 
 */


public class RistinollaFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	GameButton [][] buttons = new GameButton[3][3]; // Buttonit vastaamaan ristinollaruudukkoa
	JTextField statusbar; // Tilapalkki kertoo pelin tilan
	GamePanel panel; // oma objekti peliruudukon piirt�miseen
	PlayerImp player; // T�m�n clientin pelaaja.
	Game game; // peli, jota pelataan. Saadaan player-objektilta.
	GameListener listener; // Kuuntelee napsautukset ja p�ivitt�� pelin tilaa.
	String status; // Tilapalkkiin p�ivitett�v� teksti
	
	// Noniin, alustetaan t�h�n nyt kaikki kondikseen.
	public RistinollaFrame(PlayerImp player) throws RemoteException {
		
		this.player = player; // Pelaaja saadaan parametrina
		
		try {
			this.game = player.getGame(); // Pelattava peli saadaan playerilta
		} catch(RemoteException e) {
			e.printStackTrace();
		} // try

		// luodaan uusi listener, joka tekee pelin kannalta oikeat asiat, kun peliruudukon nappuloita painetaan.
		// T�t� varten kirjoitettu oma sis�luokka.
		listener = new GameListener(game, player);
		
		statusbar = new JTextField();
		
		// Laitetaan ulkoasu kuntoon.
		setLayout(new BorderLayout());
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		statusbar.setEditable(false);
		add(statusbar, BorderLayout.SOUTH);
		setTitle("Ristinolla - Kaikkien aikojen verkkopeli!");
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400,400,400,400);	
	}
	
	
	// T��ll� p�ivitell��n sitten k�ytt�liittym��, maltillisesti nyt 10 kertaa sekunnissa. 
	// K�ynnistet��n samalla, kun uusi peli luodaan.
	@Override
	public void run() {
		while(true) {
			try {
				updateView(); // P�ivitet��n n�kym�
				checkWinner(); // Tsekataan, onko jompi kumpi voittanut.
				
				/*
				 * Ok, voittajantarkistusmetodi on v�h�n raskas, ja turha ajaa jatkuvasti. 
				 * Ehk� ennemmin joka nappulanpainalluksen yhteydess� peliobjekti p�ivitt�isi jonkin voittaja-muuttujan,
				 * jonka arvo sitten tsekattaisiin?
				 */
				
				Thread.sleep(100); // Turha tosiaan jatkuvalla sy�t�ll� tarkistella...
				
			} catch (RemoteException | InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	protected void updateView() throws RemoteException {
		panel.updateView(this.game.getGrid()); // P�ivitt�� nappulat
		
		boolean turn = this.game.isItMyTurn(this.player); // Boolean kertoo, onko t�m�n pelaajan vuoro.
		
		// P�ivitet��n tilapalkki kertomaan, kumman vuoro on.
		if (turn) {
			status = "It's your turn.";
		} else {
			status = "It's your opponent's (" + this.game.getOpponent(this.player).getName() + ") turn.";
		}
		statusbar.setText(status); // Asetetaan oikea tilapalkin teksti.
	} // updateView()
	
	
	/*
	 * Nyt on k�ynyt sillein hassusti, ettei ole ollenkaan rematch-mahdollisuutta. Viel� pit�isi my�s keksi�, ett�
	 * peli-objekti asetetaan loppuneeksi, kun voittaja on l�ytynyt.
	 * 
	 * T�m� metodi anyway luo pop-up-ikkunan siit�, kumpi on voittanut.
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
	
	// Sis�luokka GamePanel ristinollaruudukkoa varten.
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
		
		// Nappuloiden p�ivitysmetodi.
		public void updateView(int[] grid) {
			
			// K�yd�n joka nappulal�pi, ja p�ivitet��n vastaamaan peli-objektilta saatua peliruudukkoa.
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
	
	// Sis�luokka peliruudukon nappulaa varten. 
	// Jatkettiin JButtonia, l�hinn� ett� voidaan helposti havaita sijainti ruudukossa.
	class GameButton extends JButton {

		private static final long serialVersionUID = 4747888981137584058L;
		private int indexX;
		private int indexY;
		private int gridHeight = 3; // Nyt tiedet��n ett� on 3x3 taulu...
		
		public void setIndex(int x, int y) {
			this.indexX = x;
			this.indexY = y; 
		}
		
		/*
		 * t�t� tarvitaan l�hinn� game-objektin kanssa kommunikoimiseen, 
		 * sill� siell� on pelin tila yksiulotteisessa taulussa.
		 * t�ss� on buttonit kaksiulotteisesti, joten k�ytet��n niiden indeksej� 
		 * yksiulotteisen indeksin laskemiseen.
		 */
		public int getOneDimensionalIndex() {
			return indexY + indexX*gridHeight;
		}
		
	} // class GameButton
	
	/* Varsinainen toiminnallisuus onkin sitten laitettu t�h�n. Joka painalluksen j�lkeen 
	 * listeneri p�ivitt�� peliobjektin vastaamaan tapahtunutta painallusta. 
	 * Peliobjekti taasen huolehtii vuoron vaihdosta.
	 */
	
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
			
			
			try {
				boolean LegalMove = game.makeMove(player, pressedButtonIndex);

				if (LegalMove) {
					System.out.println(game.getWinner());
				} else {
					// Pop-up ilmoittaa virheellisest� siirrosta.
					JOptionPane.showMessageDialog(null,"It's opponents turn or tile is full","Try again",JOptionPane.WARNING_MESSAGE);
				}

			} catch (RemoteException ex) {
				System.out.println("Connection lost");			
			} // try
			
		} // actionPerformed()
	} // class GameListener
}