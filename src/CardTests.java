import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardTests {
    private Game game;

    public CardTests() {}

    @Before
    public void initialize() {
        game = new Game();
    }



    @Test
    public void testPocketPair() {
        Player player = game.getPlayers().get(0);
        ArrayList<Card> hand = new ArrayList<>();
        game.dealHand();
        hand.add(new Card("Ace", "Spades"));
        hand.add(new Card("Ace", "Hearts"));
        player.setHand(hand);
        ArrayList<Card> flop = game.getDealer().getFlop();
        ArrayList<Card> newFlop = new ArrayList<>();
        newFlop.add(new Card("Two", "Spades"));
        newFlop.add(new Card("Two", "Spades"));
        newFlop.add(new Card("Two", "Spades"));
        newFlop.add(new Card("Two", "Spades"));
        newFlop.add(new Card("Two", "Spades"));
        game.determineHand();
        assertEquals(player.getBestHand(), "Pair");
    }
}
