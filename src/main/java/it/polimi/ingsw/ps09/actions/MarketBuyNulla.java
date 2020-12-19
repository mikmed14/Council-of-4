package it.polimi.ingsw.ps09.actions;

import java.util.List;

import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class inherits the MarketBuy class and it is used in case there is no
 * buy action.
 */
public class MarketBuyNulla extends MarketBuy {

	private static final long serialVersionUID = 1L;

	/**
	 * The MarketBuyNulla constructor.
	 * 
	 * @param buyer
	 *            The buyer during the market phase.
	 */
	public MarketBuyNulla(Player buyer) {
		super(buyer, null, null, false, 0, false);
	}

	@Override
	public Boolean isValidBuy(List<MarketOffer> offers, Player buyer) {
		return false;
	}

	@Override
	public Model execute(List<MarketOffer> offers, Model model) {
		return null;
	}

	@Override
	public boolean nulla() {
		return valid;
	}
}