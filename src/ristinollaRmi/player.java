package ristinollaRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface player extends Remote {
	

	
	
	public boolean isMe(player player) throws RemoteException;

	public void setMarker(String m) throws RemoteException;

	public String getMarker() throws RemoteException;

	public String getName() throws RemoteException;



}
