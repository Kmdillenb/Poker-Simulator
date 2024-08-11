import java.util.*;

public class simulation {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulator!");
        Deck hands = new Deck();
        hands.CreateDeck();
        // determines if the simulation runs
        String answer = "";

        // determines if the simulation runs again
        String replay = "";

        // the number of players in the current simulation
        int numPlayers = 0;

        // the current set flop
        int cardsOnFlop = 0;

        // suite of a card
        String suite = "";

        // Strength of a card
        int strength = 0;

        // Win/Loss/Tie Rates
        ArrayList<Integer> results = new ArrayList<>();
        results.add(0);
        results.add(0);
        results.add(0);

        Scanner scnr = new Scanner(System.in);

        System.out.println("Would you like to run a simulation?");
        while (!(answer.toLowerCase().equals("y")) && !(answer.toLowerCase().equals("n"))) {
            answer = scnr.nextLine();
        }

        while (answer.toLowerCase().equals("y")) {
            replay = "";
            numPlayers = 0;

            // finds number of players
            System.out.println("How many players?[1-20]");
            while (numPlayers < 1 || numPlayers > 21) {
                numPlayers = scnr.nextInt();
            }

        }

        scnr.close();
    }

    public int simulator() {
        return 0;
    }
}
