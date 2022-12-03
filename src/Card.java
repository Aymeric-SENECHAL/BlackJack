import java.io.Serializable;

public class Card implements Serializable {

	private char suit;
	private int value;

	public Card(char newSuit, int newValue){
		this.value = newValue;
		this.suit = newSuit;
	}

	public char getSuitDesignator() {
		return suit;
	}

	public String getValueName(){
		String name = "Unknown";

		if (this.value == 1) {		
			name = "Ace";
		}
		else if (this.value == 2) {
			name = "Two";
		}
		else if (this.value == 3) {
			name = "Three";
		}
		else if (this.value == 4) {
			name = "Four";
		}
		else if (this.value == 5) {
			name = "Five";
		}
		else if (this.value == 6) {
			name = "Six";
		}
		else if (this.value == 7) {
			name = "Seven";
		}
		else if (this.value == 8) {
			name = "Eight";
		}
		else if (this.value == 9) {
			name = "Nine";
		}
		else if (this.value == 10) {
			name = "Ten";
		}
		else if (this.value == 11) {
			name = "Jack";
		}
		else if (this.value == 12) {
			name = "Queen";
		}
		else if (this.value == 13) {
			name = "King";

		} 
		return name;
	}

	public int getValue() {
		return this.value;
	}
	
	public boolean compareTo(Card card){
		return this.suit == card.getSuitDesignator() && this.value == card.getValue();
	}
}