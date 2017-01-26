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
	
	JButton findGameButton;
	JTextField playerName;
	JTextField lobbyStatus;
	RistinollaFrame ristinollaFrame;
	
	
	public MainFrame() {
		
		setTitle("Ristinolla");
		
		findGameButton = new JButton("Etsi uusi peli");
		playerName = new JTextField("Syötä pelinimesi");
		lobbyStatus = new JTextField();
		
		
		findGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:5099/" + Lobby.NAMING);
					PlayerImp player;
					
					if(playerName.getText().equalsIgnoreCase("syötä pelinimesi") || playerName.getText() == "") {	
						//Luodaan random pelaajanimi jos ei käyttäjä laita.
						int random = (int)(Math.random() * 10000);
						String randomPlayerName = "player" + random;
						player = new PlayerImp(lobby, randomPlayerName);
					} else {
						player = new PlayerImp(lobby, playerName.getText());
					}
					
					
					lobby.findGame(player); 
					
					
					while(true) {
						try {
							if(player.getGame() != null) {
								RistinollaFrame ristinollaFrame = new RistinollaFrame(player);
								lobbyStatus.setText(player.echo());
								break;
							}
							lobbyStatus.setText("Odotetaan toista pelaajaa...");
							Thread.sleep(1000);
							System.out.println("Odotetaan");
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					
				} catch (MalformedURLException | RemoteException | NotBoundException ex) {
			
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
			}
		});
		
		
		setLayout(new BorderLayout());
		
		add(playerName, BorderLayout.NORTH);
		add(findGameButton, BorderLayout.CENTER);
		add(lobbyStatus, BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,300,300,300);
		
	}

}
