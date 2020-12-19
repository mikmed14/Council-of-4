package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic emporium class.
 */
public class Emporium implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The player who has the emporium.
	 */
	private PlayerColor color;

	/**
	 * The city in which is situated the emporium.
	 */
	private City city;

	/**
	 * The Emporium constructor sets a new emporium of a player.
	 * 
	 * @param color
	 *            The color of the owner.
	 */
	public Emporium(PlayerColor color) {
		this.color = color;
		city = null;
	}

	/**
	 * @return the color of the emporium (is the same of the own player)
	 */
	public PlayerColor getColor() {
		return color;
	}

	/**
	 * Sets the position (city) of the emporium.
	 * 
	 * @param city
	 *            The new position.
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * @return the position of the emporium
	 */
	public City getCity() {
		return this.city;
	}
}