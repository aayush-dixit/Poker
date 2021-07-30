import java.util.Arrays;
public class Card {
    private String name;
    private int rank;
    private String suit;
    private String[] suits = {"spades", "clubs", "hearts", "diamonds"};
    private String[] names = {"two", "three", "four", "five", "six", "seven",
        "eight", "nine", "ten", "jack", "queen", "king", "ace"};

    public Card(String name, String suit) {
        this.name = name;
        this.suit = suit;
        this.rank = Arrays.asList(names).indexOf(name);
    }

    public String toString() {
        return name + " of " + suit;
    }

    public int compareTo(Card card1, Card card2) {
        return card1.rank - card2.rank;
    }

    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    public String[] getSuits() { return suits; }

    public String[] getNames() { return names; }

    public int getRank() {
        return rank;
    }
}
