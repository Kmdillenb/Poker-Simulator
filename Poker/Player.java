import java.util.*;

public class Player {
    private ArrayList<Card> hand;
    private int handStrength;
    private int highestCard;
    private String highestRank;

    public Player() {
        hand = new ArrayList<Card>();
        highestCard = 0;
        handStrength = 0;
        highestRank = "Null";
    }

    // Creates a players hand given an ArrayList of cards
    public void createHand(ArrayList<Card> cards) {
        hand = cards;
    }

    // Adds cards to a players hand
    public void AddCard(Card card) {
        hand.add(card);
    }

    // Returns the players current hand
    public ArrayList<Card> returnHand() {
        ArrayList<Card> temp = hand;
        return temp;

    }

    // Returns size of hand
    public Integer HandSize() {
        return hand.size();
    }

    // Shows the contents of the players current hand
    public void showHand() {
        hand.get(0).callingCard();
        System.out.print(" and ");
        hand.get(1).callingCard();
        System.out.print(" >>> Highest rank: " + highestRank);
        System.out.print(" >>> Highest Strength: " + handStrength);
        System.out.print(" >>> Highest Number: " + highestCard);
        System.out.println();
    }

    // Returns the players current rank (ie what pairings the players hand has)
    public int getRanking() {
        return handStrength;
    }

    // Returns the highest value card the player has (depends on the rank)
    public int getHighest() {
        return highestCard;
    }

    // Returns the title of the players current rank (ex: Full House)
    public String getRankTitle() {
        return highestRank;
    }

    // Resets the players handStrength and highestCard values
    public void resetStrength() {
        handStrength = 0;
        highestCard = 0;
    }

    // Determines the highest rank a player currently is aswell as the highest card
    // for that rank
    public void determineStrength(ArrayList<Card> current_flop) {
        ArrayList<Card> allCards = new ArrayList<Card>();

        HashMap<String, ArrayList<Integer>> suitToInteger = new HashMap<String, ArrayList<Integer>>();

        HashMap<String, Integer> suits = new HashMap<String, Integer>();

        // The Number of each Number in the decks
        HashMap<Integer, Integer> Numbers = new HashMap<Integer, Integer>();
        boolean is_Straight = false;
        // For when you need to compare the strengths of the pairs
        ArrayList<Integer> pairStrengths = new ArrayList<Integer>();
        ArrayList<Integer> threeStrengths = new ArrayList<Integer>();

        allCards.addAll(hand);
        allCards.addAll(current_flop);

        // Finding int values and suits
        for (int i = 0; i < allCards.size(); i++) {
            // Checks if in hashmap, if so increments 1, if no it adds it to hashmap
            if (Numbers.get(allCards.get(i).getNumber()) == null) {
                Numbers.put(allCards.get(i).getNumber(), 1);
            } else {
                Numbers.put(allCards.get(i).getNumber(), Numbers.get(allCards.get(i).getNumber()) + 1);
            }

            if (suits.get(allCards.get(i).getsuit()) == null) {
                suits.put(allCards.get(i).getsuit(), 1);
            } else {
                suits.put(allCards.get(i).getsuit(), suits.get(allCards.get(i).getsuit()) + 1);
            }
            if (suitToInteger.get(allCards.get(i).getsuit()) == null) {
                suitToInteger.put(allCards.get(i).getsuit(), new ArrayList<Integer>());
            }
            suitToInteger.get(allCards.get(i).getsuit()).add(allCards.get(i).getNumber());
        }

        // Find Highest Card
        if (handStrength == 0) {
            highestRank = "High Card";
            for (int i = 0; i < allCards.size(); i++) {
                if (allCards.get(i).getNumber() > highestCard) {
                    highestCard = allCards.get(i).getNumber();
                }
            }
        }
        // Looks for pairs
        int num_pairs = 0;
        for (int i : Numbers.keySet()) {

            if (Numbers.get(i) == 2) {
                num_pairs += 1;
                pairStrengths.add(i);

                if (handStrength < 1) {
                    handStrength = 1;
                    highestRank = "Pair";
                    highestCard = i;
                }

            }

            if (Numbers.get(i) >= 3) {
                threeStrengths.add(i);
            }

            if (Numbers.get(i) >= 3 && handStrength < 3) {
                // Three of a kind
                handStrength = 3;
                highestRank = "Three of a kind";
                highestCard = i;
            }
            if (Numbers.get(i) == 4 && handStrength < 7) {
                handStrength = 7;
                highestCard = i;
                highestRank = "Four of a kind";
            }
            if (num_pairs == 2 && handStrength < 2) {
                handStrength = 2;
                highestRank = "Two Pair";
                highestCard = Math.max(pairStrengths.get(0), pairStrengths.get(1));
            }
        }
        // Puts the strength of the pairs in order so It's easier to compare them with
        // other players at the end
        Collections.sort(pairStrengths);
        Collections.sort(threeStrengths);

        if ((num_pairs >= 1 && handStrength == 3) && handStrength < 6) {
            handStrength = 6;
            highestRank = "Full House";
            highestCard = threeStrengths.get(0);
            for (int i = 0; i < pairStrengths.size(); i++) {
                if (pairStrengths.get(i) > highestCard) {
                    highestCard = pairStrengths.get(i);
                }
            }

        }

        // Straight
        if (Numbers.size() >= 5 && handStrength < 4) {
            // Straight_Nums is a sorted version of Numbers
            ArrayList<Integer> straight_Nums = new ArrayList<>();
            straight_Nums.addAll(Numbers.keySet());
            Collections.sort(straight_Nums);

            is_Straight = false;
            // Determines if the straight is also a straight flush
            boolean temp_Straight = false;
            // Runs 1-3 times, depending on the size of the flop
            for (int i = 0; i <= straight_Nums.size() - 5; i++) {
                for (int j = 0; j < 4; j++) {
                    temp_Straight = true;
                    if (straight_Nums.get(j + i) + 1 != straight_Nums.get(i + j + 1)) {
                        temp_Straight = false;
                        break;
                    }
                    if (j == 3 && temp_Straight == true) {
                        is_Straight = true;
                        highestCard = straight_Nums.get(j + i + 1);

                    }
                }
            }

            if (is_Straight == true) {
                handStrength = 4;
                highestRank = "Straight";

            }
        }

        for (ArrayList<Integer> i : suitToInteger.values()) {
            if (i.size() >= 5) {
                Collections.sort(i);
                if (handStrength < 5) {
                    highestCard = i.get(i.size() - 1);
                    handStrength = 5;
                    highestRank = "Flush";
                }

                // Checking for straight
                for (int j = 0; j < i.size() - 4; j++) {
                    if (i.get(0 + j) + 4 == i.get(4 + j)) {
                        highestCard = i.get(4 + j);
                        handStrength = 8;
                        highestRank = "Straight Flush";

                        if (highestCard == 13) {
                            handStrength = 9;
                            highestRank = "Royal Flush!";
                            break;
                        }
                    }
                }

            }
        }

    }
}
