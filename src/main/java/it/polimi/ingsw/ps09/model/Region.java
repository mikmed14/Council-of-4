package it.polimi.ingsw.ps09.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import it.polimi.ingsw.ps09.map.Constants;

/**
 * The basic class of a region of the board game.
 */
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Type of the region.
	 */
	private RegionTypes type;

	/**
	 * Array of cities belonging to this region.
	 */
	private City[] cities;

	/**
	 * Balcony belonging to this region.
	 */
	private Balcony balcony;

	/**
	 * Reward belonging to this region.
	 */
	private Reward reward;

	/**
	 * Permit cards: 'permits' is the deck placed in this region, and the 2
	 * cards; 'permit1' and 'permit2' are the ones visible to players and
	 * available to draw.
	 */
	private ArrayList<PermitCard> permits;
	private PermitCard permit1;
	private PermitCard permit2;

	/**
	 * The Region constructor creates and initializes all the elements of the
	 * region: the reward of the region, the permit cards, the type of the
	 * region and the cities.
	 * 
	 * @param type
	 *            The type of the region.
	 * @param numberOfPlayers
	 *            The number of players of the current game.
	 * @param reward
	 *            The reward of the region.
	 * @param cityRewards
	 *            The rewards of the cities belonging to this region.
	 * @param colors
	 *            The colors of the cities belonging to this region.
	 * @param links
	 *            The links among the cities belonging to this region.
	 * @param permits
	 *            The permit cards of this region
	 * @param names
	 *            The names of the cities of the region.
	 */
	public Region(RegionTypes type, int numberOfPlayers, Reward reward, Reward[] cityRewards, CityColor[] colors,
			String[] links, ArrayList<PermitCard> permits, String[] names) {
		this.type = type;
		this.reward = reward;
		cities = new City[5];
		this.balcony = new Balcony();
		this.permits = new ArrayList<PermitCard>();
		this.permits = permits;
		
		

		
		shuffle();
		
		int size = permits.size();
		
		
		
		
		
		
		permit1 = new PermitCard(this.permits.get(size - 1).getReward(),
				this.permits.get(size - 1).getCities());
		
		this.permits.remove(size - 1);
		
		permit2 = new PermitCard(this.permits.get(size - 2).getReward(),
				this.permits.get(size - 2).getCities());
		
		this.permits.remove(size - 2);
		
		

		initializeCities(numberOfPlayers, links, cityRewards, colors, names);
	}

	/**
	 * Sets the type of the region.
	 * 
	 * @param type
	 *            The type of the region.
	 */
	public void setRegionType(RegionTypes type) {
		this.type = type;
	}

	/**
	 * @return the type of the region
	 */
	public RegionTypes getRegionType() {
		return type;
	}

	/**
	 * Sets the cities in the region.
	 * 
	 * @param cities
	 *            The cities of the region.
	 */
	public void setCities(City[] cities) {
		this.cities = cities;
	}

	/**
	 * @return the cities placed in the region
	 */
	public City[] getCities() {
		return cities;
	}

	/**
	 * This method is used to return the city selected by a given index.
	 * 
	 * @param c
	 *            The index that indicates the first letter of the city's name.
	 * @return the selected city
	 */
	public City getCityById(char c) {
		if (checkCityById(c)) {
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++) {
				if (cities[i].getId() == c)
					return cities[i];
			}
		} else
			throw new IllegalArgumentException("la citta richiesta non e' presente in questa regione");

		return null;
	}

	/**
	 * Checks if there is selected city.
	 * 
	 * @param c
	 *            The index that indicates the first letter of the city's name.
	 * @return the boolean condition presence
	 */
	public boolean checkCityById(char c) {
		for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++) {
			if (cities[i].getId() == c)
				return true;
		}
		return false;
	}

	/**
	 * Sets the balcony of the region.
	 * 
	 * @param balcony
	 *            The balcony of the region.
	 */
	public void setBalcony(Balcony balcony) {
		this.balcony = balcony;
	}

	/**
	 * @return the balcony of the region
	 */
	public Balcony getBalcony() {
		return balcony;
	}

	/**
	 * Sets the reward of the region.
	 * 
	 * @param reward
	 *            The reward of the region.
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	/**
	 * @return the reward of the region
	 */
	public Reward getReward() {
		return reward;
	}

	/**
	 * This method returns the permit card present in the given index.
	 * 
	 * @param index
	 *            The position of the permit card.
	 * 
	 * @return the permit card interested
	 */
	public PermitCard getPermitCard(int index) {
		return this.permits.get(index);
	}

	/**
	 * This method removes and returns the first card of the deck.
	 * 
	 * @return the first card of the deck
	 */
	public PermitCard drawLastCard() {
		return permits.remove(permits.size() - 1);
	}

	/**
	 * This method is used to insert a card at the bottom.
	 * 
	 * @param card
	 *            The card to be inserted.
	 */
	public void insertCardAtTheBottom(PermitCard card) {
		for (int i = permits.size() - 1; i >= 0; i--) {
			setPermitCard(getPermitCard(i), i + 1);
			setPermitCard(card, 0);
		}
	}

	/**
	 * Sets a business permit tile in the given position.
	 * 
	 * @param card
	 *            The card to be set.
	 * @param index
	 *            The position of the card to be set.
	 */
	public void setPermitCard(PermitCard card, int index) {
		this.permits.set(index, card);
	}

	/**
	 * The player is only able to draw one of the 2 permit cards which are
	 * visible on the map. This method draws one of those cards, given the index
	 * of the card the player chooses. It also replaces the drawn card whit
	 * another one taken from the pool.
	 * 
	 * @param index
	 *            The position of the card (1 or 2).
	 * 
	 * @return the card drawn
	 */
	public PermitCard drawPermitCard(int index) {

		PermitCard temp;
		if (index == 1) {
			temp = permit1;
			this.permit1 = permits.remove(permits.size() - 1);

		} else if (index == 2) {
			temp = permit2;
			this.permit2 = permits.remove(permits.size() - 1);

		} else
			throw new IllegalArgumentException();
		return temp;
	}

	/**
	 * This method changes the two visible permit cards with other two drawn
	 * from the deck. The previous cards are placed at the beginning of the
	 * deck.
	 */
	public void changePermitCards() {

		PermitCard card1 = this.permit1;
		PermitCard card2 = this.permit2;
		this.permit1 = drawLastCard();
		this.permit2 = drawLastCard();

		permits.add(card1);
		permits.add(card2);
		shuffle();

	}

	/**
	 * @return the first business permit tile
	 */
	public PermitCard getPermitCard1() {
		return permit1;
	}

	/**
	 * Sets the first business permit tile for each region.
	 * 
	 * @param card
	 *            The permit tile to be set.
	 */
	public void setPermitCard1(PermitCard card) {
		this.permit1 = card;
	}

	/**
	 * @return the second business permit tile
	 */
	public PermitCard getPermitCard2() {
		return permit2;
	}

	/**
	 * Sets the second business permit tile for each region.
	 * 
	 * @param card
	 *            The permit tile to be set.
	 */
	public void setPermitCard2(PermitCard card) {
		this.permit2 = card;
	}

	/**
	 * This method initializes the cities belonging to this region, assigning
	 * them names, colors, rewards, links and the king.
	 * 
	 * @param numberOfPlayers
	 *            The number of players of the current game.
	 * @param links
	 *            The links among the cities belonging to this region.
	 * @param reward
	 *            The rewards of cities.
	 * @param colors
	 *            The colors of cities.
	 * @param names
	 *            The names of cities.
	 */
	public void initializeCities(int numberOfPlayers, String[] links, Reward[] reward, CityColor[] colors,
			String[] names) {

		

		for (int i = 0; i < 5; i++) {
			this.cities[i] = new City(links[i], colors[i], reward[i], numberOfPlayers, names[i]);
		}
	}

	/**
	 * This method is used to shuffle the permit cards.
	 */
	public void shuffle() {

		long seed = System.nanoTime();
		Collections.shuffle(this.permits, new Random(seed));
	}

	/**
	 * @return the ArrayList with permit cards of the region
	 */
	public ArrayList<PermitCard> getPermits() {
		return this.permits;
	}

	/**
	 * This method is used to access to the city of the region.
	 * 
	 * @param index
	 *            The position of the city in the region (0<=index<5).
	 * 
	 * @return the city of given index
	 */
	public City getCityByIndex(int index) {
		if ((index < 0) || (index > 5))
			throw new IllegalArgumentException();
		return cities[index];
	}
}