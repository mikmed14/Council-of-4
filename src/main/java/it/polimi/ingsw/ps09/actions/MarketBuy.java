package it.polimi.ingsw.ps09.actions;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.user.Player;

/**
 * This class represents the act of buying a piece of the game (between politics
 * cards, business permit tile and assistants) during the market phase (at the
 * end of each round).
 */
public abstract class MarketBuy implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The player that is doing the buy action.
	 */
	private Player buyer;

	/**
	 * The politics card being bought.
	 */
	private PoliticCard polBought;

	/**
	 * The business permit tile being bought.
	 */
	private PermitCard perBought;

	/**
	 * The boolean attribute that indicates whether the buy action concerns he
	 * assistant.
	 */
	private Boolean assBought;

	private int index;

	/**
	 * The boolean condition about the validity of the action.
	 */
	public boolean valid;

	/**
	 * The MarketBuy constructor.
	 * 
	 * @param buyer
	 *            The player that is making this buying action.
	 * @param polBought
	 *            The politics card being bought.
	 * @param perBought
	 *            The business permit tile being bought.
	 * @param assBought
	 *            The condition about the purchase of the assistant.
	 * @param index
	 * @param valid
	 *            The boolean condition about the validity of the action.
	 */
	public MarketBuy(Player buyer, PoliticCard polBought, PermitCard perBought, Boolean assBought, int index,
			boolean valid) {
		this.buyer = buyer;
		this.polBought = new PoliticCard(Colors.NULL);
		this.polBought = polBought;
		this.perBought = perBought;
		this.assBought = assBought;
		this.valid = valid;
		this.index = index;
	}

	/**
	 * This (abstract) method is used to declare to state whether the buy action
	 * is useless or not.
	 */
	public abstract boolean nulla();

	public int getIndex() {
		return this.index;
	}

	/**
	 * @return the buyer
	 */
	public Player getBuyer() {
		return buyer;
	}

	/**
	 * @return the politics card bought
	 */
	public PoliticCard getPolBought() {
		return polBought;
	}

	/**
	 * @return the business permit tile bought
	 */
	public PermitCard getPerBought() {
		return perBought;
	}

	/**
	 * @return if the player is buying an assistant
	 */
	public Boolean getAssBought() {
		return assBought;
	}

	/**
	 * Checks if this buy is valid in the context of the given list of market
	 * offers. A market buy is valid if in the given list of market offers we
	 * have an offer for the same piece of the game (between politics cards,
	 * business permit tile and assistants) and if the buy is done by the
	 * current player. The buyer must have the coins to perform the buy.
	 * 
	 * @param offers
	 *            The list of market offers.
	 * @param buyer
	 *            The player who makes a purchase.
	 * 
	 * @return if the buy action is valid or not.
	 */
	public abstract Boolean isValidBuy(List<MarketOffer> offers, Player buyer);

	/**
	 * This (abstract) method is used to execute a market buy, trade cards and
	 * money and then delete the offer.
	 * 
	 * @throws java.lang.Exception
	 */
	public abstract Model execute(List<MarketOffer> offers, Model model) throws NullPointerException;

	public Model pay(Model model, MarketOffer rightOffer) {
		Player seller = model.getPlayer(rightOffer.getOfferer().getTurn());

		int seller1 = seller.getTurn();
		int buyer1 = buyer.getTurn();

		model.getPlayer(seller1).addCoins(rightOffer.getPrice());
		model.getPlayer(buyer1).addCoins(-rightOffer.getPrice());

		if (rightOffer.getAssOffered()) {
			model.getPlayer(seller1).addAssistants(-1);
			model.getPlayer(buyer1).addAssistants(1);

		}

		else if (rightOffer.getPerOffered() != null) {

			for (int i = 0; i < model.getPlayer(seller1).getPermitsUp().size(); i++) {

				if (model.getPlayer(seller1).getPermitUpByIndex(i).getCities().equals(this.getPerBought().getCities())
						&& (model.getPlayer(seller1).getPermitUpByIndex(i).getReward().getBonuses().size() == this
								.getPerBought().getReward().getBonuses().size())) {
					model.getPlayer(seller1).getPermitsUp().remove(i);
					break;
				}
			}

			model.getPlayer(buyer1).addPermitCard(this.getPerBought());

		} else if (rightOffer.getPolOffered() != null) {

			int indexpol = 0;

			for (int i = 0; i < model.getPlayer(seller1).getCards().size(); i++) {

				if (model.getPlayer(seller1).getCardByIndex(i).getColor().equals(this.getPolBought().getColor())) {
					model.getPlayer(seller1).getCards().remove(i);

					break;
				}
			}
			model.getPlayer(seller1).getCards().remove(indexpol);
			model.getPlayer(buyer1).addPoliticCard(this.getPolBought());
		} else
			throw new NullPointerException("offerta non valida!!");
		return model;
	}
}