import java.time.Duration;
import java.time.Instant;
import java.util.*;

// Used to simulate a given hand and return that hands chances of winning and losing
public class simulation {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulator!");
        Deck hands = new Deck();
        hands.CreateDeck();
        // Determines if the simulation runs
        String answer = "";

        // How many times the simulation runs
        int numRuns = 20000;

        // What the simulation returns (1 == win, 0 == draw, -1 == loss)
        int simResult;

        // Determines if the simulation runs again
        String replay = "";

        // The number of players in the current simulation
        int numPlayers = 0;

        // The current set flop
        int cardsOnFlop = 0;

        // Suit of a card
        String suit = "";

        // Strength of a card
        int strength = 0;

        // Win/Loss/Tie Rates
        int[] results = { 0, 0, 0 };

        long before = 0;

        // Cards in the set players hands
        ArrayList<Card> setHand = new ArrayList<>();

        // Cards that are currently set in the flop
        ArrayList<Card> setFlop = new ArrayList<>();

        Scanner scnr = new Scanner(System.in);

        System.out.println("Would you like to run a simulation? [y || n]");
        while (!(answer.toLowerCase().equals("y")) && !(answer.toLowerCase().equals("n"))) {
            answer = scnr.nextLine();
        }

        while (answer.toLowerCase().equals("y")) {
            replay = "";
            numPlayers = 0;

            // Finds number of players
            System.out.println("How many players?[1-20]");
            while (numPlayers < 1 || numPlayers > 21) {
                numPlayers = scnr.nextInt();
            }

            for (int i = 0; i < numPlayers; i++) {
                hands.addPlayer(new Player());
            }

            System.out.println("How many cards are on the flop? [0/3/4/5]");
            cardsOnFlop = scnr.nextInt();

            if (hands.deckSize() == 52) {
                hands.SetHand();
                hands.SetFlop(cardsOnFlop);
            }

            before = System.nanoTime();

            System.out.println("loading...");

            for (int i = 0; i < numRuns; i++) {
                simResult = simulator(hands, cardsOnFlop);
                if (simResult == 1) {
                    results[0] += 1;
                } else if (simResult == 0) {
                    results[1] += 1;
                } else {
                    results[2] += 1;
                }
            }

            answer = "n";

        }
        System.out.println("You won " + results[0] + " games.");
        System.out.println("You tied " + results[1] + " games.");
        System.out.println("You lost " + results[2] + " games.");

        System.out.println(
                "\n Your win percentage was: " + (Math.round(10000 * ((double) results[0]) / numRuns)) / 100.00 + "%");

        System.out.println(
                "\n Your win/tie percentage was: "
                        + (Math.round(10000 * ((double) (results[0] + results[1])) / numRuns)) / 100.00 + "%");

        System.out.println(
                "\n Your loss percentage was: " + (Math.round(10000 * ((double) results[2]) / numRuns)) / 100.00 + "%");

        long durationMs = (System.nanoTime() - before) / 1_000_000;
        System.out.println("\n" + durationMs + " ms");

        scnr.close();
    }

    public static int simulator(Deck hands, int cardsOnFlop) {
        // How many times the flop has to be revealed plus one for the game over part
        int revealsLeft = 0;
        if (cardsOnFlop == 0) {
            revealsLeft = 4;
        } else {
            revealsLeft = 7 - cardsOnFlop;
        }
        hands.DealCards();
        for (int i = 0; i < revealsLeft; i++) {

            hands.revealCard();
        }

        return hands.getResult();
    }
}
