package it.polimi.ingsw.ps09.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import it.polimi.ingsw.ps09.map.Constants;

/**
 * This class represents a set of available councillors that are in the game.
 */
public class CouncillorDeck implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * These attributes are used to save the number of councillors for each
	 * color.
	 */
	private int numberOfBlueCouncillors;
	private int numberOfRedCouncillors;
	private int numberOfPinkCouncillors;
	private int numberOfYellowCouncillors;
	private int numberOfGreenCouncillors;
	private int numberOfBlackCouncillors;

	/**
	 * The pool of councillors from which the players can draw to elect
	 * councillors.
	 */
	private ArrayList<Councillor> pool;

	/**
	 * The CouncillorDeck constructor initializes the pool with a correct
	 * number of councillors for each color and shuffles them.
	 */
	public CouncillorDeck() {
		pool = new ArrayList<Councillor>();
		initializeDeck();
		shuffle();
	}

	/**
	 * This (private) method adds a new councillor to the ArrayList in the last
	 * position (it's private because is used only to initialize the pool).
	 * 
	 * @param color
	 *            The color of the councillor to add.
	 */
	private void addCouncillor(Colors color) {
		this.pool.add(new Councillor(color));
	}

	/**
	 * This (private) method initializes the pool with the right number of
	 * councillors for each color (it's private because is used only to
	 * initialize the pool).
	 */
	private void initializeDeck() {
		// The number of councillors in the game is fixed, and also the number
		// of councillors for each color
		for (int i = 0; i < Constants.NUMOFCOUNCILLORSFOREACHBALCONY; i++) {
			addCouncillor(Colors.BLACK);
			addCouncillor(Colors.BLUE);
			addCouncillor(Colors.GREEN);
			addCouncillor(Colors.PINK);
			addCouncillor(Colors.RED);
			addCouncillor(Colors.YELLOW);
		}
	}

	/**
	 * This method shuffles the pool of councillors once it has been initialized.
	 */
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(this.pool, new Random(seed));
	}

	/**
	 * Removes and returns the last element of pool.
	 * 
	 * @return the removed element
	 */
	public Councillor drawFromPool() {
		return pool.remove(pool.size() - 1);
	}

	/**
	 * This method is used to take a councillor with selected color from the
	 * pool.
	 * 
	 * @param color
	 *            The selected color.
	 *            
	 * @return the councillor with the selected color
	 */
	public Councillor drawFromPoolCouncillorColor(Colors color) {
		
		for (int i = 0; i < pool.size(); i++) {
			if (pool.get(i).getColor().equals(color)) {
				return pool.remove(i);
			}
		}
		
		throw new NullPointerException();
		
	}

	/**
	 * This method is used to count the number of councillors for each color
	 * present in the pool.
	 */
	public void updateCount() {
		
		numberOfBlueCouncillors = 0;
		numberOfRedCouncillors = 0;
		numberOfPinkCouncillors = 0;
		numberOfYellowCouncillors = 0;
		numberOfGreenCouncillors = 0;
		numberOfBlackCouncillors = 0;

		for (Councillor c : pool) {
			if (c.getColor() == Colors.BLACK)
				setNumberOfBlackCouncillors(getNumberOfBlackCouncillors() + 1);
			if (c.getColor() == Colors.BLUE)
				setNumberOfBlueCouncillors(getNumberOfBlueCouncillors() + 1);
			if (c.getColor() == Colors.GREEN)
				setNumberOfGreenCouncillors(getNumberOfGreenCouncillors() + 1);
			if (c.getColor() == Colors.RED)
				setNumberOfRedCouncillors(getNumberOfRedCouncillors() + 1);
			if (c.getColor() == Colors.PINK)
				setNumberOfPinkCouncillors(getNumberOfPinkCouncillors() + 1);
			if (c.getColor() == Colors.YELLOW)
				setNumberOfYellowCouncillors(getNumberOfYellowCouncillors() + 1);
		}
	}

	/**
	 * This method returns the councillor which is in the given position.
	 * 
	 * @param index
	 *            The position of the selected councillor in the pool.
	 *            
	 * @return the selected councillor
	 */
	public Councillor getCouncillorByIndex(int index) {
		return this.pool.get(index);
	}

	/**
	 * @return the number of blue councillors in the pool
	 */
	public int getNumberOfBlueCouncillors() {
		return numberOfBlueCouncillors;
	}

	/**
	 * Sets the number of blue councillors in the pool.
	 * 
	 * @param numberOfBlueCouncillors
	 *            The number of blue councillors.
	 */
	public void setNumberOfBlueCouncillors(int numberOfBlueCouncillors) {
		this.numberOfBlueCouncillors = numberOfBlueCouncillors;
	}

	/**
	 * @return the number of red councillors in the pool
	 */
	public int getNumberOfRedCouncillors() {
		return numberOfRedCouncillors;
	}

	/**
	 * Sets the number of red councillors in the pool.
	 * 
	 * @param numberOfRedCouncillors
	 *            The number of red councillors.
	 */
	public void setNumberOfRedCouncillors(int numberOfRedCouncillors) {
		this.numberOfRedCouncillors = numberOfRedCouncillors;
	}

	/**
	 * @return the number of pink councillors in the pool
	 */
	public int getNumberOfPinkCouncillors() {
		return numberOfPinkCouncillors;
	}

	/**
	 * Sets the number of pink councillors in the pool.
	 * 
	 * @param numberOfPinkCouncillors
	 *            The number of pink councillors.
	 */
	public void setNumberOfPinkCouncillors(int numberOfPinkCouncillors) {
		this.numberOfPinkCouncillors = numberOfPinkCouncillors;
	}

	/**
	 * @return the number of yellow councillors in the pool
	 */
	public int getNumberOfYellowCouncillors() {
		return numberOfYellowCouncillors;
	}

	/**
	 * Sets the number of yellow councillors in the pool.
	 * 
	 * @param numberOfYellowCouncillors
	 *            The number of yellow councillors.
	 */
	public void setNumberOfYellowCouncillors(int numberOfYellowCouncillors) {
		this.numberOfYellowCouncillors = numberOfYellowCouncillors;
	}

	/**
	 * @return the number of green councillors in the pool
	 */
	public int getNumberOfGreenCouncillors() {
		return numberOfGreenCouncillors;
	}

	/**
	 * Sets the number of green councillors in the pool.
	 * 
	 * @param numberOfGreenCouncillors
	 *            The number of green councillors.
	 */
	public void setNumberOfGreenCouncillors(int numberOfGreenCouncillors) {
		this.numberOfGreenCouncillors = numberOfGreenCouncillors;
	}

	/**
	 * @return the number of black councillors in the pool
	 */
	public int getNumberOfBlackCouncillors() {
		return numberOfBlackCouncillors;
	}

	/**
	 * Sets the number of black councillors in the pool.
	 * 
	 * @param numberOfBlackCouncillors
	 *            The number of black councillors.
	 */
	public void setNumberOfBlackCouncillors(int numberOfBlackCouncillors) {
		this.numberOfBlackCouncillors = numberOfBlackCouncillors;
	}

	/**
	 * This method adds a new councillor in the pool.
	 * 
	 * @param councillor
	 *            The new councillor to be added.
	 */
	public void addCouncillor(Councillor councillor) {
		this.pool.add(councillor);
	}

	/**
	 * Checks if the councillor of the given index is in the pool.
	 * 
	 * @param color
	 *            The color of selected councillor.
	 *            
	 * @return the boolean condition about its presence
	 */
	public boolean checkCouncillorIsInPool(Colors color) {
		for (int i = 0; i < pool.size(); i++)
			if (getCouncillorByIndex(i).getColor() == color)
				return true;
		return false;
	}

	/**
	 * @return the ArrayList of pool councillor
	 */
	public ArrayList<Councillor> getPool() {
		return this.pool;
	}
}