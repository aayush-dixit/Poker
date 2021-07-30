import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;
    private Random random;
    private Card temp;
    private String[] suits;
    private String[] names;

    public Deck() {
        deck = new ArrayList<>(52);
        temp = new Card(null, null);
        suits = temp.getSuits();
        names = temp.getNames();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card(names[j], suits[i]));
            }
        }
    }

    public void shuffleDeck() {
        random = new Random();
        for (int i = 0; i < deck.size(); i++) {
            swap(i,random.nextInt(52));
        }
    }

    private void swap(int i, int swap) {
        Card temp = deck.get(swap);
        deck.set(swap,deck.get(i));
        deck.set(i, temp);
    }

    public Card drawCard(ArrayList<Card> deck) {
        return deck.remove(0);
    }

    public String toString() {
        return deck.toString();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

}
