package it.polimi.ingsw.ps09.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A reward class is an ArrayList of bonuses. This solution has been adopted to
 * let every item of the game (cities, regions, permitCards, nobilityTrack)
 * which contains bonuses, contain a single reward, which contains several
 * bonuses itself.
 */
public class Reward implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The ArrayList of bonuses
	 */
	private ArrayList<Bonus> rewardToken;

	/**
	 * The Reward constructor creates the ArrayList
	 */
	public Reward() {
		this.rewardToken = new ArrayList<Bonus>();
	}

	/**
	 * This method adds a bonus to the reward.
	 * 
	 * @param type
	 *            The type of the bonus to add.
	 * @param quantity
	 *            The quantity of the bonus to add.
	 */
	public void addBonus(BonusType type, int quantity) {
		Bonus bonus = new Bonus(quantity, type);
		rewardToken.add(bonus);
	}

	/**
	 * This method returns the bonus contained in the reward of a given index.
	 * 
	 * @param i
	 *            An index of the bonus.
	 *            
	 * @return the bonus of a given index
	 */
	public Bonus getBonusByIndex(int i) {
		return rewardToken.get(i);
	}

	/**
	 * @return the ArrayList of bonuses
	 */
	public ArrayList<Bonus> getBonuses() {
		return this.rewardToken;
	}
}