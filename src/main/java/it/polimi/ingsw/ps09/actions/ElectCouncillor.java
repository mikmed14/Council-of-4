package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Councillor;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * This action represents a player electing a councillor: the player chooses one
 * of their available councillors at the side of the board, then inserts them in
 * the council balcony of their choice in the side of the arrow (pushing the
 * councillors present in that council until the last one falls out that will be
 * placed at the side of the board); the player receives four coins marking this
 * by moving their marker on the coins track.
 */
public class ElectCouncillor extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The color of the selected councillor.
	 */
	private Colors color1;

	/**
	 * The region where there is the selected council balcony.
	 */
	private RegionTypes region;

	/**
	 * The ElectCouncillor constructor sets the current board game, current
	 * turn, the color of the councillor and the balcony
	 * 
	 * @param model
	 *            The current board game
	 * @param color
	 *            The color of the selected councillor
	 * @param region
	 *            The region where there is the council balcony
	 * @param turno
	 *            The turn in which is performing the action
	 */
	public ElectCouncillor(Model model, Colors color, RegionTypes region, int turno) {
		super(model, 1, turno, true, null);
		this.color1 = color;
		this.region = region;
	}

	@Override
	public boolean isValid() {
		return model.getCouncillorDeck().checkCouncillorIsInPool(color1);
	}

	@Override
	public Model execute() {
		Councillor consigliere = model.getCouncillorDeck().drawFromPoolCouncillorColor(color1);
		if (region.equals(RegionTypes.KING)) {
			Councillor consigliereScartato = model.getKingBalcony().setCouncillor(consigliere);
			model.getCouncillorDeck().addCouncillor(consigliereScartato);
		} else {
			Councillor consigliereScartato = model.getRegion(region).getBalcony().setCouncillor(consigliere);
			model.getCouncillorDeck().addCouncillor(consigliereScartato);
		}
		model.getCouncillorDeck().updateCount();
		applyBonus();
		return this.model;
	}

	@Override
	public void applyBonus() {
		model.getPlayer(turno).addCoins(5);
	}

	@Override
	public boolean nulla() {
		return valido;
	}


}