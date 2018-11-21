import java.util.*;
import java.io.*;

public class Game
{
    private ArrayList<Card> cardsDrawn;
    private ArrayList<Card> defaultHand;
    private Deck deck;
    public static ArrayList<Player> players;
    private Player defaultPlayer;
    private Player winner;

    private int defaultWin;

    private IO ioControl;
    public Game(Deck deck, IO ioControl){
        cardsDrawn = new ArrayList<Card>();
        this.deck = deck;
        this.players = new ArrayList<Player>();
        this.ioControl = ioControl;
        this.defaultWin = 0;
    }

    public void setDefaultHand(ArrayList<Card> defaultHand){
        this.defaultHand = defaultHand;
    }
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public void simulation(int noRuns){
        for(int i = 0; i<noRuns; i++){
            this.oneGame();

            //this.printResults();
            ioControl.printToFile(this.cardsDrawn, this.winner);
        }
        ioControl.close();
    }


    public void initializePlayers(int number) {
        for (int i = 0; i < number; i++) {
            this.players.add(new Player());
        }
        this.defaultPlayer = this.players.get(0);
    }

    public void newCards(){
        defaultPlayer.clear();
        for(Card card: defaultHand){
            defaultPlayer.addCard(card);
            deck.findAndRemoveCard(card.stringRank(),card.suit());
        }

        for(Player player: this.players){
            if(player != defaultPlayer) {
                player.clear();
                player.addCard(deck.drawOne());
                player.addCard(deck.drawOne());
            }
        }
    }

    public void oneGame(){
        deck.buildDeck();
        deck.shuffleDeck();
        cardsDrawn.clear();
        newCards();

        for(int i=0; i<5; i++){
            cardsDrawn.add(deck.drawOne());
        }

        for(Player player: players){
            player.getCombinedSeven().addAll(cardsDrawn);
            player.evaluateHand();
        }
        this.winner = Collections.max(this.players);
        if(this.players.indexOf(this.winner) == 0){
            this.defaultWin++;
        }
    }

    public int defaultWinCount(){
        return this.defaultWin;
    }
    public void printResults(){
        System.out.println("Cards Drawn " + this.cardsDrawn);
        for(Player player: players){
            System.out.println("Player Hand " + player.toString());
            System.out.println("Player " + player.getBestFive());
            System.out.println();
        }
        System.out.println("Winner: " + this.winner);
    }
}
