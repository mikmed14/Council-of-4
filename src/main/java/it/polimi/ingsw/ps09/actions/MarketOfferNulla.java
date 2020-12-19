package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.user.Player;

/**
 * This class inherits the MarketOffer class and it is used in case there is no
 * offer action.
 */
public class MarketOfferNulla extends MarketOffer {

	private static final long serialVersionUID = 1L;

	/**
	 * The MarketOfferNulla constructor.
	 * 
	 * @param offerer
	 *            The offerer during the market phase.
	 */
	public MarketOfferNulla(Player offerer) {
		super(offerer, null, null, false, 0, "mossa nulla", false);
	}

	@Override
	public Boolean nulla() {
		return valido;
	}
}