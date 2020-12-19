package it.polimi.ingsw.ps09.net;

import java.io.IOException;
import java.rmi.Remote;

import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.MarketBuy;
import it.polimi.ingsw.ps09.actions.MarketOffer;

/**
 * This is an interface of a single match.
 */
public interface MatchInterface extends Remote {

	/**
	 * This method is used to send the choice of action made by the player.
	 * 
	 * @param action
	 *            The selected action.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	public void sendMossa(Action action) throws IOException, InterruptedException;

	/**
	 * This method is used to send an offer that a player makes during the
	 * market phase.
	 * 
	 * @param offerta
	 *            The involved offer.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	public void sendOffer(MarketOffer offerta) throws IOException, InterruptedException;

	/**
	 * This method is used to send a buy action that a player makes during the
	 * market phase.
	 * 
	 * @param sold
	 *            The buy action.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	void sendBuy(MarketBuy sold) throws IOException, InterruptedException;
}