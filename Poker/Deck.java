import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    private ArrayList<Card> flop;
    private ArrayList<Player> players;
    private ArrayList<Card> setHand;
    private ArrayList<Card> setFlop;

    public Deck() {
        deck = new ArrayList<Card>();
        flop = new ArrayList<Card>();
        players = new ArrayList<Player>();

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
        Shuffle();
        if (deck.size() != 52) {
            RegainCards();
        }

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).HandSize() == 0) {
                players.get(i).AddCard(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
                players.get(i).AddCard(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
            }
        }

        // calculates players current Strength level
        playerStrengths();
    }

    // Creates the players hand for the simulation
    public void SetHand() {
        setHand.add(RemoveCard());
        setHand.add(RemoveCard());

    }

    // Creates the current flop for the simulation
    public void SetFlop(int flopSize) {
        for (int i = 0; i < flopSize; i++) {
            setFlop.add(RemoveCard());
        }
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
            System.out.println("enter the cards Rank (2-10,11 = Jack, 12 = Queen, 13 == King, 14 = Ace)");
            cardRank = scnr.nextInt() - 1;
            scnr.close();
            for (Card card : deck) {
                if (card.getsuit().equals(cardSuit) && card.getNumber() == cardRank) {
                    deck.remove(card);
                    return card;
                }
            }
        }
        Card placeholder = new Card();
        return placeholder;
    }

    public void RegainCards() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).returnHand().size(); j++) {
                deck.add(players.get(i).returnHand().get(j));
            }
            players.get(i).createHand(new ArrayList<Card>());
        }

        for (int i = 0; i < flop.size(); i++) {
            deck.add(flop.get(i));
        }
        flop.clear();
        players.clear();
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

    // prints the size of the deck
    public void deckSize() {
        System.out.println(deck.size());
    }

    public void revealCard() {
        // When flop is first Shown
        if (flop.size() == 0) {
            for (int i = 0; i < 3; i++) {
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
        System.out.println("Game Over!");

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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("The Winners Are: ");

        for (int i = 0; i < Winners; i++) {
            System.out.println(
                    "Player " + (TopRanked.get(i) + 1) + " Who won with: "
                            + players.get(TopRanked.get(i)).getRankTitle()
                            + " and a highest card of: " + players.get(TopRanked.get(i)).getHighest());
        }

        RegainCards();
    }

}
