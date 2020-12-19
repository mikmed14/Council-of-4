package it.polimi.ingsw.ps09.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import it.polimi.ingsw.ps09.actions.AcquirePermit;
import it.polimi.ingsw.ps09.actions.Action;
import it.polimi.ingsw.ps09.actions.BuildEmporium;
import it.polimi.ingsw.ps09.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps09.actions.ChangePermits;
import it.polimi.ingsw.ps09.actions.ElectCouncillor;
import it.polimi.ingsw.ps09.actions.ElectCouncillorWithAssistant;
import it.polimi.ingsw.ps09.actions.EngageAssistant;
import it.polimi.ingsw.ps09.actions.MarketOffer;
import it.polimi.ingsw.ps09.map.Constants;
import it.polimi.ingsw.ps09.model.BonusType;
import it.polimi.ingsw.ps09.model.Colors;
import it.polimi.ingsw.ps09.model.Model;
import it.polimi.ingsw.ps09.model.PermitCard;
import it.polimi.ingsw.ps09.model.RegionTypes;

/**
 * This class represents the Command Line Interface (CLI) View.
 */
public class View {

	private Model model;
	private Scanner stdin = new Scanner(System.in);
	private Action action;

	/**
	 * Constructor for View.
	 */
	public View(Model map) {
		this.model = map;
	}

	/**
	 * This method is used when necessary to show a list of moves (main and
	 * quick) that a player can perform during the game and asks his choice.
	 * 
	 */
	public int sendMossa()  {
		int input = 0;

		do {
			System.out.println("**********Azioni Principali**********\n" + "1) Eleggere un consigliere\n"
					+ "2) Acquistare una tessera permesso di costruzione\n"
					+ "3) Costruire un emporio usando una tessere permesso\n"
					+ "4) Costruire un emporio con l'aiuto del re");
			System.out.println("**********Azioni Veloci**********\n" + "5) Ingaggiare un assistente\n"
					+ "6) Cambiare le tessere permesso di costruzione\n"
					+ "7) Mandare un aiutante a eleggere un consigliere\n"
					+ "8) Compiere un'azione principale aggiuntiva");
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero");
				stdin.next();
			}

			input = stdin.nextInt();

		} while (input < 1 || input > Constants.NUMMOSSEVELOCI);

		return input;
	}

	/**
	 * This method is used to memorize the selected action.
	 * 
	 * @param azione
	 *            The selected action.
	 */
	public void memory(Action azione) {
		this.action = azione;
	}

	/**
	 * @return the action of the player
	 */
	public Action getAzione() {
		return this.action;
	}

	/**
	 * This method is used to show a list of main actions that a player can
	 * perform and asks his choice.
	 * 
	 */
	public int sendMossaPrincipale() {
		int input = 0;

		do {
			System.out.println("**********Azioni Principali**********\n" + "1) Eleggere un consigliere\n"
					+ "2) Acquistare una tessera permesso di costruzione\n"
					+ "3) Costruire un emporio usando una tessere permesso\n"
					+ "4) Costruire un emporio con l'aiuto del re");
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero");
				stdin.next();
			}

			input = stdin.nextInt();

		} while (input < 1 || input > Constants.NUMMOSSEPRINCIPALI);

		return input;
	}

	/**
	 * This method is used to show a list of quick actions that a player can
	 * perform and asks his choice.
	 * 
	 */
	public int sendMossaVeloce()  {
		int inputLine = 0;

		do {
			System.out.println("**********Azioni Veloci**********\n" + "5) Ingaggiare un assistente\n"
					+ "6) Cambiare le tessere permesso di costruzione\n"
					+ "7) Mandare un aiutante a eleggere un consigliere\n"
					+ "8) Compiere un'azione principale aggiuntiva");
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero");
				stdin.next();
			}

			inputLine = stdin.nextInt();

		} while (inputLine < 5 || inputLine > Constants.NUMMOSSEVELOCI);

		return inputLine;

	}

	/**
	 * This method shows a part of the view on the execution of the chosen move.
	 * 
	 * @param input
	 *            The number related to the chosen move.
	 * @param turno
	 *            The round when the player makes the choice.
	 * @param model
	 *            The current board game.
	 * 
	 */
	public int manageMossa(int input, int turno, Model model) {
		String choice = null;
		switch (input) {
		// 1
		case Constants.ELECTCOUNCILLOR:

			System.out.println("Hai scelto di eleggere un nuovo consigliere. ");
			spaceShip();

			model.getCouncillorDeck().updateCount();
			printPoolCouncillors(model);
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionBalconies(model);
			printKingBalcony(model);
			spaceShip();
			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");

			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {
				System.out.println("Scegli il colore del consigliere");
				Colors color = inputColor();

				System.out.println("scegli in quale balcone vuoi eleggere un consigliere");
				RegionTypes type = inputRegionType();

				System.out.println("Sto eseguendo la mossa...");

				memory(new ElectCouncillor(model, color, type, turno));
				return 0;

			}

			// 2
		case Constants.ACQUIREPERMIT:
			System.out.println("Hai scelto di acquistare un permesso di costruzione");

			printPlayerPermitsUp(model, turno);
			spaceShip();
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionBalconies(model);
			spaceShip();
			printRegionPermits(model);
			spaceShip();
			printPlayerCoins(model, turno);
			int n;
			RegionTypes type;
			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");
			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {

				do {
					System.out.println("Scegli la regione in cui e' presente la tessera permesso che vuoi acquistare");
					type = inputRegionType();

					System.out.println("Hai scelto la regione ******" + type.toString() + "*****");
					System.out.println(
							"Scegli quale delle due carte permesso della regione scelta ti interessa acquistare");

					n = inputNumberOfPermitCard();

				} while (n == -1);

				System.out.println(
						"Scegli quali carte politiche che hai in mano vuoi usare per acquistare la tessera permesso scelta");

				ArrayList<Colors> colors = new ArrayList<Colors>();
				colors = inputPoliticCards();

				spaceShip();
				System.out.println("Sto esegundo la mossa");

				memory(new AcquirePermit(model, turno, type, n, colors));

				return 1;

			}

			// 3
		case Constants.BUILDEMPORIUM:
			System.out.println("Hai scelto di costruire un emporio con una tessera permesso");

			String lettera;
			int cardIndex;

			printCitiesRewards(model);
			spaceShip();
			printRegionRewards(model);
			spaceShip();
			printPlayerCoins(model, turno);
			spaceShip();
			printPlayerPermitsDown(model, turno);
			spaceShip();
			printPlayerPermitsUp(model, turno);
			spaceShip();
			printNumberOfEmporiumsLeft(model, turno);

			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");
			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {

				cardIndex = inputPlayerPermit(model, turno);
				lettera = inputCity();

				memory(new BuildEmporium(model, turno, cardIndex, lettera));

				return 2;

			}

			// 4
		case Constants.BUILDEMPORIUMKING:
			System.out.println("Hai scelto di costruire un emporio con l'aiuto del re");

			printKingBalcony(model);
			printPlayerPoliticCards(model, turno);
			printPlayerCoins(model, turno);
			printCitiesRewards(model);
			printRegionRewards(model);
			printKing(model);
			printMoveKing(model);
			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");
			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {

				String city;
				System.out.println(
						"Scegli quali carte politiche che hai in mano vuoi usare per soddisfare il consiglio del RE");

				ArrayList<Colors> colors = new ArrayList<Colors>();
				colors = inputPoliticCards();
				spaceShip();
				city = inputCity();
				System.out.println("Sto eseguendo la mossa...");

				memory(new BuildEmporiumKing(model, turno, city, colors));

			}

			return 3;

		// 5
		case Constants.ACQUIREASSISTENTE:
			System.out.println(
					"Hai scelto di ingaggiare un assistente. Devi pagare 3 monete d'oro per prendere un assistente");

			printPlayerCoins(model, turno);
			spaceShip();
			printPlayerAssistants(model, turno);

			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");
			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			}

			else {
				System.out.println("Sto eseguendo la mossa...");

				memory(new EngageAssistant(model, turno));

			}
			return 4;

		// 6
		case Constants.CHANGEPERMITCARDS:
			System.out.println("Hai scelto di cambiare le carte permesso sul tabellone. Paghi un assistente ");

			printRegionPermits(model);
			spaceShip();
			printPlayerAssistants(model, turno);
			spaceShip();

			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");

			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {
				RegionTypes type3;
				System.out.println("Scegli la regione dove vuoi cambiare le tessere permesso presenti");
				type3 = inputRegionType();
				System.out.println("Sto eseguendo la mossa...");
				memory(new ChangePermits(model, turno, type3));

			}
			return 5;

		// 7
		case Constants.ASSISTELECTCOUNCILLOR:
			System.out
					.println("Hai scelto di eleggere un consigliere con l'aiuto di un assistente. Paghi un assistente");

			spaceShip();
			model.getCouncillorDeck().updateCount();
			printPoolCouncillors(model);
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionBalconies(model);
			printKingBalcony(model);
			spaceShip();
			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");

			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			} else {

				System.out.println("Scegli il colore del consigliere");
				Colors color = inputColor();

				System.out.println("Scegli in quale balcone vuoi eleggere un consigliere");
				RegionTypes type4 = inputRegionType();

				System.out.println("Sto eseguendo la mossa...");

				memory(new ElectCouncillorWithAssistant(model, turno, type4, color));
			}

			return 6;

		// 8
		case Constants.ADDACTION:
			int mossaAggiuntiva;
			System.out.println("Hai scelto di compiere un'azione principale aggiuntiva. Paghi 3 assistenti");
			printPlayerAssistants(model, turno);
			spaceShip();

			System.out.println("-1 se vuoi tornare indietro e cambiare mossa, 0 per confermare la mossa");

			choice = stdin.next();
			while ((!choice.equals("-1")) && (!choice.equals("0"))) {
				System.out.println("Immetti un numero valido");
				choice = stdin.next();
			}

			if (Integer.parseInt(choice) == -1) {
				return Integer.parseInt(choice);
			}

			int rifaiMossa;
			do {
				mossaAggiuntiva = sendMossaPrincipale();
				rifaiMossa = manageMossa(mossaAggiuntiva, turno, model);
			} while (rifaiMossa == -1);

			return mossaAggiuntiva;

		}
		return Integer.parseInt(choice);
	}

	public void afterMossa(int input, Model model, int turno) {

		switch (input) {
		// 1
		case Constants.ELECTCOUNCILLOR:
			printPlayerCoins(model, turno);
			spaceShip();
			printPoolCouncillors(model);
			spaceShip();
			printRegionBalconies(model);
			printKingBalcony(model);
			break;
		case Constants.ACQUIREPERMIT:
			System.out.println("i valori aggiornati sono: ");
			printPlayerPermitsUp(model, turno);
			spaceShip();
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionPermits(model);
			spaceShip();
			printPlayerCoins(model, turno);
			break;
		case Constants.BUILDEMPORIUM:
			System.out.println("i valori aggiornati sono: ");
			printPlayerPermitsUp(model, turno);
			spaceShip();
			printPlayerPermitsDown(model, turno);
			spaceShip();
			printCitiesRewards(model);
			spaceShip();
			printRegionRewards(model);
			printPlayerCoins(model, turno);
			printPlayerPoints(model, turno);
			spaceShip();
			printNumberOfEmporiumsLeft(model, turno);
			break;
		case Constants.BUILDEMPORIUMKING:
			printKingBalcony(model);
			printPlayerPoliticCards(model, turno);
			printPlayerCoins(model, turno);
			printCitiesRewards(model);
			printRegionRewards(model);
			printKing(model);
			break;
		case Constants.ACQUIREASSISTENTE:
			printPlayerCoins(model, turno);
			spaceShip();
			printPlayerAssistants(model, turno);
			break;
		case Constants.CHANGEPERMITCARDS:
			printRegionPermits(model);
			spaceShip();
			printPlayerAssistants(model, turno);
			break;
		case Constants.ASSISTELECTCOUNCILLOR:
			printPlayerCoins(model, turno);
			spaceShip();
			printPoolCouncillors(model);
			spaceShip();
			printRegionBalconies(model);
			printKingBalcony(model);
			printPlayerAssistants(model, turno);

			break;
		case Constants.ADDACTION:

			break;

		}

	}

	/**
	 * This method prints the set of councillors present at the side of the
	 * board game.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printPoolCouncillors(Model model) {

		System.out.println("*****Consiglieri disponibili nel deck*****");

		for (int i = 0; i < model.getCouncillorDeck().getPool().size(); i++) {
			System.out.print(model.getCouncillorDeck().getPool().get(i).getColor().toString() + "  ");

		}
		spaceShip();
		this.model.getCouncillorDeck().updateCount();
		System.out.println("\n**********Contatore Consiglieri**********");
		System.out.print("Consiglieri neri :" + model.getCouncillorDeck().getNumberOfBlackCouncillors());
		System.out.print("\tConsiglieri blu :" + model.getCouncillorDeck().getNumberOfBlueCouncillors());
		System.out.println("\tConsiglieri rossi :" + model.getCouncillorDeck().getNumberOfRedCouncillors());
		System.out.print("Consiglieri rosa :" + model.getCouncillorDeck().getNumberOfPinkCouncillors());
		System.out.print("\tConsiglieri verdi :" + model.getCouncillorDeck().getNumberOfGreenCouncillors());
		System.out.println("\tConsiglieri gialli :" + model.getCouncillorDeck().getNumberOfYellowCouncillors());
	}

	public void printPlayerPoliticCards(Model model, int turn) {
		System.out.println("\n******Colore carte politica in mano****** ");
		for (int i = 0; i < model.getPlayer(turn).getCards().size(); i++) {

			System.out.print(i + ") " + model.getPlayer(turn).getCardByIndex(i).getColor().toString() + "        ");
		}
		System.out.println("\n");
	}

	/**
	 * This method prints the balconies present in region boards.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printRegionBalconies(Model model) {

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {
			System.out.println(" Balcone della regione : " + model.getRegionByIndex(i).getRegionType().toString());
			System.out.print("Colori: ");
			for (int j = 0; j < Constants.NUMOFCOUNCILLORSFOREACHBALCONY; j++) {
				System.out.print(
						"  " + model.getRegionByIndex(i).getBalcony().getCouncillorsByIndex(j).getColor().toString());
			}
			spaceShip();
		}

	}

	/**
	 * This method shows the moving of a king during the game.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printMoveKing(Model model) {
		System.out.println("*******Costo per spostare il RE nelle varie citta'*******");
		char charMatrix;
		int CoinsToMove;
		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {

			for (int j = 0; j < Constants.NUMBEROFCITIESPERREGION; j++) {

				charMatrix = model.getRegionByIndex(i).getCityByIndex(j).getId();
				CoinsToMove = model.GodMatrix(charMatrix);
				System.out.println("Citta': " + model.getRegionByIndex(i).getCityByIndex(j).getName()
						+ "            paghi: " + CoinsToMove * 2 + " monete");

			}

		}

	}

	/**
	 * This method prints the balconies present in the king board.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printKingBalcony(Model model) {

		System.out.println(" Balcone del RE");
		System.out.print("Colori: ");
		for (int j = 0; j < Constants.NUMOFCOUNCILLORSFOREACHBALCONY; j++) {
			System.out.print("  " + model.getKingBalcony().getCouncillorsByIndex(j).getColor().toString());
		}
		spaceShip();
	}

	/**
	 * This method shows how many coins the player has.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public void printPlayerCoins(Model model, int turn) {
		System.out.println("Hai a disposizione " + model.getPlayer(turn).getCoins() + "monete");
	}

	/**
	 * This method show the number of assistants that a player possesses during
	 * the game.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public void printPlayerAssistants(Model model, int turn) {
		System.out.println("Hai a disposizione " + model.getPlayer(turn).getAssistants() + "assistenti");
	}

	public void spaceShip() {
		System.out.println("\n");
	}

	/**
	 * This method shows the two business permit tiles present in a region.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printRegionPermits(Model model) {

		System.out.println("TESSERE PERMESSO:");

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {

			switch (i) {
			case 0:
				System.out.println("*****COAST*****");
				break;
			case 1:
				System.out.println("*****HILL*****");
				break;
			case 2:
				System.out.println("*****MOUNTAIN*****");
				break;
			}

			String cities1 = model.getRegionByIndex(i).getPermitCard1().getCities();
			System.out.print("Le citta della prima carta permesso sono:   ");
			for (int c = 0; c < cities1.length(); c++) {
				System.out.print(cities1.charAt(c) + "    ");
			}

			spaceShip();
			System.out.println("La prima tessera permesso ha "
					+ model.getRegionByIndex(i).getPermitCard1().getReward().getBonuses().size() + " bonus");

			for (int j = 0; j < model.getRegionByIndex(i).getPermitCard1().getReward().getBonuses().size(); j++) {
				System.out.println("Bonus n° " + j + "="
						+ model.getRegionByIndex(i).getPermitCard1().getReward().getBonusByIndex(j).getQuantity() + " "
						+ model.getRegionByIndex(i).getPermitCard1().getReward().getBonusByIndex(j).getType()
								.toString());
			}

			spaceShip();
			String cities2 = model.getRegionByIndex(i).getPermitCard2().getCities();
			System.out.print("Le citta della seconda tessera permesso sono:    ");
			for (int c = 0; c < cities2.length(); c++) {
				System.out.print(cities2.charAt(c) + "   ");
			}
			spaceShip();

			System.out.println("La seconda tessera permesso ha "
					+ model.getRegionByIndex(i).getPermitCard2().getReward().getBonuses().size() + " bonus");

			for (int k = 0; k < model.getRegionByIndex(i).getPermitCard2().getReward().getBonuses().size(); k++) {
				System.out.println("Bonus n° " + k + "="
						+ model.getRegionByIndex(i).getPermitCard2().getReward().getBonusByIndex(k).getQuantity() + " "
						+ model.getRegionByIndex(i).getPermitCard2().getReward().getBonusByIndex(k).getType()
								.toString());
			}

			spaceShip();
		}

	}

	/**
	 * This method prints the business permit tiles of the player not yet used
	 * to build an emporium.
	 * 
	 * @return the number of permit cards of the player not yet used to build an
	 *         emporium
	 */
	public void printPlayerPermitsUp(Model model, int turn) {

		System.out.println("Queste sono le carte permesso che puoi ancora usare per costruire un emporio");

		String cities;
		int n = model.getPlayer(turn).getPermitsUp().size();
		if (n == 0)
			System.out.println("Non hai carte permesso a disposizione");
		else {
			System.out.println("Hai a disposizione " + n + " carte permesso");

			for (int i = 0; i < n; i++) {

				System.out.println("Tessera " + (i + 1) + " :");

				cities = model.getPlayer(turn).getPermitUpByIndex(i).getCities();

				for (int c = 0; c < cities.length(); c++) {
					System.out.print("citta' " + (c + 1) + "= " + cities.charAt(c) + "\t");
				}

				System.out.println("\nBonus della carta permesso : ");

				for (int j = 0; j < model.getPlayer(turn).getPermitUpByIndex(i).getReward().getBonuses().size(); j++) {
					System.out.print("     " + j + ": "
							+ model.getPlayer(turn).getPermitUpByIndex(i).getReward().getBonusByIndex(j).getQuantity()
							+ " " + model.getPlayer(turn).getPermitUpByIndex(i).getReward().getBonusByIndex(j).getType()
									.toString()
							+ "\t");
				}
			}

		}

	}

	/**
	 * This method prints the business permit tiles owned by the player already
	 * used to build an emporium.
	 * 
	 * @return the number of permit cards of the player already used to build an
	 *         emporium
	 */
	public void printPlayerPermitsDown(Model model, int turn) {

		System.out.println("Queste sono le carte permesso che hai gia usato per costruire un emporio");

		String cities;
		int n = model.getPlayer(turn).getPermitsDown().size();
		if (n == 0)
			System.out.println("Non hai carte permesso a disposizione");
		else {
			System.out.println("Hai a disposizione " + n + " carte permesso");

			for (int i = 0; i < n; i++) {

				System.out.println("tessera " + i + 1 + " :");

				cities = model.getPlayer(turn).getPermitDownByIndex(i).getCities();

				System.out.println("le citta della carta permesso sono: ");

				for (int c = 0; c < cities.length(); c++) {
					System.out.println(cities.charAt(c) + " ");
				}

				System.out.println("i bonus (che hai gia' ottenuto) della carta permesso sono : ");

				for (int j = 0; j < model.getPlayer(turn).getPermitDownByIndex(i).getReward().getBonuses()
						.size(); j++) {
					System.out.println("bonus=" + j + "="
							+ model.getPlayer(turn).getPermitDownByIndex(i).getReward().getBonusByIndex(j).getQuantity()
							+ " " + model.getPlayer(turn).getPermitDownByIndex(i).getReward().getBonusByIndex(j)
									.getType().toString());
				}
			}

		}

	}

	/**
	 * This method shows how many points the player has in the victory track.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public void printPlayerPoints(Model model, int turn) {
		int victoryPoints;
		victoryPoints = model.getPlayer(turn).getPoints();

		System.out.println("Hai a disposizione " + victoryPoints + " punti vittoria");
	}

	/**
	 * This method shows the number of emporiums that the player can still use.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public void printNumberOfEmporiumsLeft(Model model, int turn) {
		int emporiums = model.getPlayer(turn).getEmporiums();
		System.out.println("Hai ancora a disposizione " + emporiums + " empori da costruire");

	}

	/**
	 * This method prints the token reward present in each region.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printRegionRewards(Model model) {
		System.out.println("Reward delle regioni: ");

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {

			System.out.println("bonus regione 1:");
			for (int j = 0; j < model.getRegionByIndex(i).getReward().getBonuses().size(); j++) {
				System.out.println(
						"bonus=" + j + "=" + model.getRegionByIndex(i).getReward().getBonusByIndex(j).getQuantity()
								+ " " + model.getRegionByIndex(i).getReward().getBonusByIndex(j).getType().toString());
			}

		}

	}

	/**
	 * This method prints the token reward present in each city.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printCitiesRewards(Model model) {

		System.out.println("Reward delle citta': ");

		for (int i = 0; i < Constants.NUMOFREGIONS; i++) {

			System.out.println("*****Region " + i + "*****");

			for (int k = 0; k < Constants.NUMBEROFCITIESPERREGION; k++) {

				System.out.print("Citta' " + model.getRegionByIndex(i).getCityByIndex(k).getName() + ":   ");

				for (int j = 0; j < model.getRegionByIndex(i).getCityByIndex(k).getBonus().getBonuses().size(); j++) {
					System.out.println("bonus " + j + "="
							+ model.getRegionByIndex(i).getCityByIndex(k).getBonus().getBonuses().get(j).getQuantity()
							+ " " + model.getRegionByIndex(i).getCityByIndex(k).getBonus().getBonuses().get(j).getType()
									.toString()
							+ "\t");
				}

			}

		}

	}

	public String printIndexCity(Model model, boolean secondCity) {

		System.out.println("*****BONUS DELLE CITTA'****** ");

		for (int k = 0; k < Constants.NUMBEROFCITIESPERREGION; k++) {

			System.out.println(k + " : " + model.getRegionByIndex(0).getCityByIndex(k).getName() + ":   ");

			for (int j = 0; j < model.getRegionByIndex(0).getReward().getBonuses().size(); j++) {
				if (model.getRegionByIndex(0).getReward().getBonusByIndex(j).getType() == BonusType.NOBILITY) {
					System.out.println("Non ci sono bonus");
				} else {
					System.out.println("bonus " + j + "="
							+ model.getRegionByIndex(0).getReward().getBonusByIndex(j).getQuantity() + " "
							+ model.getRegionByIndex(0).getReward().getBonusByIndex(j).getType().toString() + "\t");
				}
			}

		}

		for (int k = 0; k < Constants.NUMBEROFCITIESPERREGION; k++) {

			System.out.println((k + 5) + " : " + model.getRegionByIndex(1).getCityByIndex(k).getName() + ":   ");

			for (int j = 0; j < model.getRegionByIndex(1).getReward().getBonuses().size(); j++) {

				if (model.getRegionByIndex(1).getReward().getBonusByIndex(j).getType() == BonusType.NOBILITY) {
					System.out.println("Non ci sono bonus");
				} else {
					System.out.println("bonus " + j + "="
							+ model.getRegionByIndex(1).getReward().getBonusByIndex(j).getQuantity() + " "
							+ model.getRegionByIndex(1).getReward().getBonusByIndex(j).getType().toString() + "\t");
				}
			}

		}

		for (int k = 0; k < Constants.NUMBEROFCITIESPERREGION; k++) {

			System.out.println((k + 10) + " : " + model.getRegionByIndex(2).getCityByIndex(k).getName() + ":   ");

			for (int j = 0; j < model.getRegionByIndex(2).getReward().getBonuses().size(); j++) {

				if (model.getRegionByIndex(2).getReward().getBonusByIndex(j).getType() == BonusType.NOBILITY) {
					System.out.println("Non ci sono bonus");
				} else {
					System.out.println("bonus " + j + "="
							+ model.getRegionByIndex(2).getReward().getBonusByIndex(j).getQuantity() + " "
							+ model.getRegionByIndex(2).getReward().getBonusByIndex(j).getType().toString() + "\t");
				}

			}

		}
		if (secondCity) {
			System.out.println(
					"Inserisci l'iniziale in maiuscolo della SECONDA citta' dalla quale vuoi ricevere il bonus");
		} else {
			System.out.println("Inserisci l'iniziale in maiuscolo della citta' dalla quale vuoi ricevere il bonus");
		}

		String lettera;
		lettera = stdin.next();

		while ((lettera != "A") && (lettera != "B") && (lettera != "C") && (lettera != "D") && (lettera != "E")
				&& (lettera != "F") && (lettera != "G") && (lettera != "H") && (lettera != "I") && (lettera != "J")
				&& (lettera != "K") && (lettera != "L") && (lettera != "M") && (lettera != "N") && (lettera != "O")) {

			System.out.println("Inserisci un carattere valido");
			lettera = stdin.next();

		}
		return lettera;

	}

	/**
	 * This method shows the nobility track with bonus.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printNobilityTrack(Model model) {
		System.out.println("$$$$$PERCORSO DELLA NOBILTA'$$$$$ ");

		for (int i = 0; i < Constants.NOBILITYTRACK; i++) {

			System.out.print(i + "°) ");

			for (int j = 0; j < model.getNobilityTrack()[i].getBonuses().size(); j++) {
				System.out.print("bonus " + j + ": " + model.getNobilityReward(i).getBonusByIndex(j).getQuantity() + " "
						+ model.getNobilityReward(i).getBonusByIndex(j).getType().toString() + "\t");
			}

		}

	}

	/**
	 * This method shows how many points the player has in the nobility track.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public void printPlayerNobility(Model model, int turn) {
		System.out.println("In questo momento ti trovi sullo spazio " + model.getPlayer(turn).getNobility()
				+ " del percorso della nobilta'");
	}

	/**
	 * This method shows where is the king.
	 * 
	 * @param model
	 *            The current board game.
	 */
	public void printKing(Model model) {

		String name = null;
		char charCity;

		charCity = model.getKing();
		name = model.getRegion(model.getRegionByCity(charCity)).getCityById(charCity).getName();

		if (name == null)
			throw new IllegalArgumentException();

		System.out.println("Al momento il re si trova nella citta'" + name);
		spaceShip();

	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen region.
	 */
	public RegionTypes inputRegionType() {
		String stringa;

		System.out.println("Scegli una regione: : COAST, HILL, MOUNTAIN, KING");

		RegionTypes type = RegionTypes.NULL;

		stringa = stdin.next();
		type = RegionTypes.fromString(stringa);
		while (type == RegionTypes.NULL) {
			System.out.println("Immetti una regione valida: COAST, HILL, MOUNTAIN, KING ");
			stringa = stdin.nextLine();
			type = RegionTypes.fromString(stringa);

		}

		return type;
	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen business permit tile of the region.
	 */
	public int inputNumberOfPermitCard() {
		int n;
		System.out
				.println("Scegli una carta: 1 o 2, inserisci 0 se vuoi tornare indietro e scegliere un'altra regione");

		n = stdin.nextInt();
		if (n == 0) {
			return -1;
		}

		while (n != 1 && n != 2) {
			System.out.println("Immetti un numero valido");
			n = stdin.nextInt();

		}
		return n;
	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen color.
	 */
	public Colors inputColor() {

		String stringa;
		System.out.println("Scegli il colore : blue, yellow, black, green, pink, red");

		Colors color = Colors.NULL;

		stringa = stdin.next();
		color = Colors.fromString(stringa);

		while (color == Colors.NULL) {
			System.out.println("Immetti un colore valido: blue, yellow, black, green, pink, red");
			stringa = stdin.next();
			color = Colors.fromString(stringa);
		}
		return color;
	}

	public int inputCardIndex(ArrayList<Integer> indexes, int max) {

		int index;
		boolean flag = false;

		index = stdin.nextInt();

		for (int i = 0; i < indexes.size(); i++) {
			if (indexes.get(i) == index)
				flag = true;
		}

		while (((index < 0) && (index > max)) || (flag == true)) {
			if (flag == true)
				System.out.println("hai gia inserito questo indice. Inserisci un indice valido");

			else
				System.out.println("inserisci un indice valido");

		}

		return index;

	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen politics cards.
	 */
	public ArrayList<Colors> inputPoliticCards() {
		int numberOfCards;
		ArrayList<Colors> colors = new ArrayList<Colors>();

		System.out.println("Immetti il numero di carte politiche che vuoi usare");
		numberOfCards = stdin.nextInt();

		while ((numberOfCards < 0) || (numberOfCards > 4)) {
			System.out.println("Immetti un numero valido");
			numberOfCards = stdin.nextInt();
		}

		for (int k = 0; k < numberOfCards; k++) {
			System.out.println("Immetti il colore della  " + k + "a carta che vuoi giocare  ");
			String input = stdin.next();
			Colors color = Colors.fromString(input);
			colors.add(color);

		}

		return colors;

	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen city.
	 */
	public String inputCity() {

		System.out.println("Inserisci l'iniziale in maiuscolo della citta' in cui vuoi costruire un emporio");
		String lettera;
		lettera = stdin.next();

		while ((lettera.charAt(0) != 'A') && (lettera.charAt(0) != 'B') && (lettera.charAt(0) != 'C')
				&& (lettera.charAt(0) != 'D') && (lettera.charAt(0) != 'E') && (lettera.charAt(0) != 'F')
				&& (lettera.charAt(0) != 'G') && (lettera.charAt(0) != 'H') && (lettera.charAt(0) != 'I')
				&& (lettera.charAt(0) != 'J') && (lettera.charAt(0) != 'K') && (lettera.charAt(0) != 'L')
				&& (lettera.charAt(0) != 'M') && (lettera.charAt(0) != 'N') && (lettera.charAt(0) != 'O')) {

			System.out.println("Inserisci un carattere valido");
			lettera = stdin.next();

		}
		return lettera; // STRINGA!!
	}

	/**
	 * This method is used to handle input entered by the player about the
	 * chosen business permit tile owned by the player.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public int inputPlayerPermit(Model model, int turn) {

		int numero;

		System.out.println("inserisci il numero della carta:");
		numero = stdin.nextInt();

		while ((numero <= 0) || (numero > model.getPlayer(turn).getPermitsUp().size())) {
			System.out.println("inserisci un numero valido:");
			numero = stdin.nextInt();
		}

		return numero - 1;

	}

	/**
	 * This method shows if there is the selected city in the chosen business
	 * permit tile.
	 * 
	 * @param card
	 *            The chosen permit card.
	 * @param city
	 *            The selected city.
	 */
	public Boolean isCityInPermit(PermitCard card, String city) {

		String cities = card.getCities();
		String lettera;
		char carattere;
		Boolean flag = false;

		for (int i = 0; i < cities.length(); i++) {

			carattere = cities.charAt(i);
			lettera = "" + carattere;
			if (lettera == city)
				flag = true;

		}
		System.out.println("La citta' non e' presente nella tessera permesso scelta, rimetti i parametri!");
		return flag;

	}

	/**
	 * This method is used when a player enters a wrong choice.
	 * 
	 * @param input
	 *            The choice of the player.
	 * @param turno
	 *            The current round.
	 * @param model
	 *            The current board game.
	 * 
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws java.lang.ClassNotFoundException
	 */
	public int repeat(int input, int turno, Model model)
			throws IOException, InterruptedException, ClassNotFoundException {

		switch (input) {
		// 1
		case Constants.ELECTCOUNCILLOR:

			System.out.println("Parametri inseriti non validi!! Non ci sono consiglieri di quel colore nel deck ");
			spaceShip();
			model.getCouncillorDeck().updateCount();
			printPoolCouncillors(model);
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionBalconies(model);
			printKingBalcony(model);
			spaceShip();

			System.out.println("Scegli il colore del consigliere");
			Colors color = inputColor();

			System.out.println("scegli in quale balcone vuoi eleggere un consigliere");
			RegionTypes type = inputRegionType();

			memory(new ElectCouncillor(model, color, type, turno));

			return 0;

		// 2
		case Constants.ACQUIREPERMIT:
			System.out.println("Parametri inseriti non validi!! ");

			printPlayerPermitsUp(model, turno);
			spaceShip();
			printPlayerPoliticCards(model, turno);
			spaceShip();
			printRegionBalconies(model);
			spaceShip();
			printRegionPermits(model);
			spaceShip();
			printPlayerCoins(model, turno);
			int n;
			RegionTypes type1;

			do {
				System.out.println("Scegli la regione in cui e' presente la tessera permesso che vuoi acquistare");
				type1 = inputRegionType();

				System.out.println("Hai scelto la regione " + type1.toString());
				System.out
						.println("Scegli quale delle due carte permesso della regione scelta ti interessa acquistare");

				n = inputNumberOfPermitCard();

			} while (n == -1);

			System.out.println(
					"Scegli quali carte politiche che hai in mano vuoi usare per acquistare la tessera permesso scelta");
			ArrayList<Colors> colors = new ArrayList<Colors>();
			colors = inputPoliticCards();

			spaceShip();

			memory(new AcquirePermit(model, turno, type1, n, colors));

			return 0;

		// 3
		case Constants.BUILDEMPORIUM:
			System.out.println("Parametri inseriti non validi!!");

			String lettera;
			int cardIndex;
			PermitCard card;
			Boolean flag = false;

			printPlayerPermitsUp(model, turno);
			spaceShip();
			printPlayerPermitsDown(model, turno);
			spaceShip();
			printCitiesRewards(model);
			spaceShip();
			printRegionRewards(model);
			spaceShip();
			printPlayerCoins(model, turno);
			spaceShip();
			printPlayerPoints(model, turno);
			spaceShip();
			printNumberOfEmporiumsLeft(model, turno);

			do {

				lettera = inputCity();
				cardIndex = inputPlayerPermit(model, turno);
				card = model.getPlayer(turno).getPermitUpByIndex(cardIndex);

				flag = isCityInPermit(card, lettera);

			} while (flag == false);

			memory(new BuildEmporium(model, turno, cardIndex, lettera));

			return 0;

		// 4
		case Constants.BUILDEMPORIUMKING:
			System.out.println("Parametri inseriti non validi!!");

			printKingBalcony(model);
			printPlayerPoliticCards(model, turno);
			printPlayerCoins(model, turno);
			printCitiesRewards(model);
			printRegionRewards(model);
			printKing(model);

			String city;
			ArrayList<Colors> colori = new ArrayList<Colors>();
			spaceShip();
			city = inputCity();

			System.out.println("Sto eseguendo la mossa...");

			memory(new BuildEmporiumKing(model, turno, city, colori));

			return 0;

		// 5
		case Constants.ACQUIREASSISTENTE:
			System.out.println("Parametri inseriti non validi!!." + "Non hai abbastanza monete. Scegli un'altra mossa");
			printPlayerCoins(model, turno);
			spaceShip();
			printPlayerAssistants(model, turno);

			return -1;

		// 6
		case Constants.CHANGEPERMITCARDS:
			System.out.println(
					"Parametri inseriti non validi!!. Non hai abbastanza consiglieri" + "Scegli un'altra mossa");

			return -1;

		// 7
		case Constants.ASSISTELECTCOUNCILLOR:
			System.out.println(
					"Parametri inseriti non validi!!. Non hai abbastanza consiglieri o il colore del consigliere che hai scelto non e' disponibili tra quelli nel deck");

			return -1;

		// 8
		case Constants.ADDACTION:

			return 0;
		}

		return 0;

	}

	public int inputIndexPermitCard(Model model, int turno) {
		int numero;

		System.out.println("Queste sono le carte permesso che hai a disposizione. (UP)");

		int n = model.getPlayer(turno).getPermitsUp().size();
		if (n == 0)
			System.out.println("Non hai carte permesso a disposizione (UP)");
		else {

			for (int i = 0; i < n; i++) {

				System.out.print(i + ")   ");

				System.out.println("Bonus della carta permesso : ");

				for (int j = 0; j < model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonuses().size(); j++) {
					System.out.print("     " + j + ": "
							+ model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonusByIndex(j).getQuantity()
							+ " " + model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonusByIndex(j)
									.getType().toString()
							+ "\t");
				}
			}

		}

		if (model.getPlayer(turno).getPermitsDown().isEmpty()) {
			System.out.println("Non possiedi tessere permesso (DOWN)");
		} else {
			for (int i = 0; i < model.getPlayer(turno).getPermitsDown().size(); i++) {

				System.out.print(i + n + ")   ");

				System.out.println("Bonus della carta permesso : ");

				for (int j = 0; j < model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonuses().size(); j++) {
					System.out.print("     " + j + ": "
							+ model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonusByIndex(j).getQuantity()
							+ " " + model.getPlayer(turno).getPermitUpByIndex(i).getReward().getBonusByIndex(j)
									.getType().toString()
							+ "\t");
				}
			}
		}

		System.out.println("Inserisci l'indice della tessera permesso dalla quale vuoi ottenere il bonus");
		numero = stdin.nextInt();

		while (numero > n + model.getPlayer(turno).getPermitsDown().size() && numero < 0) {
			System.out.println("Inserisci un numero valido:");
			numero = stdin.nextInt();
		}

		return numero;

	}

	public void InputParametersWeirdBonus(int number, Model model, int turno) {
		switch (number) {

		// scegli una regione dalla quale pescare una tessera permesso senza
		// pagarla
		case 0:
			System.out.println(
					"Il bonus relativo al percorso della nobilta' ti ha permesso di prendere una tessera permesso da una regione a tua scelta senza pagarla... Nice!");
			RegionTypes tipoRegione = inputRegionType();
			int numeroTessera = inputNumberOfPermitCard();
			// vado a settare il parametro acquisito nel model
			model.setRegionBonus(tipoRegione);
			model.setNumberTesseraRegione(numeroTessera);
			break;

		/*
		 * Scegli una tra le tue carte permesso e ottieni il bonus relativo alla
		 * tessera; la carta puo' essere scelta anche tra quelle girate
		 */
		case 1:
			System.out.println(
					"Il bonus relativo al percorso della nobilta' ti permette di scegliere il bonus di una della tue carte permesso...");
			int numberPermitCard = inputIndexPermitCard(model, turno);
			model.setNumberTesseraBonus(numberPermitCard);

			break;

		/*
		 * scegli due citta per il bonus
		 */
		case 2:
			System.out.println(
					"Il bonus relativo al percorso della nobilta' ti permette di scegliere due citta' dalle quale otterrai i relativi bonus...PS non puoi scegliere bonus che ti facciano avanzare nel percorso della nobilta'");
			String city1 = printIndexCity(model, false);
			String city2 = printIndexCity(model, true);
			model.setCityBonus1(city1);
			model.setCityBonus2(city2);
			break;

		/*
		 * Scegli una citta nella quale non hai ancora contruito e ottieni
		 * immediatamente il bonus
		 */
		case 3:
			System.out.println(
					"Il bonus relativo al percorso della nobilta' ti permette di scegliere una citta' dalla quale otterrai il relativo bonus...PS non puoi scegliere bonus che ti facciano avanzare nel percorso della nobilta'");
			String city3 = printIndexCity(model, false);
			model.setCityBonus1(city3);
			break;

		}

	}

	/**
	 * This method shows the market phase.
	 */
	public void welcomeMarket() {
		System.out.println("Benvenuto nel market del Consiglio dei 4.\n Qua potrai fare delle offerte"
				+ "agli altri giocatori mettendo in vendita i tuoi assistenti/carte politica/tessere permesso in cambio di big Money. "
				+ "\n*****Faites vos jeux, messieurs!***** ");
	}

	/**
	 * This method asks if player wants to sell a thing (between 1 assistant, 1
	 * politics card and 1 business permit tile).
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public int startMarket(Model model, int turn) {
		System.out.println("Ecco i tuoi averi");
		printPlayerAssistants(model, turn);
		printPlayerPoliticCards(model, turn);
		printPlayerPermitsUp(model, turn);
		printPlayerCoins(model, turn);

		System.out.println("Partiamo con le offerte... Cosa vuoi mettere in vendita?");
		System.out.println("1) Assistenti                  2) Carte politica                  3) Tessere permesso");
		int choice = stdin.nextInt();
		return choice;
	}

	/**
	 * This method asks if player wants to sell an assistant and the price
	 * during the market phase.
	 */
	public int offerAssistant() {
		System.out.println("Vuoi liberarti di un assistente. Immetti il prezzo del suo sacrificio");
		int price = stdin.nextInt();
		return price;
	}

	/**
	 * This method asks if player wants to sell a politics card by choosing the
	 * color during the market phase.
	 */
	public Colors offerPoliticCard() {
		System.out.println(
				"Non ti serve piu' una carta Politica per corrompere i consiglieri? Vuoi anche venderla? Sei nel posto giusto! ");
		System.out.println("Scegli il colore della carta politica che vuoi vendere ");
		String input = stdin.next();
		Colors color = Colors.fromString(input);
		return color;
	}

	/**
	 * This method asks the selling price (for anything) during the market
	 * phase.
	 */
	public int priceOffer() {
		System.out.println(
				"Immetti il prezzo che desideri spennare a qualche giocatore...Non esagerare con i prezzi (l'economia al momento non se la passa molto bene)");
		int price = stdin.nextInt();
		return price;
	}

	/**
	 * This method asks if player wants to sell a business permit tile during
	 * the market phase.
	 * 
	 * @param model
	 *            The current board game.
	 * @param turn
	 *            The current round.
	 */
	public int offerPermitCard(Model model, int turn) {
		System.out.println("Scegli che tessera permesso vuoi vendere ");
		int index = inputPlayerPermit(model, turn);
		return index;
	}

	/**
	 * This method shows the current offers made by players during the market
	 * phase.
	 * 
	 * @param offerte
	 *            The offers made by players.
	 */
	public void offersDone(ArrayList<MarketOffer> offerte) {
		System.out.println("Queste sono le offerte fatte da tutti i giocatori");
		for (int i = 0; i < offerte.size(); i++) {
			switch (offerte.get(i).getType().toString()) {
			case "assistant":
				System.out.println(i + ")   " + offerte.get(i).getType() + "    " + offerte.get(i).getPrice());
				break;
			case "politic":
				System.out.println(i + ")   " + offerte.get(i).getType() + "    " + offerte.get(i).getPrice() + "    "
						+ offerte.get(i).getPolOffered().getColor().toString());
				break;
			case "permit":
				System.out.println(i + ")   " + offerte.get(i).getType() + "    " + offerte.get(i).getPrice() + "    "
						+ offerte.get(i).getPerOffered().getCities().toString());
				int size = offerte.get(i).getPerOffered().getReward().getBonuses().size();
				for (int j = 0; j < size; j++) {
					System.out.println("Bonus    " + j + "   "
							+ offerte.get(i).getPerOffered().getReward().getBonusByIndex(j).getType() + "    "
							+ offerte.get(i).getPerOffered().getReward().getBonusByIndex(j).getQuantity());
				}
			}
		}
	}

	/**
	 * This method shows the current offers made by players and asks his choice.
	 * 
	 * @param offerte
	 *            The offers made by the players during the market phase.
	 */
	public int offerChoice(ArrayList<MarketOffer> offerte) {

		System.out.println("Quale offerta ti interessa? Immetti il numero dell'offerta");
		int choice = stdin.nextInt();
		while ((choice <= 0) && choice > offerte.size()) {
			System.out.println("Inserisci un numero valido:");
			choice = stdin.nextInt();
		}
		return choice;
	}

	/**
	 * This method says that the market phase is ended.
	 * 
	 * @param model
	 *            The current model.
	 * @param turn
	 *            the current round.
	 */
	public void endMarket(Model model, int turn) {
		printPlayerAssistants(model, turn);
		printPlayerPoliticCards(model, turn);
		printPlayerPermitsUp(model, turn);
		printPlayerCoins(model, turn);
		System.out.println("Market chiuso!!!");
	}

	/**
	 * This method says that offer is ended.
	 */
	public void endOffer() {
		System.out.println("Les jeux sont faits, rien ne va plus");
	}

	/**
	 * This method shows a message about an offer and asks if the player wants
	 * to offer something during the market phase.
	 */
	public int choiceMarket() {
		System.out.println("Vuoi vendere la tua anima al diavolo o vuoi morire per attaccamento alle cosa materiali?"
				+ "\nPremi 0 per fare un'offerta altrimenti premi 1");
		int choice = stdin.nextInt();
		while (choice < 0 && choice > 1) {
			System.out.println("Inserisci un numero valido");
			choice = stdin.nextInt();
		}
		return choice;
	}

	/**
	 * This method is used when the player has not made any offers.
	 */
	public void printNoOffer() {
		System.out.println("Non hai fatto nessuna offerta");
	}

	/**
	 * This method is used when the player did not accept any offers
	 */
	public void printNoAsta() {
		System.out.println("Non hai accettato nessuna offerta");
	}

	/**
	 * This method shows a message about an purchase and asks if the player
	 * wants to buy something during the market phase.
	 */
	public int choiceAsta() {
		System.out.println(
				"Ti interessa comprare qualche arma chimica per la distruzione di massa? Purtroppo non sei nel posto giusto, qua vendiamo solo assistenti, carte politica e tessere permesso."
						+ "\nPremi 0 per accettare un'offerta altrimenti premi 1");
		int choice = stdin.nextInt();
		while (choice < 0 && choice > 1) {
			System.out.println("Inserisci un numero valido");
			choice = stdin.nextInt();
		}
		return choice;
	}
}