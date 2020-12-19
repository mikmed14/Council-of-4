package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.rmi.NotBoundException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.user.Match;

/**
 * The server (game manager) class.
 */
public class Server extends UnicastRemoteObject implements ServerInterface {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The number of available maps.
	 */
	private final int NUMBEROFMAPS = 3;

	/**
	 * A random variable used to choose the map to play.
	 */
	private Random rand;

	private final int port = 1337;
	public final static String SERVER_NAME = "server";
	public final static int SERVER_PORT = 12189;
	transient private Registry registry;
	transient private ServerSocket serverSocket;
	private static ArrayList<GameLoop> partite = new ArrayList<GameLoop>();
	private ArrayBlockingQueue<Action> q;

	private ArrayBlockingQueue<MarketOffer> offerta;
	private ArrayBlockingQueue<MarketBuy> vendita;

	private ArrayBlockingQueue<Integer> output = new ArrayBlockingQueue<Integer>(1);
	private ArrayBlockingQueue<MatchInterface> client;
	private ArrayList<ClientHandler> num;
	private int contRMI;
	transient ExecutorService executor;
	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

	private boolean serverAttivo;

	/**
	 * The Server constructor that starts the server using a RMI connection.
	 *
	 * @throws java.rmi.RemoteException
	 */
	public Server() throws RemoteException {
		super();
		registry = LocateRegistry.createRegistry(SERVER_PORT);
		registry.rebind(SERVER_NAME, this);
		LOGGER.log(Level.INFO,"Server RMI attivo");
	}

	/**
	 * This method starts the server using a Socket connection.
	 *
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws java.rmi.NotBoundException
	 * @throws org.jdom2.JDOMException
	 */
	public void startServer() throws IOException, InterruptedException, NotBoundException, JDOMException {
		contRMI = 0;
		q = new ArrayBlockingQueue<Action>(1);

		offerta = new ArrayBlockingQueue<MarketOffer>(1);
		vendita = new ArrayBlockingQueue<MarketBuy>(1);

		client = new ArrayBlockingQueue<MatchInterface>(1);

		num = new ArrayList<ClientHandler>();
		// variable needed to create threads in case of need
		executor = Executors.newCachedThreadPool();

		try {
			// starting the server on a port

			serverSocket = new ServerSocket(port);

		} catch (IOException e) {
			LOGGER.log(Level.INFO, "error nell'avvio del serverSocket", e);
			return;
		}

		LOGGER.log(Level.INFO,"Server Socket attivo");

		new ConnectionThread().start();

		// timer that blocks the server for an amount of seconds
		serverAttivo = false;

		while (!serverAttivo) {
			Thread.sleep(10000);
			if (num.size() == 2) {

				Thread.sleep(30000);

				serverAttivo = true;
			}
		}

		serverSocket.close();

		

		// initializing a new match
		newGameInit();

		// restarting the server for this to come back to listen
		startServer();

	}

	/**
	 * This (private) class is used to manage threads (needed to handle multiple
	 * games).
	 */
	private class ConnectionThread extends Thread {
		public void run() {
			while (!serverSocket.isClosed()) {
				try {
					Socket socket = serverSocket.accept();
					ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					// in another thread checks the connected socket
					LOGGER.log(Level.INFO,"La connessione con i socket e' avvenuta con successo");
					new CheckSocketThread(out, in, socket).start();
				} catch (IOException e) {
					LOGGER.log(Level.INFO, "Il server si sta riavviando", e);
					break;
				}
			}
		}
	}

	/**
	 * This (private) class is used to check Socket connection with threads.
	 */
	private class CheckSocketThread extends Thread {
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private Socket socket;

		public CheckSocketThread(ObjectOutputStream out, ObjectInputStream in, Socket socket) {
			this.out = out;
			this.in = in;
			this.socket = socket;
		}

		// this method is started in the server to add Socket clients to matches
		public void run() {
			try {

				// if the checkSocketClientReconnection() method says that the
				// client does not want/cannot reconnect to an old game, it will
				// create a socketClientHandler and adds them to the ArrayList
				// of those in the game.
				if (!checkSocketClientReconnection(socket, out, in)) {
					num.add(new SocketClientHandler(socket, out, in, q, offerta, vendita, num.size()));
					

				}
			} catch (IOException e) {
				LOGGER.log(Level.INFO, "Input Output Error", e);
			} catch (InterruptedException e) {
				LOGGER.log(Level.INFO, "Interrupted!", e);
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * This method is used to initialize a new match of the game.
	 *
	 * @throws java.io.IOException
	 * @throws java.rmi.NotBoundException
	 * @throws java.lang.InterruptedException
	 * @throws org.jdom2.JDOMException
	 */
	private void newGameInit() throws IOException, NotBoundException, InterruptedException, JDOMException {

		// computes the number of the map to initialize and creates a new map
		rand = new Random();
		int number = rand.nextInt(175623);
		int numberOfMap = number % NUMBEROFMAPS + 1;

		Model model = new Model(num.size(), numberOfMap);
		Match partita = new Match(num.size(), model);

		for (ClientHandler ch : num) {
			// creating threads
			executor.submit(ch);
		}

		// starting a GameLoop for the current match in a new thread
		GameLoop mainLoop = new GameLoop(partita, q, num, offerta, vendita);
		partite.add(mainLoop);
		LOGGER.log(Level.INFO,"Partita " + partite.size() + " inizializzata.");
		MatchInterface game = (MatchInterface) registry.lookup("partita");
		for (int i = 0; i < contRMI; i++) {
			client.put(game);
		}
		executor.submit(mainLoop);
		executor.shutdown();
	}

	/**
	 * This method checks the reconnection of a player (client) to the game (in
	 * case of Socket connection).
	 *
	 * @param x
	 * @param y
	 * @param z
	 *
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	private boolean checkSocketClientReconnection(Socket socket, ObjectOutputStream out, ObjectInputStream in)
			throws IOException, InterruptedException {
		int inputLine = 0;
		out.flush();

		// sending the match number to the client that the server is waiting to
		// create
		out.writeInt(partite.size());
		out.flush();
		// the client will send 0 to start a new game, or 1 to reconnect (we
		// handle only the 1 case)
		if (in.readInt() == 1) {
			inputLine = in.readInt();
			// if the match number sent by the client exceeds the size of
			// ArrayList of the GameLoop.
			if (inputLine > partite.size() || partite.isEmpty()) {
				out.writeInt(0);
				out.flush();
				return false;
			}
			// if the match selected by the client exists, but there are not
			// inactive players.
			if (partite.get(inputLine).getPartita().getActiveNumPlayers() == partite.get(inputLine).getPartita()
					.getNumPlayers()) {
				out.writeInt(2);
				out.flush();
				return false;
			}
			// if it is reconnecting to an old game
			out.writeInt(1);
			out.flush();
			for (int i = 0; i < partite.get(inputLine).getPartita().getNumPlayers(); i++) {
				if (!partite.get(inputLine).getPartita().getGiocatore(i).getAttivo()) {
					// creating a socketClientHandler for this client and adding
					// to those of the match, to the right place; Then starts
					SocketClientHandler x = new SocketClientHandler(socket, out, in, partite.get(inputLine).getQueue(),
							partite.get(inputLine).getQueueOffer(), partite.get(inputLine).getQueueBuy(), i);
					partite.get(inputLine).addNum(x, i);
					executor.submit(x);
					break;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * This method checks the reconnection of a player (client) to the game (in
	 * case of RMI connection).
	 *
	 * @param x
	 * @param y
	 * @param z
	 *
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	private boolean checkRMIClientReconnection(ClientInterface x, int y, int z)
			throws IOException, InterruptedException {
		if (y == 1) {
			if (z > partite.size() || partite.isEmpty()) {
				output.put(0);
				return false;
			}
			if (partite.get(z).getPartita().getActiveNumPlayers() == partite.get(z).getPartita().getNumPlayers()) {
				output.put(2);
				return false;
			}
			output.put(1);
			for (int i = 0; i < partite.get(z).getPartita().getNumPlayers(); i++) {
				if (!partite.get(z).getPartita().getGiocatore(i).getAttivo()) {
					RMIClientHandler rmi = new RMIClientHandler(i, x);
					partite.get(z).addNum(rmi, i);
					executor.submit(rmi);
				}
			}
			return true;
		}
		output.put(-1);
		return false;
	}

	@Override
	public int[] addMe(ClientInterface clientInterface, int x, int y)
			throws IOException, InterruptedException, NotBoundException {
		if (!checkRMIClientReconnection(clientInterface, x, y)) {
			num.add(new RMIClientHandler(num.size(), clientInterface));
			LOGGER.log(Level.INFO, num.size() + " client connessi");
		} else {
			// otherwise it sends the interface provided by the GameLoop
			MatchInterface game = (MatchInterface) registry.lookup("partita");
			client.put(game);
		}
		// all finished, it sends two integers: the number of the match to which
		// you connect and the number generated by checkRMIClientReconnection()
		// method.
		int[] input = new int[2];
		input[0] = partite.size();
		input[1] = output.take();
		return input;
	}

	@Override
	public MatchInterface getPartitaInt() throws RemoteException, InterruptedException {
		return client.take();
	}

	@Override
	public int getRMIClientNum() throws RemoteException, InterruptedException {
		ArrayBlockingQueue<Integer> i = new ArrayBlockingQueue<Integer>(1);
		i.put(contRMI);
		contRMI++;
		return i.take();
	}
}