import java.util.*;

public class Player implements Comparable<Player> {
    private ArrayList<Card> combinedSeven;
    private ArrayList<Card> bestFive;
    private ArrayList<Card> hand;

    private ArrayList<Integer> rankList;

    private int[] ranks = new int[15];
    private int[] suits = new int[4];

    public ArrayList<Card> getBestFive() {
        return this.bestFive;
    }

    public ArrayList<Card> getCombinedSeven(){
        return this.combinedSeven;
    }

    public void clear() {
        this.combinedSeven.clear();
        this.bestFive.clear();
        this.hand.clear();
        this.rankList.clear();
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public Player() {
        this.combinedSeven = new ArrayList<Card>();
        this.hand = new ArrayList<Card>();
        this.bestFive = new ArrayList<Card>();
        this.rankList = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getRankList() {
        return this.rankList;
    }

    public void addCard(Card card) {
        this.combinedSeven.add(card);
        hand.add(card);
    }

    public void checkNumbers() {
        for (int i = 0; i < this.ranks.length; i++) {
            this.ranks[i] = 0;
        }

        for (Card card : combinedSeven) {
            this.ranks[card.intRank()]++;
        }
    }

    public void checkSuits() {
        for (int i = 0; i < this.suits.length; i++) {
            this.suits[i] = 0;
        }

        for (Card card : combinedSeven) {
            switch (card.suit()) {
                case "Clubs":
                    this.suits[0]++;
                    break;
                case "Diamonds":
                    this.suits[1]++;
                    break;
                case "Hearts":
                    this.suits[2]++;
                    break;
                default:
                    this.suits[3]++;
            }
        }
    }

    public void addCardsByRank(int rank) {
        for (Card card : this.combinedSeven) {
            if (card.intRank() == rank) {
                this.bestFive.add(card);
            }
        }
    }

    public void addCardsBySuit(String suit) {
        int count = 1;
        for (Card card : this.combinedSeven) {
            if (card.suit().equals(suit) && count <= 5) {
                this.bestFive.add(card);
                count++;
            }
        }
    }

    public void evaluateHand() {
        Collections.sort(this.combinedSeven, Collections.reverseOrder());
        this.checkSuits();
        this.checkNumbers();

        if (checkFlush()) {
            if (checkStraightFlush()) {
                return;
            }
            return;
        }

        if (checkStraight()) {
            return;
        }

        switch (this.highestNoOfMultipleCards()) {
            case 4:
                fourOfAKind();
                break;
            case 3:
                triple();
                break;
            case 2:
                pair();
                break;
            default:
                noPair();
        }
    }

    public boolean checkFlush() {
        for (int i = 0; i < this.suits.length; i++) {
            if (this.suits[i] >= 5) {
                switch (i) {
                    case 0:
                        addCardsBySuit("Clubs");
                        break;
                    case 1:
                        addCardsBySuit("Diamonds");
                        break;
                    case 2:
                        addCardsBySuit("Hearts");
                        break;
                    default:
                        addCardsBySuit("Spades");
                }
                this.rankList.clear();
                this.rankList.add(5);
                for (Card card : this.bestFive) {
                    this.rankList.add(card.intRank());
                }
                return true;
            }
        }
        return false;
    }


    public boolean checkStraight() {
        int highestRank = 0;
        boolean straight = false;
        for (int x = 2; x < 11; x++) {
            if (this.ranks[x] >= 1 && this.ranks[x + 1] >= 1 && this.ranks[x + 2] >= 1 && this.ranks[x + 3] >= 1 && this.ranks[x + 4] >= 1) {
                straight = true;
                highestRank = x + 4;
                break;
            }
        }
        if (straight) {
            this.rankList.clear();
            this.rankList.add(4);
            this.rankList.add(highestRank);

            for (int i = highestRank; i > highestRank - 5; i--) {
                this.addCardsByRank(i);
            }

            ArrayList<Card> duplicates = new ArrayList<Card>();
            for (int i = 0; i < this.bestFive.size() - 1; i++) {
                if (this.bestFive.get(i).intRank() == this.bestFive.get(i + 1).intRank()) {
                    duplicates.add(this.bestFive.get(i));
                }
            }
            this.bestFive.removeAll(duplicates);
            return true;
        }
        return false;
    }

    public boolean checkStraightFlush() {
        for (int i = 0; i < this.ranks.length; i++) {
            this.ranks[i] = 0;
        }
        for (Card card : this.bestFive) {
            this.ranks[card.intRank()] = 1;

        }
        if (checkStraight()) {
            this.rankList.remove(0);
            this.rankList.add(0, 8);
            return true;
        }
        return false;
    }


    public int highestNoOfMultipleCards() {
        this.checkNumbers();

        int mostCards = 1;
        int highestRank = 0;
        for (int i = 0; i < this.ranks.length; i++) {
            if (this.ranks[i] >= mostCards) {
                mostCards = this.ranks[i];
                highestRank = i;
            }
        }
        addCardsByRank(highestRank);
        this.combinedSeven.removeAll(this.bestFive);
        return mostCards;
    }

    public void fourOfAKind() {
        this.rankList.add(7);
        this.rankList.add(this.bestFive.get(0).intRank());
        this.bestFive.add(this.combinedSeven.get(0));
        this.rankList.add(this.combinedSeven.get(0).intRank());
    }

    public void triple() {

        int nextMostCards = this.highestNoOfMultipleCards();

        if (nextMostCards == 3) {

            this.bestFive.remove(5);
            this.rankList.add(6);
            this.rankList.add(this.bestFive.get(0).intRank());
            this.rankList.add(this.bestFive.get(3).intRank());
        } else if (nextMostCards == 2) {

            this.rankList.add(6);
            this.rankList.add(this.bestFive.get(0).intRank());
            this.rankList.add(this.bestFive.get(3).intRank());
        } else {
            this.bestFive.add(this.combinedSeven.get(0));

            this.rankList.add(3);
            this.rankList.add(this.bestFive.get(0).intRank());
            this.rankList.add(this.bestFive.get(3).intRank());
            this.rankList.add(this.bestFive.get(4).intRank());
        }
    }

    public void pair() {
        int nextMostCards = this.highestNoOfMultipleCards();
        if (nextMostCards == 2) {
            this.bestFive.add(this.combinedSeven.get(0));

            this.rankList.add(2);
            this.rankList.add(this.bestFive.get(0).intRank());
            this.rankList.add(this.bestFive.get(2).intRank());
            this.rankList.add(this.bestFive.get(4).intRank());
        } else {
            this.highestNoOfMultipleCards();
            this.highestNoOfMultipleCards();

            this.rankList.add(1);
            this.rankList.add(this.bestFive.get(0).intRank());
            this.rankList.add(this.bestFive.get(2).intRank());
            this.rankList.add(this.bestFive.get(3).intRank());
            this.rankList.add(this.bestFive.get(4).intRank());

        }
    }

    public void noPair() {
        this.highestNoOfMultipleCards();
        this.highestNoOfMultipleCards();
        this.highestNoOfMultipleCards();
        this.highestNoOfMultipleCards();

        this.rankList.add(0);
        this.rankList.add(this.bestFive.get(0).intRank());
        this.rankList.add(this.bestFive.get(1).intRank());
        this.rankList.add(this.bestFive.get(2).intRank());
        this.rankList.add(this.bestFive.get(3).intRank());
        this.rankList.add(this.bestFive.get(4).intRank());

    }


    public void print() {
        System.out.println("Best Five " + this.bestFive);
        System.out.println("Combined Seven" + this.combinedSeven);
        System.out.println("Rank List " + this.rankList);
    }

    public void printRank() {
        System.out.println("Ranks :");
        for (int i = 0; i < ranks.length; i++) {
            System.out.print(this.ranks[i] + " ");
        }
        System.out.println();
    }

    public String toString() {
        return this.hand.get(0) + " " + this.hand.get(1);
    }

    public int compareTo(Player player) {
        ArrayList<Integer> compared = player.getRankList();
        ArrayList<Integer> current = this.getRankList();

        int length = (compared.size() < current.size()) ? compared.size() : current.size();
        for (int i = 0; i < length; i++) {
            if (current.get(i).compareTo(compared.get(i)) != 0) {
                return current.get(i).compareTo(compared.get(i));
            }
        }
        return 0;
    }

    public String printType() {
        Integer type = this.rankList.get(0);
        switch (type) {
            case 0:
                return ("High Card");
            case 1:
                return ("One Pair");
            case 2:
                return ("Two Pairs");
            case 3:
                return ("Three of A Kind");
            case 4:
                return ("Straight");
            case 5:
                return ("Flush");
            case 6:
                return ("Full House");
            case 7:
                return ("Four of A Kind");
            case 8:
                return ("Straight Flush");
            default:
                return ("error");
        }
    }
}
