package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the player that is offering a politics card at a
 * certain price.
 */
public class MarketOfferPolitic extends MarketOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketOfferPolitic constructor.
	 * 
	 * @param offerer
	 *            The player making this offer.
	 * @param polOffered
	 *            The traded politics card.
	 * @param price
	 *            The price requested for buying.
	 */
	public MarketOfferPolitic(Player offerer, PoliticCard polOffered, int price) {

		super(offerer, polOffered, null, false, price, "politic", true);

	}

	

	@Override
	public Boolean nulla() {
		
		return valido;
	}
}