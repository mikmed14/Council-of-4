package it.polimi.ingsw.ps09.main;

import it.polimi.ingsw.ps09.net.Server;

/**
 * This class represents the main for the Server (only launch the server).
 */
public class StartConsiglioDei4 {

	/**
	 * Empty private constructor to hide the public one
	 */
	private StartConsiglioDei4() {
		return;
	}

	/**
	 * The main method that initializes the server
	 * 
	 * @param args
	 * 
	 * @throws java.lang.Exception
	 */
	public static void main(String[] args) throws Exception {
		Server gameServer = new Server();
		gameServer.startServer();
	}
}