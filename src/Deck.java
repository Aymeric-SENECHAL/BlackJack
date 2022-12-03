import java.io.Serializable;
import java.util.Random;

public class Deck extends Exception implements Serializable {

	private int nextCardIndex;

	Card[] deck = new Card[52];

	public Deck(){
		int count = 0;

		for (int i = 1; i <= 13; i++) {
			deck[count++] = new Card('H', i);
		}
		for (int i = 1; i <= 13; i++) {
			deck[count++] = new Card('S', i);
		}
		for (int i = 1; i <= 13; i++) {
			deck[count++] = new Card('C', i);
		}
		for (int i = 1; i <= 13; i++) {
			deck[count++] = new Card('D', i);
		}

		nextCardIndex = 0;
	}
	
	public String toString(){

		String str = "";

		for (int i = 0; i < deck.length; i++) {
			str +=	deck[i].toString() + " ";
		}
		return str;
	}


	private void swapCards(int index1, int index2){
		Card hold;

		hold = deck[index1];
		deck[index1] = deck[index2];
		deck[index2] = hold;
	}

	public void shuffle(){
		Random rn = new Random();
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < deck.length; j++) {
				swapCards(i, rn.nextInt(52));
			}
		}
		nextCardIndex = 0;
	}
	
	public Card getCard(int index){
		return deck[index];
	}

	public boolean compareTo(Deck otherDeck) {
		for (int i=0; i < deck.length; i++){
			if (! deck[i].compareTo(otherDeck.getCard(i)) ) {
				return false;
			}
		}
		return true;
	}

	public Card nextCard() {
		return deck[nextCardIndex++];
	}

}