package it.polimi.ingsw.ps09.model;


import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Observable;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.JDOMException;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.user.Player;

/**
 * The model class.
 */
public class Model extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;



	
	private static final Logger LOGGER= Logger.getLogger(Model.class.getName());


	/**
	 * The deck of politics cards.
	 */
	private PoliticCardDeck politicCardDeck;

	/**
	 * The players of the current game.
	 */
	private ArrayList<Player> players;

	/**
	 * The array of regions.
	 */
	private Region[] regions;

	/**
	 * The number of player of the current game.
	 */
	private int numberOfPlayers;

	/**
	 * The pool of councillors.
	 */
	private CouncillorDeck councillorDeck;

	/**
	 * The nobility track (with bonuses inside).
	 */
	private Reward[] nobilityTrack = new Reward[21];

	/**
	 * The king balcony (the other 3 balconies are saved inside each region).
	 */
	private Balcony kingBalcony;

	/**
	 * The number of assistants available in the pool.
	 */
	private int assistants;

	/**
	 * The rewards given building an emporium in all the cities of the same
	 * color.
	 */
	private Reward[] colorRewards;

	/**
	 * The rewards given by the king premium cards.
	 */
	private ArrayList<Reward> kingRewards;

	/**
	 * The city id where the king is at the moment.
	 */
	private char king;

	/**
	 * An attribute used for the additional action.
	 */
	private boolean addAction = false;

	private int bonusStrano = -1;

	/**
	 * City chosen for the first bonus.
	 */
	private String cittaBonus1 = null;

	/**
	 * City chosen for the second bonus.
	 */
	private String cittaBonus2 = null;

	private RegionTypes regioneBonus = null;

	/**
	 * Card number from which the player would receive the bonus.
	 */
	private int numberTesseraBonus = -1;

	private int numberTesseraRegione = -1;

	/**
	 * The business permit tile situated in the first region.
	 */
	private ArrayList<PermitCard> permitsRegion1;

	/**
	 * The business permit tile situated in the second region.
	 */
	private ArrayList<PermitCard> permitsRegion2;

	/**
	 * The business permit tile situated in the third region.
	 */
	private ArrayList<PermitCard> permitsRegion3;

	/**
	 * The Model constructor initializes a new model: creates the regions array,
	 * the players ArrayList, the councillor deck and the politic cards deck
	 * (both of them are initialized by the related constructor), sets the
	 * number of players, invokes the readFromFiles method which initializes all
	 * the balconies with random councillors taken from the deck, invokes the
	 * readFromFiles method which reads the configuration files and initializes
	 * the configurable part of the map.
	 * 
	 * @param numberOfPlayers
	 *            The number of players of the game.
	 * 
	 * @throws java.io.IOException
	 * @throws org.jdom2.JDOMException
	 */

	public Model(int numberOfPlayers, int numberOfMap) throws IOException, JDOMException {
		

		regions = new Region[Constants.NUMOFREGIONS];
		colorRewards = new Reward[Constants.NUMOFREGIONS + 1];
		kingRewards = new ArrayList<Reward>();

		
	
		LOGGER.log(Level.INFO,"mappa "+numberOfMap);
		




		this.assistants = numberOfPlayers * 15;
		this.numberOfPlayers = numberOfPlayers;
		politicCardDeck = new PoliticCardDeck(numberOfPlayers);
		councillorDeck = new CouncillorDeck();
		players = new ArrayList<Player>();

		initializeMap(numberOfMap);
		initializeBalconies();

		initializePlayers();
	}

	/**
	 * This method reads from the 3 configuration files all the data which
	 * belongs to the configurable side of the game and initializes the region
	 * and cities rewards, the links among the cities, the color of the cities,
	 * the nobility track and the permit card decks (3 decks, one for each
	 * region)
	 * 
	 * The reading from the files is done creating an XmlReader-type object and
	 * invoking the methods readFilex (0<x<4). Those methods read the data and
	 * save them in local attributes of the XmlReader class, which are accessed
	 * by the above method with their getters.
	 * 
	 * As concerns the colors, rewards and links of the cities, they are saved
	 * in single arrays, which then need to be divided into 3 different arrays
	 * and sent to the region. This is done by the methods
	 * assignRewardsToCities, assignLinksToCities, AssignColorsToCities.
	 * 
	 * The same is true for the 3 permitCardDecks and Region Rewards to by
	 * divided among the regions.
	 * 
	 * It's private because is used only by the Model constructor to initialize
	 * the map.
	 * 
	 * @throws java.io.IOException
	 * @throws org.jdom2.JDOMException
	 */
	private void initializeMap(int n) throws JDOMException, IOException {

		XmlReader reader = new XmlReader();
		reader.readFile1(n);
		reader.readFile2(n);
		reader.readFile3(n);

		initializeKingRewards();
		initializeColorRewards();

		String[] cityNames = reader.getNames();
		CityColor[] colors = reader.getCityColors(); // getters
		Reward[] cityRewards = reader.getCityRewards();
		String[] cityLinks = reader.getLinks();
		ArrayList<PermitCard> permits = reader.getPermitCards();

		Reward[] regionRewards = reader.getRegionRewards();
		nobilityTrack = reader.getNobilityTrack();

		assignPermitsToRegions(RegionTypes.COAST, permits);
		assignPermitsToRegions(RegionTypes.HILL, permits);
		assignPermitsToRegions(RegionTypes.MOUNTAIN, permits);

		// initialize the regions
		this.regions[0] = new Region(RegionTypes.COAST, this.numberOfPlayers, regionRewards[0],
				assignRewardsToCities(RegionTypes.COAST, cityRewards), assignColorsToCities(RegionTypes.COAST, colors),
				assignLinksToCities(RegionTypes.COAST, cityLinks), permitsRegion1,
				assignNamesToCities(RegionTypes.COAST, cityNames));

		this.regions[1] = new Region(RegionTypes.HILL, this.numberOfPlayers, regionRewards[1],
				assignRewardsToCities(RegionTypes.HILL, cityRewards), assignColorsToCities(RegionTypes.HILL, colors),

				assignLinksToCities(RegionTypes.HILL, cityLinks), permitsRegion2,
				assignNamesToCities(RegionTypes.HILL, cityNames));

		this.regions[2] = new Region(RegionTypes.MOUNTAIN, this.numberOfPlayers, regionRewards[2],
				assignRewardsToCities(RegionTypes.MOUNTAIN, cityRewards),
				assignColorsToCities(RegionTypes.MOUNTAIN, colors),
				assignLinksToCities(RegionTypes.MOUNTAIN, cityLinks), permitsRegion3,
				assignNamesToCities(RegionTypes.MOUNTAIN, cityNames));

		// initialize the king
		this.king = reader.getKing();
	}

	/**
	 * This (private) method initializes all the balconies (three located in the
	 * regions and one located in the king board) with Councillors drawn from
	 * the councillor pool.
	 * 
	 * @throws java.io.IOException
	 */
	private void initializeBalconies() throws IOException {

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {
			for (int j = 0; j < 4; j++) {
				regions[i].getBalcony().addCouncillor(councillorDeck.drawFromPool(), j);
			}
		}
		this.kingBalcony = new Balcony();
		for (int j = 0; j < 4; j++) {
			kingBalcony.addCouncillor(councillorDeck.drawFromPool(), j);
		}
	}

	/**
	 * This (private) method divides the links of the cities among the group of
	 * cities belonging to the same region.
	 * 
	 * @param type
	 *            The type of region which needs to be initialized with the
	 *            links among the cities.
	 * @param cityLinks
	 *            The whole list of city links (of all the 15 cities) read from
	 *            file.
	 * 
	 * @return the links among the cities belonging to the single region of
	 *         given type if the type given does not match any RegionTypes
	 *         value, returns null
	 */
	private String[] assignLinksToCities(RegionTypes type, String[] cityLinks) {
		int n;
		switch (type) {
		case COAST:
			n = 1;
			String[] linksCity1 = new String[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				linksCity1[i] = cityLinks[i + Constants.NUMBEROFCITIESPERREGION * (n - 1)];
			return linksCity1;

		case HILL:
			n = 2;
			String[] linksCity2 = new String[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				linksCity2[i] = cityLinks[i + Constants.NUMBEROFCITIESPERREGION * (n - 1)];
			return linksCity2;

		case MOUNTAIN:
			n = 3;
			String[] linksCity3 = new String[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				linksCity3[i] = cityLinks[i + Constants.NUMBEROFCITIESPERREGION * (n - 1)];
			return linksCity3;

		default:
			return null;
		}
	}

	/**
	 * This (private) method divides the reward tokens of the cities among the
	 * group of cities belonging to the same region.
	 * 
	 * @param type
	 *            The type of region which needs to be initialized with the
	 *            rewards of the cities.
	 * @param rewards
	 *            The whole list of city rewards (of all the 15 cities) read
	 *            from file.
	 * 
	 * @return the rewards associated to the cities belonging to the single
	 *         region of given type; if the type given does not match any
	 *         RegionTypes value, return null.
	 */
	private Reward[] assignRewardsToCities(RegionTypes type, Reward[] rewards) {
		
		switch (type) {
		case COAST:
				
			Reward[] city1Rewards = new Reward[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city1Rewards[i] = rewards[i + Constants.NUMBEROFCITIESPERREGION * (0)];
			return city1Rewards;

		case HILL:
			
			Reward[] city2Rewards = new Reward[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city2Rewards[i] = rewards[i + Constants.NUMBEROFCITIESPERREGION * (1)];
			return city2Rewards;

		case MOUNTAIN:
			
			Reward[] city3Rewards = new Reward[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city3Rewards[i] = rewards[i + Constants.NUMBEROFCITIESPERREGION * (2)];
			return city3Rewards;

		default: throw new IllegalArgumentException();
		}
	}

	/**
	 * This (private) method divides the colors of the cities among the group of
	 * cities belonging to the same region.
	 * 
	 * @param type
	 *            The type of region which needs to be initialized with the
	 *            colors of the cities.
	 * @param colors
	 *            The whole list of city colors (of all the 15 cities) read from
	 *            file.
	 * 
	 * @return the colors of the cities belonging to the single region of given
	 *         type; if the type given does not match any RegionTypes value,
	 *         return null
	 */
	private CityColor[] assignColorsToCities(RegionTypes type, CityColor[] colors) {
		
		switch (type) {

		case COAST:
			
			CityColor[] city1Colors = new CityColor[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city1Colors[i] = colors[i + Constants.NUMBEROFCITIESPERREGION * (0)];
			return city1Colors;

		case HILL:
			
			CityColor[] city2Colors = new CityColor[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city2Colors[i] = colors[i + Constants.NUMBEROFCITIESPERREGION * (1)];
			return city2Colors;

		case MOUNTAIN:
			
			CityColor[] city3Colors = new CityColor[5];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				city3Colors[i] = colors[i + Constants.NUMBEROFCITIESPERREGION * (2)];
			return city3Colors;

		default:   throw new IllegalArgumentException();
		}
	}

	/**
	 * This (private) method divides the permit Cards among the regions.
	 * 
	 * @param type
	 *            The type of region which needs to be initialized with the
	 *            permit card deck.
	 * @param cards
	 *            The whole list of permit cards read from file.
	 * 
	 * @return the permit cards belonging to the single region of given type if
	 *         the type given does not match any RegionTypes value, return null
	 */
	private void assignPermitsToRegions(RegionTypes type, ArrayList<PermitCard> cards) {
		Reward reward;
		BonusType regionType;
		int quantity;
		String cities;

		int n = cards.size() / 3;
		int k;

		switch (type) {
		case COAST:
			k = 0;
			this.permitsRegion1 = new ArrayList<PermitCard>();
			for (int i = 0; i < n; i++) {
				reward = new Reward();
				for (int j = 0; j < cards.get(i + k * n).getReward().getBonuses().size(); j++) {
					regionType = cards.get(i + k * n).getReward().getBonusByIndex(j).getType();
					quantity = cards.get(i + k * n).getReward().getBonusByIndex(j).getQuantity();
					reward.addBonus(regionType, quantity);
				}
				cities = cards.get(i + k * n).getCities();
				permitsRegion1.add(new PermitCard(reward, cities));
			}
			break;

		case HILL:
			k = 1;
			this.permitsRegion2 = new ArrayList<PermitCard>();
			for (int i = 0; i < cards.size() / 3; i++) {
				reward = new Reward();
				for (int j = 0; j < cards.get(i + k * n).getReward().getBonuses().size(); j++) {
					regionType = cards.get(i + k * n).getReward().getBonusByIndex(j).getType();
					quantity = cards.get(i + k * n).getReward().getBonusByIndex(j).getQuantity();
					reward.addBonus(regionType, quantity);
				}
				cities = cards.get(i + k * n).getCities();
				permitsRegion2.add(new PermitCard(reward, cities));
			}
			break;

		case MOUNTAIN:
			k = 2;
			this.permitsRegion3 = new ArrayList<PermitCard>();
			for (int i = 0; i < n; i++) {
				reward = new Reward();
				for (int j = 0; j < cards.get(i + k * n).getReward().getBonuses().size(); j++) {
					regionType = cards.get(i + k * n).getReward().getBonusByIndex(j).getType();
					quantity = cards.get(i + k * n).getReward().getBonusByIndex(j).getQuantity();
					reward.addBonus(regionType, quantity);
				}
				cities = cards.get(i + k * n).getCities();
				permitsRegion3.add(new PermitCard(reward, cities));
			}
			break;

		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * This (private) method assigns the name to the cities in the regions.
	 * 
	 * @param type
	 *            The type of region where are located the cities.
	 * @param names
	 *            The whole list of cities read from file.
	 * 
	 * @return the names of the cities belonging to the single region of given
	 *         type; if the type given does not match any RegionTypes value,
	 *         return null.
	 */
	private String[] assignNamesToCities(RegionTypes type, String[] names) {
		
		switch (type) {
		case COAST:
			
			String[] namesCity1 = new String[Constants.NUMBEROFCITIESPERREGION];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				namesCity1[i] = names[i + Constants.NUMBEROFCITIESPERREGION * (0)];
			return namesCity1;

		case HILL:
			
			String[] namesCity2 = new String[Constants.NUMBEROFCITIESPERREGION];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				namesCity2[i] = names[i + Constants.NUMBEROFCITIESPERREGION * (1)];
			return namesCity2;

		case MOUNTAIN:
		
			String[] namesCity3 = new String[Constants.NUMBEROFCITIESPERREGION];
			for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
				namesCity3[i] = names[i + Constants.NUMBEROFCITIESPERREGION * (2)];
			return namesCity3;

		default:  throw new IllegalArgumentException();
			
		}
	}

	/**
	 * Sets the number of players of the current game.
	 * 
	 * @param num
	 *            The number of players to be set.
	 */
	public void setNumberOfPlayers(int num) {
		numberOfPlayers = num;
	}

	/**
	 * Adds a player to the current game.
	 * 
	 * @param turn
	 *            The round in which there is a new player.
	 */
	public void addPlayer(int turn) {
		players.add(new Player(turn));
	}

	/**
	 * This (private) method creates new players and assigns them initial number
	 * of assistants and politics cards.
	 * 
	 * @throws java.io.IOException
	 */
	private void initializePlayers() throws IOException {

		// for each player
		for (int j = 0; j < numberOfPlayers; j++)
			addPlayer(j);

		for (int i = 0; i < numberOfPlayers; i++) {

			getPlayer(i).addCoins(getPlayer(i).getTurn() + 10);

			drawAssistants(getPlayer(i).getTurn() + 1);

			getPlayer(i).addAssistants(getPlayer(i).getTurn() + 1);

			for (int j = 0; j < Constants.INITIALNUMBEROFCARDS; j++)
				getPlayer(i).addPoliticCard(getPoliticCardDeck().drawFromPool());

			if (numberOfPlayers == 2) {
				gameForTwoPlayers();
			}
		}
	}

	/**
	 * This method is used in the case where there are only two players.
	 * 
	 * @throws java.io.IOException
	 */
	public void gameForTwoPlayers() throws IOException {

		ArrayList<PermitCard> cards = new ArrayList<PermitCard>();

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {


			cards.add(regions[i].getPermits().get(regions[i].getPermits().size()-1));
			cards.add(regions[i].getPermits().get(regions[i].getPermits().size()-2));


		}

		for (int j = 0; j < cards.size(); j++) {
			String cities = cards.get(j).getCities();

			for (int k = 0; k < cities.length(); k++) {
				char c = cities.charAt(k);
				getRegion(getRegionByCity(c)).getCityById(c).addEmporium(-1);
			}
		}

		
		for (int k=0; k<Constants.NUMOFREGIONS; k++){
			
			regions[k].shuffle();
			
			
			
		}	


	}

	/**
	 * This method returns the region board by giving an index that indicates
	 * the type of the region (as a enumeration value).
	 * 
	 * @param index
	 *            The type of the region.
	 * 
	 * @return the selected region
	 */
	public Region getRegion(RegionTypes type) {
		switch (type) {
		case COAST:
			return this.regions[0];
		case HILL:
			return this.regions[1];
		case MOUNTAIN:
			return this.regions[2];
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * This method returns the region board by giving an index that indicates
	 * the position (as a number).
	 * 
	 * @param index
	 *            The position of the region.
	 * 
	 * @return the selected region
	 */
	public Region getRegionByIndex(int index) {
		if (index >= 0 && index < 3)
			return this.regions[index];
		else
			throw new IllegalAccessError();
	}

	/**
	 * @return the king balcony
	 */
	public Balcony getKingBalcony() {
		return this.kingBalcony;
	}

	/**
	 * This method returns the reward that is in the given position of nobility
	 * track.
	 * 
	 * @param index
	 *            The position of the reward.
	 * 
	 * @return the reward of a given index in the nobility track
	 */
	public Reward getNobilityReward(int index) {
		return this.nobilityTrack[index];
	}

	/**
	 * @return the deck of councillors
	 */
	public CouncillorDeck getCouncillorDeck() {
		return this.councillorDeck;
	}

	/**
	 * @return the deck of politics cards
	 */
	public PoliticCardDeck getPoliticCardDeck() {
		return this.politicCardDeck;
	}

	/**
	 * @return the number of available assistants
	 */
	public int getAssistants() {
		return this.assistants;
	}

	/**
	 * This method decrements the number of assistants of a given number.
	 * 
	 * @param n
	 *            The number given for the decrease.
	 */
	public void drawAssistants(int n) {
		if (this.assistants - n >= 0) {
			this.assistants = this.assistants - n;
		} else
			throw new IllegalArgumentException();
	}

	/**
	 * @return the ArrayList of players
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	/**
	 * This method returns the player identified by a given position index.
	 * 
	 * @param index
	 *            The position of the player in the game.
	 * 
	 * @return the player of given index
	 */
	public Player getPlayer(int index) {
		return this.players.get(index);
	}

	/**
	 * @return the rewards given building an emporium in all the cities of the
	 *         same color
	 */
	public Reward[] getColorRewards() {
		return colorRewards;
	}

	/**
	 * @return the rewards given by the king premium cards
	 */
	public ArrayList<Reward> getKingRewards() {
		return kingRewards;
	}

	/**
	 * @return the number of players in the current game
	 */
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}

	/**
	 * this method indicates whether there are bonuses associated with the
	 * Nobility track.
	 * 
	 * @param index
	 *            The position of nobility track.
	 * 
	 * @return the boolean condition about the presence of bonuses
	 */
	public boolean bonusDetected(int index) {
		if ((index < 0) || (index > 20))
			throw new IllegalArgumentException("indice percorso della nobilta' non valido!!!!");
		if (this.nobilityTrack[index].getBonuses().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the nobility track (with bonuses inside)
	 */
	public Reward[] getNobilityTrack() {
		return this.nobilityTrack;
	}

	/**
	 * returns the type of Region which contains the city of given Id
	 * 
	 * @param cityId
	 *            : Id of the city
	 * @return type of the region which contains the city of given Id
	 */
	public RegionTypes getRegionByCity(char cityId) {

		if ((cityId == 'A') || (cityId == 'B') || (cityId == 'C') || (cityId == 'D') || (cityId == 'E'))
			return RegionTypes.COAST;
		else if ((cityId == 'F') || (cityId == 'G') || (cityId == 'H') || (cityId == 'I') || (cityId == 'J'))
			return RegionTypes.HILL;
		else if ((cityId == 'K') || (cityId == 'L') || (cityId == 'M') || (cityId == 'N') || (cityId == 'O'))
			return RegionTypes.MOUNTAIN;
		else
			return RegionTypes.NULL;
	}

	public boolean getAction() {
		return this.addAction;
	}

	public void setActionFalse() {
		this.addAction = false;
	}

	public void setActionTrue() {
		this.addAction = true;
	}

	// getters/setters for 'bonusStrano'
	public int getBonusStrano() {
		return this.bonusStrano;
	}

	public void setBonusStranoDefault() {
		this.bonusStrano = -1;
	}

	public void setBonusStrano(int numeroMossaStrana) {
		this.bonusStrano = numeroMossaStrana;
	}

	// getters/setters for 'numberTesseraBonus'
	public int getNumberTesseraBonus() {
		return this.numberTesseraBonus;
	}

	public void setNumberTesseraBonusDefault() {
		this.numberTesseraBonus = -1;
	}

	public void setNumberTesseraBonus(int number) {
		this.numberTesseraBonus = number;
	}

	// getters/setters for 'cittaBonus1'
	public String getCityBonus1() {
		return this.cittaBonus1;
	}

	public void setCityBonus1Default() {
		this.cittaBonus1 = null;
	}

	public void setCityBonus1(String citta) {
		this.cittaBonus1 = citta;
	}

	// getters/setters for 'cittaBonus2'
	public String getCityBonus2() {
		return this.cittaBonus2;
	}

	public void setCityBonus2Default() {
		this.cittaBonus2 = null;
	}

	public void setCityBonus2(String citta) {
		this.cittaBonus2 = citta;
	}

	// getters/setters fot 'regioneBonus'
	public RegionTypes getRegionBonus() {
		return this.regioneBonus;
	}

	public void setRegionBonusDefault() {
		this.regioneBonus = null;
	}

	public void setRegionBonus(RegionTypes regione) {
		this.regioneBonus = regione;
	}

	// getters/setters for 'numberTesseraRegione'
	public int getNumberTesseraRegione() {
		return this.numberTesseraRegione;
	}

	public void setNumberTesseraRegioneDefault() {
		this.numberTesseraRegione = -1;
	}

	public void setNumberTesseraRegione(int number) {
		this.numberTesseraRegione = number;
	}

	public Integer[][] initializeKingMatrix() {

		Integer[][] matrix = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];
		for (int i = 0; i < Constants.NUMBEROFCITIES; i++)
			for (int j = 0; j < Constants.NUMBEROFCITIES; j++)
				matrix[i][j] = 0;

		char city = 'Z';
		int index = 0;

		for (int i = 0; i < Constants.NUMBEROFCITIES; i++) {

			matrix[i][i] = 1;

			city = indexToChar(i);

			String links = getRegion(getRegionByCity(city)).getCityById(city).getLinkedCities();

			for (int j = 0; j < links.length(); j++) {
				index = charToIndex(links.charAt(j));
				matrix[i][index] = 1;
			}
		}
		return matrix;
	}

	/**
	 * Converts the given index (as an integer) in character.
	 * 
	 * @param index
	 *            The input index.
	 * 
	 * @return the associated character
	 */
	public char indexToChar(int index) {

		char city = (char) (index + 65);
		if (city >= 'A' && city <= 'O')
			return city;
		else
			return 'Z';
	}

	/**
	 * Converts the given character in integer.
	 * 
	 * @param c
	 *            The input character.
	 * 
	 * @return the associated integer
	 */
	public int charToIndex(char c) {

		int index = (int) c - 65;
		if (index >= 0 && index <= 14)
			return index;
		else
			return -1;
	}

	public Integer[][] multiplicationMatrix(Integer[][] matrix1, Integer matrix2[][]) {

		Integer[][] A = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];
		Integer[][] B = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];
		Integer[][] C = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];

		for (int i = 0; i < Constants.NUMBEROFCITIES; i++) {
			for (int j = 0; j < Constants.NUMBEROFCITIES; j++) {
				A[i][j] = 0;
				B[i][j] = 0;
				C[i][j] = 0;
			}
		}

		A = matrix1;
		B = matrix2;

		for (int i = 0; i < Constants.NUMBEROFCITIES; i++)
			for (int j = 0; j < Constants.NUMBEROFCITIES; j++)
				for (int k = 0; k < Constants.NUMBEROFCITIES; k++)
					C[i][j] += A[i][k] * B[k][j];

		for (int i = 0; i < Constants.NUMBEROFCITIES; i++)
			for (int j = 0; j < Constants.NUMBEROFCITIES; j++)
				if (C[i][j] > 0)
					C[i][j] = 1;

		return C;
	}

	public int GodMatrix(char c) {
		int counter = 1;
		int indexMatrix = 0;
		int indexMatrixKing = 0;

		Integer[][] matrix = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];
		matrix = initializeKingMatrix();

		// row index of the matrix
		indexMatrix = charToIndex(c);

		// searching the city where there is the king
		char kingCity = getKing();
		indexMatrixKing = charToIndex(kingCity);

		if (kingCity == c) {
			counter = 0;
		}

		do {
			if (matrix[indexMatrixKing][indexMatrix] != 1) {
				matrix = multiplicationMatrix(matrix, matrix);
				counter++;
			}
		} while (matrix[indexMatrixKing][indexMatrix] != 1);

		return counter;
	}

	/**
	 * Sets the king in the given city (as a char that indicates its first
	 * letter).
	 * 
	 * @param c
	 *            The ID of the city.
	 */
	public void setKing(char c) {
		this.king = c;
	}

	/**
	 * @return the king
	 */
	public char getKing() {
		return this.king;
	}

	/**
	 * Checks if any player has run out of emporiums and returns true in that
	 * case. Otherwise, it returns false (and the game must go on).
	 * 
	 * @return the boolean condition
	 */
	public boolean checkEndOfTheGame() {

		boolean flag = false;

		for (int i = 0; i < getPlayers().size(); i++)
			if (getPlayer(i).getEmporiums() == 0)
				flag = true;
		return flag;
	}

	/**
	 * Checks if if the player has built an emporium in all cities of the same
	 * given color
	 * 
	 * @param cityColor
	 *            The color of the city.
	 * @param player
	 *            The round.
	 * 
	 * @return the boolean condition
	 */
	public boolean checkEmporiumsInCityOfTheSameColor(CityColor cityColor, int player) {

		if ((player < 0) || (player >= getPlayers().size()))
			throw new IllegalArgumentException("il giocatore non e' valido");
		if (cityColor == CityColor.NULL)
			return false;

		if ((cityColor != CityColor.GOLD) && (cityColor != CityColor.SILVER) && (cityColor != CityColor.BRONZE)
				&& (cityColor != CityColor.IRON))
			throw new IllegalArgumentException("la regione inserita non e' valida");

		ArrayList<City> cities = new ArrayList<City>();
		boolean flag = false;
		PlayerColor color = PlayerColor.assignColorToTurn(player);
		int count = 0;

		for (int i = 0; i < 15; i++) {

			char c = indexToChar(i);

			if (getRegion(getRegionByCity(c)).getCityById(c).getColor() == cityColor) {
				cities.add(getRegion(getRegionByCity(c)).getCityById(c));
			}
		}

		for (int j = 0; j < cities.size(); j++)
			if (cities.get(j).emporiumbBuilt(color))
				count++;
		if (count == cities.size())
			flag = true;
		return flag;
	}

	/**
	 * Checks if the player of given round has built an emporium in all the
	 * cities belonging to the same given region.
	 * 
	 * @param region
	 *            The type of the region.
	 * @param player
	 *            The round.
	 * 
	 * @return the boolean condition
	 */
	public boolean checkEmporiumsInCityOfTheSameRegion(RegionTypes region, int player) {

		PlayerColor color = PlayerColor.assignColorToTurn(player);
		int count = 0;
		boolean flag = false;

		for (int i = 0; i < Constants.NUMBEROFCITIESPERREGION; i++)
			if (getRegion(region).getCities()[i].emporiumbBuilt(color))
				count++;
		if (count == Constants.NUMBEROFCITIESPERREGION)
			flag = true;
		return flag;
	}

	/**
	 * This method initializes the king premium cards.
	 */
	public void initializeKingRewards() {

		Reward reward1 = new Reward();
		Reward reward2 = new Reward();
		Reward reward3 = new Reward();
		Reward reward4 = new Reward();
		Reward reward5 = new Reward();

		reward1.addBonus(BonusType.VICTORY, 3);
		reward2.addBonus(BonusType.VICTORY, 7);
		reward3.addBonus(BonusType.VICTORY, 12);
		reward4.addBonus(BonusType.VICTORY, 18);
		reward5.addBonus(BonusType.VICTORY, 25);

		kingRewards.add(reward1);
		kingRewards.add(reward2);
		kingRewards.add(reward3);
		kingRewards.add(reward4);
		kingRewards.add(reward5);
	}

	/**
	 * This method returns the index of the city color reward related to the
	 * given city color.
	 * 
	 * @param color
	 *            The given city color.
	 * 
	 * @return the index of city color reward
	 */
	public int assignRewardToCityColor(CityColor color) {

		if (color == CityColor.NULL)
			throw new IllegalArgumentException("colore citta non valido");
		else
			return color.ordinal();
	}

	/**
	 * This method initializes the color rewards.
	 */
	public void initializeColorRewards() {

		for (int i = 0; i < 4; i++)
			colorRewards[i] = new Reward();

		colorRewards[0].addBonus(BonusType.VICTORY, 5);
		colorRewards[1].addBonus(BonusType.VICTORY, 8);
		colorRewards[2].addBonus(BonusType.VICTORY, 12);
		colorRewards[3].addBonus(BonusType.VICTORY, 20);
	}

	/**
	 * @return if there's a king premium card available.
	 */
	public Reward getPremiumReward() {
		if (!kingRewards.isEmpty()) {
			return kingRewards.remove(kingRewards.size() - 1);
		} else
			return null;
	}

	public Reward getColorReward(CityColor color) {
		int index = assignRewardToCityColor(color);
		if (colorRewards[index] == null)
			throw new IllegalArgumentException("reward nullo!!");

		Reward colorReward;
		colorReward = colorRewards[index];
		if ((colorRewards[index].getBonusByIndex(0).getType() != BonusType.NULL)
				&& (colorRewards[index].getBonusByIndex(0).getQuantity() > 0)) {
			return colorReward;
		} else
			throw new IllegalArgumentException("color reward non valido!!");
	}
}