package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.AcquirePermit;
import it.polimi.ingsw.ps09.model.BonusType;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.RegionTypes;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.AcquirePermit}
 */
public class TestAcquirePermit {

	

	Model model;
	AcquirePermit action;

	PermitCard permit;

	@Before
	public void setUp1() throws IOException, JDOMException {

		
		
		ArrayList<Colors> colors=new ArrayList<Colors>();
		
		
		
		
		
		
		
		model=new Model(2, 1);
		
	
		PermitCard card;
		Reward reward = new Reward();

		reward.addBonus(BonusType.ASSISTANT, 5);
		reward.addBonus(BonusType.COIN, 3);
		reward.addBonus(BonusType.POLITIC, 7);
		reward.addBonus(BonusType.VICTORY, 10);
		card = new PermitCard(reward, "ABC");

		model.getRegion(RegionTypes.COAST).setPermitCard1(card);

	
		colors.add(Colors.BLACK);
		colors.add(Colors.BLUE);
		colors.add(Colors.YELLOW);
		colors.add(Colors.GREEN);

		model.getPlayer(0).getCardByIndex(0).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.YELLOW);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.BLUE);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(4).setColor(Colors.RED);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);

		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].setColor(Colors.YELLOW);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[1].setColor(Colors.BLACK);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[2].setColor(Colors.GREEN);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[3].setColor(Colors.BLUE);

		action = new AcquirePermit(model, 0, RegionTypes.COAST, 1, colors);

	}

	@Before
	public void setUp2() throws IOException, JDOMException {
		PermitCard card;
		Reward reward = new Reward();

		reward.addBonus(BonusType.ASSISTANT, 5);
		reward.addBonus(BonusType.COIN, 3);
		reward.addBonus(BonusType.POLITIC, 7);
		reward.addBonus(BonusType.VICTORY, 10);
		card = new PermitCard(reward, "ABC");

		ArrayList<Colors> colors = new ArrayList<Colors>();

		colors.add(Colors.JOLLY);
		colors.add(Colors.BLACK);
		colors.add(Colors.GREEN);

		
		model=new Model(2, 1);
	
		
		

		model.getPlayer(0).getCardByIndex(0).setColor(Colors.JOLLY);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(4).setColor(Colors.RED);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);

		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].setColor(Colors.BLACK);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[1].setColor(Colors.BLACK);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[2].setColor(Colors.GREEN);

		model.getRegion(RegionTypes.COAST).setPermitCard1(card);

		System.out.println(model.getPlayer(0).getCards().size() + " carte in mano:");
		for (int i = 0; i < model.getPlayer(0).getCards().size(); i++) {
			System.out.println("carta " + i + "= " + model.getPlayer(0).getCardByIndex(i).getColor().toString());
		}

		action = new AcquirePermit(model, 0, RegionTypes.COAST, 1, colors);
	}

	@Before
	public void setUp3() throws IOException, JDOMException {
		ArrayList<Colors> cards = new ArrayList<Colors>();

		PermitCard card;
		Reward reward = new Reward();

		reward.addBonus(BonusType.ASSISTANT, 5);
		reward.addBonus(BonusType.COIN, 3);
		reward.addBonus(BonusType.POLITIC, 7);
		reward.addBonus(BonusType.VICTORY, 10);

		card=new PermitCard(reward, "ABC");
		
		
		model=new Model(2, 1);
		

		model.getRegion(RegionTypes.COAST).setPermitCard1(card);

		cards.add(Colors.BLACK);
		cards.add(Colors.BLACK);
		cards.add(Colors.GREEN);
		cards.add(Colors.GREEN);

		model.getPlayer(0).getCardByIndex(0).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.GREEN);

		model.getPlayer(0).getCardByIndex(4).setColor(Colors.RED);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);

		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].setColor(Colors.GREEN);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[1].setColor(Colors.GREEN);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[2].setColor(Colors.BLACK);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[3].setColor(Colors.BLACK);

		action = new AcquirePermit(model, 0, RegionTypes.COAST, 1, cards);
	}

	@Before
	public void setUp4() throws IOException, JDOMException {

		ArrayList<Colors> colors=new ArrayList<Colors>();
		model=new Model(2, 1);
		

		PermitCard card;
		Reward reward = new Reward();

		reward.addBonus(BonusType.ASSISTANT, 5);
		reward.addBonus(BonusType.COIN, 3);
		reward.addBonus(BonusType.POLITIC, 7);
		reward.addBonus(BonusType.VICTORY, 10);
		card = new PermitCard(reward, "ABC");

		colors.add(Colors.JOLLY);
		colors.add(Colors.GREEN);
		colors.add(Colors.GREEN);

		model.getPlayer(0).getCardByIndex(0).setColor(Colors.JOLLY);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(4).setColor(Colors.RED);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);

		model.getRegion(RegionTypes.COAST).setPermitCard1(card);

		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].setColor(Colors.BLACK);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[1].setColor(Colors.GREEN);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[2].setColor(Colors.YELLOW);
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[3].setColor(Colors.GREEN);

		
		action=new AcquirePermit(model, 0, RegionTypes.COAST, 1, colors);
	}



	@After
	public void tearDown() {
		model = null;
		action = null;

	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.actions.AcquirePermit#isValid()}
	 * 
	 * @throws java.io.IOException
	 * @throws org.jdom2.JDOMException
	 */
	@Test
	public void testIsValid() throws IOException, JDOMException {
		setUp1();

		assertEquals(action.isValid(), true);

		tearDown();
		setUp1();
		assertNotEquals(action.isValid(), false);

		tearDown();

		setUp2();
		assertEquals(action.isValid(), true);

		tearDown();
		setUp2();
		assertNotEquals(action.isValid(), false);

		tearDown();

		setUp3();
		assertEquals(action.isValid(), true);

		tearDown();
		setUp3();
		assertNotEquals(action.isValid(), false);

		tearDown();

		setUp4();
		assertEquals(action.isValid(), true);

		tearDown();
		setUp4();
		assertNotEquals(action.isValid(), false);

		tearDown();
		/**
		 * setUp5(); assertEquals(action.isValid(), true); tearDown();
		 **/
	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.actions.AcquirePermit#execute()}
	 * 
	 * @throws java.io.IOException
	 * @throws org.jdom2.JDOMException
	 */
	@Test
	public void testExecute() throws IOException, JDOMException {
		tearDown();
		Model newModel;

		setUp1();
		newModel = action.execute();

		assertEquals(newModel.getPlayer(0).getPermitUpByIndex(0).getCities(), "ABC");

		assertEquals(BonusType.ASSISTANT,
				newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(0).getType());
		assertEquals(5, newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(0).getQuantity());

		assertEquals(BonusType.COIN,
				newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(1).getType());
		assertEquals(3, newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(1).getQuantity());

		assertEquals(BonusType.POLITIC,
				newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(2).getType());
		assertEquals(7, newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(2).getQuantity());

		assertEquals(BonusType.VICTORY,
				newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(3).getType());
		assertEquals(10, newModel.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(3).getQuantity());

		tearDown();

	}

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.actions.AcquirePermit#applyBonus()}
	 * 
	 * @throws java.io.IOException
	 * @throws org.jdom2.JDOMException
	 */
	@Test
	public void testApplyBonus() throws IOException, JDOMException {
		Model newModel;

		setUp1();
		newModel = action.execute();

		assertEquals(newModel.getPlayer(0).getAssistants(), 6);
		assertEquals(newModel.getPlayer(0).getCards().size(), 9);
		assertEquals(newModel.getPlayer(0).getCoins(), 13);
		assertEquals(newModel.getPlayer(0).getPoints(), 10);

		tearDown();

	}

	@Test
	public void testComputeNumberOfCoins() throws IOException, JDOMException {

		setUp1();

		assertEquals(action.computeNumberOfCoins(), 0);
		tearDown();

		setUp1();

		assertNotEquals(action.computeNumberOfCoins(), 2);
		tearDown();

		setUp2();

		assertEquals(action.computeNumberOfCoins(), 5);
		tearDown();
		setUp2();

		assertNotEquals(action.computeNumberOfCoins(), 2);

		tearDown();

		setUp3();

		assertEquals(action.computeNumberOfCoins(), 0);
		tearDown();
		setUp3();

		assertNotEquals(action.computeNumberOfCoins(), 2);

		tearDown();

		setUp4();

		assertEquals(action.computeNumberOfCoins(), 5);
		tearDown();
		setUp4();

		assertNotEquals(action.computeNumberOfCoins(), 0);

		tearDown();

	}

	@Test
	public void testCountNumberOfJollys() throws IOException, JDOMException {
		setUp1();
		assertEquals(action.countNumberOfJollys(), 0);

		tearDown();

	}

	@Test
	public void testCountCardsColors() throws IOException, JDOMException {

		setUp1();

		List<Colors> colors = new ArrayList<Colors>();
		colors.add(Colors.BLACK);
		colors.add(Colors.BLACK);
		colors.add(Colors.BLACK);
		colors.add(Colors.BLACK);
		colors.add(Colors.BLUE);
		colors.add(Colors.BLUE);

		Integer[] array = new Integer[6];

		array[0] = 2;
		array[1] = 0;
		array[2] = 4;
		array[3] = 0;
		array[4] = 0;
		array[5] = 0;

		assertArrayEquals(action.countCardsColors(colors), array);
	}
}