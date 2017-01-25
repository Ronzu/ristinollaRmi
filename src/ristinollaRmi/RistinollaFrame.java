package ristinollaRmi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * 
 * T‰ss‰ on nyt luokka peli-ikkunan muodostamista varten.
 * 
 */


class RistinollaFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton [][] buttons = new JButton[3][3]; // Buttonit vastaamaan ristinollaruudukkoa
	JTextField statusbar; // Tilapalkki kertoo pelin tilan
	GamePanel panel; // 
	GameListener listener = new GameListener(); // Kuuntelee napsautukset ja p‰ivitt‰‰ pelin tilaa.
	PlayerImp player;
	
	public RistinollaFrame(PlayerImp player) {
		this.player = player;
		
		Thread t = new Thread(player); 
		t.start(); // k‰ynnist‰‰ pelaaja-olion 
		
		
		setLayout(new BorderLayout());
		panel = new GamePanel();
		add(panel, BorderLayout.CENTER);
		
		statusbar = new JTextField("Pelaaja 1:n vuoro");
		statusbar.setEditable(false);
		add(statusbar, BorderLayout.SOUTH);
		setTitle("Ristinolla");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400,400,400,400);
		
	}
	
	class GamePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public GamePanel() {
			setLayout(new GridLayout(3,3));
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)   {
					buttons[i][j]=new JButton();
					buttons[i][j].putClientProperty("INDEX", new Integer[]{i,j});
					buttons[i][j].putClientProperty("OWNER", null);
					buttons[i][j].addActionListener(listener);
					add(buttons[i][j]);
			}

			
		}
	}
	
	class GameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println();
		}
	}
}