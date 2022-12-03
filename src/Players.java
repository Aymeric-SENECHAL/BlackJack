import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Players implements Serializable {

	// Nous avons le design pattern Composite
	private int bank;
	private int bet;
	private String name;
	private Hand hand;

	private List<Players> players;

	// Creates a player object
	public Players() {
		hand = new Hand();
		players = new ArrayList<>();
	}

	public Players(int bank){
		this.bank = bank;
		hand = new Hand();
	}

	public void add(Players player) {
		players.add(player);
	}

	public List<Players> getPlayers(){
		return players;
	}


	// Gets a player's bank amount
	public int getBank() {
		return bank;
	}
	
	// Removes a player's bet from their bank if they bust. Sets bet to zero afterwards.
	public void bust() {
		bank -= bet;
		bet = 0;
	}
	
	// Adds a player's bet from their bank if they win. Sets bet to zero afterwards.
	public void win() {
		bank += bet;
		bet = 0;
	}

	// Removes a player's bet from their bank if they lose. Sets bet to zero afterwards.
	public void loss() {
		bank -= bet;
		bet = 0;
	}
	
	// This sets the player bank to -1. -1 is unreachable and they are removed from the game
	public void removeFromGame() {
		bank = -1;
	}
	
	// This resets the bank to 0. Currently used to reset a removed player's bank from -1 to 0.
	public void resetBank() {
		bank = 0;
	}
	
	// This calculate the bet for a player who has a Blackjack
	public void blackjack() {
		bank += bet * 1.5;
		bet = 0;
	}
	
	// Sets a player's bet to 0 if the "push". Notice, no bet is added or removed.
	public void push() {
		bet = 0;
	}
	
	// Sets a player's bet
	public void setBet(int newBet) {
		bet = newBet;
	}
	
	// Sets a player's name
	public void setName(String name1){
		name = name1;
	}
	
	// Gets a player's name
	public String getName() {
		return name;
	}
	
	// Gets a player's hand total
	public int getTotal() {
		return hand.calculateTotal();
	}
	
	// Gets a player's bet
	public int getBet(){
		return this.bet;
	}
		
	// Adds a card to a player's hand
	public void addCard(Card card) {
		hand.addCard(card);
	}
	
	// Gets the player's cards to print as a string
	public String getHandString() {
		String str = "Cards:" + hand.toString();

		return str;
	}
		
	// Clears a player's hand
	public void clearHand() {
		hand.clearHand();
	}

	/****/

	public boolean isBlackjack(){
		if (hand.calculateTotal() == 21){
			return true;
		} else {
			return false;
		}
	}

	// This automates the dealer's play
	public void dealerPlay(Deck deck){
		System.out.println();
		while (hand.calculateTotal() <= 16) {
			System.out.println("Dealer has " + hand.calculateTotal()+ " and hits");
			hand.addCard(deck.nextCard());
			System.out.println("Dealer " + this.getHandString(true, false));
		}
		if ( hand.calculateTotal() > 21) {
			System.out.println("Dealer busts. " + this.getHandString(true, false));
		} else {
			System.out.println("Dealer stands. " + this.getHandString(true, false));
		}
	}

	// Gets the dealer's hand as a string
	public String getHandString(boolean isDealer, boolean hideHoleCard ) {
		String str = "Cards:" + hand.toString(isDealer, hideHoleCard);

		return str;
	}

	// Calculates the dealer's hand total
	public int calculateTotal() {
		return hand.calculateTotal();
	}

	// Peeks the dealer's face-down card
	public boolean peek() {
		return hand.dealerPeek();
	}
		
}