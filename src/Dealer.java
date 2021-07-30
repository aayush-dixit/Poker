import java.util.ArrayList;

public class Dealer {
    private Deck D;
    private ArrayList<Card> cards;
    private ArrayList<Player> players = Game.getPlayers();
    private ArrayList<Card> flop;

    public Dealer() {
        D = new Deck();
        cards = D.getDeck();
    }

    public void deal(ArrayList<Player> players) {
        D.shuffleDeck();
        cards = D.getDeck();
        for (Player player : players) {
           Card one = D.drawCard(cards);
           Card two = D.drawCard(cards);
           ArrayList<Card> tempHand = new ArrayList<>();
           tempHand.add(one);
           tempHand.add(two);
           player.setHand(tempHand);
        }
    }

    public void flop() {
        flop = new ArrayList<>(5);
        for (int i = 0; i < 3; i++) {
            flop.add(D.drawCard(cards));
        }
    }

    public void add() {
        flop.add(D.drawCard(cards));
    }

    public ArrayList<Card> getFlop() {
        return flop;
    }

    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        dealer.D.shuffleDeck();
        dealer.deal(dealer.players);
    }

    public void setFlop(ArrayList<Card> flop) {
        this.flop = flop;
    }
}
