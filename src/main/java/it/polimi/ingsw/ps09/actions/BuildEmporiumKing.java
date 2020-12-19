package it.polimi.ingsw.ps09.actions;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.Bonus;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * This action represents a player building an emporium with the help of the
 * king: [ASSUMPTION: the player must satisfy the King's council following the
 * same rules of 'Acquire a business permit tile' (discard cards and pay coins)]
 * they move the King to the city of their choice. The King must use
 * uninterrupted roads to make the journey. The player pays 2 coins for each
 * road travelled. The King may also be left in the same city, in this case, the
 * player pays no money. The player immediately builds an emporium in the space
 * where the King is located at the end of his journey.
 */
public class BuildEmporiumKing extends Action {

	private static final long serialVersionUID = 1L;

	

	/**
	 * The ID (as a character) of the city in which the player has to build the
	 * emporium.
	 */
	char city;

	/**
	 * The round in which the player is performing the action.
	 */
	int turn;

	/**
	 * This array is used by the applyBonus() method to memorize the emporiums
	 * in all cities.
	 */
	Integer[] nodeColors = new Integer[15];

	/**
	 * The color of the player.
	 */
	PlayerColor color;
	private static final Logger LOGGER = Logger.getLogger(BuildEmporiumKing.class.getName());

	/**
	 * The BuildEmporiumWithKing constructor.
	 * 
	 * @param model
	 *            a copy of the model to modify.
	 * @param turn
	 *            the round of the player.
	 * @param city
	 *            the city in which the player has to build the emporium.
	 * @param colors
	 *            the ArrayList of colors which contains the colors of the cards
	 *            that the player wants to use.
	 */
	public BuildEmporiumKing(Model model, int turn, String city, ArrayList<Colors> colors) {
		super(model, 4, turn, true, colors);
		
		this.city = city.charAt(0);
		this.turn = turn;

		// saves the color of the player into a global variable
		color = model.getPlayer(turn).getColor();

		// initialize nodeColors:
		// if the current player has NO emporiums on the city of index i,
		// nodeColors[i]=0;
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

	@Override
	public boolean isValid() {
		boolean flag;
		boolean flag1;
		boolean flag2;
		boolean flag3;
		// controllo che il player abbia abbastanza monete per muovere il re e
		// per scartare le carte politica
		int coinsPlayer = model.getPlayer(turn).getCoins();
		int coinsToMoveKing = model.GodMatrix(city) * 2;
		int coinsPoliticCards = computeNumberOfCoins();
		if (coinsPlayer >= coinsToMoveKing + coinsPoliticCards) {
			flag1 = true;
		} else {
			flag1 = false;
			LOGGER.log(Level.INFO,"Non hai abbastanza monete per muovere il Re");
		}

		// controllo che le carte inserite per soddisfare il consiglio siano
		// compatibili con il balcone del re
		// carte in mano al giocatore
		Integer[] manoPlayer = new Integer[Constants.NUMBEROFCOLORS + 1];
		// carte scartate con i jolly
		Integer[] cardsCountersWithJolly = new Integer[Constants.NUMBEROFCOLORS + 1];
		// carte scartate senza i jolly
		Integer[] cardsCountersWithoutJolly = new Integer[Constants.NUMBEROFCOLORS];
		// colore consiglieri balcone
		Integer[] councillorsCounters = new Integer[Constants.NUMBEROFCOLORS];

		// conto le carte scartate compresi i jolly
		manoPlayer = countHandColors(model.getPlayer(turn).getCards());
		cardsCountersWithJolly = countCardsColorsWithJolly(cards);
		cardsCountersWithoutJolly = countCardsColors(cards);
		councillorsCounters = countNumberOfCouncillorsColors(model.getKingBalcony().getCouncillors());

		int contatore1 = 0;
		int contatore2 = 0;

		// for tra le carte in mano e le carte scartate quindi compresi di jolly
		for (int i = 0; i < 7; i++) {
			if (cardsCountersWithJolly[i] <= manoPlayer[i]) {
				contatore1++;
			}
		}

		for (int j = 0; j < 6; j++) {
			if (cardsCountersWithoutJolly[j] <= councillorsCounters[j]) {
				contatore2++;
			}
		}

		if (contatore1 == (contatore2 + 1)) {
			flag2 = true;
		} else {
			flag2 = false;
			LOGGER.log(Level.INFO,"Le carte scelte non corrispondono a quelle che hai in mano o a quelle nel balcone del Re");
		}

		// controllo che nella citta' dove vuole contruire non abbia già qualche
		// emporio di sua proprieta'
		if (model.getRegion(model.getRegionByCity(city)).getCityById(city).emporiumbBuilt(color)) {
			flag = false;
			LOGGER.log(Level.INFO,"Hai già costruito in questa citta'. Cambia Citta'!!");
		} else
			flag = true;

		// controllo il numero di assistentti a disposizione
		int numberOfEmporiums = model.getRegion(model.getRegionByCity(city)).getCityById(city).getNumberOfEmporiums();
		if (numberOfEmporiums <= model.getPlayer(turn).getAssistants()) {
			flag3 = true;
		} else {
			flag3 = false;
			LOGGER.log(Level.INFO,"Non hai un numero sufficiente di assistenti per costruire un emporio nella citta' scelta");
		}
		return flag && flag1 && flag2 && flag3;
	}

	@Override
	public Model execute() {

		// removes the politic cards chosen by the player
		for (int i = 0; i < cards.size(); i++) {

			if ((cards.get(i) == null) || (cards.get(i) == Colors.NULL))
				throw new NullPointerException("la carta giocata e' nulla!!");

			model.getPlayer(turn).removeFirstOccurrence(cards.get(i));
		}

		// removes the coins needed to play the cards
		int coinsPoliticCards = computeNumberOfCoins();
		model.getPlayer(turn).addCoins(-coinsPoliticCards);

		// removes the coins needed to move the king around

		int CoinsToMove = model.GodMatrix(city);
		model.getPlayer(turn).addCoins(-CoinsToMove);

		// removes a councillor for each emporium already built by other players
		// on the city
		int numEmporiumsBuilt = model.getRegion(model.getRegionByCity(city)).getCityById(city).getNumberOfEmporiums();
		model.getPlayer(turn).addAssistants(-numEmporiumsBuilt);

		// builds the emporium
		model.getRegion(model.getRegionByCity(city)).getCityById(city).addEmporium(turn);

		// decrements the number of emporium still available from the player's
		// hand
		model.getPlayer(turn).updateEmporium();

		// moves the king
		model.setKing(city);

		applyBonus();

		return model;
	}

	@Override
	public void applyBonus() {

		Reward reward = model.getRegion(model.getRegionByCity(city)).getCityById(city).getBonus();

		if (reward == null)
			throw new IllegalArgumentException("la citta' non ha un reward!!");

		if (reward.getBonuses() == null)
			throw new IllegalArgumentException("non ci sono bonus dentro il reward della city");

		int size = reward.getBonuses().size();

		for (int i = 0; i < size; i++) {
			Bonus bonusTessera = model.getRegion(model.getRegionByCity(city)).getCityById(city).getBonus()
					.getBonusByIndex(i);
			switchBonus(bonusTessera);
		}

		applyLinkedCitiesBonuses(city);

		applyRegionReward();
		applyColorReward();

	}

	@Override
	public int applyLinkedCitiesBonuses(char c) {
		return super.applyLinkedCitiesBonuses(c);
	}

	@Override
	public void switchBonus(Bonus bonus) {
		super.switchBonus(bonus);
	}

	@Override
	public boolean nulla() {

		return valido;
	}

	@Override
	public void applyRegionReward() {
		super.applyRegionReward();
	}

	@Override
	public void applyColorReward() {
		super.applyColorReward();
	}




}