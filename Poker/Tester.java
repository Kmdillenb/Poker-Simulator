public class Tester {
    public static void main(String[] args) {
        System.out.println("Hola!");

        Deck hands = new Deck();
        hands.CreateDeck();
        // hands.Shuffle();
        hands.CallCards();
        hands.deckSize();
        hands.addPlayer(new Player());
        hands.DealCards();

        hands.showFlop();
        hands.playerStrengths();
        hands.playerHands();

    }

}