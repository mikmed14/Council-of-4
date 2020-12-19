package it.polimi.ingsw.ps09.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Bonus;
import it.polimi.ingsw.ps09.model.City;
import it.polimi.ingsw.ps09.model.CityColor;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Councillor;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.PoliticCard;
import it.polimi.ingsw.ps09.model.RegionTypes;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * An Action class represents all the type of actions available in the game and
 * the actors involved in an action.
 */
public abstract class Action implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The current board game status in which is performing an action.
	 */
	protected Model model;

	/**
	 * The move of the player.
	 */
	public int mossa;

	/**
	 * The current round in which the player is performing the action.
	 */
	public int turno;

	/**
	 * The boolean condition about the validity of the action.
	 */
	public boolean valido;

	/**
	 * This array is used by the applyBonus() method to memorize the emporiums
	 * in all cities.
	 */
	Integer[] nodeColors = new Integer[15];

	/**
	 * The color of the player.
	 */
	PlayerColor color;

	/**
	 * Cards involved in the market phase.
	 */
	List<Colors> cards;

	private static final Logger LOGGER = Logger.getLogger(Action.class.getName());

	/**
	 * The Action constructor that sets the current board game, move and turn.
	 * 
	 * @param model
	 *            The current board game
	 * @param mossa
	 *            The move of the player.
	 * @param turn
	 *            The turn in which is performing the action
	 * @param valido
	 *            The boolean condition about the validity of the action.
	 */
	public Action(Model model, int mossa, int turn, boolean valido, List<Colors> colors) {
		this.model = model;
		this.mossa = mossa;
		this.turno = turn;
		this.valido = valido;
		this.cards = new ArrayList<Colors>();
		this.cards = colors;

		// saves the color of the player into a global variable
		color = model.getPlayer(turn).getColor();

		// initialize nodeColors:
		// if the current player has NO emporiums on the city of index i,
		// nodeColors[i]=0
		// if the current player has an emporium on the city of index i and he
		// didn't yet receive the related bonus during this action,
		// nodeColors[i]=1
		// if the current player has an emporium on the city of index i and he
		// already received the related bonus during this action,
		// nodeColors[i]=-1
		for (int i = 0; i < 15; i++) {
			if (model.getRegion(model.getRegionByCity(model.indexToChar(i))).getCityById(model.indexToChar(i))
					.emporiumbBuilt(color)) {
				nodeColors[i] = 1;
			} else
				nodeColors[i] = 0;
		}
	}

	/**
	 * This (abstract) method checks if the move is valid.
	 */
	public abstract boolean isValid();

	/**
	 * This (abstract) method execute the move on the given boardstatus.
	 */
	public abstract Model execute();

	/**
	 * This (abstract) method is used to apply the bonuses relating to
	 * performed. actions.
	 */
	public abstract void applyBonus();

	/**
	 * This (abstract) method is used to declare to state whether the action is
	 * useless or not.
	 */
	public abstract boolean nulla();

	/**
	 * This method is used to apply bonuses
	 * 
	 * @param c
	 * @return
	 */
	public int applyLinkedCitiesBonuses(char c) {

		// declare the initial matrix
		Integer[][] matrix = new Integer[Constants.NUMBEROFCITIES][Constants.NUMBEROFCITIES];

		for (int i = 0; i < Constants.NUMBEROFCITIES; i++) {
			for (int j = 0; j < Constants.NUMBEROFCITIES; j++) {
				matrix[i][j] = 0;
			}
		}

		boolean flag = false;
		// initializes the initial matrix
		matrix = model.initializeKingMatrix();
		// for each city, the city is not connected to itself
		for (int i = 0; i < 15; i++) {
			matrix[i][i] = 0;
		}

		// computes the index related to the city where the player wants to
		// build
		int cityIndex = model.charToIndex(c);

		// initialize array

		nodeColors[cityIndex] = -1;

		// if nodeColors has no 1 inside all the bonuses have been successfully
		// applied

		for (int j = 0; j < 15; j++) {
			if (nodeColors[j] == 1)
				flag = true;
		}

		if (flag == false)
			return 0;

		// for each city
		for (int k = 0; k < Constants.NUMBEROFCITIES; k++) {

			// check if the city of index k is connected to the one the player
			// wants to built an emporium into and that it has an emporium
			// already built by the same player
			if ((matrix[cityIndex][k] == 1) && (nodeColors[k] == 1)) {

				// compute the char related to the city of index k
				char cityId = model.indexToChar(k);
				// takes a copy of the city of index k
				City city = model.getRegion(model.getRegionByCity(cityId)).getCityById(cityId);

				// saves the number of bonuses of the city and apply them
				int size = city.getBonus().getBonuses().size();

				for (int i = 0; i < size; i++) {
					Bonus bonus = city.getBonus().getBonusByIndex(i);

					switchBonus(bonus);
				}
				// sets -1 on the array nodeColors (bonuses already applied)
				nodeColors[k] = -1;
				// do the same for the city of index k
				applyLinkedCitiesBonuses(cityId);

			}

		}

		return 0;
	}

	public void switchBonus(Bonus bonusTessera) {
		try {
			switch (bonusTessera.getType()) {
			case VICTORY:
				model.getPlayer(turno).addPoints(bonusTessera.getQuantity());
				break;
			case NOBILITY:
				model.getPlayer(turno).addNobility(bonusTessera.getQuantity());
				int pointsNobility = model.getPlayer(turno).getNobility();
				if (model.bonusDetected(pointsNobility)) {

					int size = model.getNobilityReward(pointsNobility).getBonuses().size();

					for (int i = 0; i < size; i++) {
						Bonus bonusNobility = model.getNobilityReward(pointsNobility).getBonusByIndex(i);

						switchBonus(bonusNobility);
					}
				}
				break;
			case COIN:
				model.getPlayer(turno).addCoins(bonusTessera.getQuantity());
				break;
			case POLITIC:
				for (int i = 0; i < bonusTessera.getQuantity(); i++) {
					PoliticCard cartaPescata = model.getPoliticCardDeck().drawFromPool();
					model.getPlayer(turno).addPoliticCard(cartaPescata);
				}

				break;
			case ASSISTANT:
				model.drawAssistants(bonusTessera.getQuantity());
				model.getPlayer(turno).addAssistants(bonusTessera.getQuantity());
				break;
			case ACTION:
				model.setActionTrue();

				break;

			// caso numero 0
			case PERMIT:
				model.setBonusStrano(0);
				Thread.sleep(20000);
				RegionTypes regione = model.getRegionBonus();
				int magicNumber = model.getNumberTesseraRegione();

				if (regione != RegionTypes.NULL) {

					PermitCard carta = model.getRegion(regione).drawPermitCard(magicNumber);

					model.getPlayer(turno).addPermitCard(carta);
					for (int i = 0; i < carta.getReward().getBonuses().size(); i++) {
						Bonus bonusCard = carta.getReward().getBonusByIndex(i);

						switchBonus(bonusCard);
					}

					// reset dei parametri usati
					model.setRegionBonusDefault();
					model.setNumberTesseraRegioneDefault();
					model.setBonusStranoDefault();
				}
				break;

			// caso numero 1
			case MYPERMIT:
				model.setBonusStrano(1);
				Thread.sleep(20000);
				int tesseraPlayer = model.getNumberTesseraBonus();
				if (tesseraPlayer < 0)
					throw new IllegalArgumentException("l'indice della carta permesso e' negativo!!");

				if (tesseraPlayer < model.getPlayer(turno).getPermitsUp().size()) {
					for (int i = 0; i < model.getPlayer(turno).getPermitsUp().get(tesseraPlayer).getReward()
							.getBonuses().size(); i++) {
						Bonus bonus = model.getPlayer(turno).getPermitsUp().get(tesseraPlayer).getReward()
								.getBonusByIndex(i);
						switchBonus(bonus);
					}
				} else {
					int indexDown = tesseraPlayer - model.getPlayer(turno).getPermitsUp().size();
					for (int i = 0; i < model.getPlayer(turno).getPermitsDown().get(indexDown).getReward().getBonuses()
							.size(); i++) {
						Bonus bonus = model.getPlayer(turno).getPermitsDown().get(indexDown).getReward()
								.getBonusByIndex(i);
						switchBonus(bonus);
					}
				}

				model.setNumberTesseraBonusDefault();
				model.setBonusStranoDefault();

				break;

			// caso numero 2
			case CITYREWARD:
				if (bonusTessera.getQuantity() == 2) {
					model.setBonusStrano(2);
					Thread.sleep(20000);
					String city1 = model.getCityBonus1();
					String city2 = model.getCityBonus2();

					if (!model.getRegion(model.getRegionByCity(city1.charAt(0))).getCityById(city1.charAt(0)).getBonus()
							.getBonuses().isEmpty()) {
						for (int i = 0; i < model.getRegion(model.getRegionByCity(city1.charAt(0)))
								.getCityById(city1.charAt(0)).getBonus().getBonuses().size(); i++) {
							Bonus bonus1 = model.getRegion(model.getRegionByCity(city1.charAt(0)))
									.getCityById(city1.charAt(0)).getBonus().getBonusByIndex(i);
							switchBonus(bonus1);
						}
					}

					if (!model.getRegion(model.getRegionByCity(city2.charAt(0))).getCityById(city2.charAt(0)).getBonus()
							.getBonuses().isEmpty()) {
						for (int i = 0; i < model.getRegion(model.getRegionByCity(city2.charAt(0)))
								.getCityById(city2.charAt(0)).getBonus().getBonuses().size(); i++) {
							Bonus bonus2 = model.getRegion(model.getRegionByCity(city2.charAt(0)))
									.getCityById(city2.charAt(0)).getBonus().getBonusByIndex(i);
							switchBonus(bonus2);
						}
					}
					model.setCityBonus1Default();
					model.setCityBonus2Default();
					model.setBonusStranoDefault();

				} else {
					model.setBonusStrano(3);

					Thread.sleep(20000); 

					String city3 = model.getCityBonus1();
					if (!model.getRegion(model.getRegionByCity(city3.charAt(0))).getCityById(city3.charAt(0)).getBonus()
							.getBonuses().isEmpty()) {
						for (int i = 0; i < model.getRegion(model.getRegionByCity(city3.charAt(0)))
								.getCityById(city3.charAt(0)).getBonus().getBonuses().size(); i++) {
							Bonus bonus3 = model.getRegion(model.getRegionByCity(city3.charAt(0)))
									.getCityById(city3.charAt(0)).getBonus().getBonusByIndex(i);
							switchBonus(bonus3);
						}
					}
					model.setCityBonus1Default();
					model.setBonusStranoDefault();
				}

				break;

			default:
				break;

			// VICTORY, NOBILITY, COIN, POLITIC, ASSISTANT, ACTION, PERMIT,
			// MYPERMIT, CITYREWARD, NULL
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	/**
	 * cThis method checks if there is a region reward to apply.
	 * 
	 * @return the region which contains the reward to apply
	 */
	public RegionTypes checkRegionRewards() {
		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {
			RegionTypes region = model.getRegionByIndex(i).getRegionType();
			if (model.checkEmporiumsInCityOfTheSameRegion(region, turno)
					&& !(model.getPlayer(turno).getRegion(region))) {
				return region;
			}
		}
		return RegionTypes.NULL;
	}

	/**
	 * This method applies the region reward (in case of need).
	 */
	public void applyRegionReward() {
		RegionTypes region = checkRegionRewards();

		if (checkRegionRewards() != RegionTypes.NULL) {
			LOGGER.log(Level.INFO,
					"hai costruito un emporio in tutte le citta' della regione" + region.toString() + "!");

			model.getPlayer(turno).setRegion(region);

			// applies kingReward
			Reward kingReward = new Reward();
			kingReward = model.getPremiumReward();

			if (kingReward != null) {
				model.getPlayer(turno).addFinalBonuses(kingReward.getBonusByIndex(0).getQuantity());
				LOGGER.log(Level.INFO, "hai pescato una tessera premio del re da "
						+ kingReward.getBonusByIndex(0).getQuantity() + " punti vittoria");
			} else
				LOGGER.log(Level.INFO, "non ci sono tessere premio del re disponobili!");

			// applies region reward
			Reward reward = new Reward();
			reward = model.getRegion(region).getReward();
			for (int i = 0; i < reward.getBonuses().size(); i++) {

				LOGGER.log(Level.INFO, "ricevi " + reward.getBonusByIndex(i).getQuantity() + " "
						+ reward.getBonusByIndex(i).getType().toString() + " !");
				switchBonus(reward.getBonusByIndex(i));
			}
		}
	}

	/**
	 * This method is used to check whether in a selected city there is one of
	 * its emporiums (through the player's color).
	 */
	public CityColor checkColorRewards() {

		for (CityColor color : CityColor.values()) {

			if ((model.checkEmporiumsInCityOfTheSameColor(color, turno)
					&& !(model.getPlayer(turno).getCityColor(color)))) {
				return color;
			}
		}
		return CityColor.NULL;
	}

	/**
	 * This method is used when a player is to receive the bonus of nearby
	 * cities with its emporiums (using the method checkColorRewards() for
	 * checking its emporiums through his color).
	 */
	public void applyColorReward() {
		CityColor color = checkColorRewards();
		if (checkColorRewards() != CityColor.NULL) {

			LOGGER.log(Level.INFO,
					"hai costruito un emporio in tutte le citta' dello stesso colore" + color.toString() + "!");

			model.getPlayer(turno).setColor(color);
			// applies kingReward
			Reward kingReward = new Reward();
			kingReward = model.getPremiumReward();

			if (kingReward != null) {
				model.getPlayer(turno).addFinalBonuses(kingReward.getBonusByIndex(0).getQuantity());
				LOGGER.log(Level.INFO, "hai pescato una tessera premio del re da "
						+ kingReward.getBonusByIndex(0).getQuantity() + " punti vittoria");
			} else
				LOGGER.log(Level.INFO, "non ci sono tessere premio del re disponobili!");

			// applies color reward

			Reward colorReward = new Reward();
			if (model.getColorReward(color) == null)
				throw new IllegalArgumentException("reward nullo!!");
			else {
				colorReward = model.getColorReward(color);

				model.getPlayer(turno).addFinalBonuses(colorReward.getBonusByIndex(0).getQuantity());
				LOGGER.log(Level.INFO, "hai pescato una tessera bonus colore da "
						+ colorReward.getBonusByIndex(0).getQuantity() + " punti vittoria");
			}
		}
	}

	/**
	 * @return an array of integers in which, for each position in connection
	 *         with a specified color, is specified the number of politics cards
	 *         of the same color that the player use to perform the action.
	 */
	public Integer[] countHandColors(List<PoliticCard> cards) {

		Integer[] cardsCounters = new Integer[Constants.NUMBEROFCOLORS + 1];

		for (int i = 0; i < Constants.NUMBEROFCOLORS + 1; i++)
			cardsCounters[i] = 0;

		for (int k = 0; k < cards.size(); k++) {

			switch (cards.get(k).getColor()) {

			case BLUE:
				cardsCounters[0]++;
				break;

			case YELLOW:
				cardsCounters[1]++;
				break;

			case BLACK:
				cardsCounters[2]++;
				break;

			case GREEN:
				cardsCounters[3]++;
				break;

			case PINK:
				cardsCounters[4]++;
				break;

			case RED:
				cardsCounters[5]++;
				break;

			case JOLLY:
				cardsCounters[6]++;
				break;

			case NULL:
				throw new IllegalArgumentException("null nelle carte");

			default:
				break;
			}
		}
		return cardsCounters;
	}

	/**
	 * @return an array of integers in which, for each position in connection
	 *         with a specified color, is specified the number of councillors
	 *         present in the council of the same color
	 */
	public Integer[] countNumberOfCouncillorsColors(Councillor[] councillors) {

		Integer[] councillorsCounters = new Integer[Constants.NUMBEROFCOLORS];

		for (int i = 0; i < Constants.NUMBEROFCOLORS; i++)
			councillorsCounters[i] = 0;

		for (int p = 0; p < Constants.NUMOFCOUNCILLORSFOREACHBALCONY; p++) {

			// blue 0
			// yellow 1
			// black 2
			// green 3
			// pink 4
			// red 5

			switch (councillors[p].getColor()) {

			case BLUE:
				councillorsCounters[0]++;
				break;

			case YELLOW:
				councillorsCounters[1]++;
				break;

			case BLACK:
				councillorsCounters[2]++;
				break;

			case GREEN:
				councillorsCounters[3]++;
				break;

			case PINK:
				councillorsCounters[4]++;
				break;

			case RED:
				councillorsCounters[5]++;
				break;

			case NULL:
				throw new IllegalArgumentException("null nei consiglieri!!");

			case JOLLY:
				throw new IllegalArgumentException("jolly nei consiglieri!!");
			default:
				break;
			}
		}
		return councillorsCounters;
	}

	/**
	 * @return an array of integers in which, for each position in connection
	 *         with a specified color, is specified the number of cards of the
	 *         same color (in addition there is the jolly)
	 */
	public Integer[] countCardsColorsWithJolly(List<Colors> colors) {

		Integer[] cardsCountersWithJolly = new Integer[Constants.NUMBEROFCOLORS + 1];

		for (int i = 0; i < Constants.NUMBEROFCOLORS + 1; i++)
			cardsCountersWithJolly[i] = 0;

		for (int k = 0; k < colors.size(); k++) {

			// blue 0
			// yellow 1
			// black 2
			// green 3
			// pink 4
			// red 5
			// jolly 6

			switch (colors.get(k)) {

			case BLUE:
				cardsCountersWithJolly[0]++;
				break;

			case YELLOW:
				cardsCountersWithJolly[1]++;
				break;

			case BLACK:
				cardsCountersWithJolly[2]++;
				break;

			case GREEN:
				cardsCountersWithJolly[3]++;
				break;

			case PINK:
				cardsCountersWithJolly[4]++;
				break;

			case RED:
				cardsCountersWithJolly[5]++;
				break;

			case JOLLY:
				cardsCountersWithJolly[6]++;
				break;

			case NULL:
				throw new IllegalArgumentException("null nelle carte");
			default:
				break;
			}
		}
		return cardsCountersWithJolly;
	}

	/**
	 * @return an array of integers in which, for each position in connection
	 *         with a specified color, is specified the number of politics cards
	 *         of the same color
	 */
	public Integer[] countCardsColors(List<Colors> colors) {

		Integer[] cardsCounters = new Integer[Constants.NUMBEROFCOLORS];

		for (int i = 0; i < Constants.NUMBEROFCOLORS; i++)
			cardsCounters[i] = 0;

		for (int k = 0; k < colors.size(); k++) {

			// blue 0
			// yellow 1
			// black 2
			// green 3
			// pink 4
			// red 5

			switch (colors.get(k)) {

			case BLUE:
				cardsCounters[0]++;
				break;

			case YELLOW:
				cardsCounters[1]++;
				break;

			case BLACK:
				cardsCounters[2]++;
				break;

			case GREEN:
				cardsCounters[3]++;
				break;

			case PINK:
				cardsCounters[4]++;
				break;

			case RED:
				cardsCounters[5]++;
				break;

			case NULL:
				throw new IllegalArgumentException("null nelle carte");

			case JOLLY:
				break;
			default:
				break;

			}
		}
		return cardsCounters;
	}

	/**
	 * @return the number of jollys used by the player during the action.
	 */
	public int countNumberOfJollys() {

		int numberOfJollys = 0;

		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i) == Colors.JOLLY)
				numberOfJollys++;
		}
		return numberOfJollys;
	}

	/**
	 * @return the sum of money that the player has to pay
	 */
	public int computeNumberOfCoins() {

		int coins = 0;

		switch (cards.size()) {

		case 1:
			coins = coins + 10;
			break;
		case 2:
			coins = coins + 7;
			break;
		case 3:
			coins = coins + 4;
			break;
		case 4:
			break;
		default:
			break;

		}
		return coins + (this.countNumberOfJollys());
	}

}