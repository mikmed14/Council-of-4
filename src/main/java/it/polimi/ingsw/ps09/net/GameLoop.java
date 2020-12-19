package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.user.Match;

public class GameLoop extends UnicastRemoteObject implements Runnable, MatchInterface {

	private static final long serialVersionUID = 1L;
	private Match partita;
	private ArrayBlockingQueue<Action> q;

	private ArrayBlockingQueue<MarketOffer> offer;
	private ArrayBlockingQueue<MarketBuy> vendita;

	private ArrayList<ClientHandler> num;
	transient private final Registry registry;
	private Model model;
	private static final Logger LOGGER = Logger.getLogger(GameLoop.class.getName());

	/*
	 * Inizializza il GameLoop assegnandogli una partita, una coda da cui
	 * ricevero' informazioni, e l'arraylist dei serverclienthandler per
	 * dialogare coi client. Infine, rebinda se stesso su "partita", per farsi
	 * trovare su RMI.
	 */
	public GameLoop(Match partita, ArrayBlockingQueue<Action> q, ArrayList<ClientHandler> num,
			ArrayBlockingQueue<MarketOffer> offerta, ArrayBlockingQueue<MarketBuy> vendita) throws RemoteException {
		this.partita = partita;
		this.q = q;
		this.offer = offerta;
		this.vendita = vendita;
		this.num = num;
		registry = LocateRegistry.getRegistry("localhost", Server.SERVER_PORT);
		registry.rebind("partita", this);

	}

	@Override
	public void run() {
		Action mossaClient = null;

		// con partita init passi il turno e la partita al client
		partitaInit();

		startLoop(mossaClient);

		finalLoop(mossaClient);

		if (partita.getActiveNumPlayers() > 1) {
			Integer[] puntiVittoria = new Integer[partita.getNumPlayers()];
			for (int i = 0; i < partita.getNumPlayers(); i++) {
				puntiVittoria[i] = 0;
			}
			puntiVittoria = assegnaPunti();

			try {
				sendWinners(puntiVittoria);
			} catch (IOException | InterruptedException e) {
				LOGGER.log(Level.INFO, "send winners", e);
			}

		}
		LOGGER.log(Level.INFO,"Partita conclusa!");

	}

	public  int sendWinners(Integer[] vittoria) throws IOException, InterruptedException {
		int max = 0;
		for (int i = 0; i < partita.getNumPlayers(); i++) {
			if (vittoria[i] > max) {
				max = vittoria[i];
			}
		}
		ArrayList<Integer> winners = new ArrayList<Integer>();
		for (int i = 0; i < partita.getNumPlayers(); i++) {
			if (vittoria[i] == max) {

				// aggiungo l'indice del giocatore che ha totalizzato il
				// punteggio massimo
				winners.add(i);
				
			}
		}

		// se esiste piu' di un giocatore con un punteggio massimo allora
		// controllo chi ha più carte politica/tessere permesso/assistenti
		if (winners.size() > 1) {
			ArrayList<Integer> assistenti = new ArrayList<Integer>();
			ArrayList<Integer> politicCards = new ArrayList<Integer>();
			ArrayList<Integer> permitCards = new ArrayList<Integer>();
			ArrayList<Integer> sum = new ArrayList<Integer>();
			for (int j = 0; j < winners.size(); j++) {
				assistenti.add(partita.getMap().getPlayer(winners.get(j)).getAssistants());
				politicCards.add(partita.getMap().getPlayer(winners.get(j)).getCards().size());
				permitCards.add(partita.getMap().getPlayer(winners.get(j)).getPermitsUp().size()
						+ partita.getMap().getPlayer(winners.get(j)).getPermitsDown().size());
				sum.add(assistenti.get(j) + politicCards.get(j) + permitCards.get(j));
			}

			int maxWinner = 0;
			for (int j = 0; j < winners.size(); j++) {
				if (sum.get(j) > maxWinner) {
					maxWinner = sum.get(j);
				}

			}

			int vincitore = 0;
			for (int k = 0; k < winners.size(); k++) {
				if (sum.get(k) == maxWinner) {
					vincitore = k;
				}
			}

			for (int i = 0; i < num.size(); i++) {
				if (vincitore == i) {
					num.get(i).sendString("Hai vinto! Hai totalizzato: " + vittoria[i] + " punti Vittoria!");
				} else {
					num.get(i).sendString("Hai perso; hai totalizzato: " + vittoria[i] + " punti Vittoria!");
				}
			}
			return vincitore;
		} else {
			int x = 0;
			for (int i = 0; i < num.size(); i++) {
				if (winners.contains((int) i)) {
					x=i;
					num.get(i).sendString("Hai vinto! Hai totalizzato: " + vittoria[i] + " punti Vittoria!");
				} else {
					num.get(i).sendString("Hai perso; hai totalizzato: " + vittoria[i] + " punti Vittoria!");
				}
			}
			return  x;
		}
	}

	private Integer[] assegnaPunti() {
		Integer[] puntiVittoria = new Integer[partita.getNumPlayers()];
		for (int i = 0; i < partita.getNumPlayers(); i++) {
			puntiVittoria[i] = 0;
		}

		for (int j = 0; j < partita.getActiveNumPlayers(); j++) {
			puntiVittoria[j] = puntiVittoria[j] + partita.getMap().getPlayer(j).assignFinalBonuses();
		}

		return puntiVittoria;

	}

	private void startLoop(Action mossaClient) {
		boolean flag = false;
		boolean emporiFiniti = false;

		while (partita.getActiveNumPlayers() > 1 && !emporiFiniti) {

			/*
			 * creo un ciclo per il market almeno non richiede le mosse
			 * boolean dentro partita
			 */

			while (partita.getMarket()) {

				for (int i = 0; i < partita.getNumPlayers(); i++) {
					MarketOffer offerClient = null;
					try {
						offerClient = offer.poll(120, TimeUnit.SECONDS);
					} catch (Exception e) {
						LOGGER.log(Level.INFO, "Non sei stato abbastanza veloce", e);
					}
					if(offerClient==null) throw new NullPointerException();
					if(offerClient.nulla()){
					partita.addOffer(offerClient);
					partita.setMarkeTurn();
					

					LOGGER.log(Level.INFO," Hai fatto la tua offerta al market");
					System.out.println("" +Integer.toString( partita.getMarkeTurn()));
					}else {
					partita.setMarkeTurn();
					LOGGER.log(Level.INFO,"  Hai scelto di non fare un'offerta al market");
				
					}
					 

					sendStatusGame();
				}

				partita.setMarkeTurnDefault();
				sendStatusGame();
				LOGGER.log(Level.INFO,"" + partita.getMarkeTurn());

				for (int i = 0; i < partita.getNumPlayers(); i++) {
					MarketBuy buyClient = null;
					try {
						buyClient = vendita.poll(120, TimeUnit.SECONDS);
					} catch (Exception e) {
						LOGGER.log(Level.INFO, "Non sei stato abbastanza veloce", e);
					}
					if(buyClient==null) throw new NullPointerException();
					if(buyClient.nulla()){
					
					

					model = buyClient.execute(partita.getOffers(), partita.getMap());				
					partita.updateModel(model);				
					partita.removeOffer(buyClient.getIndex());
					partita.setMarkeTurn();
					sendStatusGame();
					}else {
					partita.setMarkeTurn();
					sendStatusGame();
					LOGGER.log(Level.INFO," Hai scelto di non fare un'offerta al market");

					}
				}

				partita.setMarketFalse();
				sendStatusGame();
			}

			LOGGER.log(Level.INFO,"Turno del giocatore :" + partita.getTurno());

			// Il giocatore ad ogni turno pesca una carta
			PoliticCard cartaPescata = partita.getMap().getPoliticCardDeck().drawFromPool();
			partita.getMap().getPlayer(partita.getTurno()).addPoliticCard(cartaPescata);
			partita.setCard(cartaPescata);
			sendStatusGame();

			while (partita.getNumMosse() != Constants.NUMOSSE) {
				try {
					mossaClient = q.poll(120, TimeUnit.SECONDS);
				} catch (Exception e) {
					LOGGER.log(Level.INFO, "Nessuna mossa principale acquisita", e);
				}
				if (mossaClient == null) {
					LOGGER.log(Level.INFO, "E' scaduto il tempo per fare la tua mossa");
					Disconnesso(partita.getTurno());
				} else if (mossaClient.mossa <= 4 && mossaClient.mossa > 0) {

					applicaMossa(mossaClient);

					partita.incrementaNumMosse();

					sendStatusGame();

					// in caso di bonus ADDACTION
					if (partita.getMap().getAction()) {
						try {
							mossaClient = q.poll(120, TimeUnit.SECONDS);
						} catch (Exception e) {
							LOGGER.log(Level.INFO, "Nessuna mossa acquisita", e);
						}
						applicaMossa(mossaClient);
						partita.getMap().setActionFalse();
						sendStatusGame();
					}

					if (flag == false) {
						try {
							mossaClient = q.poll(120, TimeUnit.SECONDS);
						} catch (Exception e) {
							LOGGER.log(Level.INFO, "Non e' arrivata alcuna mossa", e);
						}

						if (!mossaClient.nulla()) {
							partita.incrementaNumMosse();
							
							LOGGER.log(Level.INFO,
									"E' scaduto il tempo per fare la tua mossa, hai scelto di non usare la mossa veloce");

						} else {

							applicaMossa(mossaClient);
							partita.incrementaNumMosse();
							sendStatusGame();
							
						}
					}

				} else if (mossaClient.mossa <= 8 && mossaClient.mossa > 4) {
					applicaMossa(mossaClient);
					partita.incrementaNumMosse();
					flag = true;
					sendStatusGame();
				}
			}
			flag = false;

			if (partita.getTurno() == (partita.getNumPlayers() - 1)) {
				partita.setMarketTrue();
				partita.setMarkeTurnDefault();
				partita.resetOffers();
				sendStatusGame();
			}

			if (partita.getNumMosse() == Constants.NUMOSSE) {
				partita.azzeraMosse();
				do {
					partita.changeTurn();
					LOGGER.log(Level.INFO,"Turno: " + partita.getTurno());
					flag = false;
				} while (!(partita.getGiocatore(partita.getTurno()).getAttivo()));

				sendStatusGame();
			}
			// controllo che tutti i giocatori abbiano almeno un emporio
			for (int x = 0; x < partita.getActiveNumPlayers(); x++) {
				if (partita.getMap().getPlayer(x).getEmporiums() == 0) {
					emporiFiniti = true;
				}
			}
		}
	}

	private void finalLoop(Action mossaClient) {
		int giocatoreSenzaEmpori = 0;
		for (int x = 0; x < partita.getActiveNumPlayers(); x++) {
			if (partita.getMap().getPlayer(x).getEmporiums() == 0) {
				giocatoreSenzaEmpori = x;
			}
		}
		partita.setStatoPartita("ULTIMO TURNO DI GIOCO: IL GIOCATORE " + giocatoreSenzaEmpori
				+ " HA FINITO GLI EMPORI!!!!!!!!!!!!!!!!!!");

		boolean flag = false;
		int loopRimasti = partita.getMap().getNumberOfPlayers() - 1;
		while (partita.getActiveNumPlayers() > 1 && loopRimasti > 0) {
			LOGGER.log(Level.INFO,"Turno del giocatore :" + partita.getTurno());

			// Il giocatore ad ogni turno pesca una carta
			PoliticCard cartaPescata = partita.getMap().getPoliticCardDeck().drawFromPool();
			partita.getMap().getPlayer(partita.getTurno()).addPoliticCard(cartaPescata);
			partita.setCard(cartaPescata);
			sendStatusGame();

			while (partita.getNumMosse() != Constants.NUMOSSE) {
				try {
					mossaClient = q.poll(120, TimeUnit.SECONDS);
				} catch (Exception e) {
					LOGGER.log(Level.INFO, "Nessuna mossa principale acquisita", e);
				}
				if (mossaClient == null) {
					LOGGER.log(Level.INFO, "E' scaduto il tempo per fare la tua mossa");
					Disconnesso(partita.getTurno());
				} else if (mossaClient.mossa <= 4 && mossaClient.mossa > 0) {

					applicaMossa(mossaClient);
					partita.incrementaNumMosse();

					sendStatusGame();
					if (flag == false) {
						try {
							mossaClient = q.poll(120, TimeUnit.SECONDS);
						} catch (Exception e) {
							LOGGER.log(Level.INFO, "Non e' arrivata alcuna mossa", e);
						}

						if (!mossaClient.nulla()) {
							partita.incrementaNumMosse();
							
							LOGGER.log(Level.INFO,
									"E' scaduto il tempo per fare la tua mossa, hai scelto di non usare la mossa veloce");

						} else {

							applicaMossa(mossaClient);
							
							partita.incrementaNumMosse();
							sendStatusGame();
							
						}
					}

				} else if (mossaClient.mossa <= 8 && mossaClient.mossa > 4) {
					applicaMossa(mossaClient);
					partita.incrementaNumMosse();
					
					flag = true;
					sendStatusGame();
				}
			}
			flag = false;

			/*
			 *  qua setto la variabile market a true quando il turno del
			 * gioco e' uguale a quello della partita
			 */

			if (partita.getNumMosse() == Constants.NUMOSSE) {
				loopRimasti--;
				partita.azzeraMosse();
				do {
					partita.changeTurn();
					LOGGER.log(Level.INFO,"Turno: " + partita.getTurno());
					flag = false;
				} while (!(partita.getGiocatore(partita.getTurno()).getAttivo()));

				sendStatusGame();
			}
		}

	}

	/*
	 * Metodo utilizzato per inviare lo stato aggiornato della partita ai
	 * client. Se si accorge che un client si e' disconnesso, lo disabilita
	 * dalla partita
	 */
	private void sendStatusGame() {
		for (int i = 0; i < num.size(); i++) {
			if (partita.getGiocatore(i).getAttivo()) {
				try {
					num.get(i).sendStatusGame(partita);
				} catch (Exception e) {
					LOGGER.log(Level.INFO, "Error nella send della partita aggiornata", e);
					Disconnesso(i);
				}
			}
		}
	}

	/**
	 * This method is used to apply the action selected by the player during the
	 * match.
	 * 
	 * @param mossaClient
	 *            The selected action.
	 */
	private void applicaMossa(Action mossaClient) {
		
		Action azione = mossaClient;
		model = azione.execute();
		partita.updateModel(model);
		sendStatusGame();
		
	}

	// collego la classe partita con il client inviando l'oggetto partita al
	// client
	private void partitaInit() {
		// invio lo stato iniziale della partita a tutti i client
		for (int i = 0; i < num.size(); i++) {
			try {
				num.get(i).sendTurno();
				num.get(i).sendStatusGame(partita);
			} catch (InterruptedException | IOException e) {
				LOGGER.log(Level.INFO, "Error: not send initial status Game", e);
				Disconnesso(i);
			}
		
		}
	}

	private void Disconnesso(int i) {
		LOGGER.log(Level.INFO,"Il giocatore " + i + " e' stato disconnesso.");
		partita.getGiocatore(i).setAttivo(false);

		if (partita.getTurno() == i) {
			do {
				partita.changeTurn();
			} while (!(partita.getGiocatore(partita.getTurno()).getAttivo()));
			partita.azzeraMosse();
		}
	}

	public Match getPartita() {
		return partita;
	}

	public ArrayBlockingQueue<Action> getQueue() {
		return q;
	}

	public ArrayBlockingQueue<MarketOffer> getQueueOffer() {
		return offer;
	}

	public ArrayBlockingQueue<MarketBuy> getQueueBuy() {
		return vendita;
	}

	// aggiunge il clienthandler x all'arraylist (per la riconnessione), al
	// posto giusto e gli invia lo stato della partita e il suo turno di gioco.
	public void addNum(ClientHandler x, int i) throws RemoteException, InterruptedException, IOException  {
		num.remove(i);
		num.add(i, x);
		partita.getGiocatore(i).setAttivo(true);
		num.get(i).sendTurno();
		num.get(i).sendStatusGame(partita);
	}

	@Override
	public void sendMossa(Action action) throws IOException, InterruptedException {
		q.put(action);
	}

	@Override
	public void sendOffer(MarketOffer offerta) throws IOException, InterruptedException {
		offer.put(offerta);
	}

	@Override
	public void sendBuy(MarketBuy sold) throws IOException, InterruptedException {
		vendita.put(sold);
	}
}