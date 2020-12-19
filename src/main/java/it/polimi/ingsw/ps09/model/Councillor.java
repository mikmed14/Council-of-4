package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic councillor class.
 */
public class Councillor implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The color of the councillor.
	 */
	private Colors color;

	/**
	 * The constructor sets a color of the new councillor.
	 * 
	 * @param color
	 *            The new color.
	 */
	public Councillor(Colors color) {
		this.color = color;
	}

	/**
	 * Sets a color of the councillor.
	 * 
	 * @param color
	 *            The new color.
	 */
	public void setColor(Colors color) {
		this.color = color;
	}

	/**
	 * @return the color of the councillor
	 */
	public Colors getColor() {
		return this.color;
	}
}