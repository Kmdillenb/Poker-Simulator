import java.util.*;

public class Player {
    private ArrayList<Card> hand;
    // Goes up for every rank
    private int handStrength;
    private int highestCard;
    private String highestRank;

    public Player() {
        hand = new ArrayList<Card>();
        highestCard = 0;
        handStrength = 0;
        highestRank = "Null";
    }

    public void createHand(ArrayList<Card> cards) {
        hand = cards;
    }

    public void AddCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> returnHand() {
        ArrayList<Card> temp = hand;
        return temp;

    }

    public void showHand() {
        hand.get(0).callingCard();
        System.out.print(" and ");
        hand.get(1).callingCard();
        System.out.print(" >>> Highest rank: " + highestRank);
        System.out.print(" >>> Highest Strength: " + handStrength);
        System.out.print(" >>> Highest Number: " + highestCard);
        System.out.println();
    }

    public int getRanking() {
        return handStrength;
    }

    public int getHighest() {
        return highestCard;
    }

    public String getRankTitle() {
        return highestRank;
    }

    // Determines the highest rank a player currently is aswell as the highest card
    // for that rank
    public void determineStrength(ArrayList<Card> current_flop) {
        ArrayList<Card> allCards = new ArrayList<Card>();

        // Figure this part out (list of numbers attached to a key of the suit name)
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
            // checks if in hashmap, if so increments 1, if no it adds it to hashmap
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
        // do something
        // look for pairs
        int num_pairs = 0;
        // THIS IS CURRENTLY BEING CHANGED, MAKE SURE THE I's REFLECT THIS
        for (int i : Numbers.keySet()) {
            // Get ride of handStrength check, makes three of a kind impossible?
            if (Numbers.get(i) == 2) {
                num_pairs += 1;
                pairStrengths.add(i);

                if (handStrength < 1) {
                    System.out.println("occured");
                    handStrength = 1;
                    highestRank = "Pair";
                    highestCard = i;
                }

            } else if (Numbers.get(i) == 3 && handStrength < 3) {
                // Three of a kind
                handStrength = 3;
                threeStrengths.add(i);
                highestRank = "Three of a kind";
                highestCard = i;
            } else if (Numbers.get(i) == 4 && handStrength < 7) {
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
        // puts the strength of the pairs in order so It's easier to compare them with
        // other players at the end
        Collections.sort(pairStrengths);
        Collections.sort(threeStrengths);

        // Full House? (need to test)
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
            // runs 1-3 times, depending on the size of the flop
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
                // if (straight_Nums.get(i) + 1 != straight_Nums.get(i + 1)) {
                // chances -= 1;
                // }
            }

            if (is_Straight == true) {
                handStrength = 4;
                highestRank = "Straight";

                // Need to figure this out elsewhere

                // if (suits.size() == 1) {
                // handStrength = 8;
                // highestRank = "Straight Flush";

                // if (straight_Nums.get(straight_Nums.size() - 1) == 13) {
                // handStrength = 9;
                // highestRank = "Royal Flush!";
                // }
                // }
            }
        }
        // Gonna try making a map instead and seeing if that works
        for (ArrayList<Integer> i : suitToInteger.values()) {
            System.out.println(i.size());
            if (i.size() >= 5) {
                Collections.sort(i);
                if (handStrength < 5) {
                    highestCard = i.get(i.size() - 1);
                    handStrength = 5;
                    highestRank = "Flush";
                }

                // checking for straight
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
