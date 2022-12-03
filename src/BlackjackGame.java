import java.util.Scanner;


public class BlackjackGame {

	private static BlackjackGame game = new BlackjackGame();

	private BlackjackGame(){}

	public static BlackjackGame getInstance() {
		return game;
	}

	// Permet de lire ce qui est écrit dans le terminal
	private Scanner ki = new Scanner(System.in);
	private int numberPlayers;
	private Deck deck;

	private Players group = new Players();

	// Starts game and displays the rules
	public void initializeGame(){
		String names;
		System.out.println("Welcome to Blackjack!");
		System.out.println("");
		System.out.println("  BLACKJACK RULES: ");
		System.out.println("	-Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
		System.out.println("	-Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
		System.out.println("	-The players cards are added up for their total.");
		System.out.println("	-Players “Hit” to gain another card from the deck. Players “Stay” to keep their current card total.");
		System.out.println("	-Dealer “Hits” until they equal or exceed 17.");
		System.out.println("	-The goal is to have a higher card total than the dealer without going over 21.");
		System.out.println("	-If the player total equals the dealer total, it is a “Push” and the hand ends."); 
		System.out.println("	-Players win their bet if they beat the dealer. Players win 1.5x their bet if they get “Blackjack” which is 21.");
		System.out.println("");
		System.out.println("");
		
		// Gets the amount of players and creates them
		do {
			System.out.print("How many people are playing (1-6)? ");
			numberPlayers = ki.nextInt();
		} while (numberPlayers > 6 || numberPlayers < 0);

		for (int i = 0; i < numberPlayers; i++) {
			group.add(new Players(100));
		}

		// On crée le deck
		deck = new Deck();

		// Asks for player names and assigns them
		int idx = 0;
		for (Players player : group.getPlayers()) {
			System.out.print("What is player " + (idx + 1) + "'s name? ");
			idx ++;
			names = ki.next();
			player.setName(names);
		}
	}
	
	// Shuffles the deck
	public void shuffle(){
		deck.shuffle();
	}

	// Gets the bets from the players
	public void getBets(){
		int betValue;

		for (Players player : group.getPlayers()) {
			if (player.getBank() > 0) {
				// On boucle si on souhaite miser moins que 0 et plus que ce que l'on a
			do {
				System.out.print("How much do you want to bet " + player.getName()  + (" (1-" + player.getBank()) + ")? " );
				betValue = ki.nextInt();
				player.setBet(betValue);
			} while (!( betValue > 0 && betValue <= player.getBank()));
			System.out.println("");
			}
		}
	}
	
	// Deals the cards to the players and dealer
	public void dealCards(){
		for (int j = 0; j < 2; j++) {
			for (Players player : group.getPlayers()) {
				if(player.getBank() > 0)
				{
				player.addCard(deck.nextCard());
				}
			}
			group.addCard(deck.nextCard());
		}
	}
	
	// Initial check for dealer or player Blackjack
	public void checkBlackjack(){
		//System.out.println();

		if (group.isBlackjack() ) {
			System.out.println("Dealer has BlackJack!");
			for (Players player : group.getPlayers()){
				if (player.getTotal() == 21 ) {
					System.out.println(player.getName() + " pushes");
					player.push();
				} else {
					System.out.println(player.getName() + " loses");
					player.bust();
				}	
			}
		} else {
			if (group.peek() ) {
				System.out.println("Dealer peeks and does not have a BlackJack");
			}

			for (Players player : group.getPlayers()) {
				if (player.getTotal() == 21 ) {
					System.out.println(player.getName() + " has blackjack!");
					player.blackjack();
				}
			}
		}		
	}
	
	// This code takes the user commands to hit or stand
	public void hitOrStand() {
		String command;
		char c;
		for (Players player : group.getPlayers()) {
			if (player.getBet() > 0) {
				System.out.println();
				System.out.println(player.getName() + " has " + player.getHandString());

				do {
					do {
						System.out.print(player.getName() + " (H)it or (S)tand? ");
						command = ki.next();
						c = command.toUpperCase().charAt(0);
					} while ( ! ( c == 'H' || c == 'S' ) );
					if ( c == 'H' ) {
						player.addCard(deck.nextCard());
						System.out.println(player.getName() + " has " + player.getHandString());
					}
				} while (c != 'S' && player.getTotal() <= 21 );
			}
		}
	}
	
	// Code for the dealer to play
	public void dealerPlays() {
		boolean isSomePlayerStillInTheGame = false;

		for (Players player : group.getPlayers()) {
			if (player.getBet() > 0 && player.getTotal() <= 21 ) {
				isSomePlayerStillInTheGame = true;
			}
		}
		if (isSomePlayerStillInTheGame) {
			group.dealerPlay(deck);
		}
	}
	
	// This code calculates all possible outcomes and adds or removes the player bets
	public void settleBets() {
		System.out.println();

		for (Players player : group.getPlayers()) {
			if (player.getBet() > 0 ) {
				if( player.getTotal() > 21 ) {
					System.out.println(player.getName() + " has busted");
					player.bust();
				} else if ( player.getTotal() == group.calculateTotal() ) {
					System.out.println(player.getName() + " has pushed");
					player.push();
				} else if ( player.getTotal() < group.calculateTotal() && group.calculateTotal() <= 21 ) {
					System.out.println(player.getName() + " has lost");
					player.loss();
				} else if (player.getTotal() == 21) {
					System.out.println(player.getName() + " has won with blackjack!");
					player.blackjack();
				} else {
					System.out.println(player.getName() + " has won");
					player.win();
					
				}
			}
		}

	}

	// This prints the players hands
	public void printStatus() {
		for (Players player : group.getPlayers()) {
			if(player.getBank() > 0)
			{
			System.out.println(player.getName() + " has " + player.getHandString());
			}
		}
		System.out.println("Dealer has " + group.getHandString(true, true));
	}
	
	// This prints the players banks and tells the player if s/he is out of the game
	public void printMoney() {
		for (Players player : group.getPlayers()) {
			if(player.getBank() > 0)
			{
			System.out.println(player.getName() + " has " + player.getBank());
			}
			if(player.getBank() == 0)
			{
			System.out.println(player.getName() + " has " + player.getBank() + " and is out of the game.");
			player.removeFromGame();
			}
		}
	}

	// This code resets all hands
	public void clearHands() {
		for (Players player : group.getPlayers()) {
			player.clearHand();
		}
		group.clearHand();
	}
	
	// This decides to force the game to end when all players lose or lets players choose to keep playing or not
	public boolean playAgain() {
		String command;
		char c;
		Boolean playState = true;
		if(forceEnd()) {
			playState = false;	
		}
		else {
			do {
				System.out.println("");
				System.out.print("Do you want to play again (Y)es or (N)o? ");
				command = ki.next();
				c = command.toUpperCase().charAt(0);
			} while ( ! ( c == 'Y' || c == 'N' ) );
			if(c == 'N')
			{
				playState = false;
			}
		}
		return playState;
	}
	
	// This says true or false to forcing the game to end
	public boolean forceEnd() {
		boolean end = false;
		int endCount = 0;

		for (Players player : group.getPlayers()) {
			if(player.getBank() == -1)
			{
				endCount++;
			}
		}
		if(endCount == numberPlayers)
		{
			end = true;
		}
		if(end)
		{
			System.out.println("");
			System.out.println("All players have lost and the game ends.");
		}
		
		return end;
	}
	
	// This is the endgame code for when all players are out of the game or players decide to stop playing
	public void endGame() {
		int endAmount;
		String endState = " no change.";
		System.out.println("");
		for (Players player : group.getPlayers()) {
			if(player.getBank() == -1)
			{
				player.resetBank();
			}
			endAmount = player.getBank() - 100;
			if(endAmount > 0)
			{
				endState = " gain of ";
			}
			else if(endAmount < 0)
			{
				endState = " loss of ";
			}
			System.out.println(player.getName() + " has ended the game with " + player.getBank() + ".");
			if(endState != " no change.")
			{
			System.out.println("A" + endState + Math.abs(endAmount) + ".");
			}
			else
			{
			System.out.println("No change from their starting value.");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
		System.out.println("Thank you for playing!");
	}
}