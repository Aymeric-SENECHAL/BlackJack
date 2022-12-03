public class Main {

    // Ici nous avons le patron de conception Façade
    public static void main(String[] args) {

        // Nous avons crée un Singleton, pour avoir une seule partie à la fois.
        BlackjackGame mygame = BlackjackGame.getInstance();

        mygame.initializeGame();
        do {
            mygame.shuffle();
            mygame.getBets();
            mygame.dealCards();
            mygame.printStatus();
            mygame.checkBlackjack();
            mygame.hitOrStand();
            mygame.dealerPlays();
            mygame.settleBets();
            mygame.printMoney();
            mygame.clearHands();
        } while (mygame.playAgain());
    }
}