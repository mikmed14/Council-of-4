package it.polimi.ingsw.ps09.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPlayerColors {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		
		assertEquals(PlayerColor.assignColorToTurn(-1), PlayerColor.WHITE);
		assertEquals(PlayerColor.assignColorToTurn(0), PlayerColor.VIOLET);
		assertEquals(PlayerColor.assignColorToTurn(1), PlayerColor.IVORY);
		assertEquals(PlayerColor.assignColorToTurn(2), PlayerColor.CHOCOLATE);
		assertEquals(PlayerColor.assignColorToTurn(3), PlayerColor.LIME);
		assertEquals(PlayerColor.assignColorToTurn(4), PlayerColor.LIGHTBLUE);
		assertEquals(PlayerColor.assignColorToTurn(5), PlayerColor.ORANGE);
		assertEquals(PlayerColor.assignColorToTurn(6), PlayerColor.GREY);
		assertEquals(PlayerColor.assignColorToTurn(7), PlayerColor.PURPLE);
		assertEquals(PlayerColor.assignColorToTurn(8), PlayerColor.BROWN);
		assertEquals(PlayerColor.assignColorToTurn(9), PlayerColor.TURQOISE);
		assertEquals(PlayerColor.assignColorToTurn(10), PlayerColor.BEIJE);
		assertEquals(PlayerColor.assignColorToTurn(11), PlayerColor.ACQUAMARINE);
		assertEquals(PlayerColor.assignColorToTurn(12), PlayerColor.SILVER);
		assertEquals(PlayerColor.assignColorToTurn(13), PlayerColor.GOLD);
		assertEquals(PlayerColor.assignColorToTurn(14), PlayerColor.PLATINUM);
		assertEquals(PlayerColor.assignColorToTurn(15), PlayerColor.DIAMOND);
		assertEquals(PlayerColor.assignColorToTurn(16), PlayerColor.GREEN);
		assertEquals(PlayerColor.assignColorToTurn(17), PlayerColor.YELLOW);
		assertEquals(PlayerColor.assignColorToTurn(18), PlayerColor.RED);
		assertEquals(PlayerColor.assignColorToTurn(19), PlayerColor.BLUE);
		
		assertEquals(PlayerColor.assignColorToTurn(20), PlayerColor.BLACK);
		
		
		
		
		
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.WHITE), -1);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.VIOLET), 0);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.IVORY), 1);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.CHOCOLATE), 2);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.LIME), 3);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.LIGHTBLUE), 4);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.ORANGE), 5);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.GREY), 6);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.PURPLE), 7);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.BROWN), 8);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.TURQOISE), 9);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.BEIJE), 10);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.ACQUAMARINE), 11);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.SILVER), 12);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.GOLD), 13);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.PLATINUM), 14);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.DIAMOND), 15);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.GREEN), 16);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.YELLOW), 17);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.RED), 18);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.BLUE), 19);
		assertEquals(PlayerColor.getTurnByColor(PlayerColor.BLACK), 20);
		
		
		
	}

}
