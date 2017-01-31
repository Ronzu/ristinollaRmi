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


public class RistinollaFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	GameButton [][] buttons = new GameButton[3][3]; // Buttonit vastaamaan ristinollaruudukkoa
	JTextField statusbar; // Tilapalkki kertoo pelin tilan
	GamePanel panel; // oma objekti peliruudukon piirtämiseen
	PlayerImp player; // Tämän clientin pelaaja.
	Game game; // peli, jota pelataan. Saadaan player-objektilta.
	GameListener listener; // Kuuntelee napsautukset ja päivittää pelin tilaa.
	String status; // Tilapalkkiin päivitettävä teksti
	
	// Noniin, alustetaan tähän nyt kaikki kondikseen.
	public RistinollaFrame(PlayerImp player) throws RemoteException {
		
		this.player = player; // Pelaaja saadaan parametrina
		
		try {
			this.game = player.getGame(); // Pelattava peli saadaan playerilta
		} catch(RemoteException e) {
			e.printStackTrace();
		} // try

		// luodaan uusi listener, joka tekee pelin kannalta oikeat asiat, kun peliruudukon nappuloita painetaan.
		// Tätä varten kirjoitettu oma sisäluokka.
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
	
	
	// Täällä päivitellään sitten käyttöliittymää, maltillisesti nyt 10 kertaa sekunnissa. 
	// Käynnistetään samalla, kun uusi peli luodaan.
	@Override
	public void run() {
		while(true) {
			try {
				updateView(); // Päivitetään näkymä
				checkWinner(); // Tsekataan, onko jompi kumpi voittanut.
				
				/*
				 * Ok, voittajantarkistusmetodi on vähän raskas, ja turha ajaa jatkuvasti. 
				 * Ehkä ennemmin joka nappulanpainalluksen yhteydessä peliobjekti päivittäisi jonkin voittaja-muuttujan,
				 * jonka arvo sitten tsekattaisiin?
				 */
				
				Thread.sleep(100); // Turha tosiaan jatkuvalla syötöllä tarkistella...
				
			} catch (RemoteException | InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	protected void updateView() throws RemoteException {
		panel.updateView(this.game.getGrid()); // Päivittää nappulat
		
		boolean turn = this.game.isItMyTurn(this.player); // Boolean kertoo, onko tämän pelaajan vuoro.
		
		// Päivitetään tilapalkki kertomaan, kumman vuoro on.
		if (turn) {
			status = "It's your turn.";
		} else {
			status = "It's your opponent's (" + this.game.getOpponent(this.player).getName() + ") turn.";
		}
		statusbar.setText(status); // Asetetaan oikea tilapalkin teksti.
	} // updateView()
	
	
	/*
	 * Nyt on käynyt sillein hassusti, ettei ole ollenkaan rematch-mahdollisuutta. Vielä pitäisi myös keksiä, että
	 * peli-objekti asetetaan loppuneeksi, kun voittaja on löytynyt.
	 * 
	 * Tämä metodi anyway luo pop-up-ikkunan siitä, kumpi on voittanut.
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
	
	// Sisäluokka GamePanel ristinollaruudukkoa varten.
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
		
		// Nappuloiden päivitysmetodi.
		public void updateView(int[] grid) {
			
			// Käydän joka nappulaläpi, ja päivitetään vastaamaan peli-objektilta saatua peliruudukkoa.
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
	
	// Sisäluokka peliruudukon nappulaa varten. 
	// Jatkettiin JButtonia, lähinnä että voidaan helposti havaita sijainti ruudukossa.
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
		
	} // class GameButton
	
	/* Varsinainen toiminnallisuus onkin sitten laitettu tähän. Joka painalluksen jälkeen 
	 * listeneri päivittää peliobjektin vastaamaan tapahtunutta painallusta. 
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
					// Pop-up ilmoittaa virheellisestä siirrosta.
					JOptionPane.showMessageDialog(null,"It's opponents turn or tile is full","Try again",JOptionPane.WARNING_MESSAGE);
				}

			} catch (RemoteException ex) {
				System.out.println("Connection lost");			
			} // try
			
		} // actionPerformed()
	} // class GameListener
}