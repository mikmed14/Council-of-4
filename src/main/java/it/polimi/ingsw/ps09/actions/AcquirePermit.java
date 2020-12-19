package it.polimi.ingsw.ps09.actions;

import java.util.ArrayList;
import java.util.List;


import it.polimi.ingsw.ps09.model.Bonus;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * This action represents a player acquiring a business permit tile: the player
 * choose and satisfies the council of a region by discarding between one and
 * four politics cards corresponding to the colors of the councillors present in
 * that council; then pays a sum of money, moving their marker on the coins
 * track (depending on the number of councillors satisfied); the player chooses
 * one of the two permit tiles face up in front of the council they have
 * satisfied, then takes it and places it face up in front of them; finally,
 * they obtain the bonuses indicated at the bottom of the tile and replace the
 * tile with the first tile of the corresponding deck.
 */
public class AcquirePermit extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The round in which the player is performing the action.
	 */
	private int turn;

	/**
	 * The index position of the business permit tile chosen by the player.
	 */
	private int permit;

	/**
	 * The region in which is situated the selected permit tile.
	 */
	private RegionTypes region;

	/**
	 * The cards used by the player to perform the action.
	 */
	private List<Colors> cards;

	/**
	 * This is the AcquirePermit constructor.
	 * 
	 * @param model
	 *            a copy of the model to modify.
	 * @param turn
	 *            the round of the player.
	 * @param region
	 *            the type of region where the balcony chosen by the player
	 *            stays.
	 * @param permit
	 *            the index of one of the two visible permit cards that the
	 *            player wants to acquire.
	 * @param colors
	 *            the ArrayList of colors which contains the colors of the cards
	 *            that the player wants to use.
	 */
	public AcquirePermit(Model model, int turn, RegionTypes region, int permit, List<Colors> colors) {
		super(model, 2, turn, true, colors);

		this.turn = turn;
		this.permit = permit;
		this.region = region;
		this.cards = new ArrayList<Colors>();
		this.cards = colors;
		
		
	}

	// checks if the colors insert by the client match the colors of the
	// councillors of the specified balcony
	@Override
	public boolean isValid() {

		// carte in mano al giocatore
		Integer[] manoPlayer;
		// carte scartate con i jolly
		Integer[] cardsCountersWithJolly;
		// carte scartate senza i jolly
		Integer[] cardsCountersWithoutJolly;
		// colore consiglieri balcone
		Integer[] councillorsCounters;


		// conto le carte scartate compresi i jolly
		manoPlayer = countHandColors(model.getPlayer(turn).getCards());
		cardsCountersWithJolly = countCardsColorsWithJolly(cards);
		cardsCountersWithoutJolly = countCardsColors(cards);
		councillorsCounters = countNumberOfCouncillorsColors(model.getRegion(region).getBalcony().getCouncillors());

		int contatore1 = 0;
		int contatore2 = 0;

		// for tra le carte in mano e le carte scartate quindi compresi di jolly
		for (int i = 0; i < 7; i++) {
			if (cardsCountersWithJolly[i] <= manoPlayer[i]) {
				contatore1++;
			}
		}

		for (int j = 0; j < 6; j++) {
			if (cardsCountersWithoutJolly[j] <= councillorsCounters[j]) {
				contatore2++;
			}
		}

		return (contatore1 == (contatore2 + 1))&& (model.getPlayer(turn).getCoins() >= computeNumberOfCoins());
	}

	@Override
	public Model execute() {

		for (int i = 0; i < cards.size(); i++) {
			model.getPlayer(turn).removeFirstOccurrence(cards.get(i));
		}

		applyBonus();

		model.getPlayer(this.turn).addPermitCard(model.getRegion(this.region).drawPermitCard(this.permit));

		return model;
	}

	@Override
	public void applyBonus() {

		PermitCard card;
		List<Bonus> bonuses;
		
		int coins = computeNumberOfCoins();

		model.getPlayer(this.turn).addCoins(-coins);

		if (permit == 1)
			card = model.getRegion(this.region).getPermitCard1();
		else if (permit == 2)
			card = model.getRegion(this.region).getPermitCard2();
		else
			throw new IllegalArgumentException();

		bonuses = card.getReward().getBonuses();

		for (int j = 0; j < bonuses.size(); j++) {
			switchBonus(bonuses.get(j));

		
		}
	}

	@Override
	public boolean nulla() {
		return valido;
	}



	





}