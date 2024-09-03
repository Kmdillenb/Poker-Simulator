import java.util.*;

public class smallTest {
    public static void main(String[] args) {
        int number = 0;
        String word = "";
        Scanner scnr = new Scanner(System.in);
        while (word.equals("scnr") != true) {
            System.out.println("word");
            word = scnr.nextLine();
            System.out.println("number");
            number = scnr.nextInt();
            scnr.nextLine();
        }

        // System.out.println("Hi!");
        // Deck hands = new Deck();
        // hands.CreateDeck();
        // hands.RemoveCard();
        // hands.RemoveCard();
        // hands.CallCards();
        // hands.deckSize();
    }
}
