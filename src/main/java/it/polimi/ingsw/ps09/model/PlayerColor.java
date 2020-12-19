package it.polimi.ingsw.ps09.model;

/**
 * This enumeration defines the color of a player.
 */
public enum PlayerColor {

	VIOLET, IVORY, CHOCOLATE, LIME, LIGHTBLUE, ORANGE, GREY, PURPLE, BROWN, TURQOISE, BEIJE, ACQUAMARINE, SILVER, GOLD, PLATINUM, DIAMOND, GREEN, YELLOW, RED, BLUE, BLACK, WHITE; // and
																																													// other
																																													// colors

	/**
	 * @return the string name of the enum value (lower case letters)
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

	/**
	 * This method is used to assign a color (chosen for each player) to the
	 * turn of the game.
	 * 
	 * @param turn
	 *            The turn in which to assign a color.
	 *            
	 * @return the color chosen by the player
	 */
	public static PlayerColor assignColorToTurn(int turn) {

		switch (turn) {
		
		//-1 is just in case of game for two players (to initialize the map)	
		case -1:
			return WHITE;
					
		//assign a color to a player of given turn 	
		case 0:
			return VIOLET;
		case 1:
			return IVORY;
		case 2:
			return CHOCOLATE;
		case 3:
			return LIME;
		case 4:
			return LIGHTBLUE;
		case 5:
			return ORANGE;
		case 6:
			return GREY;
		case 7:
			return PURPLE;
		case 8:
			return BROWN;
		case 9:
			return TURQOISE;
		case 10:
			return BEIJE;
		case 11:
			return ACQUAMARINE;
		case 12:
			return SILVER;
		case 13:
			return GOLD;
		case 14:
			return PLATINUM;
		case 15:
			return DIAMOND;
		case 16:
			return GREEN;
		case 17:
			return YELLOW;
		case 18:
			return RED;
		case 19:
			return BLUE;
		case 20:
			return BLACK;

		default:
			throw new IllegalArgumentException("i colori disponibili sono esauriti");
		}
	}

	/**
	 * This method is used to get the round of the game by color
	 * 
	 * @param color
	 *            The given color to know the round
	 * @return the round of the current game
	 */
	public static int getTurnByColor(PlayerColor color) {

		switch (color) {

		case WHITE:
			return -1;

		case VIOLET:
			return 0;
		case IVORY:
			return 1;
		case CHOCOLATE:
			return 2;
		case LIME:
			return 3;
		case LIGHTBLUE:
			return 4;
		case ORANGE:
			return 5;
		case GREY:
			return 6;
		case PURPLE:
			return 7;
		case BROWN:
			return 8;
		case TURQOISE:
			return 9;
		case BEIJE:
			return 10;
		case ACQUAMARINE:
			return 11;
		case SILVER:
			return 12;
		case GOLD:
			return 13;
		case PLATINUM:
			return 14;
		case DIAMOND:
			return 15;
		case GREEN:
			return 16;
		case YELLOW:
			return 17;
		case RED:
			return 18;
		case BLUE:
			return 19;
		case BLACK:
			return 20;

		default:
			throw new IllegalArgumentException("l'indice scelto non corrisponde a nessun colore");
		}
	}
}