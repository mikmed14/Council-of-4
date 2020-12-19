package it.polimi.ingsw.ps09.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps09.model.Bonus;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * This action represents a player building an emporium using a permit tile: the
 * player chooses one of the permit tiles face up in front of them; they place
 * an emporium on the corresponding space in the city whose initial is indicated
 * on the tile (if the tile indicates more than one city, they choose which city
 * to build in); they turn the used face down and CANNOT use anymore to build
 * another emporium.
 */
public class BuildEmporium extends Action {

	private static final long serialVersionUID = 1L;

	/**
	 * The ID (as a character) of the city in which the player has to build the
	 * emporium.
	 */
	char city;

	/**
	 * The index position of the business permit tile chosen by the player.
	 */
	int cardIndex;

	/**
	 * The round in which the player is performing the action.
	 */
	int turn;

	/**
	 * The selected permit tile to perform the action.
	 */
	PermitCard card;

	/**
	 * This array is used by the applyBonus() method to memorize the emporiums
	 * in all cities.
	 */
	Integer[] nodeColors = new Integer[15];

	/**
	 * The color of the player.
	 */
	PlayerColor color;
	private static final Logger LOGGER = Logger.getLogger(BuildEmporium.class.getName());

	/**
	 * The BuildEmporium constructor.
	 * 
	 * @param model
	 *            a copy of the model to modify.
	 * @param turn
	 *            the round of the player.
	 * @param cardIndex
	 *            the index position of the business permit tile chosen.
	 * @param city
	 *            the city in which the player has to build the emporium.
	 */
	public BuildEmporium(Model model, int turn, int cardIndex, String city) {
		super(model, 3, turn, true, null);
		this.turn = turn;
		this.city = city.charAt(0);
		this.cardIndex = cardIndex;
		this.card = model.getPlayer(turn).getPermitUpByIndex(cardIndex);

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

		boolean flag = false;

		String cities = card.getCities();

		char carattere;
		// controlla che la città inserita sia presente nella tessera permesso
		// scelta
		for (int i = 0; i < cities.length(); i++) {

			carattere = cities.charAt(i);

			if (carattere == city) {
				flag = true;
			} else {
				flag = false;
				LOGGER.log(Level.INFO,"La citta' inserita non e' presente nella tessera permesso scelta");
			}

		}

		int numberOfEmporiums = model.getRegion(model.getRegionByCity(city)).getCityById(city).getNumberOfEmporiums();
		// verifica che il giocatore abbia un numero di assistenti almeno pari
		// al numero di empori costruiti in quella citta' da altri giocatori
		if (numberOfEmporiums <= model.getPlayer(turn).getAssistants())
			flag = true;
		// verifica che il giocatore non abbia ancora contruito in quella citta'
		if (model.getRegion(model.getRegionByCity(city)).getCityById(city)
				.emporiumbBuilt(model.getPlayer(turn).getColor())) {
			flag = false;
			LOGGER.log(Level.INFO,"Hai gia' costruito in questa citta'");
		}

		return flag;
	}

	@Override
	public Model execute() {

		int consiglieriDaRestituire = model.getRegion(model.getRegionByCity(city)).getCityById(city)
				.getNumberOfEmporiums();
		model.getRegion(model.getRegionByCity(city)).getCityById(city).addEmporium(turn);
		model.getPlayer(turn).updateEmporium();
		model.getPlayer(turn).usePermit(cardIndex);

		model.getPlayer(turn).addAssistants(-consiglieriDaRestituire);

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
	public void applyRegionReward() {
		super.applyRegionReward();
	}

	@Override
	public void applyColorReward() {
		super.applyColorReward();
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
		return false;
	}
}