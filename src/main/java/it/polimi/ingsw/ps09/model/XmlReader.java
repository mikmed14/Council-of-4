package it.polimi.ingsw.ps09.model;

import java.io.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import it.polimi.ingsw.ps09.map.Constants;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The XmlReader class reads all the configurable parts of the map from 3
 * different configuration files. All data is saved locally (on this class'
 * attributes); these attributes are accessed by the model through the getters
 * methods. The model has to take the data with the getters and divide
 * everything among the 3 regions and the cities to initialize the map To
 * perform the reading from the files we used the JDOM Libraries, for the XML
 * files.
 */
public class XmlReader {


	/**
	 * Logger used to throw exceptions
	 */
	static Logger logger = Logger.getLogger(XmlReader.class.getName());

	/**
	 * These arrays are used to save all the links among the cities (links), the
	 * region rewards, (regionRewards) the colors, the rewards and the names of
	 * the cities belonging to all regions (colors, cityRewards, names), read
	 * from the first configuration file.
	 */
	private String[] links;
	private String[] names;
	private Reward[] regionRewards;
	private CityColor[] colors;
	private Reward[] cityRewards;
	private char kingCity;

	/**
	 * This attribute is used to save a list of all the permit cards (belonging
	 * to every deck of every region).
	 */
	private ArrayList<PermitCard> permits;

	/**
	 * This attribute is used to save a list of all the nobility track rewards.
	 */
	private Reward[] nobility;

	
	
	

	/**
	 * This method reads the first configuration file. This file contains the
	 * rewards of regions and cities, the colors of the cities and the
	 * connections among the cities
	 * 
	 * IMPORTANT: a bonus consists of an integer (which contains the quantity)
	 * and an enum (which contains the type of the bonus itself). Bonuses are
	 * stacked into rewards, which are ArrayList of bonuses. This means that a
	 * reward can contain any number of bonuses For each reward of each item
	 * (permit card, region, city..) an arbitrary number of bonuses can be read,
	 * but in the file, their actual number has to be specified as an attribute
	 * for each item.
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	public void readFile1(int map) throws JDOMException {

		try {

			// Creates a SAXBuilder and builds a document with it
			SAXBuilder builder = new SAXBuilder();
			File file1 = new File("map"+map+"/config1.xml");
			
			
			Document document = (Document) builder.build(file1);

			// gets the root from the document
			Element root = document.getRootElement();

			// extracts the children elements (corresponding to the regions)
			// from the document and saves them in a List
			List<Element> regions = root.getChildren();

			// creates the variables that will contain all the information
			names = new String[Constants.NUMBEROFCITIES];
			links = new String[Constants.NUMBEROFCITIES];
			regionRewards = new Reward[Constants.NUMOFREGIONS];
			cityRewards = new Reward[Constants.NUMBEROFCITIES];
			colors = new CityColor[Constants.NUMBEROFCITIES];

			// for each child (region)
			for (int i = 0; i < regions.size(); i++) {

				// saves the region element in a local variable
				Element region = (Element) regions.get(i);
				// extracts the children (cities belonging to the selected
				// region) and saves them into a new List
				List<Element> cities = region.getChildren();

				// reads the number of region bonuses
				int numberOfRegionBonuses = Integer.parseInt(region.getAttributeValue("numberOfBonuses"));
				regionRewards[i] = new Reward();
				// FOR EACH BONUS
				for (int k = 0; k < numberOfRegionBonuses; k++) {

					// saves the type of the region bonus and its quantity into
					// auxiliary variables
					BonusType type = BonusType.fromString(region.getAttributeValue("bonus" + Integer.toString(k + 1)));

					int quantity = Integer.parseInt(region.getAttributeValue("quantity" + Integer.toString(k + 1)));

					// checks if the bonus read is valid and, in that case,
					// saves it into a new reward (ArraylList of Bonuses)
					if ((type != BonusType.NULL) && (quantity > 0)) {

						regionRewards[i].addBonus(type, quantity);
					}

				}

				// FOR EACH CITY

				for (int j = 0; j < cities.size(); j++) {
					int n = 5 * i;
					// saves the city element into an auxiliary variable
					Element city = (Element) cities.get(j);

					// extracts the color and the links of the selected city and
					// saves them into the arrays (global attributes)
					names[j + n] = city.getAttributeValue("name");
					colors[j + n] = CityColor.fromString(city.getAttributeValue("color"));
					links[j + n] = city.getAttributeValue("links");
					cityRewards[j + n] = new Reward();

					int king = Integer.parseInt(city.getAttributeValue("king"));
					if (king == 1)
						kingCity = city.getAttributeValue("name").charAt(0);

					int numberOfCityBonuses = Integer.parseInt(city.getAttributeValue("numberOfBonuses"));

					for (int k = 0; k < numberOfCityBonuses; k++) {
						// reads the city bonuses types and quantities and saves
						// them into local variables
						BonusType type = BonusType
								.fromString(city.getAttributeValue("bonus" + Integer.toString(k + 1)));
						int quantity = Integer.parseInt(city.getAttributeValue("quantity" + Integer.toString(k + 1)));

						// checks if the values are valid, and in that case adds
						// the bonuses into the right position of the array of
						// city rewards
						// (global variable)

						if ((type != BonusType.NULL) && (quantity > 0))
							cityRewards[j + n].addBonus(type, quantity);

					}

				} // END OF CITIES FOR
			} // END OF REGIONS FOR
		} catch (IOException e) {

			logger.log(Level.SEVERE, "errore durante la lettura del file", e);

		}
	}

	/**
	 * This method reads the second configuration file, which contains the
	 * information about the permit cards. All the cards are saved into the same
	 * arrayList. Each permit card contains a reward and a string of the initial
	 * letters of the names of the cities you can build an emporium into with
	 * that specific card
	 * 
	 * The basic steps are the same for all the configuration files, the only
	 * thing that changes is where the attributes of the elements of the files
	 * are saved (that's why the above lines are uncommented)
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	public void readFile2(int map) throws JDOMException {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new File("map"+map+"/config2.xml"));
			Element root = document.getRootElement();
			// takes children from the root
			List<Element> cards = root.getChildren();
			permits = new ArrayList<PermitCard>();

			Reward reward;

			String cities;

			// FOR EACH CARD
			for (int i = 0; i < cards.size(); i++) {
				Element card = (Element) cards.get(i);

				int numberOfCardBonuses = Integer.parseInt(card.getAttributeValue("numberOfBonuses"));
				cities = card.getAttributeValue("cities");
				reward = new Reward();

				for (int k = 0; k < numberOfCardBonuses; k++) {

					BonusType type = BonusType.fromString(card.getAttributeValue("type" + Integer.toString(k + 1)));
					int quantity = Integer.parseInt(card.getAttributeValue("quantity" + Integer.toString(k + 1)));

					if ((type != BonusType.NULL) && (quantity > 0)) {

						reward.addBonus(type, quantity);
					}

					// creates a local variable of type PermitCard and saves the
					// reward and the cities into it.
					// adds the new card to the global ArrayList of permit
					// cards.

				}

				this.permits.add(new PermitCard(reward, cities));

			} // END OF FOR

		} catch (IOException e) {
			logger.log(Level.SEVERE, "errore durante la lettura del file", e);
		}

	}

	/**
	 * This method reads from the third configuration file the information about
	 * the nobility track, which basically is an Array of rewards of fixed
	 * length. This array is saved as global attribute and can be accessed (as
	 * all the other variables) by the model.
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	public void readFile3(int map) throws JDOMException {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(new File("map"+map+"/config3.xml"));
			Element root = document.getRootElement();
			// takes children from the root
			List<Element> steps = root.getChildren();
			nobility = new Reward[Constants.NOBILITYTRACK];

			// FOR EACH STEP IN THE NOBILITY TRACK
			for (int i = 0; i < Constants.NOBILITYTRACK; i++) {

				Reward nobilityReward = new Reward();

				Element step = (Element) steps.get(i);
				int numberOfStepBonuses = Integer.parseInt(step.getAttributeValue("numberOfBonuses"));

				for (int k = 0; k < numberOfStepBonuses; k++) {

					BonusType type1 = BonusType.fromString(step.getAttributeValue("type" + Integer.toString(k + 1)));
					int quantity1 = Integer.parseInt(step.getAttributeValue("quantity" + Integer.toString(k + 1)));

					if ((type1 != BonusType.NULL) && (quantity1 > 0))
						nobilityReward.addBonus(type1, quantity1);

				}
				this.nobility[i] = nobilityReward;

			} // END OF FOR

		} catch (Exception e) {
			logger.log(Level.SEVERE, "errore durante la lettura del file", e);
		}

	}

	/**
	 * @return the links among the cities
	 */
	public String[] getLinks() {
		return this.links;
	}

	/**
	 * @return the names of the cities
	 */
	public String[] getNames() {
		return this.names;
	}

	/**
	 * @return the reward tokens of the regions
	 */
	public Reward[] getRegionRewards() {
		return this.regionRewards;
	}

	/**
	 * @return the color of the cities
	 */
	public CityColor[] getCityColors() {
		return this.colors;
	}

	/**
	 * @return the reward tokens of the cities
	 */
	public Reward[] getCityRewards() {
		return this.cityRewards;
	}

	/**
	 * @return the business permit tiles
	 */
	public ArrayList<PermitCard> getPermitCards() {
		return this.permits;
	}

	/**
	 * @return the nobility track
	 */
	public Reward[] getNobilityTrack() {
		return this.nobility;
	}



	/**
	 * @return the king of the game
	 */
	public char getKing() {
		return this.kingCity;
	}
}