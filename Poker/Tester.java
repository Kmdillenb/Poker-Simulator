import java.util.*;

// Used to run a single game of poker
public class Tester {
    public static void main(String[] args) {
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

        scnr.close();
    }

}