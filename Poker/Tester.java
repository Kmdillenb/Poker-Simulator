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
            if (hands.deckSize() == 52) {
                hands.SetHand();
                hands.SetFlop(0);
            }

            hands.DealCards();

            // The game in progress
            for (int i = 0; i < 5; i++) {
                if (i > 0) {
                    hands.revealCard();
                }
                if (i < 4) {
                    hands.playerStrengths();
                    hands.showFlop();
                    hands.playerHands();

                    System.out.println("Continue? [y or n]");
                    replay = "";
                    while (!(replay.toLowerCase().equals("y")) && !(replay.toLowerCase().equals("n"))) {
                        replay = scnr.nextLine();
                    }
                    if (replay.toLowerCase().equals("n")) {
                        break;
                    }
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            }
            replay = "";

            // Determines if the player wants to play again
            System.out.println("Play Again? [y or n]");
            while (!(replay.toLowerCase().equals("y")) && !(replay.toLowerCase().equals("n"))) {
                replay = scnr.nextLine();
            }
            if (replay.toLowerCase().equals("n")) {
                break;
            }
            hands.RegainCards();
        }
        System.out.println("Game Over!");

        // hands.CreateDeck();
        // hands.Shuffle();
        // hands.CallCards();
        // hands.deckSize();
        // hands.addPlayer(new Player());
        // hands.addPlayer(new Player());

        // hands.DealCards();
        // hands.revealCard();
        // hands.revealCard();
        // hands.revealCard();
        // hands.showFlop();
        // hands.playerStrengths();
        // hands.playerHands();
        // hands.revealCard();

        scnr.close();
    }

}