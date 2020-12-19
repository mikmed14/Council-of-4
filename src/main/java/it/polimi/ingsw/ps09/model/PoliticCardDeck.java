package it.polimi.ingsw.ps09.model;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Random;

/**
 * A deck of available politics cards in the game.
 */
public class PoliticCardDeck implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The 'pool' variable is used to draw cards each round.
	 */
	private ArrayList<PoliticCard> pool;

	/**
	 * The 'discards' variable is used to throw cards away each round.
	 */
	private ArrayList<PoliticCard> discards;

	/**
	 * Number of total politic cards, number of jolly in the deck, number of
	 * card for each color in the deck. These variables depend on the number of
	 * Players of the current game.
	 */
	private int numberOfCards;
	private int numberOfJollys;
	private int numberOfCardsForEachColor;

	/**
	 * The PoliticCardDeck constructor creates the pool and the discards deck
	 * (ArrayList), invokes the methods to compute the number of cards, the
	 * number of jollys and the number of cards for each color in the deck; then
	 * invokes the methods to initialize the pool with the exact number of cards
	 * for each color; finally invokes the method that shuffles the pool cards.
	 * 
	 * @param numberOfPlayers
	 *            The number of players of the current game.
	 */
	public PoliticCardDeck(int numberOfPlayers) {
		pool = new ArrayList<PoliticCard>();
		discards = new ArrayList<PoliticCard>();
		numberOfCards = computeNumberOfCards(numberOfPlayers);
		numberOfJollys = computeNumberOfJollys(numberOfPlayers);
		numberOfCardsForEachColor = computeNumberOfCardsForEachColor(numberOfCards);
		initializePool(numberOfCardsForEachColor, numberOfJollys);
		shuffle();
	}

	/**
	 * This method removes and returns the last card of the pool.
	 * 
	 * @return the removed card
	 */
	public PoliticCard drawFromPool() {
		if (isEmpty())
			reverse();
		return pool.remove(pool.size() - 1);
	}

	/**
	 * Checks if the pool is empty; returns true if the pool is empty.
	 */
	public boolean isEmpty() {
		return this.pool.isEmpty();
	}

	/**
	 * This method adds a new card to the pool (this is a private method, used
	 * only to initialize the pool).
	 * 
	 * @param color
	 *            The color of the card to add.
	 */
	private void addPoolCard(Colors color) {
		pool.add(new PoliticCard(color));
	}

	/**
	 * This methos adds a new politcs card to the discards deck.
	 */
	public void addDiscardsCard(PoliticCard card) {
		discards.add(card);
	}

	/**
	 * Removes the last card of the discards.
	 * 
	 * @return the removed card
	 */
	private PoliticCard removeFromDiscards() {
		return discards.remove(discards.size() - 1);
	}

	/**
	 * This method computes the number of cards, given the number of players of
	 * the current game.
	 * 
	 * @param numberOfPlayers
	 *            The numbers of players of the current game.
	 * 
	 * @return number of total politic card of the current game
	 */
	public int computeNumberOfCards(int numberOfPlayers) {
		int numberOfCard;
		if (numberOfPlayers % 2 == 0)
			numberOfCard = 21 * numberOfPlayers + 6;
		else
			numberOfCard = 21 * (numberOfPlayers - 1) + 6;
		return numberOfCard;
	}

	/**
	 * This method computes the number of jollys of the politic card deck, given
	 * the number of players of the current game.
	 * 
	 * @param numberOfPlayers
	 *            The numbers of players of the current game.
	 * 
	 * @return the number of jollys of the current game
	 */
	public int computeNumberOfJollys(int numberOfPlayers) {
		if (numberOfPlayers % 2 == 0)
			return numberOfPlayers * 3;
		else
			return (numberOfPlayers - 1) * 3;
	}

	/**
	 * This method computes the number of cards for each color, given the number
	 * of players, the total number of cards and the number of jollys.
	 * 
	 * @param numberOfCards
	 *            The number of politic cards of the current game
	 * @return the number of politics cards for each color of the current game
	 */
	public int computeNumberOfCardsForEachColor(int numberOfCards) {
		return numberOfCards / 6;
	}

	/**
	 * This method initializes the pool with the exact number of cards for each
	 * color and jollys.
	 * 
	 * @param numberOfCardsForEachColor
	 *            The number of cards for each color of the current game.
	 * @param numberOfJollys
	 *            The number of jollys of the current game.
	 */
	public void initializePool(int numberOfCardsForEachColor, int numberOfJollys) {
		int i;
		for (i = 0; i < numberOfJollys; i++) {
			addPoolCard(Colors.JOLLY);
		}

		for (i = 0; i < numberOfCardsForEachColor; i++) {
			addPoolCard(Colors.GREEN);
			addPoolCard(Colors.BLUE);
			addPoolCard(Colors.YELLOW);
			addPoolCard(Colors.RED);
			addPoolCard(Colors.PINK);
			addPoolCard(Colors.BLACK);
		}
	}

	/**
	 * This method shuffles the already initialized pool of politic cards.
	 */
	private void shuffle() {

		long seed = System.nanoTime();
		Collections.shuffle(this.pool, new Random(seed));
	}

	/**
	 * This method is used when the deck of politics cards ends. It adds the
	 * discarded cards in the deck and shuffles.
	 */
	public void reverse() {
		int discardSize = discards.size();

		for (int i = 0; i < discardSize; i++) {
			addPoolCard(removeFromDiscards().getColor());
		}
		shuffle();
	}
	
	
	/**
	 * 
	 * @return the pool of politic cards
	 */
	public ArrayList<PoliticCard> getPool(){
		return this.pool;
	}
	
	/**
	 * 
	 * @return the discards pool
	 */
	
	public ArrayList<PoliticCard> getDiscards(){
		return this.discards;
	}
	
}