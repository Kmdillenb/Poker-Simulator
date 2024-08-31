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

        // suit of a card
        String suit = "";

        // Strength of a card
        int strength = 0;

        // Win/Loss/Tie Rates
        ArrayList<Integer> results = new ArrayList<>();
        results.add(0);
        results.add(0);
        results.add(0);

        // cards in the set players hands
        ArrayList<Card> setHand = new ArrayList<>();

        // Cards that are currently set in the flop
        ArrayList<Card> setFlop = new ArrayList<>();

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

            // TO DO (the whole simulating thing)

            System.out.println("Create your hand.");
            setHand.add(hands.RemoveCard());
            setHand.add(hands.RemoveCard());

            System.out.println("How many cards are on the flop?");
            cardsOnFlop = scnr.nextInt();

            System.out.println("Create the flop.");
            for (int i = 0; i < cardsOnFlop; i++) {
                setFlop.add(hands.RemoveCard());
            }

        }

        scnr.close();
    }

    public ArrayList<Card> setHand(Deck hands) {
        ArrayList<Card> playerHand = new ArrayList<Card>();
        playerHand.add(hands.RemoveCard());
        playerHand.add(hands.RemoveCard());
        return playerHand;

    }

    public int simulator() {
        return 0;
    }
}
