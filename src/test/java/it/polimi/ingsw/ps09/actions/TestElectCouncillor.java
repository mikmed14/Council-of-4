package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.ElectCouncillor;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.ElectCouncillor}
 */
public class TestElectCouncillor {
	
	private Model model;

	private Action action;
	
	
	
	

	@Before
	public void setUp() throws Exception {
		
		
		model=new Model(2, 1);
		
		
		
		
		model.getCouncillorDeck().getPool().get(0).setColor(Colors.GREEN);
		
		
		action= new ElectCouncillor(model, Colors.GREEN, RegionTypes.COAST, 0);
		
		
		
		
		
		
		
	}
	
	@Before
	public void setUp3() throws Exception {
		
		
		model=new Model(2, 1);
		
		
		
		
		model.getCouncillorDeck().getPool().get(0).setColor(Colors.GREEN);
		
		
		action= new ElectCouncillor(model, Colors.GREEN, RegionTypes.KING, 0);
		
		
		
		
		
		
		
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
		
		
		action=new ElectCouncillor(model, Colors.BLACK, RegionTypes.COAST, 0);
		
	}
	
	
	
	
	@After
	public void tearDown(){
		model=null;
		action=null;
		
	}
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.ElectCouncillor#isValid()}
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
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.actions.ElectCouncillor#execute()} and for
	 * {@link it.polimi.ingsw.ps09.actions.ElectCouncillor#applyBonus()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testExecuteAndApplyBonus() throws Exception{
		setUp();
		Model newModel= action.execute();
		
		
		assertEquals(newModel.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].getColor(), Colors.GREEN);
		assertEquals(newModel.getPlayer(0).getCoins(), 15);	
		
		
		
		assertNotEquals(newModel.getRegion(RegionTypes.COAST).getBalcony().getCouncillors()[0].getColor(), Colors.BLACK);
		assertNotEquals(newModel.getPlayer(0).getCoins(), 10);
		
		tearDown();
		
		
		
		
		setUp3();
		Model newModel2= action.execute();
		
		assertEquals(newModel2.getKingBalcony().getCouncillors()[0].getColor(), Colors.GREEN);
		assertEquals(newModel2.getPlayer(0).getCoins(), 15);	
		
		
		assertNotEquals(newModel2.getKingBalcony().getCouncillors()[0].getColor(), Colors.BLUE);
		assertNotEquals(newModel2.getPlayer(0).getCoins(), 10);
		
		
		tearDown();
	}

	
	
	
	
	
}
