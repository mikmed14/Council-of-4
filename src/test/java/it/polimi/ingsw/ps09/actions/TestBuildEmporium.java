package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.BuildEmporium;
import it.polimi.ingsw.ps09.model.Bonus;
import it.polimi.ingsw.ps09.model.BonusType;
import it.polimi.ingsw.ps09.model.CityColor;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.RegionTypes;
import it.polimi.ingsw.ps09.model.Reward;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.BuildEmporium}
 */
public class TestBuildEmporium {

	Model model;
	BuildEmporium  action;
	int numberOfEmporiums=0;
	
	@Before
	public void setUp() throws Exception {
		
		model=new Model(2, 1);
		
	
		
		
		Reward reward = new Reward();
		reward.addBonus(BonusType.ASSISTANT, 3);
		reward.addBonus(BonusType.COIN, 5);
		
		
		PermitCard card= new PermitCard(reward, "ABC");
		
		model.getPlayer(0).getPermitsUp().add(card);
		model.getPlayer(0).addAssistants(5);
		
		model.getRegion(model.getRegionByCity('B')).getCityById('B').addEmporium(0);
		model.getRegion(model.getRegionByCity('D')).getCityById('D').addEmporium(0);
		model.getRegion(model.getRegionByCity('F')).getCityById('F').addEmporium(0);
		model.getRegion(model.getRegionByCity('G')).getCityById('G').addEmporium(0);
		model.getRegion(model.getRegionByCity('J')).getCityById('J').addEmporium(0);
		model.getRegion(model.getRegionByCity('H')).getCityById('H').addEmporium(0);
		model.getRegion(model.getRegionByCity('E')).getCityById('E').addEmporium(0);
		
		numberOfEmporiums= model.getRegion(RegionTypes.COAST).getCityById('A').getNumberOfEmporiums();
		
		
		action= new BuildEmporium(model, 0, 0, "A");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	@Before 
	public void setUp2() throws IOException, JDOMException{
		model= new Model(2, 1);
		
		model.getRegion(model.getRegionByCity('B')).getCityById('B').addEmporium(0);
		model.getRegion(model.getRegionByCity('D')).getCityById('D').addEmporium(0);
		model.getRegion(model.getRegionByCity('C')).getCityById('C').addEmporium(0);
		model.getRegion(model.getRegionByCity('E')).getCityById('E').addEmporium(0);
		model.getRegion(model.getRegionByCity('B')).getCityById('A').addEmporium(0);
		
		
		Reward reward = new Reward();
		reward.addBonus(BonusType.ASSISTANT, 3);
		reward.addBonus(BonusType.COIN, 5);
		
		
		PermitCard card= new PermitCard(reward, "ABC");
		
		model.getPlayer(0).getPermitsUp().add(card);

		action= new BuildEmporium(model, 0, 0, "A");
		
		
		
	}
	
	public void setUp3() throws IOException, JDOMException{
		model= new Model(2, 1);
		
		
		model.getRegion(model.getRegionByCity('G')).getCityById('G').addEmporium(0);
		model.getRegion(model.getRegionByCity('M')).getCityById('M').addEmporium(0);
		model.getRegion(model.getRegionByCity('C')).getCityById('C').addEmporium(0);
		model.getRegion(model.getRegionByCity('E')).getCityById('E').addEmporium(0);
		
		
		
		
		Reward reward = new Reward();
		reward.addBonus(BonusType.ASSISTANT, 3);
		reward.addBonus(BonusType.COIN, 5);
		
		
		PermitCard card= new PermitCard(reward, "ABC");
		
		model.getPlayer(0).getPermitsUp().add(card);

		action= new BuildEmporium(model, 0, 0, "A");
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	@After
	public void tearDown(){
		model=null;
		action=null;
		
	}
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.BuildEmporium#isValid()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsValid() throws Exception {
		
		setUp();
		assertEquals(action.isValid(), true);
		
			tearDown();
		
		
		
		
		
	} 
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.BuildEmporium#execute()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testExecute() throws Exception{
		
		setUp();
		Model newModel = action.execute();
		assertEquals(newModel.getPlayer(0).getAssistants() , 6-numberOfEmporiums+2 );
		assertEquals(newModel.getPlayer(0).getCoins() , 14 );
		assertEquals(newModel.getPlayer(0).getPoints() , 5 );
		assertEquals(newModel.getPlayer(0).getNobility() , 2 );
		
		
		assertEquals(newModel.getPlayer(0).getPermitDownByIndex(0).getCities(), "ABC");
		assertEquals (newModel.getPlayer(0).getPermitDownByIndex(0).getReward().getBonusByIndex(0).getType(), BonusType.ASSISTANT);
		assertEquals (newModel.getPlayer(0).getPermitDownByIndex(0).getReward().getBonusByIndex(1).getType(), BonusType.COIN);
		assertEquals (newModel.getPlayer(0).getPermitDownByIndex(0).getReward().getBonusByIndex(0).getQuantity(), 3);
		assertEquals (newModel.getPlayer(0).getPermitDownByIndex(0).getReward().getBonusByIndex(1).getQuantity(), 5);
		
		assertEquals(newModel.getRegion(newModel.getRegionByCity('A')).getCityById('A').emporiumbBuilt(PlayerColor.assignColorToTurn(0)) , true );
		
		assertEquals(newModel.getPlayer(0).getEmporiums(), 9);
		
		//2 victory
		//1 coin
		//2 assistant
		
		
		
		
		
		
		
		
		
		
		
		tearDown();
		
	}
	
	
	@Test
	public void testApplyRegionBonus() throws IOException, JDOMException{
		
		
		setUp2();
		
		action.applyRegionReward();
		action.applyColorReward();
		
		assertEquals(model.getPlayer(0).getRegion(RegionTypes.COAST), true);
		assertNotEquals(model.getPlayer(0).getRegion(RegionTypes.COAST), false);
		assertEquals(model.getPlayer(0).getFinalBonuses(), 25);
		assertNotEquals(model.getPlayer(0).getFinalBonuses(), 20);
		assertEquals(model.getPlayer(0).getPoints(), 5);
		assertNotEquals(model.getPlayer(0).getPoints(), 0);
		
		
		tearDown();
		
		setUp3();
		
		action.applyColorReward();
		action.applyRegionReward();
		
		assertEquals(model.getPlayer(0).getCityColor(CityColor.SILVER), true);
		assertNotEquals(model.getPlayer(0).getCityColor(CityColor.SILVER), false);
		assertEquals(model.getPlayer(0).getFinalBonuses(), 37);
		assertNotEquals(model.getPlayer(0).getFinalBonuses(), 25);
		assertEquals(model.getPlayer(0).getPoints(), 0);
		assertNotEquals(model.getPlayer(0).getPoints(), 5);
		
		
		tearDown();
		
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void testSwitchBonus() throws Exception{
		
		Bonus bonus1= new Bonus(5, BonusType.VICTORY);
		Bonus bonus2= new Bonus(5, BonusType.COIN);
		Bonus bonus3= new Bonus(5, BonusType.POLITIC);
		Bonus bonus4= new Bonus(5, BonusType.ASSISTANT);
		Bonus bonus5= new Bonus(1, BonusType.PERMIT);
		Bonus bonus6= new Bonus(1, BonusType.MYPERMIT);
		Bonus bonus7= new Bonus(1, BonusType.CITYREWARD);
		Bonus bonus8= new Bonus(2, BonusType.NOBILITY);
		Bonus bonus9= new Bonus(10, BonusType.NOBILITY);
		Bonus bonus10= new Bonus(2, BonusType.CITYREWARD);
		
		
		setUp();
		
		
		
		
		action.switchBonus(bonus1);
		assertEquals(model.getPlayer(0).getPoints(), 5);
		tearDown();
		
		setUp();
		action.switchBonus(bonus2);
		assertEquals(model.getPlayer(0).getCoins(), 15);
		tearDown();
		
		
		
		setUp();
		action.switchBonus(bonus3);
		assertEquals(model.getPlayer(0).getCards().size(), 11);
		tearDown();
		
		
		setUp();
		action.switchBonus(bonus4);
		assertEquals(model.getPlayer(0).getAssistants(), 11);
		tearDown();
		
		
		setUp();
		
		model.setRegionBonus(RegionTypes.COAST);
		model.setNumberTesseraRegione(1);
		
		
		Reward reward = new Reward();
		reward.addBonus(BonusType.VICTORY, 5);
		reward.addBonus(BonusType.COIN, 5);
		
		
		PermitCard permit= new PermitCard(reward, "ABC");
		model.getRegion(RegionTypes.COAST).setPermitCard1(permit);
		
		action.switchBonus(bonus5);
		assertEquals(model.getPlayer(0).getCoins(), 15);
		assertEquals(model.getPlayer(0).getPoints(), 5);
		tearDown();
		
		
		
		setUp();
		action.switchBonus(bonus8);
		assertEquals(model.getPlayer(0).getNobility(), 2);
		assertEquals(model.getPlayer(0).getPoints(), 2);
		assertEquals(model.getPlayer(0).getCoins(), 12);
		
		tearDown();
	
		setUp();
		
		
		model.setRegionBonus(RegionTypes.COAST);
		model.setNumberTesseraRegione(1);
		
		
		Reward reward1 = new Reward();
		reward1.addBonus(BonusType.VICTORY, 5);
		reward1.addBonus(BonusType.COIN, 5);
		
		
		PermitCard permit1= new PermitCard(reward1, "ABC");
		model.getRegion(RegionTypes.COAST).setPermitCard1(permit1);
		
	
		action.switchBonus(bonus9);
		assertEquals(model.getPlayer(0).getNobility(), 10);
		assertEquals(model.getNobilityReward(10).getBonusByIndex(0).getType(), BonusType.PERMIT);
		assertEquals(model.getNobilityReward(10).getBonusByIndex(0).getQuantity(), 1);
		
		
		assertEquals(model.getPlayer(0).getPermitUpByIndex(1).getReward().getBonusByIndex(0).getType(), BonusType.VICTORY);
		assertEquals(model.getPlayer(0).getPermitUpByIndex(1).getReward().getBonusByIndex(0).getQuantity(), 5);
		
		assertEquals(model.getPlayer(0).getCoins(), 15);
		assertEquals(model.getPlayer(0).getPoints(), 5);
		tearDown();
		
		
		
		setUp();
		model.setNumberTesseraBonus(0);
		action.switchBonus(bonus6);
		
		
		
		assertEquals(model.getPlayer(0).getPoints(), 0);
		assertEquals(model.getPlayer(0).getCoins(), 15);
		
		tearDown();
		
		setUp();
		
		
		model.setCityBonus1("A");
	
		
		
		action.switchBonus(bonus7);
		
		
		
		assertEquals(model.getPlayer(0).getPoints(), 3);
		assertEquals(model.getPlayer(0).getCoins(), 10);
		
		tearDown();
		
		
		setUp();
		
		
		model.setCityBonus1("A");
		model.setCityBonus2("O");
		
		action.switchBonus(bonus10);
		
		
		
		assertEquals(model.getPlayer(0).getAssistants(), 7);
		assertEquals(model.getPlayer(0).getCards().size(), 7);
		
		tearDown();
		
		
	}
	

}
