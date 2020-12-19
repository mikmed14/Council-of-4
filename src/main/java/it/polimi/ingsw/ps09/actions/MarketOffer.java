package it.polimi.ingsw.ps09.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This is the representation of a market offer, representing the player that is
 * offering a piece of the game (between politics cards, business permit tile
 * and assistants) at a certain price during the market phase (at the end of
 * each round).
 */
public abstract class MarketOffer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The player making this offer.
	 */
	private Player offerer;

	/**
	 * The traded politics card.
	 */
	private PoliticCard polOffered;

	/**
	 * The traded business permit tile.
	 */
	private PermitCard perOffered;

	/**
	 * The boolean attribute that indicates whether the offer action concerns he
	 * assistant.
	 */
	private Boolean assOffered;

	/**
	 * The price requested for buying (for one thing).
	 */
	private final int price;

	/**
	 * This string variable is used to access the type of offer in a quick way.
	 */
	private String type;

	/**
	 * This boolean variable is used to know is the offer is valid.
	 */
	public boolean valido;

	/**
	 * The MarketOffer constructor.
	 * 
	 * @param offerer
	 *            The player that is making this offer.
	 * @param polOffered
	 *            The traded politics card.
	 * @param perOffered
	 *            The traded business permit tile.
	 * @param assOffered
	 *            If the player is offering an assistant.
	 * @param price
	 *            The price that the player is requesting for buying action.
	 * @param type
	 *            The type of offer.
	 * @param valido
	 *            To know the validity of the action.
	 */
	public MarketOffer(Player offerer, PoliticCard polOffered, PermitCard perOffered, Boolean assOffered, int price,
			String type, boolean valido) {

		this.offerer = offerer;
		this.polOffered = new PoliticCard(Colors.NULL);
		this.polOffered = polOffered;
		this.perOffered = perOffered;
		this.assOffered = assOffered;
		this.price = price;

		this.setType(type);

		this.valido = valido;
	}

	/**
	 * @return the offerer
	 */
	public Player getOfferer() {
		return offerer;
	}

	/**
	 * @return the traded politics card
	 */
	public PoliticCard getPolOffered() {
		return polOffered;
	}

	/**
	 * @return the traded business permit tile
	 */
	public PermitCard getPerOffered() {
		return perOffered;
	}

	/**
	 * @return if the player is offering an assistant
	 */
	public Boolean getAssOffered() {
		return assOffered;
	}

	/**
	 * @return the price requested for buying
	 */
	public int getPrice() {
		return price;
	}


	/**
	 * This (abstract) method is used to declare to state whether the action is
	 * useless or not.
	 */
	public abstract Boolean nulla();

	/**
	 * @return the type of the offer
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the offer.
	 * 
	 * @param type
	 *            The type to be set.
	 */
	public void setType(String type) {
		this.type = type;
	}
}