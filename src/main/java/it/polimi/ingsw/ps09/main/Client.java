package it.polimi.ingsw.ps09.main;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.ps09.net.ClientRMI;
import it.polimi.ingsw.ps09.net.ClientSocket;

/**
 * This class represents the main for the Client
 */
public class Client {
	
	/**
	 * A variable used to intercept the choice of the client
	 */
	private static Scanner stdin = new Scanner(System.in);
	
	/**
	 * Empty private constructor to hide the public one
	 */
	private Client() {
		return;
	}

	/**
	 * This is the main method in which the client must choose between RMI and Socket.
	 * 
	 * @param args
	 * 
	 * @throws java.lang.InterruptedException
	 * @throws java.rmi.RemoteException
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.io.IOException
	 * @throws java.rmi.NotBoundException
	 * @throws java.rmi.AlreadyBoundException
	 */
	public static void main(String[] args) throws InterruptedException, RemoteException, ClassNotFoundException, IOException, NotBoundException, AlreadyBoundException {

		int inputLine;
		System.out.println("Socket (0) o RMI (1)?");
		do {
			while (!stdin.hasNextInt()) {
				System.out.println("Immettere un numero.");
				stdin.next();
			}
			inputLine = stdin.nextInt();
		} while (inputLine != 0 && inputLine != 1);
		if (inputLine == 0) {
			new ClientSocket("localhost", 1337, stdin).startClient();
		} else {
			new ClientRMI(stdin).startClient();
		}
	}
}
