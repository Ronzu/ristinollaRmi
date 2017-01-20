package ristinollaRmi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Client {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
	
		JFrame jframe = new JFrame("Ristinolla");
		Container container = jframe.getContentPane();
		Font helvetica = new Font("Helvetica", Font.PLAIN, 10);
		JTextField targetField = new JTextField(60);
		final JTextField portField = new JTextField(30);
		targetField.setFont(helvetica);
		portField.setFont(helvetica);
		JButton b = new JButton("Test");
		
		Player player = (Player) Naming.lookup("rmi://localhost:5099/player");
			
		container.add(targetField, BorderLayout.NORTH);
		container.add(portField, BorderLayout.SOUTH);
		container.add(b);
		jframe.pack();
		jframe.setVisible(true);
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						portField.setText(player.echo());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
	}
	
	

}
