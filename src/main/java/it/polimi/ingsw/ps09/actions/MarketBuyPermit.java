package it.polimi.ingsw.ps09.actions;

import java.util.List;

import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the act of buying a business permit tile during the
 * market phase.
 */
public class MarketBuyPermit extends MarketBuy {
	private MarketOffer rightOffer;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The MarketBuyPermit constructor.
	 * 
	 * @param buyer
	 *            The player that is doing the buy action.
	 * @param perBought
	 *            The business permit tile being bought.
	 */
	public MarketBuyPermit(Player buyer, PermitCard perBought, int index) {
		super(buyer, null, perBought, false, index, true);
	}

	@Override
	public Boolean isValidBuy(List<MarketOffer> offers, Player buyer) {

		if (!this.getBuyer().equals(buyer)) {
			return false;
		}

		
		for (MarketOffer offer : offers) {
			
			if ((offer.getPerOffered()!=null)&&(this.getPerBought()!=null)){
			
			
			if (offer.getPerOffered().equals(this.getPerBought())) {
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
