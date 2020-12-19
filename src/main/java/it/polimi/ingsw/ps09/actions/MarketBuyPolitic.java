package it.polimi.ingsw.ps09.actions;

import java.util.List;

import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the act of buying a politics card during the market
 * phase.
 */
public class MarketBuyPolitic extends MarketBuy {

	
	MarketOffer rightOffer;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketBuyPolitic constructor.
	 * 
	 * @param buyer
	 *            The player that is doing the buy action.
	 * @param polBought
	 *            The politics card being bought.
	 */
	public MarketBuyPolitic(Player buyer, PoliticCard polBought , int index) {
		
		
		
		
		super(buyer, polBought, null, false, index, true);
		
	}

	@Override
	public Boolean isValidBuy(List<MarketOffer> offers, Player buyer) {

		if (!this.getBuyer().equals(buyer)) {
			return false;
		}

	 
		
		
		for (MarketOffer offer : offers) {
			if ((offer.getPolOffered()!=null)&&(this.getPolBought()!=null)) {
				
				if(offer.getPolOffered().equals(this.getPolBought())){
				
				rightOffer = offer;
				break;
				}
				
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
