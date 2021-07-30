import java.util.ArrayList;

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
                                for (int v = d; v > b; v--) {
                                    if (flopArr[v] == flopArr[b]) {
                                        tableTrips = true;
                                        tableP = false;
                                    } else {
                                        tableTwoP = true;
                                        tableP = false;
                                    }
                                }
                            } else {
                                tableP = true;
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
                            player.setPair(true);
                        } else if (firstP == 2) {
                            player.setTrips(true);
                            if (secondP >= 1 && second.getRank() != first.getRank()) {
                                player.setFullHouse(true);
                            }
                        } else if (firstP == 3) {
                            player.setQuads(true);
                        }
                    }
                    if (second.getRank() == check.getRank()) {
                        secondP++;
                        if (secondP == 1 && firstP != 1) {
                            player.setPair(true);
                        }
                        if (secondP == 1 && firstP == 1 && first.getRank() != second.getRank()) {
                            player.setTwoPair(true);
                        } else if (secondP == 2) {
                            player.setTrips(true);
                            if (firstP >= 1 && second.getRank() != first.getRank()) {
                                player.setFullHouse(true);
                            }
                        } else if (secondP == 3){
                            player.setQuads(true);
                        }
                    }
                }

                //Pocket pair
                if (first.getRank() == second.getRank()) {
                    player.setPair(true);
                    if (firstP == 1) {
                        player.setTrips(true);
                    } else if (firstP == 2) {
                        player.setQuads(true);
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

                if (flopSuit == 5) {
                    player.setFlush(true);
                    player.setStraight(false);
                    player.setTrips(false);
                    player.setTwoPair(false);
                    player.setPair(false);
                } else if (sameSuit1 == 5 || sameSuit2 == 5) {
                    player.setFlush(true);
                    player.setStraight(false);
                    player.setTrips(false);
                    player.setTwoPair(false);
                    player.setPair(false);
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
