package it.polimi.ingsw.ps09.actions;

import it.polimi.ingsw.ps09.model.Model;

/**
 * This class inherits the Action class and it is used in case there is no
 * action.
 */
public class MossaNulla extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The MossaNulla constructor.
	 * 
	 *  @param model
	 *            The current board game in which is performing the action.
	 * @param mossa
	 *            The move of the player.
	 * @param turn
	 *            The current round in which the player is performing the
	 *            action.
	 */
	public MossaNulla(Model model, int mossa, int turn) {
		super(model, mossa, turn, false, null);
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public Model execute() {
		return null;
	}

	@Override
	public void applyBonus() {
		return;
	}

	@Override
	public boolean nulla() {
		return valido;
	}
}