public class Card {
    private int number;
    private String suit;
    private String title;

    public Card() {
        number = 1;
        suit = "huh";
        title = "special";
    }

    public int getNumber() {
        return number;
    }

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

    public String getsuit() {
        return suit;
    }

    public void setsuit(String suit) {
        this.suit = suit;
    }

    public void callingCard() {
        if (number < 10) {
            System.out.print(number + 1);
        } else if (number >= 10) {
            System.out.print(title);
        }
        System.out.print(" of " + suit + "");
    }

}
