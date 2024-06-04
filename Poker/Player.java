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
        hand = new ArrayList<Card>();
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

    public void determineStrength(ArrayList<Card> current_flop) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        HashMap<String, Integer> suits = new HashMap<String, Integer>();
        HashMap<Integer, Integer> Numbers = new HashMap<Integer, Integer>();
        boolean is_Straight = false;
        int temp_highest = 0;
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

        }

        // Find Highest Card
        for (int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).getNumber() > highestCard) {
                highestCard = allCards.get(i).getNumber();
            }
        }
        highestRank = "High Card";
        // do something
        // look for pairs
        int num_pairs = 0;
        // THIS IS CURRENTLY BEING CHANGED, MAKE SURE THE I's REFLECT THIS
        for (int i : Numbers.keySet()) {
            // Get ride of handStrength check, makes three of a kind impossible?
            if (Numbers.get(i) == 2 && handStrength < 1) {
                handStrength = 1;
                num_pairs += 1;
                pairStrengths.add(i);

                highestRank = "Pair";
            } else if (Numbers.get(i) == 3 && handStrength < 3) {
                // Three of a kind
                handStrength = 3;
                threeStrengths.add(i);
                highestRank = "Three of a kind";
            } else if (Numbers.get(i) == 4 && handStrength < 7) {
                handStrength = 7;
                // Important, see if this impacts stuff later on
                highestCard = i;
                highestRank = "Four of a kind";
            }
            if (num_pairs == 2 && handStrength < 2) {
                handStrength = 2;
                highestRank = "Two Pair";
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
        }

        // Straight
        if (Numbers.size() >= 5 && handStrength < 4) {
            // Straight_Nums is a sorted version of Numbers
            ArrayList<Integer> straight_Nums = new ArrayList<>();
            straight_Nums.addAll(Numbers.keySet());
            Collections.sort(straight_Nums);

            // Needs reworking
            // Update: think it works but maybe not
            is_Straight = false;
            boolean temp_Straight = false;
            temp_highest = 0;
            // runs 1-3 times, depending on the size of the flop
            for (int i = 0; i <= straight_Nums.size() - 5; i++) {
                for (int j = 0; j < 4; j++) {
                    temp_Straight = true;
                    temp_highest = straight_Nums.get(j + i) + 1;
                    if (straight_Nums.get(j + i) + 1 != straight_Nums.get(i + j + 1)) {
                        temp_Straight = false;
                        break;
                    }
                    if (j == 3 && temp_Straight == true) {
                        is_Straight = true;
                        highestCard = temp_highest;

                    }
                }
                // if (straight_Nums.get(i) + 1 != straight_Nums.get(i + 1)) {
                // chances -= 1;
                // }
            }

            if (is_Straight == true) {
                handStrength = 4;
                highestRank = "Straight";
                highestCard = temp_highest;

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

        // flush
        if (suits.size() <= 3 && handStrength < 5) {
            for (int i : suits.values()) {
                if (i >= 5) {
                    handStrength = 5;
                    highestRank = "Flush";

                    if (is_Straight == true) {
                        handStrength = 8;
                        highestRank = "Straight Flush";

                        if (temp_highest == 13) {
                            handStrength = 9;
                            highestRank = "Royal Flush!";
                        }
                    }
                    break;
                }
            }
        }

    }

}
