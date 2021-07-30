import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private static ArrayList<Player> players = new ArrayList<>();
    private Dealer dealer;
    private Card[] checkStraight;

    public Game() {
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));
        players.add(new Player("Player 4"));
        players.add(new Player("Player 5"));
        dealer = new Dealer();
    }

    public void dealHand() {
        dealer.deal(players);
        System.out.println(players.get(0).getName() + ": " + players.get(0).getHand());
        System.out.println(players.get(1).getName() + ": " + players.get(1).getHand());
        System.out.println(players.get(2).getName() + ": " + players.get(2).getHand());
        System.out.println(players.get(3).getName() + ": " + players.get(3).getHand());
        System.out.println(players.get(4).getName() + ": " + players.get(4).getHand());
        dealer.flop();
        dealer.add();
        dealer.add();
        System.out.println("Flop: " + dealer.getFlop());
    }

    public void determineHand() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        Player player3 = players.get(2);
        Player player4 = players.get(3);
        Player player5 = players.get(4);
        ArrayList<Card> flop = dealer.getFlop();
        player1.setHighCard(true);
        Card[] flopArr = new Card[flop.size()];
        int a = 0;
        for (Card c : flop) {
            flopArr[a] = c;
            a++;
        }

        for (Player player : players) {
            Card first = player.getHand().get(0);
            Card second = player.getHand().get(1);

            //Check if table flop has a pair
            boolean tableP = false;
            boolean tableTwoP = false;
            boolean tableTrips = false;
            for (int b = 0; b < flopArr.length - 1; b++) {
                for (int d = b + 1; d < flopArr.length; d++) {
                    if (flopArr[b].getRank() == flopArr[d].getRank()) {
                        //System.out.println(flopArr[b] + " + " + flopArr[d]);
                        if (flopArr[b].getRank() == first.getRank() ||
                        flopArr[b].getRank() == second.getRank()) {
                            continue;
                        } else {
                            if (tableP) {
                                for (int v = d - 1; v > b; v--) {
                                    if (flopArr[v] == flopArr[b]) {
                                        tableTrips = true;
                                        player.getTop()[3] = flopArr[v];
                                        tableP = false;
                                    } else {
                                        tableTwoP = true;
                                        player.getTop()[2] = flopArr[v];
                                        if (player.getTop()[1].getRank() > player.getTop()[2].getRank()) {
                                            Card temp = player.getTop()[2];
                                            player.getTop()[2] = player.getTop()[1];
                                            player.getTop()[1] = temp;
                                        }
                                        tableP = false;
                                    }
                                }
                            } else {
                                tableP = true;
                                player.getTop()[1] = flopArr[b];
                            }
                        }
                    }
                }
            }

                int firstP = 0;
                int secondP = 0;

                //Check for pair or two pair
                int size = flop.size();
                for (int e = 0; e < size; e++) {
                    Card check = flop.get(e);
                    if (first.getRank() == check.getRank()) {
                        firstP++;

                        if (firstP == 1) {
                            if (tableTrips) {
                                player.getTop()[2] = first;
                            } else if (tableTwoP) {
                                if (player.getTop()[2].getRank() < first.getRank()) {
                                    player.getTop()[1] = player.getTop()[2];
                                    player.getTop()[2] = first;
                                    player.getTop()[6] = player.getTop()[3];
                                } else if (player.getTop()[2].getRank() > first.getRank()
                                && player.getTop()[1].getRank() < first.getRank()) {
                                    player.getTop()[1] = first;
                                    player.getTop()[6] = player.getTop()[3];
                                }
                            } else if (tableP) {
                                if (player.getTop()[1].getRank() > first.getRank()) {
                                    player.getTop()[2] = player.getTop()[1];
                                    player.getTop()[1] = first;
                                }
                            } else {
                                player.getTop()[1] = first;
                            }
                            player.setPair(true);

                        } else if (firstP == 2) {
                            player.setTrips(true);
                            player.getTop()[3] = first;
                            if (secondP >= 1 && second.getRank() != first.getRank()) {
                                player.setFullHouse(true);
                                if (firstP >= 1 && second.getRank() != first.getRank()) {
                                    player.setFullHouse(true);
                                    if (firstP > secondP) {
                                        player.getTop()[6] = first;
                                        player.getTop()[3] = first;
                                        player.getTop()[1] = second;
                                    } else if (firstP == secondP) {
                                        if (first.getRank() >= second.getRank()) {
                                            player.getTop()[6] = first;
                                            if (first.getRank() != second.getRank()) {
                                                player.getTop()[3] = first;
                                                player.getTop()[1] = second;
                                            }
                                        }
                                    } else {
                                        player.getTop()[6] = second;
                                        player.getTop()[3] = second;
                                        player.getTop()[1] = first;
                                    }
                                }
                            }
                        } else if (firstP == 3) {
                            player.setQuads(true);
                            player.getTop()[8] = first;
                        }
                    }


                    if (second.getRank() == check.getRank()) {
                        secondP++;
                        if (secondP == 1 && firstP != 1) {
                            if (tableTrips) {
                                player.getTop()[2] = second;
                            } else if (tableTwoP) {
                                if (player.getTop()[2].getRank() < second.getRank()) {
                                    player.getTop()[1] = player.getTop()[2];
                                    player.getTop()[2] = second;
                                } else if (player.getTop()[2].getRank() > second.getRank()
                                        && player.getTop()[1].getRank() < second.getRank()) {
                                    player.getTop()[1] = second;
                                }
                            } else if (tableP) {
                                if (player.getTop()[1].getRank() > second.getRank()) {
                                    player.getTop()[2] = player.getTop()[1];
                                    player.getTop()[1] = second;
                                }
                            } else {
                                player.getTop()[1] = second;
                            }
                            player.setPair(true);
                        }

                        if (secondP == 1 && firstP == 1 && first.getRank() != second.getRank()) {
                            player.setTwoPair(true);
                            if (second.getRank() > first.getRank()) {
                                player.getTop()[2] = second;
                                player.getTop()[1] = first;
                            } else {
                                player.getTop()[1] = second;
                                player.getTop()[2] = first;
                            }

                        } else if (secondP == 2) {
                            player.setTrips(true);
                            player.getTop()[3] = second;

                            if (firstP >= 1 && second.getRank() != first.getRank()) {
                                player.setFullHouse(true);
                                if (firstP > secondP) {
                                    player.getTop()[6] = first;
                                    player.getTop()[3] = first;
                                    player.getTop()[1] = second;
                                } else if (firstP == secondP) {
                                    if (first.getRank() >= second.getRank()) {
                                        player.getTop()[6] = first;
                                        if (first.getRank() != second.getRank()) {
                                            player.getTop()[3] = first;
                                            player.getTop()[1] = second;
                                        }
                                    }
                                } else {
                                    player.getTop()[6] = second;
                                    player.getTop()[3] = second;
                                    player.getTop()[1] = first;
                                }
                            }
                        } else if (secondP == 3){
                            player.setQuads(true);
                            player.getTop()[8] = second;
                        }
                    }
                }

                //Pocket pair
                if (first.getRank() == second.getRank()) {
                    player.setPair(true);
                    if (firstP == 1) {
                        player.setTrips(true);
                        player.getTop()[3] = first;
                    } else if (firstP == 2) {
                        player.setQuads(true);
                        player.getTop()[8] = first;
                    } else if (player.getTop()[1] != null) {
                        if (first.getRank() > player.getTop()[1].getRank()) {
                            player.getTop()[2] = first;
                        } else {
                            player.getTop()[2] = player.getTop()[1];
                            player.getTop()[1] = first;
                        }
                    }
                }

                //Check for straight
                checkStraight = new Card[7];
                int i = 0;
                for (Card c : flop) {
                    checkStraight[i] = c;
                    i++;
                }
                checkStraight[5] = first;
                checkStraight[6] = second;

                //Sort flop + hand
                int length = checkStraight.length;
                for (int j = 0; j < length - 1; j++) {
                    int min = j;
                    for (int k = j + 1; k < length; k++) {
                        if (checkStraight[k].compareTo(checkStraight[k], checkStraight[min]) > 0) {
                            min = k;
                        }
                    }
                    Card temp = checkStraight[min];
                    checkStraight[min] = checkStraight[j];
                    checkStraight[j] = temp;
                }

                //Check if cards are sequential
                int seq = 1;
                for (int z = 0; z < length - 1; z++)
                    if (checkStraight[z + 1].getRank() == checkStraight[z].getRank() - 1) {
                        seq++;
                    } else {
                        seq = 1;
                    }

                if (seq == 5) {
                    player.setStraight(true);
                    player.getTop()[4] = checkStraight[0];
                    player.setTrips(false);
                    player.setTwoPair(false);
                    player.setPair(false);
                }

                //Check for flush
                Card[] flushOrder = new Card[7];
                int count = 0;
                int sameSuit1;
                int sameSuit2 = 0;
                int flopSuit = 1;

                if (first.getSuit() == second.getSuit()) {
                    sameSuit1 = 2;
                } else {
                    sameSuit1 = 1;
                    sameSuit2 = 1;
                }

                for (Card c : flop) {
                    if (c.getSuit() == first.getSuit()) {
                        sameSuit1++;
                    } else if (c.getSuit() == second.getSuit()) {
                        sameSuit2++;
                    }
                }

                //check if the flop has a flush
                String f = flop.get(0).getSuit();
                int next = 1;
                while (flop.get(next).getSuit() == f && next < flop.size()) {
                    flopSuit++;
                    next++;
                }

                if (flopSuit == 5 && (sameSuit1 < 5 || sameSuit2 < 5)) {
                    player.setFlush(true);
                    player.setStraight(false);
                    player.setTrips(false);
                    player.setTwoPair(false);
                    player.setPair(false);
                    //Order array with player hand
                    for (Card c : flop) {
                        flushOrder[count] = c;
                        count++;
                    }
                    int length1 = flushOrder.length;
                    for (int j = 0; j < length1 - 1; j++) {
                        int min = j;
                        for (int k = j + 1; k < length1; k++) {
                            if (flushOrder[k].compareTo(flushOrder[k], flushOrder[min]) > 0) {
                                min = k;
                            }
                        }
                        Card temp = flushOrder[min];
                        flushOrder[min] = flushOrder[j];
                        flushOrder[j] = temp;
                    }
                    player.getTop()[5] = flushOrder[0];


                } else if (sameSuit1 >= 5 || sameSuit2 == 5) {
                    player.setFlush(true);
                    player.setStraight(false);
                    player.setTrips(false);
                    player.setTwoPair(false);
                    player.setPair(false);
                    if (sameSuit1 == sameSuit2) {
                        for (Card c : flop) {
                            if (c.getSuit() == first.getSuit()) {
                                flushOrder[count] = c;
                                count++;
                            }
                        }
                        flushOrder[count] = first;
                        flushOrder[count + 1] = second;

                    } else if (sameSuit1 == 5) {
                        for (Card c : flop) {
                            if (c.getSuit() == first.getSuit()) {
                                flushOrder[count] = c;
                                count++;
                            }
                        }
                        flushOrder[count] = first;
                    } else {
                        for (Card c : flop) {
                            if (c.getSuit() == second.getSuit()) {
                                flushOrder[count] = c;
                                count++;
                            }
                        }
                        flushOrder[count] = second;
                    }

                    int length1 = flushOrder.length;
                    for (int j = 0; j < length1 - 1; j++) {
                        int min = j;
                        for (int k = j + 1; k < length1; k++) {
                            try {
                                if (flushOrder[k].compareTo(flushOrder[k], flushOrder[min]) > 0) {
                                    min = k;
                                }
                            } catch (Exception e) {
                                System.out.println("null error");
                            }
                        }
                        Card temp = flushOrder[min];
                        flushOrder[min] = flushOrder[j];
                        flushOrder[j] = temp;
                    }
                    player.getTop()[5] = flushOrder[0];
                }

                //check for full house
                if (player.isTrips()) {

                    //if player has pocket pair, check flop for a different pair
                    if (first.compareTo(first,second) == 0) {
                        for (int b = 0; b < flopArr.length - 1; b++) {
                            for (int d = 1; d < flopArr.length; d++) {
                                if (flopArr[d] == flopArr[b]) {
                                    player.setPair(true);
                                }
                                d++;
                            }
                            b++;
                        }
                    } else {
                        int card1count = 1;
                        int card2count = 1;
                        for (int d = 0; d < flopArr.length; d++) {
                            if (first.getRank() == flopArr[d].getRank()) {
                                card1count++;
                            } else if (second.getRank() == flopArr[d].getRank()) {
                                card2count++;
                            }
                        }
                        if (card1count >= 2 && card2count >= 2) {
                            player.setFullHouse(true);
                        }
                    }

                }

            //Check for combos with pairs/trips already on the table
            if (player.isTrips() && tableTrips || player.isTrips() && tableP ||
                player.isTrips() && tableTwoP) {
                player.setFullHouse(true);
            } else if (player.isTwoPair() && tableTrips) {
                player.setFullHouse(true);
            } else if (player.isPair() && tableTrips) {
                player.setFullHouse(true);
            } else if (player.isPair() && tableP || player.isPair() && tableTwoP) {
                player.setTwoPair(true);
            } else if (player.isHighCard() && tableTrips) {
                player.setTrips(true);
            } else if (player.isHighCard() && tableTwoP) {
                player.setTwoPair(true);
            } else if (player.isHighCard() && tableP) {
                player.setPair(true);
            }

            if (player.isFullHouse()) {
                player.getTop()[6] = player.getTop()[3];
            }

            //Print out hand
            if (player.isRoyalFlush()) {
                player.setBestHand("Royal Flush");
                player.setHandRank(9);
            } else if (player.isQuads()) {
                player.setBestHand("Four of a Kind");
                player.setHandRank(8);
            } else if (player.isStraightFlush()) {
                player.setBestHand("Straight Flush");
                player.setHandRank(7);
            } else if (player.isFullHouse()) {
                player.setBestHand("Full House");
                player.setHandRank(6);
            } else if (player.isFlush()) {
                player.setBestHand("Flush");
                player.setHandRank(5);
            } else if (player.isStraight()) {
                player.setBestHand("Straight");
                player.setHandRank(4);
            } else if (player.isTrips()) {
                player.setBestHand("Three of a Kind");
                player.setHandRank(3);
            } else if (player.isTwoPair()) {
                player.setBestHand("Two Pairs");
                player.setHandRank(2);
            } else if (player.isPair()) {
                player.setBestHand("Pair");
                player.setHandRank(1);
            } else {
                player.setBestHand("High Card");
            }

            //Compare hands to determine winner

        }
        System.out.println(player1.getName() + ": " + player1.getBestHand());
        System.out.println(player2.getName() + ": " + player2.getBestHand());
        System.out.println(player3.getName() + ": " + player3.getBestHand());
        System.out.println(player4.getName() + ": " + player4.getBestHand());
        System.out.println(player5.getName() + ": " + player5.getBestHand());
        determineWinner();
    }



    private void determineWinner() {
        ArrayList<Player> playerRankings = new ArrayList<>(players.size());
        for (int i = 9; i >= 0; i--) {
            for (Player player : players) {
                if (player.getHandRank() == i) {
                    playerRankings.add(player);
                }
            }
        }
        int high = playerRankings.get(0).getHandRank();
        //System.out.println(high);
        for (Player player : players) {
            if (player.getHandRank() != high) {
                playerRankings.remove(player);
            }
        }

//        for (Player player : playerRankings) {
//            System.out.println(player.getName());
//        }

        if (playerRankings.size() == 1) {
            System.out.println(playerRankings.get(0).getName() + " wins!");
        } else {
            Player currWinner = playerRankings.get(0);
           // for (Player player : playerRankings)
                //if (player.getTop()[high].getRank() > currWinner.getTop()[high].getRank()) {
                 //   currWinner = player;
                //}
           // System.out.println(currWinner.getName() + " wins!");
        }
        for (Player player : playerRankings) {
            try {
                System.out.println(Arrays.toString(player.getTop()));
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.dealHand();
        game.determineHand();
    }

    public static ArrayList<Player> getPlayers() { return players; }

    public Dealer getDealer() {
        return dealer;
    }
}
