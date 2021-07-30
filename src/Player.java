import java.util.ArrayList;

public class Player {

    private int money;
    private String name;
    private ArrayList<Card> hand;
    private boolean highCard = false;
    private boolean pair = false;
    private boolean secondPair = false;
    private boolean twoPair = false;
    private boolean trips = false;
    private boolean straight = false;
    private boolean flush = false;
    private boolean fullHouse = false;
    private boolean straightFlush = false;
    private boolean quads = false;
    private boolean royalFlush = false;
    private String bestHand = "High Card";
    private int high;
    private int low;
    private Card top;

    public Player(String name, int buyin) {
        hand = new ArrayList<>(2);
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("Please enter a valid name");
        } else {
            this.name = name;
            this.money = buyin;
        }
    }

    public Player(String name) {
        hand = new ArrayList<>(2);
        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("Please enter a valid name");
        } else {
            this.name = name;
            this.money = 1000;
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> newHand) {
        hand = newHand;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int newMoney) {
        money = newMoney;
    }

    public String getName() {
        return name;
    }

    public void setBestHand(String bestHand) {
        this.bestHand = bestHand;
    }

    public String getBestHand() {
        return bestHand;
    }

    public boolean isHighCard() {
        return highCard;
    }

    public void setHighCard(boolean highCard) {
        this.highCard = highCard;
    }

    public boolean isPair() {
        return pair;
    }

    public void setPair(boolean pair) {
        this.pair = pair;
    }

    public boolean isSecondPair() {
        return secondPair;
    }

    public void setSecondPair(boolean secondPair) {
        this.secondPair = secondPair;
    }

    public boolean isTwoPair() {
        return twoPair;
    }

    public void setTwoPair(boolean twoPair) {
        this.twoPair = twoPair;
    }

    public boolean isTrips() {
        return trips;
    }

    public void setTrips(boolean trips) {
        this.trips = trips;
    }

    public boolean isStraight() {
        return straight;
    }

    public void setStraight(boolean straight) {
        this.straight = straight;
    }

    public boolean isFlush() {
        return flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    public boolean isFullHouse() {
        return fullHouse;
    }

    public void setFullHouse(boolean fullHouse) {
        this.fullHouse = fullHouse;
    }

    public boolean isStraightFlush() {
        return straightFlush;
    }

    public void setStraightFlush(boolean straightFlush) {
        this.straightFlush = straightFlush;
    }

    public boolean isQuads() {
        return quads;
    }

    public void setQuads(boolean quads) {
        this.quads = quads;
    }

    public boolean isRoyalFlush() {
        return royalFlush;
    }

    public void setRoyalFlush(boolean royalFlush) {
        this.royalFlush = royalFlush;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public Card getTop() {
        return top;
    }

    public void setTop(Card top) {
        this.top = top;
    }
}
