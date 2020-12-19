package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic city class of the board.
 */
public class City implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The name of the city.
	 */
	private String name;

	/**
	 * The ID of the city identified by the first letter of his name.
	 */
	private char id;

	/**
	 * The color of the city.
	 */
	private CityColor color;

	/**
	 * The region the city belongs to.
	 */
	private Region region;

	/**
	 * The links of the city with other nearby cities, as a string which
	 * contains the first letters of the linked cities.
	 */
	private String links;

	/**
	 * The emporiums present in every city.
	 */
	private Emporium[] emporiums;

	/**
	 * The reward token present in the city.
	 */
	private Reward reward;

	/**
	 * The number of players involved in the game.
	 */
	private int numberOfPlayers;

	/**
	 * The City constructor sets id, color, region, links, king and bonus tile
	 * members.
	 * 
	 * @param links
	 *            The links of the city with nearby cities.
	 * @param color
	 *            The color of the city.
	 * @param bonus
	 *            The reward token of the city.
	 * @param numberOfPlayers
	 *            The number of players (used to initialize the number of
	 *            emporiums for each city).
	 * @param name
	 *            The name of the city.
	 */
	public City(String links, CityColor color, Reward bonus, int numberOfPlayers, String name) {
		this.name = name;
		this.id = name.charAt(0);
		this.links = links;
		this.color = color;
		this.reward = new Reward();
		this.reward = bonus;

		if (numberOfPlayers == 2) {
			this.emporiums = new Emporium[4];

		} else {
			this.emporiums = new Emporium[numberOfPlayers];
		}
		this.numberOfPlayers = numberOfPlayers;

	}

	/**
	 * This method sets the name of the city.
	 * 
	 * @param name
	 *            The new name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name of the city
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the links of the city with nearby cities.
	 * 
	 * @param links
	 *            The new links.
	 */
	public void setLinkedCities(String links) {
		this.links = links;
	}

	/**
	 * @return the links of the nearby cities
	 */
	public String getLinkedCities() {
		return this.links;
	}

	/**
	 * Adds a new emporium in the emporium array of the city.
	 * 
	 * @param player
	 *            The owner of the emporium.
	 * 
	 * @throws java.io.IOException
	 */
	public void addEmporium(int turn) {
		int index = 0;

		for (int i = 0; i < emporiums.length; i++) {
			if (emporiums[i] == null)
				index = i;
		}

		emporiums[index] = new Emporium(PlayerColor.assignColorToTurn(turn));
	}

	/**
	 * Sets the region of the city.
	 * 
	 * @param region
	 *            The region of the city.
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * @return the region of the city
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Sets the color of the city.
	 * 
	 * @param color
	 *            The new color of the city.
	 */
	public void setColor(CityColor color) {
		this.color = color;
	}

	/**
	 * @return the color of the city
	 */
	public CityColor getColor() {
		return color;
	}

	/**
	 * @return the reward token present in the city
	 */
	public Reward getBonus() {
		return this.reward;

	}

	/**
	 * @return the ID (as a character) of the city
	 */
	public char getId() {
		return this.id;
	}

	/**
	 * Sets the ID (as a character) of the city.
	 * 
	 * @param c
	 *            The new id of the city.
	 */
	public void setId(char c) {
		this.id = c;
	}

	/**
	 * @return the number of emporiums already built on this city
	 */
	public int getNumberOfEmporiums() {
		int count = 0;
		for (int i = 0; i < numberOfPlayers; i++) {
			if (this.emporiums[i] != null)
				count++;
		}

		return count;

	}

	/**
	 * This method tells if the player already has an emporium (identified by
	 * the chosen color) in that city.
	 * 
	 * @param color
	 *            The color of the player for the comparison.
	 * 
	 * @return the boolean condition
	 */
	public boolean emporiumbBuilt(PlayerColor color) {

		if (color == null)
			throw new IllegalArgumentException("mi arriva  un colore  del giocatore NON valido");

		boolean emporium = false;
		for (int i = 0; i < this.emporiums.length; i++) {
			if ((emporiums[i] != null) && (emporiums[i].getColor() == color)) {
				emporium = true;
			}
		}
		return emporium;
	}
}