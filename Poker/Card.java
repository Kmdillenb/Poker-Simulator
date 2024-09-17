public class Card {
    private int number;
    private String suit;
    private String title;

    public Card() {
        number = 1;
        suit = "None";
        title = "Number";
    }

    // Returns the number of the card
    public int getNumber() {
        return number;
    }

    // Sets the number of the card
    public void setNumber(int number) {
        this.number = number;

        if (number == 10) {
            title = "Jack";
        }
        if (number == 11) {
            title = "Queen";
        } else if (number == 12) {
            title = "King";
        } else if (number == 13) {
            title = "Ace";
        }
    }

    // Returns the suit of the card
    public String getsuit() {
        return suit;
    }

    // Sets the suit of the card
    public void setsuit(String suit) {
        this.suit = suit;
    }

    // Prints out the features of the card (ex: 10 of Hearts)
    public void callingCard() {
        if (number < 10) {
            System.out.print(number + 1);
        } else if (number >= 10) {
            System.out.print(title);
        }
        System.out.print(" of " + suit + "");
    }

}
