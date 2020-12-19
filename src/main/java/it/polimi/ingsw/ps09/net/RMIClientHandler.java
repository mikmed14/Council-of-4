package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps09.user.*;

/**
 * This class is used to manage the incoming RMI connections (the RMI version of
 * a client handler).
 */
public class RMIClientHandler implements ClientHandler {

	private final int numPlayer;
	private final ClientInterface ci;

	/**
	 * This is the RMIClientHandler constructor that initializes with a round of
	 * the game (as an integer) and using the ClientHandlerInterface for using
	 * methods exposed to client.
	 * 
	 * @param i
	 *            The round.
	 * @param x
	 *            The client interface.
	 */
	public RMIClientHandler(final int i, final ClientInterface x) {
		this.numPlayer = i;
		this.ci = x;
	}

	@Override
	public void run() {
	}

	@Override
	public void sendTurno() throws RemoteException, InterruptedException, IOException {
		ci.getTurno(numPlayer);
	}

	@Override
	public void sendStatusGame(Match partita) throws InterruptedException, RemoteException, IOException {
		ci.getPartita(partita);
	}

	@Override
	public void sendString(String string) throws RemoteException, InterruptedException {
		ci.getString(string);
	}
}