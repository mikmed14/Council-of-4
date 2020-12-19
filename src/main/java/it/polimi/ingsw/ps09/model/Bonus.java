package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic bonus tile class of the game.
 */
public class Bonus implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * A bonus is characterized by a quantity (as a integer) and a bonus type
	 * (as an enumeration).
	 */
	private int quantity;
	private BonusType type;

	/**
	 * The Bonus constructor sets its two attributes.
	 * 
	 * @param quantity
	 *            The first attribute (quantity).
	 * @param type
	 *            The second attribute (type).
	 */
	public Bonus(int quantity, BonusType type) {
		this.quantity = quantity;
		this.type = type;
	}

	/**
	 * @return the type of the bonus
	 */
	public BonusType getType() {
		return type;
	}

	/**
	 * @return the quantity of the bonus
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * This method is used to set the first attribute (bonus type) of balcony
	 * class.
	 * 
	 * @param type
	 *            The type of the bonus to be set.
	 */
	public void setType(BonusType type) {
		this.type = type;
	}

	/**
	 * This method is used to set the second attribute (quantity) of balcony
	 * class.
	 * 
	 * @param type
	 *            The quantity of the bonus to be set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}