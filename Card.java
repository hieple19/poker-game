public class Card implements Comparable<Card> {
    private String suit;
    private String rank;

    public static void main(String[] args) {
        Card one = new Card("3", "Hearts");
        Card two = new Card("2", "Hearts");
        System.out.println(one.compareTo(two));
    }

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String suit() {
        return this.suit;
    }

    public String stringRank() {
        return this.rank;
    }

    public String toString() {
        return this.rank + this.suit.charAt(0) + " ";
    }

    public int intRank() {
        switch (this.rank) {
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "Jack":
                return 11;
            case "Queen":
                return 12;
            case "King":
                return 13;
            case "Ace":
                return 14;
            default:
                return -1;
        }
    }

    public int compareTo(Card card) {
        return ((Integer) this.intRank()).compareTo((Integer) card.intRank());
    }
}
