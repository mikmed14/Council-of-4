package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.user.Match;
import it.polimi.ingsw.ps09.view.ClientController;
import it.polimi.ingsw.ps09.view.MainFrame;

/**
 * This class represents client communicator for Socket.
 */
public class ClientSocket {

	private final String ip;
	private final int port;
	private int turno;
	private Match partita;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private final Scanner stdin;
	private boolean reconnected = false;
	private int numPartita;
	private static final Logger LOGGER = Logger.getLogger(ClientSocket.class.getName());

	/**
	 * The ClientSocket constructor initializes the client by giving him an IP
	 * address and a port to connect to.
	 * 
	 * @param ip
	 *            The IP address.
	 * @param port
	 *            The port to connect to.
	 * @param stdin
	 */
	public ClientSocket(String ip, int port, Scanner stdin) {
		this.ip = ip;
		this.port = port;
		this.stdin = stdin;
	}

	/**
	 * Loop of clients, which continues until the game is finished (the status
	 * will be change by the server when entering the final part).
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.lang.InterruptedException
	 */
	public void startClient() throws IOException, ClassNotFoundException, InterruptedException {
		System.out.println("Connection established");

		// creating the socket to be sent to the server
		try {
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.flush();
			tryReconnection();
			partitaInit();
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Error in ClientSocket", e);
		} finally {
			socket.close();
			stdin.close();
			in.close();
			out.close();
		}
	}

	/**
	 * This (private) method is used to allow the player back in the game.
	 * 
	 * @throws java.io.IOException
	 */
	private void tryReconnection() throws IOException {
		int inputLine;
		numPartita = in.readInt();
		System.out.println("Il server sta creando la partita numero: " + numPartita);
		System.out.println(
				"Benvenuto! Cosa vuoi fare? 0 per iniziare una nuova partita, 1 per collegarti ad una partita abbandonata");
		do {
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero.");
				stdin.next();
			}
			inputLine = stdin.nextInt();
		} while (inputLine != 0 && inputLine != 1);
		out.writeInt(inputLine);
		out.flush();
		if (inputLine == 1) {
			System.out.println("Indica il numero della partita abbandonata.");
			do {
				while (!stdin.hasNextInt()) {
					System.out.println("Immettere un numero.");
					stdin.next();
				}
				inputLine = stdin.nextInt();
			} while (inputLine < 0);
			out.writeInt(inputLine);
			out.flush();
			switch (in.readInt()) {
			case 0:
				System.out.println("Partita inesistente. Attendi inizio nuova partita.");
				break;
			case 1:
				System.out.println("Ti riconnetterai alla partita selezionata.");
				reconnected = true;
				break;
			case 2:
				System.out.println("La partita selezionata non ha giocatori inattivi. Cominci una nuova partita.");
				break;
			}
		}
	}

	/**
	 * This (private) method initializes the match.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.lang.InterruptedException
	 */
	private void partitaInit() throws IOException, ClassNotFoundException, InterruptedException {

		int scelta;

		// get round of the client from the server
		turno = in.readInt();

		// get initial state from the server
		partita = (Match) in.readObject();

		System.out.println("Partita numero: " + numPartita + "; ricordatene per riconnetterti.");
		System.out.println("Partita con: " + partita.getNumPlayers() + " giocatori");
		System.out.println("Sei il giocatore numero: " + turno);
		System.out.println("Come vuoi giocare? 0 CLI, 1 static UI.");
		do {
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero.");
				stdin.next();
			}
			scelta = stdin.nextInt();
		} while (scelta != 0);
		switch (scelta) {
		case 0:
			ClientController c = new ClientController(turno, partita, out, in, null, null, socket, stdin);
			c.startCLI(reconnected);
			break;
		case 1:
			MainFrame f = new MainFrame(partita, turno, out, null, null);
			f.startUI(in, null, socket, reconnected);
			break;
		}
	}
}