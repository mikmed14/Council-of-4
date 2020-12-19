package it.polimi.ingsw.ps09.net;

import java.io.IOException;


import it.polimi.ingsw.ps09.user.*;

/**
 * This interface is used to manage the incoming connections during the game.
 */
public interface ClientHandler extends Runnable {

	/**
	 * This method is used by the GameLoop class to send the round of each
	 * client.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 * @throws java.io.IOException
	 */
	public void sendTurno() throws InterruptedException, IOException;

	/**
	 * This method is used to send the specified match object to the client.
	 * 
	 * @param partita
	 *            The selected match.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 * @throws java.io.IOException
	 */
	public void sendStatusGame(Match partita) throws InterruptedException, IOException;

	/**
	 * This method is used to send the final game text message (eg. "you won!").
	 * 
	 * @param string
	 *            The final game text message.
	 * 
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.InterruptedException
	 * @throws java.io.IOException
	 */
	public void sendString(String string) throws InterruptedException, IOException;
}