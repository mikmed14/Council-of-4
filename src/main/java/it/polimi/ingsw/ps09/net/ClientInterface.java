package it.polimi.ingsw.ps09.net;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps09.user.*;

/**
 * This is the interface for the remote object of the client.
 */
public interface ClientInterface extends Remote {

	/**
	 * This method puts on the RMI client queue his round to play.
	 * 
	 * @param turn
	 *            The specified round.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 */
	public void getTurno(int turn) throws RemoteException, InterruptedException;

	/**
	 * This method returns the match sent by the server to the RMI client,
	 * inserting it in the queue of the game client.
	 * 
	 * @param match
	 *            The selected match.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 */
	public void getPartita(Match match) throws RemoteException, InterruptedException;

	/**
	 * This method is used to get a text message.
	 * 
	 * @param string
	 *            The text message.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 */
	public void getString(String string) throws RemoteException, InterruptedException;
}