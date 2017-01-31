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
		playerName = new JTextField("Syötä pelinimesi");
		lobbyStatus = new JTextField();
		
		// Toiminnallisuus on nyt findGame-buttonin takana. 
		findGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					
					// Haetaan registrystä lobby-objekti, jonka kautta saadaan Game-objekti uutta peliä varten.
					Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:5099/" + Lobby.NAMING); 
					// Luodaan uusi pelaaja tätä clienttiä varten.
					PlayerImp player;
					
					// Luodaan pelaajalle pelaajanimi, mikäli pelaaja ei sitä itse syötä
					if(playerName.getText().equalsIgnoreCase("syötä pelinimesi") || playerName.getText() == "") {	
						
						int random = (int)(Math.random() * 10000);
						String randomPlayerName = "player" + random;
						player = new PlayerImp(randomPlayerName);
					
					} else {
						// Jos pelaaja on syöttänyt nimensä, valitaan se.
						player = new PlayerImp(playerName.getText());
					}
					
					// Etsitään clientin pelaajalle peli lobbystä. Lobby luo uuden pelin, mikäli linjoilla on kaksi vapaata pelaajaa.
					lobby.findGame(player); 
					
					// While-looppi tsekkaa sekunnin välein, onko pelaajalle löytynyt peli
					while(true) {
						try {
							if(player.getGame() != null) {
								// Mikäli peli löytyy, luodaan uusi peli-ikkuna...
								RistinollaFrame ristinollaFrame = new RistinollaFrame(player);
								Thread t  = new Thread(ristinollaFrame);
								// ...ja käynnistetään se päivittymään Game-objektin muutoksien mukaan.
								t.start();
								lobbyStatus.setText(player.echo());
								break; // While loopin voi tässä vaiheessa keskeyttää.
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
		
		// Asetellaan vielä komponentit ikkunaan.
		setLayout(new BorderLayout());
		
		add(playerName, BorderLayout.NORTH);
		add(findGameButton, BorderLayout.CENTER);
		add(lobbyStatus, BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,300,300,300);
		
	}
}
