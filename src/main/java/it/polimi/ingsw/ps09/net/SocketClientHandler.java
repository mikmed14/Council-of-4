package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketOffer;

import it.polimi.ingsw.ps09.user.Match;;

/**
 * This class is used to manage the incoming Socket connections (the Socket
 * version of a client handler).
 */
public class SocketClientHandler implements ClientHandler {

	private final Socket socket;
	private BlockingQueue<Action> q;
	private BlockingQueue<MarketOffer> offerta;
	private BlockingQueue<MarketBuy> vendita;
	private final int playerNum;
	private final ObjectOutputStream out;
	private final ObjectInputStream in;
	private static final Logger LOGGER = Logger.getLogger(SocketClientHandler.class.getName());

	/**
	 * This is the SocketClientHandler constructor.
	 * 
	 * @param socket
	 * @param out
	 * @param in
	 * @param q
	 * @param size
	 */
	public SocketClientHandler(Socket socket, final ObjectOutputStream out, final ObjectInputStream in,
			ArrayBlockingQueue<Action> q, ArrayBlockingQueue<MarketOffer> offerta,
			ArrayBlockingQueue<MarketBuy> vendita, int size) {
		this.socket = socket;
		this.q = q;
		playerNum = size;
		this.out = out;
		this.in = in;
		this.offerta = offerta;
		this.vendita = vendita;
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				Object z = in.readObject();
				if (z instanceof Action) {
					q.put((Action) z);
				} else if (z instanceof MarketOffer) {
					offerta.put((MarketOffer) z);
				} else if (z instanceof MarketBuy) {
					vendita.put((MarketBuy) z);
				}
			}

			// closing streams and Socket
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error nella chiusura degli stream e del socket", e);
		}
	}

	@Override
	public void sendStatusGame(Match partita) throws IOException {
		out.reset();
		out.writeObject(partita);
		out.flush();
	}

	@Override
	public void sendTurno() throws IOException {
		out.writeInt(playerNum);
		out.flush();
	}

	@Override
	public void sendString(String string) throws IOException {
		PrintWriter output = new PrintWriter(socket.getOutputStream());
		output.println(string);
		output.flush();
		output.close();
	}
}