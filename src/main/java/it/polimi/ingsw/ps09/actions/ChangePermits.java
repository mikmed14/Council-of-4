package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * This (quick) action represents a player changing a business permit tile: the
 * player returns an assistant to the pool, then takes the two business permit
 * tiles face up in a region, returns them to the bottom of the corresponding
 * deck and draws two new ones from the top of the deck.
 */
public class ChangePermits extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The region in which there are the two business permit tiles to take.
	 */
	RegionTypes type;

	/**
	 * The ChangePermit constructor sets the current board game, round, turn and
	 * the selected region.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The round in which is performing the action.
	 * @param type
	 *            The selected region for taking the two business permit tiles.
	 */
	public ChangePermits(Model model, int turn, RegionTypes type) {
		super(model, Constants.CHANGEPERMITCARDS, turn, true, null);
		this.type = type;
	}

	@Override
	public boolean isValid() {
		if (model.getPlayer(turno).getAssistants() >= 1)
			return true;
		return false;
	}

	@Override
	public Model execute() {
		model.getRegion(type).changePermitCards();
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