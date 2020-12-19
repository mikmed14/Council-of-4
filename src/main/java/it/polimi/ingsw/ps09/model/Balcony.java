package it.polimi.ingsw.ps09.model;

import java.io.IOException;
import java.io.Serializable;

import it.polimi.ingsw.ps09.map.Constants;

/**
 * The basic council balcony class
 */
public class Balcony implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The 4 councillors who are in a balcony.
	 */
	private Councillor[] councillors;

	/**
	 * The Balcony constructor sets a new array of councillors (in each balcony
	 * there are four councillors).
	 */
	public Balcony() {
		councillors = new Councillor[Constants.NUMOFCOUNCILLORSFOREACHBALCONY];
	}

	/**
	 * This method inserts a new councillor in the first position of the array,
	 * then removes and returns the councillor in the last position.
	 * 
	 * @param councillor
	 *            The new councillor to be included.
	 * @return the last councillor
	 */
	public Councillor setCouncillor(Councillor councillor) {

		// reference to the councillor in the
		// last position
		Councillor last = councillors[councillors.length - 1];

		// moves every councillor of the array to the next position
		for (int i = councillors.length - 1; i > 0; i--) {
			councillors[i] = councillors[i - 1];
		}

		// initialize the first position of the array with the new councillor
		// and returns the removed councillor
		councillors[0] = councillor;
		return last;
	}

	/**
	 * Checks if the balcony is full. If it's not, inserts a new councillor in
	 * the last available position of the array. This is a protected method only
	 * used to initialize the balconies by the model class.
	 * 
	 * @param councillor
	 *            The new councillor to be included
	 * @param i
	 *            The index that indicates the position of the councillor
	 * @throws java.io.IOException
	 */
	protected void addCouncillor(Councillor councillor, int i) throws IOException {
		councillors[i] = councillor;
	}

	/**
	 * @return The array of councillors
	 */
	public Councillor[] getCouncillors() {
		return councillors;
	}

	/**
	 * This method is used to return the councillor of the specified
	 * position
	 * 
	 * @param x
	 *            The position of interested councillor
	 * @return The councillor of given index
	 */
	public Councillor getCouncillorsByIndex(int x) {
		if (x < Constants.NUMOFCOUNCILLORSFOREACHBALCONY && x >= 0) {
			return councillors[x];
		} else
			throw new IllegalAccessError();
	}
}