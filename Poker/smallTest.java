public class smallTest {
    public static void main(String[] args) {
        System.out.println("Hi!");
        Deck hands = new Deck();
        hands.CreateDeck();
        hands.RemoveCard();
        hands.RemoveCard();
        hands.CallCards();
        hands.deckSize();
    }
}
