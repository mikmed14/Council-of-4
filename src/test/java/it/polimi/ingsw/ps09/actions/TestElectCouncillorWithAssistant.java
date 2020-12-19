package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant}
 */
public class TestElectCouncillorWithAssistant {

	private Model model;

	private Action action;
	
	
	@Before
	public void setUp() throws Exception {
		
		
		
		model= new Model(2, 1);
		model.getCouncillorDeck().getPool().get(0).setColor(Colors.GREEN);
		
		action=new ElectCouncillorWithAssistant(model,0, RegionTypes.COAST , Colors.GREEN);
		
		
	}
	
	
	public void setUp3() throws IOException, JDOMException{
		
		model= new Model(2, 1);
		model.getCouncillorDeck().getPool().get(0).setColor(Colors.GREEN);
		
		model.getPlayer(0).addAssistants(-1);
		
		
		action=new ElectCouncillorWithAssistant(model,0, RegionTypes.COAST , Colors.GREEN);
		
	}
	
	
	
	
	@Before
	public void setUp2() throws IOException, JDOMException{
		model=new Model(2, 1);
		
		for (int i=0; i<model.getCouncillorDeck().getPool().size(); i++){
			if (model.getCouncillorDeck().getPool().get(i).getColor()==Colors.BLACK){
				model.getCouncillorDeck().getPool().get(i).setColor(Colors.GREEN);
			}
		}
		
		model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[3].setColor(Colors.PINK);
		
		
		
		action=new ElectCouncillorWithAssistant(model,0, RegionTypes.COAST , Colors.BLACK);
		
		
		
	}
	
	
	
	@After
	public void tearDown(){
		model=null;
		action=null;
		
	}

	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant#isValid()}
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
		
		
		setUp3();
		assertEquals(action.isValid(), false);
		tearDown();
		
		
		
		
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant#execute()} and for
	 * {@link it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant#applyBonus()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testExecuteAndApplyBonus() throws Exception{
		setUp();
		Model newModel= action.execute();
		
		
		assertEquals(model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].getColor(), Colors.GREEN);
		assertEquals(newModel.getPlayer(0).getCoins(), 10);	
		
		
		
		assertNotEquals(model.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].getColor(), Colors.BLACK);
		assertNotEquals(model.getPlayer(0).getCoins(), 15);
		
		tearDown();
		
	}
	

}
