package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.EngageAssistant;
import it.polimi.ingsw.ps09.model.Model;

/**
 * Test for {@link it.polimi.ingsw.ps09.actions.EngageAssistant}
 */
public class TestEngageAssistant {
	
	
	Action action;
	Model model;
	
	

	@Before
	public void setUp() throws Exception {
		
		
		 model= new Model(2, 1);
		
		
		
		
		
		
		action= new EngageAssistant(model, 0);
		
		
	}
	
	
	
	@Before 
	public void setUp2() throws IOException, JDOMException{
		
		model=new Model(2, 1);
		
		model.getPlayer(0).addCoins(-10);
		action= new EngageAssistant(model,0);
		
	}

	
	@After
	
	public void tearDown(){
		model=null;
		action=null;
	}
	
	
	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.EngageAssistant#isValid()}
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
	 * Test method for {@link it.polimi.ingsw.ps09.actions.EngageAssistant#execute()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testExecute() throws Exception {
		
		setUp();
		Model newModel = action.execute();
		
		assertEquals(newModel.getPlayer(0).getCoins(), 7);
		assertNotEquals(newModel.getPlayer(0).getCoins(), 10);
		tearDown();
		
		
		
		
	}

}
