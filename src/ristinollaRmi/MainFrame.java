package ristinollaRmi;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -4238209101564932785L;
	JButton findGameButton;
	JTextField playerName;
	JTextField lobbyStatus;
	RistinollaFrame ristinollaFrame;
	
	
	public MainFrame() {
		
		setTitle("Ristinolla");
		findGameButton = new JButton("Etsi uusi peli");
		playerName = new JTextField("Sy�t� pelinimesi");
		lobbyStatus = new JTextField();
		
		// Toiminnallisuus on nyt findGame-buttonin takana. 
		findGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					
					// Haetaan registryst� lobby-objekti, jonka kautta saadaan Game-objekti uutta peli� varten.
					Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:5099/" + Lobby.NAMING); 
					// Luodaan uusi pelaaja t�t� clientti� varten.
					PlayerImp player;
					
					// Luodaan pelaajalle pelaajanimi, mik�li pelaaja ei sit� itse sy�t�
					if(playerName.getText().equalsIgnoreCase("sy�t� pelinimesi") || playerName.getText() == "") {	
						
						int random = (int)(Math.random() * 10000);
						String randomPlayerName = "player" + random;
						player = new PlayerImp(randomPlayerName);
					
					} else {
						// Jos pelaaja on sy�tt�nyt nimens�, valitaan se.
						player = new PlayerImp(playerName.getText());
					}
					
					// Etsit��n clientin pelaajalle peli lobbyst�. Lobby luo uuden pelin, mik�li linjoilla on kaksi vapaata pelaajaa.
					lobby.findGame(player); 
					
					// While-looppi tsekkaa sekunnin v�lein, onko pelaajalle l�ytynyt peli
					while(true) {
						try {
							if(player.getGame() != null) {
								// Mik�li peli l�ytyy, luodaan uusi peli-ikkuna...
								RistinollaFrame ristinollaFrame = new RistinollaFrame(player);
								Thread t  = new Thread(ristinollaFrame);
								// ...ja k�ynnistet��n se p�ivittym��n Game-objektin muutoksien mukaan.
								t.start();
								lobbyStatus.setText(player.echo());
								break; // While loopin voi t�ss� vaiheessa keskeytt��.
							} // if 
							Thread.sleep(1000);
							
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						} // try
					} // while
					
				} catch (MalformedURLException | RemoteException | NotBoundException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} //try
			} // actionPerformed()
		} // addActionListener 
		); // new ActionListener() 
		
		// Asetellaan viel� komponentit ikkunaan.
		setLayout(new BorderLayout());
		
		add(playerName, BorderLayout.NORTH);
		add(findGameButton, BorderLayout.CENTER);
		add(lobbyStatus, BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,300,300,300);
		
	}
}
