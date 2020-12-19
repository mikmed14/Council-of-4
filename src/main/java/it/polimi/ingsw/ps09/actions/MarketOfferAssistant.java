package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the player that is offering an assistant at a certain
 * price.
 */
public class MarketOfferAssistant extends MarketOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketOfferAssistant constructor.
	 * 
	 * @param offerer
	 *            The player making this offer.
	 * @param price
	 *            The price requested for buying.
	 */
	public MarketOfferAssistant(Player offerer, int price) {

		super(offerer, null, null, true, price, "assistant", true);

		

	}


	@Override
	public Boolean nulla() {
		
		return valido;
	}
}