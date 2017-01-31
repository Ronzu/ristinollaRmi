package ristinollaRmi;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
	
	// Client-luokka vain k‰ynnist‰‰ uuden aloitusikkunan pelille. 
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();		
			}
		});
		
	}
	
	

}


