package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Councillor;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * This (quick) action represents a player sending an assistant to elect a
 * councillor: the player returns an assistant to the pool, then takes a
 * councillor and inserts in a selected balcony, exactly as with the main action
 * 'elect a councillor', but they DO NOT earn any coins by performing this
 * action.
 */
public class ElectCouncillorWithAssistant extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The region in which there is the selected balcony. The balcony will be
	 * used to insert the new councillor.
	 */
	RegionTypes type;

	/**
	 * The color of the new councillor selected by the player.
	 */
	Colors color1;

	/**
	 * The ElectCouncillorWithAssistant constructor sets the current board game,
	 * round, turn, selected balcony and councillor.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The round in which is performing the action.
	 * @param type
	 *            The region of the selected balcony.
	 * @param color
	 *            The color of the selected councillor.
	 */
	public ElectCouncillorWithAssistant(Model model, int turn, RegionTypes type, Colors color) {
		super(model, Constants.ASSISTELECTCOUNCILLOR, turn, true, null);
		this.type = type;
		this.color1 = color;
	}

	@Override
	public boolean isValid() {
		if ((model.getPlayer(turno).getAssistants() > 0) && model.getCouncillorDeck().checkCouncillorIsInPool(color1))
			return true;
		return false;
	}

	@Override
	public Model execute() {

		Councillor consigliere = model.getCouncillorDeck().drawFromPoolCouncillorColor(color1);

		if (type.equals(RegionTypes.KING)) {
			Councillor consigliereScartato = model.getKingBalcony().setCouncillor(consigliere);
			model.getCouncillorDeck().addCouncillor(consigliereScartato);
		} else {
			Councillor consigliereScartato = model.getRegion(type).getBalcony().setCouncillor(consigliere);
			model.getCouncillorDeck().addCouncillor(consigliereScartato);
		}
		model.getCouncillorDeck().updateCount();
		applyBonus();

		return model;
	}

	@Override
	public void applyBonus() {
		model.getPlayer(turno).addAssistants(-1);
		model.drawAssistants(-1);
	}

	@Override
	public boolean nulla() {
		return valido;
	}
}