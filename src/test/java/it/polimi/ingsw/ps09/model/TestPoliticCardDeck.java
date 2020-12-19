package it.polimi.ingsw.ps09.model;

import static org.junit.Assert.*;

//import java.util.ArrayList;

import org.junit.Test;

/**
 * Test for {@link it.polimi.ingsw.ps09.model.PoliticCardDeck}
 */
public class TestPoliticCardDeck {

	private int numberOfPlayers = 2;

	PoliticCardDeck deck = new PoliticCardDeck(numberOfPlayers);

	private int numberOfCards = deck.computeNumberOfCards(numberOfPlayers);
	private int numberOfJollys = deck.computeNumberOfJollys(numberOfPlayers);
	private int numberOfCardsForEachColor = deck.computeNumberOfCardsForEachColor(numberOfCards);

	/**
	 * Test method for
	 * {@link it.polimi.ingsw.ps09.model.PoliticCardDeck#PoliticCardDeck()}
	 */
	@Test
	public void testContructor() {

		assertEquals(numberOfCards, 48);
		assertEquals(numberOfJollys, 6);
		assertEquals(numberOfCardsForEachColor, 8);
	}
	
	public void setUp(){
	while(!deck.isEmpty()){
		deck.addDiscardsCard(deck.drawFromPool());
		
		
		
	}
		
		deck.drawFromPool();
		
		numberOfCards = deck.computeNumberOfCards(numberOfPlayers);
		numberOfJollys = deck.computeNumberOfJollys(numberOfPlayers);
		numberOfCardsForEachColor = deck.computeNumberOfCardsForEachColor(numberOfCards);
		
		
		assertEquals(numberOfCards, 48);
		assertEquals(numberOfJollys, 6);
		assertEquals(numberOfCardsForEachColor, 8);
		
		
	}
	
	
	
	public void testReverse(){
		
		
		
		
	}
}