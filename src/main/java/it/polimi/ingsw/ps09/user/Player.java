package it.polimi.ingsw.ps09.user;

import java.io.Serializable;
import java.util.ArrayList;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.CityColor;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * The class that models the player of the game.
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The name of the player.
	 */
	private String name;

	/**
	 * This variable indicates the activity status of the player in the game.
	 */
	private boolean busy;

	/**
	 * This variable indicates the round of the player in the game.
	 */
	private int turn;

	/**
	 * The number of emporiums still available to build for this player.
	 */
	private int emporiums;

	/**
	 * The permit cards owned by the player that the player didn't yet use to
	 * build an emporium.
	 */
	private ArrayList<PermitCard> permitsUp;

	/**
	 * The permit cards owned by the player already used to build an emporium.
	 */
	private ArrayList<PermitCard> permitsDown;

	/**
	 * The politic cards of the player.
	 */
	private ArrayList<PoliticCard> cards;

	/**
	 * The color of the player.
	 */
	private PlayerColor color;

	/**
	 * The Victory points track.
	 */
	private int points;

	/**
	 * The coins track.
	 */
	private int coins;

	/**
	 * The nobility track.
	 */
	private int nobility;

	/**
	 * The assistants used by the player.
	 */
	private int assistants;

	/**
	 * These boolean variables are used to know if the player already got the
	 * bonuses taken by building emporiums in every city of the same region.
	 */
	private Boolean[] regions;
	private Boolean[] colors;

	/**
	 * The number of victory points earned by the player throught premium king
	 * cards and bonus cards
	 */
	private int finalBonuses;

	/**
	 * The Player constructor sets the round of the player in the game and his
	 * color.
	 * 
	 * @param turn
	 *            The round of the player.
	 */
	public Player(int turn) {
		this.turn = turn;
		busy = true;

		nobility = 0;
		points = 0;

		emporiums = 10;
		this.permitsUp = new ArrayList<PermitCard>();
		this.permitsDown = new ArrayList<PermitCard>();
		this.cards = new ArrayList<PoliticCard>();
		this.color = PlayerColor.assignColorToTurn(turn);

		regions = new Boolean[Constants.NUMOFREGIONS];
		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {
			regions[i] = false;
		}

		finalBonuses = 0;

		colors = new Boolean[Constants.NUMOFREGIONS + 1];
		for (int j = 0; j < Constants.NUMOFREGIONS + 1; j++) {
			colors[j] = false;
		}

	}

	/**
	 * @return the round of the player in the game
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * @return the activity status of the player in the game
	 */
	public boolean getAttivo() {
		return busy;
	}

	/**
	 * Sets the activity status of the player.
	 * 
	 * @param x
	 *            The boolean condition about activity status to be set.
	 */
	public void setAttivo(boolean x) {
		busy = x;
	}

	/**
	 * Adds a permit card to the ArrayList of Permit cards of the player.
	 * 
	 * @param card
	 *            The permit card to add.
	 */
	public void addPermitCard(PermitCard card) {
		this.permitsUp.add(card);
	}

	/**
	 * Adds a politics card to the player hand.
	 * 
	 * @param card
	 *            The politic card to add.
	 */
	public void addPoliticCard(PoliticCard card) {
		this.cards.add(card);
	}

	/**
	 * @return the player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the color selected by the player
	 */
	public PlayerColor getColor() {
		return color;
	}

	/**
	 * @return the Victory points of the player
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Increments the Victory points of the player of a given value.
	 * 
	 * @param quantity
	 *            The given value to add.
	 */
	public void addPoints(int quantity) {
		if (points + quantity <= 0) {
			throw new IllegalArgumentException("error in addPoints");
		} else
			points = points + quantity;
	}

	/**
	 * @return the coins number of the player
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * Increments the coins number of the player of a given value.
	 * 
	 * @param quantity
	 *            The given value to add.
	 */
	public void addCoins(int quantity) {
		if (coins + quantity < 0)
			throw new IllegalArgumentException();
		coins = coins + quantity;
	}

	/**
	 * @return the nobility of the player
	 */
	public int getNobility() {
		return nobility;
	}

	/**
	 * Increments the nobility points of the player of a given value.
	 * 
	 * @param quantity
	 *            The given value to add.
	 */
	public void addNobility(int quantity) {
		if (quantity <= 0)
			throw new IllegalArgumentException();
		else
			this.nobility = nobility + quantity;
	}

	/**
	 * Increments the number of assistants used by the player of a given value.
	 * 
	 * @param quantity
	 *            The given value to add.
	 */
	public void addAssistants(int quantity) {

		if (this.assistants + quantity < 0)
			throw new IllegalArgumentException();
		else
			this.assistants = assistants + quantity;
	}

	/**
	 * @return the number of assistants used by the player
	 */
	public int getAssistants() {
		return assistants;
	}

	/**
	 * @return the politics cards ArrayList of the player
	 */
	public ArrayList<PoliticCard> getCards() {
		return this.cards;
	}

	/**
	 * Returns the politic card situated in the given position.
	 * 
	 * @param index
	 *            The position of the selected politic card.
	 * @return the selected politic card
	 */
	public PoliticCard getCardByIndex(int index) {
		return this.cards.get(index);
	}

	/**
	 * @return the updated value that indicated the number of emporiums that the
	 *         player owns
	 */
	public int updateEmporium() {
		emporiums--;
		if (emporiums == 0) {
			addCoins(3);
		}
		return emporiums;
	}

	/**
	 * @return the number of emporiums still available to build for this player
	 */
	public int getEmporiums() {
		return emporiums;
	}

	/**
	 * @return the list of permits owned by the current player and not yet used
	 *         to build an emporium
	 */
	public ArrayList<PermitCard> getPermitsUp() {
		return this.permitsUp;
	}

	/**
	 * @return the list of permit cards owned by the player and already used to
	 *         build an emporium
	 */
	public ArrayList<PermitCard> getPermitsDown() {
		return this.permitsDown;
	}

	/**
	 * This method is used to return a specific permit card (owned by the player
	 * that the player didn't yet use to build an emporium) by giving an index.
	 * 
	 * @param index
	 *            The position of the permit card in the ArrayList.
	 * @return the permit card owned by the current player of a given index
	 *         (permitsUp)
	 */
	public PermitCard getPermitUpByIndex(int index) {
		return this.permitsUp.get(index);
	}

	/**
	 * This method is used to return a specific permit card (owned by the player
	 * already used to build an emporium) by giving an index.
	 * 
	 * @param index
	 *            The position of the permit card in the ArrayList.
	 * @return the permit card owned by the current player of a given index
	 *         (permitsDown)
	 */
	public PermitCard getPermitDownByIndex(int index) {
		return this.permitsDown.get(index);
	}

	/**
	 * This method moves a permit card used to build an emporium from the
	 * permitsUp deck to the permitsDown deck.
	 * 
	 * @param index
	 *            The position of the permit card to move.
	 */
	public void usePermit(int index) {
		this.permitsDown.add(this.permitsUp.remove(index));
		this.permitsUp.trimToSize();
	}

	/**
	 * This method updates the victory points of the current player.
	 * 
	 * @param quantity
	 *            The number of victory points to add
	 */
	public PoliticCard removeFirstOccurrence(Colors color) {
		PoliticCard card = new PoliticCard(Colors.NULL);
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getColor() == color) {
				card = cards.remove(i);
				return card;
			}
		}
		throw new IllegalArgumentException(
				"la carta che vuoi rimuovere non e' presente tra le carte in mano al giocatore");
	}

	/**
	 * Sets true the boolean related to the region the player has build an
	 * emporium in every city into.
	 * 
	 * @param region
	 *            The related region.
	 */
	public void setRegion(RegionTypes region) {
		regions[getRegionIndex(region)] = true;
	}

	/**
	 * This method returns the index of the region by giving its type.
	 * 
	 * @param region
	 *            The type of the region.
	 * @return index of the region
	 */
	public int getRegionIndex(RegionTypes region) {

		switch (region) {
		case COAST:
			return 0;
		case HILL:
			return 1;
		case MOUNTAIN:
			return 2;
		case NULL:
			throw new IllegalArgumentException("NULL get RegionIndex Error in player");
		case KING:
			throw new IllegalArgumentException("KING get RegionIndex Error in player");
		}
		throw new IllegalArgumentException("la regione non e' valida");
	}

	/**
	 * This method returns the boolean associated to the region by giving its
	 * type.
	 * 
	 * @param region
	 *            The type of the region.
	 * @return the boolean associated to the region
	 */
	public Boolean getRegion(RegionTypes region) {
		return regions[getRegionIndex(region)];
	}

	/**
	 * @return the final number of victory points of the player
	 */
	public int assignFinalBonuses() {
		points = points + finalBonuses;
		return points;
	}

	/**
	 * This method adds final bonuses.
	 * 
	 * @param quantity
	 *            The quantity to add
	 */
	public void addFinalBonuses(int quantity) {

		finalBonuses = finalBonuses + quantity;
	}

	/**
	 * This method return the index position of the selected color.
	 * 
	 * @param color
	 *            The selected color.
	 * @return the position index of the color
	 */
	public int getColorIndex(CityColor color) {

		switch (color) {
		case GOLD:
			return 0;
		case SILVER:
			return 1;
		case BRONZE:
			return 2;
		case IRON:
			return 3;
		case NULL:
			throw new IllegalArgumentException("NULL Error in getColorIndex ");
		}
		throw new IllegalArgumentException("il colore della citta' non e' valido");
	}

	public void setColor(CityColor color) {
		regions[getColorIndex(color)] = true;
	}

	public Boolean getCityColor(CityColor color) {
		return regions[getColorIndex(color)];
	}

	public int getFinalBonuses() {
		return this.finalBonuses;
	}
}