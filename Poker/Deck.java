import java.util.*;

public class Deck {
    private ArrayList<Card> deck;
    private ArrayList<Card> flop;
    private ArrayList<Player> players;

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

    public void DealCards() {
        // Shuffle();
        // if (deck.size() != 52) {
        // RegainCards();
        // }

        // for (int i = 0; i < players.size(); i++) {
        // players.get(i).AddCard(deck.get(deck.size() - 1));
        // deck.remove(deck.size() - 1);
        // players.get(i).AddCard(deck.get(deck.size() - 1));
        // deck.remove(deck.size() - 1);
        // }
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
        flop = new ArrayList<Card>();
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

    public void deckSize() {
        System.out.println(deck.size());
    }

    public void revealCard() {
        if (flop.size() < 3) {
            flop.add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
        } else {
            endGame();
        }
    }

    public ArrayList<Card> returnFlop() {
        return flop;
    }

    public void showFlop() {
        // Used for Testing
        flop.add(deck.get(0));
        flop.add(deck.get(13));
        flop.add(deck.get(26));
        flop.add(deck.get(39));
        flop.add(deck.get(51));
        players.get(0).AddCard(deck.get(10));
        players.get(0).AddCard(deck.get(9));
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
        int TopRank = 0;
        ArrayList<Integer> TopRanked = new ArrayList<Integer>();
        int TopCard = 0;
        ArrayList<Integer> rankings = new ArrayList<Integer>();
        ArrayList<Integer> highCards = new ArrayList<Integer>();
        for (int i = 0; i < players.size(); i++) {
            rankings.add(players.get(i).getRanking());
            highCards.add(players.get(i).getHighest());
        }

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRanking() > TopRank) {
                TopRank = players.get(i).getRanking();
                TopRanked.clear();
                TopRanked.add(i);
            } else if (players.get(i).getRanking() == TopRank) {
                TopRanked.add(i);
            }
        }
        // if (TopRanked.size() > 1) {
        // for (int i = 0; i < TopRanked.size(); i++) {
        // // TO DO
        // }
        // } else {
        // System.out.print("The Winner is Player " + TopRanked.get(0) + " winning
        // with:"
        // + players.get(TopRanked.get(0)).getRankTitle());
        // }

        RegainCards();
    }

}
