package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Model;

/**
 * This (quick) action represents a player engaging an assistant: the player
 * pays three coins moving their marker on the coins track back three spaces;
 * they take an assistant from the pool; if they are unable to move the marker
 * back, they CANNOT PERFORM AN ACTION.
 */
public class EngageAssistant extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The EngageAssistant constructor sets the current board game and current
	 * round.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The round of the player in which is performing the action.
	 */
	public EngageAssistant(Model model, int turn) {
		super(model, Constants.ACQUIREASSISTENTE, turn, true, null);
	}

	@Override
	public boolean isValid() {
		if (model.getPlayer(turno).getCoins() >= 3)
			return true;
		return false;
	}

	@Override
	public Model execute() {
		model.getPlayer(turno).addCoins(-3);
		model.drawAssistants(1);
		model.getPlayer(turno).addAssistants(1);
		return model;
	}

	@Override
	public void applyBonus() {
		// in this case no bonus is applied
		return;
	}

	@Override
	public boolean nulla() {
		return valido;
	}
}