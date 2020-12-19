package it.polimi.ingsw.ps09.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketBuyAssistant;
import it.polimi.ingsw.ps09.actions.MarketBuyNulla;
import it.polimi.ingsw.ps09.actions.MarketBuyPermit;
import it.polimi.ingsw.ps09.actions.MarketBuyPolitic;
import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.actions.MarketOfferAssistant;
import it.polimi.ingsw.ps09.actions.MarketOfferNulla;
import it.polimi.ingsw.ps09.actions.MarketOfferPermit;
import it.polimi.ingsw.ps09.actions.MarketOfferPolitic;
import it.polimi.ingsw.ps09.actions.MossaNulla;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.net.MatchInterface;
import it.polimi.ingsw.ps09.user.Match;

/**
 * This is the basic game controller, manages the communication from clients to
 * the game model.
 */
public class ClientController {

	private final int turn;
	private Match partita;
	private final ObjectInputStream in;
	private final ObjectOutputStream out;
	private final MatchInterface game;
	private ArrayBlockingQueue<Match> match;
	private final Socket socket;
	private final Scanner stdin;
	private static final Logger LOGGER = Logger.getLogger(ClientController.class.getName());
	private View view;

	// inizializzo il costruttore con tutto cio' che necessitano le interfacce,
	// sia quella RMI che Socket
	public ClientController(final int turno, Match partita, final ObjectOutputStream out, final ObjectInputStream in,
			final MatchInterface game, ArrayBlockingQueue<Match> match, final Socket socket, final Scanner stdin) {

		this.turn = turno;
		this.socket = socket;
		this.partita = partita;
		this.out = out;
		this.in = in;
		this.game = game;
		this.match = match;
		this.stdin = stdin;
		view = new View(partita.getMap());

	}

	// inizializzo l'interfaccio di linea di comando
	public void startCLI(boolean reconnected) throws IOException, ClassNotFoundException, InterruptedException {
		int mossaScelta = 0;
		int scelta;
		int goodPlayer;
		

		try {
			if (partita.getTurno() != turn) {
				System.out.println("Colore giocatore:  " + partita.getMap().getPlayer(turn).getColor().toString());
				System.out.println("Aspetta il tuo turno");
				System.out.println("Il turno di gioco  e' : " + partita.getTurno());
				System.out.println("Il tuo turno e' : " + turn);
			}

			while (!partita.finita() && partita.getActiveNumPlayers() >= 1) {

				while (partita.getTurno() != turn) {
					updatePartita();
					if (partita.getMarket()) {
						updatePartita();
						partita.resetOffers();
						startMarket();
						aspetta();
						startAsta();
						aspetta();
						Thread.sleep(3000);
						updatePartita();
						view.endMarket(partita.getMap(), turn);

					}
				}

				
				if (partita.getMarket()) {
					updatePartita();
					partita.resetOffers();
					startMarket();
					aspetta();
					startAsta();
					aspetta();
					Thread.sleep(3000);
					updatePartita();
					view.endMarket(partita.getMap(), turn);

				}

				/*
				 * se e' il proprio turno parte un timer di 60 secondi, alla
				 * fine del quale se il giocatore non ancora effettuato l'azione
				 * verra' kiccato fuori
				 */
				if (partita.getTurno() == turn) {
					System.out.println(partita.statoPartita());
					System.out.println("Turno del giocatore " + partita.getTurno());
					updatePartita();
					// getCarta prende la carta pesacata nela gameloop
					System.out.println("Hai pescato una carta politica: " + partita.getCarta().getColor().toString());

					Timer timer = new Timer();
					timer.schedule(new TimerTask() {

						@Override
						public void run() {
							System.out.println(
									"Il tempo per compiere la mossa e' scaduto, sei statto buttato fuori dal server");
							stdin.close();
							if (game == null) {
								try {
									in.close();
									out.close();
									socket.close();
								} catch (IOException e) {
									LOGGER.log(Level.INFO, "Error close stream", e);
								}
							}
						}

					}, 120000);

					int rifaiMossa;
					if (partita.getNumMosse() == 0) {
						// se siamo alla prima mossa
						mossaScelta = 0;
					}

					do {
						mossaScelta = view.sendMossa();
						rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
					} while (rifaiMossa == -1);

					timer.cancel();
					boolean flag1 = true;

					do {
						if (view.getAzione().isValid()) {
							send(view.getAzione());

							flag1 = false;
						} else {

							goodPlayer = view.repeat(mossaScelta, partita.getTurno(), partita.getMap());
							if (goodPlayer == -1) {
								do {
									mossaScelta = view.sendMossa();
									rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
								} while (rifaiMossa == -1);
							}

						}
					} while (flag1);

					// controllo se ci sono dei bonus strani e se il model e'
					// stato modificato
					if (partita.getMap().getBonusStrano() != -1) {
						view.InputParametersWeirdBonus(partita.getMap().getBonusStrano(), partita.getMap(),
								partita.getTurno());
						// devo controllare se funziona
					}

					updatePartita();

					view.afterMossa(mossaScelta, partita.getMap(), partita.getTurno());

					// in caso di ADDACTION
					if (partita.getMap().getAction()) {
						do {
							mossaScelta = view.sendMossaPrincipale();
							rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
						} while (rifaiMossa == -1);

						boolean flagX = true;
						do {
							if (view.getAzione().isValid()) {
								send(view.getAzione());
								flagX = false;
							} else {

								goodPlayer = view.repeat(mossaScelta, partita.getTurno(), partita.getMap());
							}
						} while (flagX);
						updatePartita();
					}

					if (mossaScelta < 5 && mossaScelta > 0) {
						System.out
								.println("\nHai a disposizione anche una mossa veloce: vuoi utilizzarla?  0)si , 1)no");

						// reads the client choice
						while (!stdin.hasNextInt()) {
							System.out.println("Immettere un numero");
							stdin.next();
						}

						scelta = stdin.nextInt();

						if (scelta == 0) {
							do {
								mossaScelta = view.sendMossaVeloce();
								if (mossaScelta == 8
										&& partita.getMap().getPlayer(partita.getTurno()).getAssistants() < 3) {
									rifaiMossa = -1;
									System.out.println(
											"Non hai abbastanza assistenti per rifare una mossa Principale. Scegli un'altra mossa Veloce");
								} else if (mossaScelta == 8
										&& partita.getMap().getPlayer(partita.getTurno()).getAssistants() >= 3) {
									rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
									if (rifaiMossa != -1) {
										partita.getMap().getPlayer(partita.getTurno()).addAssistants(-3);
										partita.getMap().drawAssistants(-3);
									}

								} else {

									rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
								}
							} while (rifaiMossa == -1);

							boolean flag2 = true;
							do {
								if (view.getAzione().isValid()) {
									send(view.getAzione());
									flag2 = false;
								} else {

									goodPlayer = view.repeat(mossaScelta, partita.getTurno(), partita.getMap());
									if (goodPlayer == -1) {
										do {
											mossaScelta = view.sendMossa();
											rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(),
													partita.getMap());
										} while (rifaiMossa == -1);
									}

								}
							} while (flag2);

							updatePartita();

							// controllo se ci sono dei bonus strani e se il
							// model e' stato modificato
							if (partita.getMap().getBonusStrano() != -1) {
								view.InputParametersWeirdBonus(partita.getMap().getBonusStrano(), partita.getMap(),
										partita.getTurno());
								// devo controllare se funziona
							}

							updatePartita();

							view.afterMossa(mossaScelta, partita.getMap(), partita.getTurno());

							// una volta inviata la mossa possiamo cancellare il
							// timer

						}
						send(new MossaNulla(partita.getMap(), 0, partita.getTurno()));

						updatePartita();

					} else {

						do {
							mossaScelta = view.sendMossaPrincipale();
							rifaiMossa = view.manageMossa(mossaScelta, partita.getTurno(), partita.getMap());
						} while (rifaiMossa == -1);

						boolean flag3 = true;
						do {
							if (view.getAzione().isValid()) {
								send(view.getAzione());
								flag3 = false;
							} else {
								goodPlayer = view.repeat(mossaScelta, partita.getTurno(), partita.getMap());
							}
						} while (flag3);

						// controllo se ci sono dei bonus strani e se il model
						// e' stato modificato
						if (partita.getMap().getBonusStrano() != -1) {
							view.InputParametersWeirdBonus(partita.getMap().getBonusStrano(), partita.getMap(),
									partita.getTurno());
							// devo controllare se funziona
						}
						updatePartita();

					}
					updatePartita();
				}

			}

			endGame();

		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Error StartCLI", e);
		}

	}

	private void aspetta() throws ClassNotFoundException, IOException, InterruptedException {
		while (partita.getMarkeTurn() >= 0) {
			updatePartita();// aspetto che tutti abbiano fatto delle offerte}
		}
	}

	private boolean testOffer(int choice) {
		boolean valido = true;
		switch (choice) {
		case 1:
			if (partita.getMap().getPlayer(turn).getAssistants() >= 1) {
				valido = true;
			} else {
				valido = false;
			}
			break;

		case 2:
			if (partita.getMap().getPlayer(turn).getCards().size() >= 1) {
				valido = true;
			} else {
				valido = false;
			}
			break;
		case 3:
			if (partita.getMap().getPlayer(turn).getPermitsUp().size() >= 1) {
				valido = true;
			} else {
				valido = false;
			}
			break;
		}
		return valido;
	}

	private void startMarket() throws ClassNotFoundException, IOException, InterruptedException {

		MarketOffer offer = null;

		boolean valido;
		int offerta;
		int prezzo;
		view.welcomeMarket();

		System.out.println(""+partita.getMarkeTurn());
		//ciclo fino quando non e' il mio turno
		while(partita.getMarkeTurn()!= turn){ updatePartita();	}
		
		
		if(view.choiceMarket()==1){
			 offer = new MarketOfferNulla(partita.getMap().getPlayer(turn));
			 sendOffer(offer);
			 view.printNoOffer();
		}else{
		
		
		do{
		offerta = view.startMarket(partita.getMap(), partita.getMarkeTurn());
		valido = testOffer(offerta);
		}while(!valido);
		
		switch(offerta){

		case 1:
			prezzo = view.offerAssistant();
			offer = new MarketOfferAssistant(partita.getMap().getPlayer(turn), prezzo);
			sendOffer(offer);
			break;
		case 2:
			Colors coloreCarta = view.offerPoliticCard();
			prezzo = view.priceOffer();
			PoliticCard card = new PoliticCard(coloreCarta);
			offer = new MarketOfferPolitic(partita.getMap().getPlayer(turn), card, prezzo);
			sendOffer(offer);
			break;
		case 3:
			int numberPermitCard = view.offerPermitCard(partita.getMap(), partita.getMarkeTurn());
			prezzo = view.priceOffer();
			if (partita.getMap().getPlayer(turn).getPermitUpByIndex(numberPermitCard) == null)
				throw new NullPointerException("la carta permesso inserita non e' valida!!");
			PermitCard permit = partita.getMap().getPlayer(turn).getPermitUpByIndex(numberPermitCard);
			offer = new MarketOfferPermit(partita.getMap().getPlayer(turn), permit, numberPermitCard);
			sendOffer(offer);

			break;
		}

		if (offer == null)
			throw new NullPointerException("l'offerta non e' valida!!");

		System.out.println("l'offerta e' stata accettata!");
		view.endOffer();
		}

	}

	private void startAsta() throws ClassNotFoundException, IOException, InterruptedException {

		MarketBuy buy = null;
		MarketOffer offer = null;

		int scelta;
		boolean valido = true;

		while(partita.getMarkeTurn()!= turn){ updatePartita();	}
		
		view.offersDone(partita.getOffers());
		
		
		if(view.choiceAsta()==1){
			buy= new MarketBuyNulla(partita.getMap().getPlayer(turn));
			sendBuy(buy);
			
		}else {
		
		
		scelta = view.offerChoice(partita.getOffers());

		offer = partita.getOfferByIndex(scelta);
		if (offer == null)
			throw new IllegalArgumentException("l'offerta scelta non e' valida!!");

		do {
			if (partita.getOfferByIndex(scelta).getPrice() <= partita.getMap().getPlayer(turn).getCoins()) {

				if (offer.getAssOffered()) {

					buy = new MarketBuyAssistant(partita.getMap().getPlayer(turn), scelta);
					if (buy.isValidBuy(partita.getOffers(), partita.getMap().getPlayer(turn))) {
						sendBuy(buy);
						valido = true;
					} else {
						valido = false;
					}

				} else if (offer.getPerOffered() != null) {

					PermitCard permit = offer.getPerOffered();

					if (permit == null)
						throw new NullPointerException("carta permesso nulla!!");

					buy = new MarketBuyPermit(partita.getMap().getPlayer(turn), permit, scelta);
					if (buy.isValidBuy(partita.getOffers(), partita.getMap().getPlayer(turn))) {
						sendBuy(buy);
						valido = true;
					} else {
						valido = false;
					}

				}

				else if (offer.getPolOffered() != null) {

					PoliticCard card = offer.getPolOffered();

					if (card == null)
						throw new NullPointerException("carta politica nulla!!");

					buy = new MarketBuyPolitic(partita.getMap().getPlayer(turn), card, scelta);

					if (buy.isValidBuy(partita.getOffers(), partita.getMap().getPlayer(turn))) {
						sendBuy(buy);

						valido = true;
					} else {

						valido = false;
					}

				} else
					throw new IllegalArgumentException("l'offerta scelta non contiene un cazzo diocane!!");

			} else {
				System.out.println(
						"Non puoi permetterti di accettare questa offerta... non hai abbastanza soldi PEZZENTE");
				scelta = view.offerChoice(partita.getOffers());
				valido = false;
			}
		} while (!valido);
		}

	}

	private void updatePartita() throws ClassNotFoundException, IOException, InterruptedException {
		if (game == null) {
			partita = (Match) in.readObject();
		} else {
			partita = match.take();
		}
	}

	private void endGame() {
		if (partita.getActiveNumPlayers() > 1) {
			if (game == null) {
				System.out.println("Partita terminata");
			} else {
				System.out.println("Partita terminata, sei l'unico giocatore rimasto");
			}
		}
	}

	// invia la mossa al server
	private void send(Action x) throws IOException, InterruptedException {
		if (game == null) {
			out.writeObject(x);
			out.flush();
		} else {
			game.sendMossa(x);
		}
	}

	private void sendOffer(MarketOffer y) throws IOException, InterruptedException {
		if (game == null) {
			out.writeObject(y);
			out.flush();
		} else {
			game.sendOffer(y);
		}
	}

	private void sendBuy(MarketBuy z) throws IOException, InterruptedException {
		if (game == null) {
			out.writeObject(z);
			out.flush();
		} else {
			game.sendBuy(z);
		}
	}
}