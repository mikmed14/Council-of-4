package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic politics card of the game.
 */
public class PoliticCard implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The color of the card.
	 */
	private Colors color;

	/**
	 * The PoliticCard constructor sets the color.
	 * 
	 * @param color
	 *            The color of the politics card.
	 */
	public PoliticCard(Colors color) {
		this.color = color;
	}

	/**
	 * @return the color of the politics card
	 */
	public Colors getColor() {
		return this.color;
	}

	/**
	 * @return the string representing a card
	 */
	@Override
	public String toString() {
		return "Politics Card with color: " + color;
	}

	/**
	 * This method sets a new color to the card.
	 * 
	 * @param color
	 *            The new color to bet set.
	 */
	public void setColor(Colors color) {
		this.color = color;
	}
}