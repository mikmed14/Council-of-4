package it.polimi.ingsw.ps09.actions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.model.Model;

public class TestNullActions {

	Action action;
	MarketOffer offer;
	MarketBuy buy;
	Model model;
	
	@Before
	public void setUp() throws Exception {
		
		model= new Model(2, 1);
		
		offer= new MarketOfferNulla(model.getPlayer(0));
		
		buy= new MarketBuyNulla(model.getPlayer(1));
		
		action= new MossaNulla(model, 0, 0);
		
		
	}

	@Test
	public void test() throws Exception {
		setUp();
		
		assertEquals(action.nulla(), false);
		assertEquals(offer.nulla(), false);
		assertEquals(buy.nulla(), false);
		
	}

}
