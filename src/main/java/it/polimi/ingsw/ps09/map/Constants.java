package it.polimi.ingsw.ps09.map;

/**
 * This class contains the constants of the game.
 */
public class Constants {

	/** The maximum number of players in the game */
	public static final int MAXPLAYERS = 15;

	/** The minimum number of players in the game */
	public static final int MINPLAYERS = 2;

	/** */
	public static final int NUMTERRITORI = 10;

	/** The number of the main actions a player can perform during the game */
	public static final int NUMMOSSEPRINCIPALI = 4;

	/** The number of the quick actions a player can perform during the game */
	public static final int NUMMOSSEVELOCI = 8;

	/**
	 * This constant is used to refer to the first main action ('elect a
	 * councillor')
	 */
	public static final int ELECTCOUNCILLOR = 1;

	/**
	 * This constant is used to refer to the second main action ('acquire a
	 * business permit tile')
	 */
	public static final int ACQUIREPERMIT = 2;

	/**
	 * This constant is used to refer to the third main action ('build an
	 * emporium using a permit tile')
	 */
	public static final int BUILDEMPORIUM = 3;

	/**
	 * This constant is used to refer to the fourth main action ('build an
	 * emporium with the help of the king')
	 */
	public static final int BUILDEMPORIUMKING = 4;

	/**
	 * This constant is used to refer to the first quick action ('engage an
	 * assistant')
	 */
	public static final int ACQUIREASSISTENTE = 5;

	/**
	 * This constant is used to refer to the second quick action ('change
	 * business permit tiles')
	 */
	public static final int CHANGEPERMITCARDS = 6;

	/**
	 * This constant is used to refer to the third quick action ('send an
	 * assistant to elect a councillor')
	 */
	public static final int ASSISTELECTCOUNCILLOR = 7;

	/**
	 * This constant is used to refer to the fourth quick action ('perform an
	 * additional main action')
	 */
	public static final int ADDACTION = 8;

	/** */
	public static final int NUMOSSE = 2;

	/** The number of region board game */
	public static final int NUMOFREGIONS = 3;

	/** The number of councillors which are in a balcony */
	public static final int NUMOFCOUNCILLORSFOREACHBALCONY = 4;

	/** The number of colors of the councillors */
	public static final int NUMBEROFCOLORS = 6;

	/** The number of cities in the game */
	public static final int NUMBEROFCITIES = 15;

	/** The number of cities situated in each region board game */
	public static final int NUMBEROFCITIESPERREGION = 5;

	/**
	 * The initial number of politics cards drawn by the player at the beginning
	 * of the game
	 */
	public static final int INITIALNUMBEROFCARDS = 6;

	/** The number of spaces of the nobility track */
	public static final int NOBILITYTRACK = 21;

	/**
	 * Empty private constructor to hide the public one
	 */
	private Constants() {
		return;
	}
}