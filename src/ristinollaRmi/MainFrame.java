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
		playerName = new JTextField("Sy�t� pelinimesi");
		lobbyStatus = new JTextField();
		
		findGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					Lobby lobby = (Lobby) Naming.lookup("rmi://localhost:5099/" + Lobby.NAMING);
					PlayerImp player = new PlayerImp(lobby, playerName.getText());
					
					lobby.findGame(player); // lis�� Clientin pelaajan peliin
					lobbyStatus.setText(player.echo());
					
					ristinollaFrame = new RistinollaFrame(player);
					
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
