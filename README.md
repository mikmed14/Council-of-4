# Council of Four #

**Software Engineering (Final Test) 2015/2016**

## General info ##

This is the repository for the Java implementation of the board game *Council of Four* (created by CRANIO CREATIONS s.r.l). This project is part of the Software Engineering course (and Final Test) at Politecnico di Milano.

### Features ###

* RMI
* Socket
* CLI


### Repository folders: ###

* **src/main:** contains the Java source code of the application game.
* **src/test:** contains the unit tests.
* **docs/:** various resources unrelated to the source code like game requirements, game rules and UML diagrams (situated in the subfolder "UML diagrams").

## How to run the project ##

Below we will present the steps to follow to start playing.

NOTE:
We could not implement GUI; anyway at the beginning of the game players are invited to choose between CLI and GUI. At the moment, if you choose GUI, you need to insert your choice again until you choose CLI.

### Start the game manager (server) ###

In order to play you first need to start the game manager, to do that run `src/main/java/it/polimi/ingsw/ps09/main/StartConsiglioDei4.java`, it will start automatically and wait for incoming RMI/Socket connections.

### Start the game player (client) ###

To start a game player run `src/main/java/it/polimi/ingsw/ps09/main/Client.java`, you will then be prompted to choose between **Socket** or **RMI** connection:

	- insert `0` to use a Socket connection
	- insert `1` to use Remote Method Invocation

## Credits ##

ps09 group:

- *Joshua Nicolay Ortiz Osorio*

- *Michelangelo Medori*

- *Paolo Minniti*