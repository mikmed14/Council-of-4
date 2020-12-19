package it.polimi.ingsw.ps09.model;

import java.io.Serializable;

/**
 * The basic business permit tile class.
 */
public class PermitCard implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The business permit tile contains a reward (as a list of bonuses related
	 * to the single card) and a string which has as single characters the
	 * initial letter of the city you can build an emporium into with the card.
	 */
	private Reward reward;

	private String cities;

	/**
	 * The PermitCard constructor initializes the cities and the reward.
	 * 
	 * @param reward
	 *            The reward token given by obtaining the permit card.
	 * @param cities
	 *            The string with the initial letters of the cities you can
	 *            build an emporium into with the card.
	 */
	public PermitCard(Reward reward, String cities) {
		this.reward = new Reward();
		for (int i = 0; i < reward.getBonuses().size(); i++) {
			this.reward.addBonus(reward.getBonusByIndex(i).getType(), reward.getBonusByIndex(i).getQuantity());
		}
		this.cities = cities;
	}

	/**
	 * Sets the reward.
	 * 
	 * @param reward
	 *            The reward to be set.
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	/**
	 * @return the reward
	 */
	public Reward getReward() {
		return this.reward;
	}

	/**
	 * Sets the cities.
	 * 
	 * @param cities
	 *            The new cities to be set.
	 */
	public void setCities(String cities) {
		this.cities = cities;
	}

	/**
	 * @return the cities
	 */
	public String getCities() {
		return this.cities;
	}
}