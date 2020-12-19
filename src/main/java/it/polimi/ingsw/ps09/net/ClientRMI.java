package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.view.ClientController;
import it.polimi.ingsw.ps09.view.MainFrame;
import it.polimi.ingsw.ps09.net.MatchInterface;
import it.polimi.ingsw.ps09.user.Match;

/**
 * This class represents client communicator for RMI.
 */
public class ClientRMI extends UnicastRemoteObject implements ClientInterface {

	private static final long serialVersionUID = 1L;
	private Match partita;
	transient private final Registry registry;
	private boolean reconnected = false;
	transient private final Scanner stdin;
	transient private MatchInterface game;
	private int turno;
	private ArrayBlockingQueue<Match> match = new ArrayBlockingQueue<Match>(1);
	private ArrayBlockingQueue<Integer> turn = new ArrayBlockingQueue<Integer>(1);
	private ArrayBlockingQueue<String> string = new ArrayBlockingQueue<String>(1);
	private static final Logger LOGGER = Logger.getLogger(ClientRMI.class.getName());

	/**
	 * The ClientRMI constructor sets the registry.
	 * 
	 * @param stdin
	 * @throws java.rmi.RemoteException
	 */
	public ClientRMI(Scanner stdin) throws RemoteException {
		super();
		registry = (Registry) LocateRegistry.getRegistry("localhost", Server.SERVER_PORT);
		this.stdin = stdin;
	}

	/**
	 * This method starts the client.
	 * 
	 * @throws java.rmi.NotBoundException
	 * @throws java.lang.InterruptedException
	 * @throws java.io.IOException
	 * @throws java.lang.ClassNotFoundException
	 */
	public void startClient() throws NotBoundException, InterruptedException, IOException, ClassNotFoundException {

		ServerInterface client = (ServerInterface) registry.lookup(Server.SERVER_NAME);

		System.out.println("Connection established");

		// ask the number of connected players using the connection RMI
		int num = client.getRMIClientNum();
		try {
			registry.rebind("client" + num, this);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Error with registry rebind", e);
		}
		int[] x = new int[2];
		x = tryReconnection();
		x = client.addMe((ClientInterface) registry.lookup("client" + num), x[0], x[1]);

		switch (x[1]) {

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

		game = client.getPartitaInt();
		System.out.println("Partita numero: " + x[0] + "\t Ricordatene per riconnetterti.");
		partitaInit();
	}

	/**
	 * This (private) method is used to allow the player back in the game.
	 * 
	 * @return the number needed to get back in the match abandoned
	 */
	private int[] tryReconnection() {
		int[] x = new int[2];
		System.out.println(
				"Benvenuto! Cosa vuoi fare? 0 per iniziare una nuova partita, 1 per collegarti ad una partita abbandonata");
		do {
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero.");
				stdin.next();
			}
			x[0] = stdin.nextInt();
		} while (x[0] != 0 && x[0] != 1);
		if (x[0] == 1) {
			System.out.println("Indica il numero della partita abbandonata.");
			do {
				while (!stdin.hasNextInt()) {
					System.out.println("Immettere un numero.");
					stdin.next();
				}
				x[1] = stdin.nextInt();
			} while (x[1] < 0);
		}
		return x;
	}

	/**
	 * This (private) method initializes the match.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws java.lang.ClassNotFoundException
	 */
	private void partitaInit() throws IOException, InterruptedException, ClassNotFoundException {

		int scelta;

		// get round of the client from the server
		turno = turn.take();

		// get initial state from the server
		partita = match.take();

		System.out.println("Come vuoi giocare? 0 CLI, 1 GUI");

		do {
			while (!stdin.hasNextInt()) {
				System.err.println("Immettere un numero valido.");
				stdin.next();
			}
			scelta = stdin.nextInt();
		} while (scelta != 0);
		switch (scelta) {
		case 0:
			ClientController c = new ClientController(turno, partita, null, null, game, match, null, stdin);
			c.startCLI(reconnected);
			break;
		case 1:
			MainFrame f = new MainFrame(partita, turno, null, game, string);
			f.startUI(null, match, null, reconnected);
			break;

		default:
			LOGGER.log(Level.INFO, "Non hai scelto nessuna delle opzioni proposte: ERROR");
			break;
		}

		System.out.println("Stai giocando con: " + partita.getNumPlayers() + " giocatori");
		System.out.println("Il tuo turno:: " + turno);
	}

	@Override
	public void getTurno(int turno) throws RemoteException, InterruptedException {
		turn.put(turno);
	}

	@Override
	public void getPartita(Match partita) throws RemoteException, InterruptedException {
		match.put(partita);
	}

	@Override
	public void getString(String string) throws RemoteException, InterruptedException {
		this.string.put(string);
	}
}