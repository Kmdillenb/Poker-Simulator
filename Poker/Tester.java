import java.util.*;

public class Tester {
    public static void main(String[] args) {
        System.out.println("Hola!");
        Deck hands = new Deck();
        hands.CreateDeck();

        String answer = "";
        String replay = "";
        int numPlayers = 0;
        Scanner scnr = new Scanner(System.in);

        System.out.println("Would you like to play a round? [y for Yes || n for No]");
        while (!(answer.toLowerCase().equals("y")) && !(answer.toLowerCase().equals("n"))) {
            answer = scnr.nextLine();
        }

        while (answer.toLowerCase().equals("y") && !(replay.toLowerCase().equals("n"))) {
            hands.Shuffle();
            replay = "";
            numPlayers = 0;

            // Determines number of players
            System.out.println("How many players?[1-20]");
            while (numPlayers < 1 || numPlayers > 21) {
                numPlayers = scnr.nextInt();
            }
            for (int i = 0; i < numPlayers; i++) {
                hands.addPlayer(new Player());
            }

            // Determines if the player wants to play again
            System.out.println("Play Again? [y or n]");
            while (!(replay.toLowerCase().equals("y")) && !(replay.toLowerCase().equals("n"))) {
                replay = scnr.nextLine();
            }

        }

        hands.CreateDeck();
        hands.Shuffle();
        hands.CallCards();
        hands.deckSize();
        hands.addPlayer(new Player());
        hands.addPlayer(new Player());

        hands.DealCards();
        hands.revealCard();
        hands.revealCard();
        hands.revealCard();
        hands.showFlop();
        hands.playerStrengths();
        hands.playerHands();
        hands.revealCard();

        scnr.close();
    }

}