package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the interface for the remote object of the server.
 */
public interface ServerInterface extends Remote {

	/**
	 * This method register the client connected at the server.
	 * 
	 * @param clientInterface
	 * @param x
	 * @param y
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws java.rmi.NotBoundException
	 */
	public int[] addMe(ClientInterface clientInterface, int x, int y)
			throws RemoteException, IOException, InterruptedException, NotBoundException;

	/**
	 * This method back to RMI client the remote interface made available by the
	 * GameLoop class.
	 * 
	 * @return
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 */
	public MatchInterface getPartitaInt() throws RemoteException, InterruptedException;

	/**
	 * Return to each RMI client their "number" based on how many RMI client are
	 * already connected.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 */
	public int getRMIClientNum() throws RemoteException, InterruptedException;
}