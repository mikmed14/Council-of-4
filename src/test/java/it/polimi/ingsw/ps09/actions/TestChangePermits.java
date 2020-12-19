package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.ChangePermits;
import it.polimi.ingsw.ps09.model.BonusType;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.ChangePermits}
 */
public class TestChangePermits {
	
	
	
	private Model model;
	private  Action action;

	@Before
	public void setUp() throws Exception {
		model=new Model(2, 1);
		
		Reward reward1= new Reward();
		reward1.addBonus(BonusType.ASSISTANT, 5);
		reward1.addBonus(BonusType.COIN, 4);
		reward1.addBonus(BonusType.VICTORY,3);
		
		
		
		Reward reward2= new Reward();
		reward2.addBonus(BonusType.ASSISTANT, 1);
		reward2.addBonus(BonusType.COIN, 2);
		reward2.addBonus(BonusType.VICTORY,3);
		
		
		
		
		
		
		//PermitCard card1 = new PermitCard(reward1, "ABC");
		
		//PermitCard card2 = new PermitCard(reward2, "DEF");
		
		int size=0;
		size=model.getRegion(RegionTypes.COAST).getPermits().size();
		
		model.getRegion(RegionTypes.COAST).getPermits().get(size-1).setReward(reward1);
		model.getRegion(RegionTypes.COAST).getPermits().get(size-1).setCities("ABC");
		model.getRegion(RegionTypes.COAST).getPermits().get(size-2).setReward(reward2);
		model.getRegion(RegionTypes.COAST).getPermits().get(size-2).setCities("DEF");
		
		
		
		
		
		
		action= new ChangePermits(model, 0 ,RegionTypes.COAST );
		
		
		
		
		
		
		
	}
	
	@Before
	public void setUp2() throws IOException, JDOMException{
		model=new Model(2, 1);
		
		
		
		model.getPlayer(0).addAssistants(-1);
		
		action= new ChangePermits(model,0,  RegionTypes.COAST);
		
		
		
	}
	
	
	@After
	public void tearDown(){
		model=null;
		action=null;
		
	}
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.ChangePermits#isValid()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsValid() throws Exception {
		setUp();
		assertEquals(action.isValid(), true);
		
		tearDown();
		
		setUp2();
		assertEquals(action.isValid(), false);
		
		tearDown();
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.ChangePermits#execute()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testExecute() throws Exception
	{
		setUp();
		Model newModel= action.execute();
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getCities(), "ABC");
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getCities(), "DEF");
		
		
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getCities(), "CH");
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getCities(), "DF");
		
		
		
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(0).getQuantity(), 5);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(1).getQuantity(), 4);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(2).getQuantity(), 3);
		
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(0).getType(), BonusType.ASSISTANT);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(1).getType(), BonusType.COIN);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(2).getType(), BonusType.VICTORY);
		
		
		
		
		
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(0).getQuantity(), 3);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(1).getQuantity(), 5);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(2).getQuantity(), 4);
		
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(0).getType(), BonusType.PERMIT);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(1).getType(), BonusType.ASSISTANT);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard1().getReward().getBonusByIndex(2).getType(), BonusType.NOBILITY);
		
		
		
		
		
		
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(0).getQuantity(), 1);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(1).getQuantity(), 2);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(2).getQuantity(), 3);
	
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(0).getType(), BonusType.ASSISTANT);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(1).getType(), BonusType.COIN);
		assertEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(2).getType(), BonusType.VICTORY);
		
		
		

		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(0).getQuantity(), 3);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(1).getQuantity(), 1);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(2).getQuantity(), 2);
	
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(0).getType(), BonusType.VICTORY);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(1).getType(), BonusType.ASSISTANT);
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getPermitCard2().getReward().getBonusByIndex(2).getType(), BonusType.COIN);
		
		
		
		
	}

}
