package it.polimi.ingsw.ps09.user;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PoliticCard;

/**
 * This class has a copy of the board game and takes care of changing the turn
 * of the game.
 */
public class Match implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The board game.
	 */
	private Model model;

	/**
	 * The game round.
	 */
	private int turnGioco;

	/**
	 * A variable that indicates whether a match is over or not.
	 */
	private boolean gameover;

	/**
	 * A variable that specify the current turn of the game.
	 */
	private int numMosseTurno;

	/**
	 * The number of the players in the game.
	 */
	private int numPlayers;

	/**
	 * The politics card drawn.
	 */
	private PoliticCard cartaPescata;

	/**
	 * A text message.
	 */
	private String stato = "Partita in Corso!!";

	/**
	 * The number that indicates the market phase.
	 */
	private int markeTurn;

	/**
	 * The boolean condition related to the market phase.
	 */
	private boolean market = false;

	/**
	 * The list of offers during the market phase.
	 */
	private ArrayList<MarketOffer> offers;

	/**
	 * The Match constructor sets the current board game and the number of
	 * players.
	 * 
	 * @param numPlayers
	 *            The number of players involved in the game.
	 * @param model
	 *            The current board game.
	 */
	public Match(int numPlayers, Model model) {
		this.model = model;
		turnGioco = 0;
		gameover = false;
		numMosseTurno = 0;
		this.numPlayers = numPlayers;
		this.offers = new ArrayList<MarketOffer>();
	}

	/**
	 * @return the number of players involved in the game
	 */
	public int getNumPlayers() {
		return this.numPlayers;
	}

	/**
	 * This method returns the player of given turn.
	 * 
	 * @param turno
	 *            The turn of the player
	 * @return the player of given turn
	 */
	public Player getGiocatore(int turno) {
		return model.getPlayer(turno);
	}

	/**
	 * @return the current board game (as a map)
	 */
	public Model getMap() {
		return this.model;
	}

	/**
	 * @return the active number of players
	 */
	public int getActiveNumPlayers() {
		int cont = 0;
		for (int i = 0; i < model.getPlayers().size(); i++)
			if (model.getPlayers().get(i).getAttivo())
				cont++;
		return cont;
	}

	/**
	 * @return the turn in which the game is
	 */
	public int getTurno() {
		return turnGioco;
	}

	/**
	 * This method is used to change a turn of the player.
	 */
	public void changeTurn() {
		turnGioco++;
		if (turnGioco >= model.getPlayers().size())
			turnGioco = 0;
	}

	/**
	 * @return the boolean condition to know if the match is over or not
	 */
	public boolean finita() {
		return gameover;
	}

	/**
	 * This method is used to terminate a match.
	 */
	public void termina() {
		gameover = true;
	}

	/**
	 * @return the number of moves executed in the considered turn
	 */
	public int getNumMosse() {
		return numMosseTurno;
	}

	/**
	 * This method is used to increment the number of moves.
	 */
	public void incrementaNumMosse() {
		numMosseTurno++;
	}

	/**
	 * This method is used to decrement the number of moves.
	 */
	public void decrementaNumMosse() {
		numMosseTurno--;
	}

	/**
	 * This method is used to reset the number of moves.
	 */
	public void azzeraMosse() {
		numMosseTurno = 0;
	}

	/**
	 * This method is used to update the board game during a match.
	 * 
	 * @param model
	 *            The board game to be updated.
	 */
	public void updateModel(Model model) {
		this.model = model;
	}

	/**
	 * This method is used the the politics card drawn.
	 * 
	 * @param cartaPescata
	 *            The new politics card drawn.
	 */
	public void setCard(PoliticCard cartaPescata) {
		this.cartaPescata = cartaPescata;
	}

	/**
	 * @return the politics card drawn
	 */
	public PoliticCard getCarta() {
		return this.cartaPescata;
	}

	/**
	 * This method sets the text message about the match.
	 * 
	 * @param nome
	 *            The new text message.
	 */
	public void setStatoPartita(String nome) {
		this.stato = nome;
	}

	/**
	 * @return the text message about the match.
	 */
	public String statoPartita() {
		return this.stato;
	}

/**
 * This method sets the number that indicates the market phase (to default
 * value).
 */

	public void setMarkeTurnDefault(){
		this.markeTurn = (numPlayers-1);
	}



	/**
	 * This method is used to set the number that indicates the market phase.
	 */
	public void setMarkeTurn() {
		this.markeTurn--;
	}

	/**
	 * @return the number that indicates the market phase.
	 */
	public int getMarkeTurn() {
		return this.markeTurn;
	}

	/**
	 * This method sets 'false' the market boolean (in this case the market
	 * phase is over).
	 */
	public void setMarketFalse() {
		this.market = false;
	}

	/**
	 * This method sets 'true' the market boolean (in this case the market phase
	 * has begun).
	 */
	public void setMarketTrue() {
		this.market = true;
	}

	/**
	 * @return the boolean condition related to the market phase
	 */
	public boolean getMarket() {
		return this.market;
	}

	/**
	 * This method adds an offer to the ArrayList of offers.
	 * 
	 * @param offer
	 *            The offer to add.
	 */
	public void addOffer(MarketOffer offer) {
		if (offer == null)
			throw new NullPointerException("l'offerta non e' valida");
		this.offers.add(offer);
	}

	/**
	 * @return the ArrayList of offers.
	 */
	public ArrayList<MarketOffer> getOffers() {
		return this.offers;
	}

	/**
	 * This method returns the offer of given index.
	 * 
	 * @param i
	 *            The index of the offer to return.
	 * 
	 * @return the selected offer
	 */
	public MarketOffer getOfferByIndex(int i) {
		if ((i < 0) || (i >= this.offers.size()))
			throw new IllegalArgumentException("l'indice dell'offerta non e' valido!!");
		return this.offers.get(i);
	}

	/**
	 * This method removes the offer of given index from the ArrayList of
	 * Offers.
	 * 
	 * @param i
	 *            The index position of the Offer to remove.
	 */
	public void removeOffer(int i) {
		if ((i < 0) || (i >= this.offers.size()))
			throw new IllegalArgumentException("l'indice dell'offerta non e' valido!!");
		offers.remove(i);
	}

	/**
	 * This method resets the ArrayList of Offers (so that it no more contains
	 * anything).
	 */
	public void resetOffers() {
		this.offers = null;
		this.offers = new ArrayList<MarketOffer>();
	}
}