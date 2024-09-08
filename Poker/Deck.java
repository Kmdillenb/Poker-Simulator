import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    private ArrayList<Card> flop;
    private ArrayList<Player> players;
    private ArrayList<Card> setHand;
    private ArrayList<Card> setFlop;
    private int simResult;

    public Deck() {
        deck = new ArrayList<Card>();
        flop = new ArrayList<Card>();
        players = new ArrayList<Player>();
        setHand = new ArrayList<Card>();
        setFlop = new ArrayList<Card>();
        simResult = 0;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void CreateDeck() {
        String suit = "Diamonds";
        Card template;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                suit = "Clubs";
            } else if (i == 2) {
                suit = "Hearts";
            } else if (i == 3) {
                suit = "Spades";
            }
            for (int j = 1; j <= 13; j++) {
                template = new Card();
                template.setNumber(j);
                template.setsuit(suit);
                deck.add(template);
            }
        }
    }

    public void CallCards() {
        for (int i = 0; i < deck.size(); i++) {
            deck.get(i).callingCard();
            System.out.println();
        }
    }

    public void Shuffle() {
        ArrayList<Card> shuffled = new ArrayList<Card>();
        int random = 0;
        Random rand = new Random();
        while (deck.size() > 0) {
            random = rand.nextInt(deck.size());
            shuffled.add(deck.get(random));
            deck.remove(random);
        }
        deck = shuffled;
    }

    public void addPlayer(Player person) {
        players.add(person);
    }

    // Gives out hands at the beginning of the game
    public void DealCards() {
        if (deck.size() + setHand.size() + setFlop.size() < 52) {
            RegainCards();
        }
        Shuffle();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).HandSize() == 0) {
                if (i == 0 && setHand.size() > 0) {
                    players.get(0).createHand(setHand);
                } else {
                    players.get(i).AddCard(deck.get(deck.size() - 1));
                    deck.remove(deck.size() - 1);
                    players.get(i).AddCard(deck.get(deck.size() - 1));
                    deck.remove(deck.size() - 1);
                }
            }
        }

        // calculates players current Strength level
        playerStrengths();
    }

    // Creates the players hand for the simulation
    // Edit it so that you can change the options and run again
    public void SetHand() {
        System.out.println("Creating players Hand!");
        if (setHand.size() > 0) {
            for (Card card : setHand) {
                deck.add(card);
                Shuffle();
            }
            setHand.clear();
        }

        setHand.add(RemoveCard());
        setHand.add(RemoveCard());

        players.get(0).createHand(setHand);

    }

    // Creates the current flop for the simulation
    public void SetFlop(int flopSize) {
        if (setFlop.size() > 0) {
            System.out.println("Create the current flop!");
            for (Card card : setFlop) {
                deck.add(card);
                Shuffle();
            }
        }

        for (int i = 0; i < flopSize; i++) {
            setFlop.add(RemoveCard());
        }
    }

    // Returns result of simulation
    public int getResult() {
        return simResult;
    }

    // Removes a card from the deck and returns it

    public Card RemoveCard() {
        String cardSuit;
        int cardRank;
        boolean cardFound = false;
        Scanner scnr = new Scanner(System.in);
        while (cardFound == false) {
            System.out.println("enter the cards Suit (Hearts/Clubs/Diamonds/Spades)");
            cardSuit = scnr.nextLine();
            cardSuit = cardSuit.toLowerCase();
            System.out.println("enter the cards Rank (2-10,11 = Jack, 12 = Queen, 13 == King, 14 = Ace)");
            cardRank = scnr.nextInt() - 1;
            scnr.nextLine();
            for (Card card : deck) {
                if ((card.getsuit().toLowerCase()).equals(cardSuit) && card.getNumber() == cardRank) {
                    deck.remove(card);
                    return card;
                }
            }
        }
        scnr.close();
        Card placeholder = new Card();
        return placeholder;
    }

    public void RegainCards() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).resetStrength();

            // Should stop program from taking set hand back into deck
            if (setHand.size() > 0 && i == 0) {
                continue;
            }

            for (int j = 0; j < players.get(i).returnHand().size(); j++) {
                deck.add(players.get(i).returnHand().get(j));
            }
            players.get(i).createHand(new ArrayList<Card>());
        }

        for (int i = 0; i < flop.size(); i++) {
            // should stop program from taking set flop
            if (i < setFlop.size()) {
                continue;
            }
            deck.add(flop.get(i));
        }
        flop.clear();
        if (setHand.size() == 0) {
            players.clear();
        }
        Shuffle();
    }

    public void playerHands() {
        for (int i = 0; i < players.size(); i++) {
            System.out.print("Player " + (i + 1) + ": ");
            players.get(i).showHand();
        }
    }

    public void playerStrengths() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).determineStrength(flop);
        }
    }

    // returns the size of the deck
    public int deckSize() {
        return deck.size();
    }

    public void revealCard() {
        // When flop is first Shown
        if (flop.size() == 0) {

            // adds cards from setFlop
            if (setFlop.size() > 0) {
                for (Card card : setFlop) {
                    flop.add(card);
                }
            }

            while (flop.size() < 3) {
                flop.add(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
            }
        }

        // Adds another card until 5 has been reached in which the game will instead end
        else if (flop.size() < 5) {
            flop.add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
        } else {
            endGame();
        }
        playerStrengths();
    }

    public ArrayList<Card> returnFlop() {
        return flop;
    }

    public void showFlop() {
        // Used for Testing
        // flop.clear();
        // flop.add(deck.get(42));
        // flop.add(deck.get(11));
        // flop.add(deck.get(19));
        // flop.add(deck.get(10));
        // flop.add(deck.get(4));
        // players.get(0).AddCard(deck.get(16));
        // players.get(0).AddCard(deck.get(38));
        // players.get(1).AddCard(deck.get(51));
        // players.get(1).AddCard(deck.get(10));

        System.out.print("Flop is: ");
        for (int i = 0; i < flop.size(); i++) {
            flop.get(i).callingCard();
            if (i != flop.size() - 1) {
                System.out.print(" || ");
            }
        }
        System.out.println("");
    }

    public void endGame() {

        // Determine winner
        int Winners = 0;

        // Highest rank out of the players
        int TopRank = 0;

        // Highest Card out of the high ranked players
        int TopCard = 0;

        // array of everyones ranks
        ArrayList<Integer> rankings = new ArrayList<Integer>();

        // array of everyones high cards
        ArrayList<Integer> highCards = new ArrayList<Integer>();

        // Just players with the highest rank
        ArrayList<Integer> TopRanked = new ArrayList<Integer>();

        // Fills in score data - possibly to print it out
        for (int i = 0; i < players.size(); i++) {
            rankings.add(players.get(i).getRanking());
            highCards.add(players.get(i).getHighest());
        }

        // Creates the top rank
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRanking() > TopRank
                    || (players.get(i).getRanking() == TopRank && players.get(i).getHighest() > TopCard)) {
                TopRank = players.get(i).getRanking();
                TopCard = players.get(i).getHighest();
                TopRanked.clear();
                TopRanked.add(i);
            } else if (players.get(i).getRanking() == TopRank && players.get(i).getHighest() == TopCard) {
                TopRanked.add(i);
            }
        }
        Winners = TopRanked.size();
        if (setHand.size() == 0) {
            // System.out.print("\033[H\033[2J");
        }
        System.out.flush();
        if (setHand.size() == 0) {
            System.out.println("The Winners Are: ");

            for (int i = 0; i < Winners; i++) {
                System.out.println(
                        "Player " + (TopRanked.get(i) + 1) + " Who won with: "
                                + players.get(TopRanked.get(i)).getRankTitle()
                                + " and a highest card of: " + players.get(TopRanked.get(i)).getHighest());
            }
        }

        // Determines outcome of simulation round
        if (TopRanked.get(0) == 0) {
            if (Winners > 1) {
                simResult = 0;
            } else {
                simResult = 1;
            }
        } else {
            simResult = -1;
        }

        RegainCards();
    }

}
