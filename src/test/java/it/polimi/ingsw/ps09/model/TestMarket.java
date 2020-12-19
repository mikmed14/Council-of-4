package it.polimi.ingsw.ps09.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketBuyAssistant;
import it.polimi.ingsw.ps09.actions.MarketBuyPermit;
import it.polimi.ingsw.ps09.actions.MarketBuyPolitic;
import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.actions.MarketOfferAssistant;
import it.polimi.ingsw.ps09.actions.MarketOfferPermit;
import it.polimi.ingsw.ps09.actions.MarketOfferPolitic;

/**
 * Market phase tests
 */
public class TestMarket {

	private ArrayList<MarketOffer> offers;
	private Model model;

	@Before
	public void setUp() throws Exception {

		
		model= new Model(4, 1);
		offers= new ArrayList<MarketOffer>();
		
		
		MarketOffer offer0= new  MarketOfferAssistant(model.getPlayer(0), 2);
		

		PoliticCard card = new PoliticCard(Colors.BLACK);
		model.getPlayer(1).addPoliticCard(card);
		assertEquals(model.getPlayer(1).getCardByIndex(model.getPlayer(1).getCards().size() - 1).getColor(),
				Colors.BLACK);

		model.getPlayer(1).getCardByIndex(0).setColor(Colors.RED);
		model.getPlayer(1).getCardByIndex(1).setColor(Colors.RED);
		model.getPlayer(1).getCardByIndex(2).setColor(Colors.RED);
		model.getPlayer(1).getCardByIndex(3).setColor(Colors.RED);
		model.getPlayer(1).getCardByIndex(4).setColor(Colors.RED);
		model.getPlayer(1).getCardByIndex(5).setColor(Colors.RED);

		MarketOffer offer1 = new MarketOfferPolitic(model.getPlayer(1), card, 2);

		Reward reward = new Reward();
		reward.addBonus(BonusType.VICTORY, 5);
		reward.addBonus(BonusType.COIN, 3);
		PermitCard permit = new PermitCard(reward, "ABC");

		model.getPlayer(2).addPermitCard(permit);

		MarketOffer offer2 = new MarketOfferPermit(model.getPlayer(2), permit, 5);

		PoliticCard card2 = new PoliticCard(Colors.GREEN);
		model.getPlayer(3).addPoliticCard(card2);
		MarketOffer offer3 = new MarketOfferPolitic(model.getPlayer(3), card2, 20);

		offers.add(offer0);
		offers.add(offer1);
		offers.add(offer2);
		offers.add(offer3);

	}

	@After
	public void tearDown() {
		offers = null;
		model = null;
	}

	/**
	 * Test method for {@link it.polimi.ingsw.ps09.actions.MarketBuy#isValidBuy()}
	 * 
	 * @throws java.lang.Exception
	 */
	@Test
	public void testIsValid() throws Exception {

	
	setUp();
	
	
	
	
	
	
	assertEquals(model.getPlayer(0).getCoins(), 10);
	assertEquals(model.getPlayer(1).getCoins(), 11);
	assertEquals(model.getPlayer(2).getCoins(), 12);
	assertEquals(model.getPlayer(3).getCoins(), 13);
		
	//il giocatore 0 accetta l'offerta del giocatore 4 (politic green)	
	MarketBuy buy0= new MarketBuyPolitic(model.getPlayer(0), offers.get(3).getPolOffered(), 3);
	//il giocatore 0 accetta l'offert del giocatore 1 (politic black)
		MarketBuy buy1 = new MarketBuyPolitic(model.getPlayer(0), offers.get(1).getPolOffered(), 1);
		//il giocatore 0 accetta l offerta del giovcatore 2 (permit)
		MarketBuy buy2 = new MarketBuyPermit(model.getPlayer(0), offers.get(2).getPerOffered(), 2);
	//il giocatore 0 accetta l'offerta del giocatore 0 (1 assistant, non valido)
	MarketBuy buy3 = new MarketBuyAssistant(model.getPlayer(0), 0);
	
	MarketBuy buy4= new MarketBuyAssistant(model.getPlayer(1), 0);
	
	
	assertEquals(buy0.isValidBuy(offers, model.getPlayer(0)), false);	
	assertEquals(buy1.isValidBuy(offers, model.getPlayer(0)), true);
	assertEquals(buy2.isValidBuy(offers, model.getPlayer(0)), true);
	assertEquals(buy3.isValidBuy(offers, model.getPlayer(0)), false);
	
	assertEquals(buy4.isValidBuy(offers, model.getPlayer(1)), true);
	
	
	
	Model newModel1= buy1.execute(offers, model);
	
	assertEquals(newModel1.getPlayer(0).getCoins(), 8);
	
	
	
	assertEquals(newModel1.getPlayer(0).getCardByIndex(model.getPlayer(0).getCards().size()-1 ).getColor(), Colors.BLACK);
	
	assertEquals(newModel1.getPlayer(1).getCoins(), 13);
	
	for (int i=0; i<model.getPlayer(1).getCards().size(); i++){
		assertNotEquals(model.getPlayer(1).getCardByIndex(i).getColor(), Colors.BLACK);
	}
	
	
	
	
	
	Model newModel2= buy2.execute(offers, model);
	
	
	assertEquals(newModel2.getPlayer(0).getCoins(), 3);
	assertEquals(newModel2.getPlayer(0).getPermitsUp().size(), 1);
	assertEquals(newModel2.getPlayer(0).getPermitUpByIndex(0).getCities(), "ABC");
	assertEquals(newModel2.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(0).getType(), BonusType.VICTORY);
	assertEquals(newModel2.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(1).getType(), BonusType.COIN);
	assertEquals(newModel2.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(0).getQuantity(), 5);
	assertEquals(newModel2.getPlayer(0).getPermitUpByIndex(0).getReward().getBonusByIndex(1).getQuantity(), 3);
	
	
	
	assertEquals(newModel2.getPlayer(2).getCoins(), 17);
	
	
	
	Model newModel3= buy4.execute(offers, model);
	
	assertEquals(newModel3.getPlayer(0).getAssistants(), 0);
	assertEquals(newModel3.getPlayer(1).getAssistants(), 3);
	
		
	tearDown();	
		
		
		
		
		
		
	}

}