package it.polimi.ingsw.ps09.actions;

import java.util.List;

import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the act of buying an assistant during the market phase.
 */
public class MarketBuyAssistant extends MarketBuy {
private MarketOffer rightOffer;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketBuyAssistant constructor.
	 * 
	 * @param buyer The player that is doing the buy action.
	 */
	public MarketBuyAssistant(Player buyer, int index) {
		super(buyer, null, null, true, index, true);
	}

	@Override
	public Boolean isValidBuy(List<MarketOffer> offers, Player buyer) {

		if (!this.getBuyer().equals(buyer)) {
			return false;
		}

		
		for (MarketOffer offer : offers) {
			if (offer.getAssOffered().equals(this.getAssBought())&& !(offer.getOfferer().equals(this.getBuyer()))) {
				rightOffer = offer;
				break;
			}
		}

		if (rightOffer == null) {
			return false;
		}

		return buyer.getCoins() >= rightOffer.getPrice();
	}

	@Override
	public Model execute(List<MarketOffer> offers, Model model) {

	

		
		if (rightOffer==null) throw new  NullPointerException("offerta nulla!");

		return pay(model, rightOffer);


		
		
		
	}

	@Override
	public boolean nulla() {
		return valid;
	}
	
	

	
	
	
}