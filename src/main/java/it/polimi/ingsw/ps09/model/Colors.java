package it.polimi.ingsw.ps09.model;

/**
 * This enum defines the color of a card.
 */
public enum Colors {

	BLUE, YELLOW, BLACK, JOLLY, GREEN, PINK, RED, NULL;

	/**
	 * @return the string name of the enum value (lower case letters)
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}

	/**
	 * Compares a given string with all the enum values and returns the matching
	 * one if exists. If it doesn't exists returns the value NULL, which is also
	 * an enum value.
	 * 
	 * @param text
	 *            The string to be analyzed.
	 *            
	 * @return enum value which matches the given string
	 */
	public static Colors fromString(String text) {
		for (Colors b : Colors.values()) {
			if (text.equals(b.toString()))
				return b;
		}
		return Colors.NULL;
	}
}