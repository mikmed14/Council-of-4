package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PlayerColor;
import it.polimi.ingsw.ps09.model.RegionTypes;

public class TestBuildEmporiumKing {

	
	Model model;
Action  action;
	int numberOfEmporiums=0;
	
	
	@Before
	public void setUp1() throws Exception {
		model= new Model(2, 1);
		
		ArrayList<Colors> colors= new ArrayList<Colors>();
		colors.add(Colors.GREEN);
		colors.add(Colors.BLACK);
		colors.add(Colors.JOLLY);
		colors.add(Colors.BLACK);
		
		
		model.getPlayer(0).addCoins(10);
		model.getPlayer(0).addAssistants(5);
		
		
		
		
		model.getPlayer(0).getCardByIndex(0).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.JOLLY);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(4).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);
		
		
		model.getKingBalcony().getCouncillorsByIndex(0).setColor(Colors.GREEN);
		model.getKingBalcony().getCouncillorsByIndex(1).setColor(Colors.BLACK);
		model.getKingBalcony().getCouncillorsByIndex(2).setColor(Colors.BLACK);
		
		
		
		
	
		
		numberOfEmporiums= model.getRegion(RegionTypes.COAST).getCityById('A').getNumberOfEmporiums();
		
		
		action= new BuildEmporiumKing(model, 0,  "A", colors);
		
		
	}
	
	
	@Before
	public void setUp2() throws IOException, JDOMException{
		
			model= new Model(2, 1);
		
		ArrayList<Colors> colors= new ArrayList<Colors>();
		colors.add(Colors.GREEN);
		colors.add(Colors.BLACK);
		colors.add(Colors.JOLLY);
		colors.add(Colors.BLACK);
		
		
		
		

		model.getPlayer(0).getCardByIndex(0).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(1).setColor(Colors.GREEN);
		model.getPlayer(0).getCardByIndex(2).setColor(Colors.JOLLY);
		model.getPlayer(0).getCardByIndex(3).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(4).setColor(Colors.BLACK);
		model.getPlayer(0).getCardByIndex(5).setColor(Colors.PINK);
		
		
		model.getKingBalcony().getCouncillorsByIndex(0).setColor(Colors.GREEN);
		model.getKingBalcony().getCouncillorsByIndex(1).setColor(Colors.BLACK);
		model.getKingBalcony().getCouncillorsByIndex(2).setColor(Colors.BLACK);
		
		
		model.getPlayer(0).addCoins(-10);
		
		numberOfEmporiums= model.getRegion(RegionTypes.COAST).getCityById('A').getNumberOfEmporiums();
		
		action= new BuildEmporiumKing(model, 0,  "A", colors);
		
		
	}
	
	
	@After
public void tearDown(){
		model=null;
		action=null;
	
	
	
	
}
	
	@Test
	public void testIsValid() throws Exception{
		tearDown();
		setUp1();
		
		assertEquals(action.isValid(), true);
		tearDown();
		
		setUp2();
		assertEquals (model.getPlayer(0).getCoins(), 0);
		assertEquals(action.isValid(), false);
		tearDown();
		
	}
	
	
	@Test
	public void testExecute() throws Exception {
		Model newModel;
		setUp1();
		newModel = action.execute();
		
		assertEquals(newModel.getRegion(newModel.getRegionByCity('A')).getCityById('A').emporiumbBuilt(PlayerColor.assignColorToTurn(0)) , true );
		assertEquals(newModel.getPlayer(0).getCards().size(), 2);
		assertEquals(newModel.getPlayer(0).getEmporiums(), 9);
	}
	
	
	
	

}
