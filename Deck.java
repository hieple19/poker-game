import java.util.*;

public class Deck
{
    private ArrayList<Card> currentDeck;    // Stores shuffled deck of cards
    private ArrayList<Card> defaultDeck;
    public static String[] suitsList = {"Hearts", "Clubs", "Spades", "Diamonds"};
    public static String[] ranksList  = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    public Deck(){
        this.defaultDeck = new ArrayList<Card>();
        this.currentDeck = new ArrayList<Card>();
        this.buildDeck();
        this.shuffleDeck();
    }

    public ArrayList<Card> defaultDeck(){
        return this.defaultDeck;
    }

    public ArrayList<Card> currentDeck(){
        return this.currentDeck;
    }

    /* Build default deck */
    public void buildDeck(){
        this.defaultDeck.clear();
        for(int i = 0; i<this.suitsList.length; i++){
            String currentSuit = this.suitsList[i];
            for(int j = 0; j<this.ranksList.length; j++){
                Card card = new Card(this.ranksList[j],currentSuit);
                this.defaultDeck.add(card);
            }
        }
    }

    public void shuffleDeck(){
        Random random = new Random();
        currentDeck.clear();
        for(int i=0; i<52; i++){
            int index = random.nextInt(defaultDeck.size());
            currentDeck.add(defaultDeck.remove(index));
        }
    }

    public Card drawOne(){
        return currentDeck.remove(0);
    }

    public Card findAndRemoveCard(String rank, String suit){
        Card cardFound = null;
        for (Card card: this.currentDeck){
            if(card.stringRank().equals(rank) && card.suit().equals(suit)){
                cardFound = card;
                break;
            }
        }
        if(cardFound != null) {
            this.currentDeck.remove(cardFound);
            return cardFound;
        }

        System.out.println("Could not find card");
        return null;
    }
}
