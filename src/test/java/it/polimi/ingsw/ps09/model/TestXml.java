package it.polimi.ingsw.ps09.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.jdom2.JDOMException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.map.Constants;

/**
 * Test loading files
 */
public class TestXml {

	int n = 1;

	/**
	 * The Xml reader
	 */
	XmlReader reader = new XmlReader();

	/**
	 * Links of cities
	 */
	String[] cityLinks = new String[Constants.NUMBEROFCITIES];

	/**
	 * Names of cities
	 */
	String[] cityNames = new String[Constants.NUMBEROFCITIES];

	/**
	 * Colors of cities
	 */
	CityColor[] colors = new CityColor[Constants.NUMBEROFCITIES];

	/**
	 * Reward tokens of regions
	 */
	Reward[] regionRewards = new Reward[Constants.NUMOFREGIONS];

	/**
	 * Reward tokens of cities
	 */
	Reward[] cityRewards = new Reward[Constants.NUMBEROFCITIES];

	/**
	 * Business permit tiles
	 */
	ArrayList<PermitCard> permits = new ArrayList<PermitCard>();

	/**
	 * Colors of reward tokens
	 */
	Reward[] colorRewards = new Reward[Constants.NUMBEROFCITIESPERREGION - 1];

	/**
	 * Reward tokens of the king
	 */
	Reward[] kingRewards = new Reward[Constants.NUMBEROFCITIESPERREGION];

	/**
	 * Reward tokens that are in nobility track
	 */
	Reward[] nobilityTrack = new Reward[21];

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// cityLinks
		cityLinks[0] = "BC";
		cityLinks[1] = "ADE";
		cityLinks[2] = "AF";
		cityLinks[3] = "BG";
		cityLinks[4] = "BH";
		cityLinks[5] = "CI";
		cityLinks[6] = "DJ";
		cityLinks[7] = "EJM";
		cityLinks[8] = "FJK";
		cityLinks[9] = "GHIL";
		cityLinks[10] = "IN";
		cityLinks[11] = "JO";
		cityLinks[12] = "HO";
		cityLinks[13] = "KO";
		cityLinks[14] = "NLM";

		// cityNames
		cityNames[0] = "AtlanticCity";
		cityNames[1] = "Babilon";
		cityNames[2] = "Cologno";
		cityNames[3] = "Detroit";
		cityNames[4] = "Echo";
		cityNames[5] = "FarAway";
		cityNames[6] = "GhostCity";
		cityNames[7] = "HighandDry";
		cityNames[8] = "Imagination";
		cityNames[9] = "JavaCity";
		cityNames[10] = "Korine";
		cityNames[11] = "LoveandCry";
		cityNames[12] = "Mercury";
		cityNames[13] = "Nocturne";
		cityNames[14] = "Obsidian";

		// cityColors
		colors[0] = CityColor.GOLD;
		colors[1] = CityColor.BRONZE;
		colors[2] = CityColor.SILVER;
		colors[3] = CityColor.IRON;
		colors[4] = CityColor.SILVER;
		colors[5] = CityColor.GOLD;
		colors[6] = CityColor.SILVER;
		colors[7] = CityColor.GOLD;
		colors[8] = CityColor.BRONZE;
		colors[9] = CityColor.NULL;
		colors[10] = CityColor.GOLD;
		colors[11] = CityColor.IRON;
		colors[12] = CityColor.SILVER;
		colors[13] = CityColor.BRONZE;
		colors[14] = CityColor.GOLD;

		// cityRewards
		cityRewards[0] = new Reward();
		cityRewards[1] = new Reward();
		cityRewards[2] = new Reward();
		cityRewards[3] = new Reward();
		cityRewards[4] = new Reward();
		cityRewards[5] = new Reward();
		cityRewards[6] = new Reward();
		cityRewards[7] = new Reward();
		cityRewards[8] = new Reward();
		cityRewards[9] = new Reward();
		cityRewards[10] = new Reward();
		cityRewards[11] = new Reward();
		cityRewards[12] = new Reward();
		cityRewards[13] = new Reward();
		cityRewards[14] = new Reward();

		cityRewards[0].addBonus(BonusType.VICTORY, 3);
		cityRewards[1].addBonus(BonusType.COIN, 1);
		cityRewards[2].addBonus(BonusType.POLITIC, 1);
		cityRewards[3].addBonus(BonusType.ASSISTANT, 2);
		cityRewards[4].addBonus(BonusType.NOBILITY, 1);

		cityRewards[5].addBonus(BonusType.VICTORY, 1);
		cityRewards[5].addBonus(BonusType.POLITIC, 1);
		cityRewards[6].addBonus(BonusType.NOBILITY, 1);
		cityRewards[7].addBonus(BonusType.COIN, 1);
		cityRewards[8].addBonus(BonusType.VICTORY, 2);

		cityRewards[10].addBonus(BonusType.VICTORY, 1);
		cityRewards[11].addBonus(BonusType.COIN, 1);
		cityRewards[12].addBonus(BonusType.ASSISTANT, 1);
		cityRewards[12].addBonus(BonusType.COIN, 1);
		cityRewards[13].addBonus(BonusType.ASSISTANT, 1);
		cityRewards[14].addBonus(BonusType.ASSISTANT, 1);
		cityRewards[14].addBonus(BonusType.POLITIC, 1);

		// regionRewards
		regionRewards[0] = new Reward();
		regionRewards[1] = new Reward();
		regionRewards[2] = new Reward();
		regionRewards[0].addBonus(BonusType.VICTORY, 5);
		regionRewards[1].addBonus(BonusType.VICTORY, 5);
		regionRewards[2].addBonus(BonusType.VICTORY, 5);

		Reward reward1 = new Reward();
		reward1.addBonus(BonusType.VICTORY, 4);
		reward1.addBonus(BonusType.COIN, 3);

		Reward reward2 = new Reward();
		reward2.addBonus(BonusType.POLITIC, 1);
		reward2.addBonus(BonusType.COIN, 3);

		Reward reward3 = new Reward();
		reward3.addBonus(BonusType.COIN, 2);
		reward3.addBonus(BonusType.VICTORY, 1);

		Reward reward4 = new Reward();
		reward4.addBonus(BonusType.ASSISTANT, 1);
		reward4.addBonus(BonusType.VICTORY, 1);

		Reward reward5 = new Reward();
		reward5.addBonus(BonusType.NOBILITY, 2);

		Reward reward6 = new Reward();
		reward6.addBonus(BonusType.VICTORY, 3);

		Reward reward7 = new Reward();
		reward7.addBonus(BonusType.POLITIC, 1);
		reward7.addBonus(BonusType.COIN, 1);

		Reward reward8 = new Reward();
		reward8.addBonus(BonusType.NOBILITY, 1);
		reward8.addBonus(BonusType.COIN, 1);

		Reward reward9 = new Reward();
		reward9.addBonus(BonusType.POLITIC, 1);
		reward9.addBonus(BonusType.ASSISTANT, 1);

		Reward reward10 = new Reward();
		reward10.addBonus(BonusType.ASSISTANT, 2);

		Reward reward11 = new Reward();
		reward11.addBonus(BonusType.COIN, 3);

		Reward reward12 = new Reward();
		reward12.addBonus(BonusType.POLITIC, 1);
		reward12.addBonus(BonusType.VICTORY, 1);

		Reward reward13 = new Reward();
		reward13.addBonus(BonusType.NOBILITY, 1);
		reward13.addBonus(BonusType.VICTORY, 1);

		Reward reward14 = new Reward();
		reward14.addBonus(BonusType.POLITIC, 2);

		Reward reward15 = new Reward();
		reward15.addBonus(BonusType.ASSISTANT, 1);
		reward15.addBonus(BonusType.NOBILITY, 1);

		Reward reward16 = new Reward();
		reward16.addBonus(BonusType.POLITIC, 3);
		reward16.addBonus(BonusType.ASSISTANT, 1);

		Reward reward17 = new Reward();
		reward17.addBonus(BonusType.VICTORY, 3);
		reward17.addBonus(BonusType.COIN, 3);

		Reward reward18 = new Reward();
		reward18.addBonus(BonusType.COIN, 1);
		reward18.addBonus(BonusType.VICTORY, 2);

		Reward reward19 = new Reward();
		reward19.addBonus(BonusType.POLITIC, 1);
		reward19.addBonus(BonusType.NOBILITY, 1);

		Reward reward20 = new Reward();
		reward20.addBonus(BonusType.ASSISTANT, 1);
		reward20.addBonus(BonusType.COIN, 1);

		Reward reward21 = new Reward();
		reward21.addBonus(BonusType.ASSISTANT, 2);
		reward21.addBonus(BonusType.COIN, 3);

		Reward reward22 = new Reward();
		reward22.addBonus(BonusType.VICTORY, 3);
		reward22.addBonus(BonusType.ASSISTANT, 1);

		Reward reward23 = new Reward();
		reward23.addBonus(BonusType.VICTORY, 7);

		Reward reward24 = new Reward();
		reward24.addBonus(BonusType.VICTORY, 3);
		reward24.addBonus(BonusType.ASSISTANT, 1);

		Reward reward25 = new Reward();
		reward25.addBonus(BonusType.ACTION, 1);
		reward25.addBonus(BonusType.COIN, 2);

		Reward reward26 = new Reward();
		reward26.addBonus(BonusType.POLITIC, 2);
		reward26.addBonus(BonusType.COIN, 4);

		Reward reward27 = new Reward();
		reward27.addBonus(BonusType.COIN, 5);

		Reward reward28 = new Reward();
		reward28.addBonus(BonusType.ASSISTANT, 2);
		reward28.addBonus(BonusType.COIN, 1);

		Reward reward29 = new Reward();
		reward29.addBonus(BonusType.ASSISTANT, 2);
		reward29.addBonus(BonusType.VICTORY, 2);
		reward29.addBonus(BonusType.NOBILITY, 1);

		Reward reward30 = new Reward();
		reward30.addBonus(BonusType.ASSISTANT, 3);

		Reward reward31 = new Reward();
		reward31.addBonus(BonusType.ASSISTANT, 4);

		Reward reward32 = new Reward();
		reward32.addBonus(BonusType.VICTORY, 5);

		Reward reward33 = new Reward();
		reward33.addBonus(BonusType.POLITIC, 2);
		reward33.addBonus(BonusType.ASSISTANT, 2);

		Reward reward34 = new Reward();
		reward34.addBonus(BonusType.POLITIC, 2);
		reward34.addBonus(BonusType.NOBILITY, 1);

		Reward reward35 = new Reward();
		reward35.addBonus(BonusType.POLITIC, 4);

		Reward reward36 = new Reward();
		reward36.addBonus(BonusType.POLITIC, 1);
		reward36.addBonus(BonusType.ASSISTANT, 2);

		Reward reward37 = new Reward();
		reward37.addBonus(BonusType.POLITIC, 2);
		reward37.addBonus(BonusType.NOBILITY, 1);

		Reward reward38 = new Reward();
		reward38.addBonus(BonusType.COIN, 1);
		reward38.addBonus(BonusType.ASSISTANT, 3);

		Reward reward39 = new Reward();
		reward39.addBonus(BonusType.POLITIC, 3);

		Reward reward40 = new Reward();
		reward40.addBonus(BonusType.VICTORY, 5);
		reward40.addBonus(BonusType.NOBILITY, 1);

		Reward reward41 = new Reward();
		reward41.addBonus(BonusType.ACTION, 1);

		Reward reward42 = new Reward();
		reward42.addBonus(BonusType.COIN, 7);

		Reward reward43 = new Reward();
		reward43.addBonus(BonusType.VICTORY, 2);
		reward43.addBonus(BonusType.POLITIC, 2);

		Reward reward44 = new Reward();
		reward44.addBonus(BonusType.POLITIC, 3);
		reward44.addBonus(BonusType.NOBILITY, 1);

		Reward reward45 = new Reward();
		reward45.addBonus(BonusType.POLITIC, 3);
		reward45.addBonus(BonusType.VICTORY, 1);

		permits.add(new PermitCard(reward1, "A"));
		permits.add(new PermitCard(reward2, "AB"));
		permits.add(new PermitCard(reward3, "FGJ"));
		permits.add(new PermitCard(reward4, "BCD"));
		permits.add(new PermitCard(reward5, "AE"));
		permits.add(new PermitCard(reward6, "LMN"));
		permits.add(new PermitCard(reward7, "KLO"));
		permits.add(new PermitCard(reward8, "FGH"));
		permits.add(new PermitCard(reward9, "GHI"));
		permits.add(new PermitCard(reward10, "ABC"));

		permits.add(new PermitCard(reward11, "ABE"));
		permits.add(new PermitCard(reward12, "KNO"));
		permits.add(new PermitCard(reward13, "MNO"));
		permits.add(new PermitCard(reward14, "IJH"));
		permits.add(new PermitCard(reward15, "FIJ"));
		permits.add(new PermitCard(reward16, "B"));
		permits.add(new PermitCard(reward17, "BC"));
		permits.add(new PermitCard(reward18, "ADE"));
		permits.add(new PermitCard(reward19, "CDE"));
		permits.add(new PermitCard(reward20, "KLM"));

		permits.add(new PermitCard(reward21, "C"));
		permits.add(new PermitCard(reward22, "CD"));
		permits.add(new PermitCard(reward23, "D"));
		permits.add(new PermitCard(reward24, "DE"));
		permits.add(new PermitCard(reward25, "E"));
		permits.add(new PermitCard(reward26, "F"));
		permits.add(new PermitCard(reward27, "FG"));
		permits.add(new PermitCard(reward28, "FJ"));
		permits.add(new PermitCard(reward29, "G"));
		permits.add(new PermitCard(reward30, "GH"));

		permits.add(new PermitCard(reward31, "H"));
		permits.add(new PermitCard(reward32, "HI"));
		permits.add(new PermitCard(reward33, "I"));
		permits.add(new PermitCard(reward34, "IJ"));
		permits.add(new PermitCard(reward35, "K"));
		permits.add(new PermitCard(reward36, "KL"));
		permits.add(new PermitCard(reward37, "KO"));
		permits.add(new PermitCard(reward38, "L"));
		permits.add(new PermitCard(reward39, "LM"));
		permits.add(new PermitCard(reward40, "M"));

		permits.add(new PermitCard(reward41, "MN"));
		permits.add(new PermitCard(reward42, "N"));
		permits.add(new PermitCard(reward43, "NO"));
		permits.add(new PermitCard(reward44, "O"));
		permits.add(new PermitCard(reward45, "CD"));

		// nobilityTrack
		nobilityTrack[0] = new Reward();

		nobilityTrack[1] = new Reward();

		nobilityTrack[2] = new Reward();
		nobilityTrack[2].addBonus(BonusType.VICTORY, 2);
		nobilityTrack[2].addBonus(BonusType.COIN, 2);

		nobilityTrack[3] = new Reward();

		nobilityTrack[4] = new Reward();
		nobilityTrack[4].addBonus(BonusType.CITYREWARD, 1);

		nobilityTrack[5] = new Reward();

		nobilityTrack[6] = new Reward();
		nobilityTrack[6].addBonus(BonusType.ACTION, 1);

		nobilityTrack[7] = new Reward();

		nobilityTrack[8] = new Reward();
		nobilityTrack[8].addBonus(BonusType.VICTORY, 3);
		nobilityTrack[8].addBonus(BonusType.POLITIC, 1);

		nobilityTrack[9] = new Reward();

		nobilityTrack[10] = new Reward();
		nobilityTrack[10].addBonus(BonusType.PERMIT, 1);

		nobilityTrack[11] = new Reward();

		nobilityTrack[12] = new Reward();
		nobilityTrack[12].addBonus(BonusType.VICTORY, 5);
		nobilityTrack[12].addBonus(BonusType.ASSISTANT, 1);

		nobilityTrack[13] = new Reward();

		nobilityTrack[14] = new Reward();
		nobilityTrack[14].addBonus(BonusType.MYPERMIT, 1);

		nobilityTrack[15] = new Reward();

		nobilityTrack[16] = new Reward();
		nobilityTrack[16].addBonus(BonusType.CITYREWARD, 2);

		nobilityTrack[17] = new Reward();

		nobilityTrack[18] = new Reward();
		nobilityTrack[18].addBonus(BonusType.VICTORY, 8);

		nobilityTrack[19] = new Reward();
		nobilityTrack[19].addBonus(BonusType.VICTORY, 2);

		nobilityTrack[20] = new Reward();
		nobilityTrack[20].addBonus(BonusType.VICTORY, 3);

		colorRewards[0] = new Reward();
		colorRewards[0].addBonus(BonusType.VICTORY, 20);

		colorRewards[1] = new Reward();
		colorRewards[1].addBonus(BonusType.VICTORY, 12);

		colorRewards[2] = new Reward();
		colorRewards[2].addBonus(BonusType.VICTORY, 8);

		colorRewards[3] = new Reward();
		colorRewards[3].addBonus(BonusType.VICTORY, 5);

		kingRewards[0] = new Reward();
		kingRewards[0].addBonus(BonusType.VICTORY, 25);

		kingRewards[1] = new Reward();
		kingRewards[1].addBonus(BonusType.VICTORY, 18);

		kingRewards[2] = new Reward();
		kingRewards[2].addBonus(BonusType.VICTORY, 12);

		kingRewards[3] = new Reward();
		kingRewards[3].addBonus(BonusType.VICTORY, 7);

		kingRewards[4] = new Reward();
		kingRewards[4].addBonus(BonusType.VICTORY, 3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.model.XmlReader#readFile1(int map)}
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	@Test

	public void testReadFile1() throws JDOMException {
		

		reader.readFile1(n);

		assertArrayEquals(reader.getNames(), cityNames);
		assertArrayEquals(reader.getLinks(), cityLinks);
		assertArrayEquals(reader.getCityColors(), colors);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < reader.getRegionRewards()[i].getBonuses().size(); j++) {
				assertEquals(reader.getRegionRewards()[i].getBonusByIndex(j).getQuantity(),
						regionRewards[i].getBonusByIndex(j).getQuantity());
				assertEquals(reader.getRegionRewards()[i].getBonusByIndex(j).getType(),
						regionRewards[i].getBonusByIndex(j).getType());
			}
		}

		for (int k = 0; k < 15; k++) {
			for (int m = 0; m < reader.getCityRewards()[k].getBonuses().size(); m++) {
				assertEquals(reader.getCityRewards()[k].getBonusByIndex(m).getQuantity(),
						cityRewards[k].getBonusByIndex(m).getQuantity());
				assertEquals(reader.getCityRewards()[k].getBonusByIndex(m).getType(),
						cityRewards[k].getBonusByIndex(m).getType());
			}
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.model.XmlReader#readFile2(int map)}
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	@Test

	public void testReadFile2() throws JDOMException {
		

		reader.readFile2(n);

		for (int i = 0; i < reader.getPermitCards().size(); i++) {

			assertEquals(reader.getPermitCards().get(i).getCities(), permits.get(i).getCities());

			for (int j = 0; j < reader.getPermitCards().get(i).getReward().getBonuses().size(); j++) {
				assertEquals(reader.getPermitCards().get(i).getReward().getBonusByIndex(j).getQuantity(),
						permits.get(i).getReward().getBonusByIndex(j).getQuantity());
				assertEquals(reader.getPermitCards().get(i).getReward().getBonusByIndex(j).getType(),
						permits.get(i).getReward().getBonusByIndex(j).getType());
			}
		}
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.model.XmlReader#readFile3(int map)}
	 * 
	 * @throws org.jdom2.JDOMException
	 */
	@Test

	public void testReadFile3() throws JDOMException {
		

		reader.readFile3(n);

		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < reader.getNobilityTrack()[i].getBonuses().size(); j++) {
				assertEquals(reader.getNobilityTrack()[i].getBonusByIndex(j).getQuantity(),
						nobilityTrack[i].getBonusByIndex(j).getQuantity());
				assertEquals(reader.getNobilityTrack()[i].getBonusByIndex(j).getType(),
						nobilityTrack[i].getBonusByIndex(j).getType());
			}
		}
	}
}