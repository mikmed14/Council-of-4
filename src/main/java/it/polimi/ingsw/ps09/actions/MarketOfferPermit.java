package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the player that is offering a business permit tile at a
 * certain price.
 */
public class MarketOfferPermit extends MarketOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketOfferPermit constructor.
	 * 
	 * @param offerer
	 *            The player making this offer.
	 * @param perOffered
	 *            The traded business permit tile.
	 * @param price
	 *            The price requested for buying.
	 */
	public MarketOfferPermit(Player offerer, PermitCard perOffered, int price) {

		super(offerer, null, perOffered, false, price, "permit", true);

	}

	

	@Override
	public Boolean nulla() {
		
		return valido;
	}

}