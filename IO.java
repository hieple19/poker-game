import java.util.*;
import java.io.*;

public class IO {
    private ArrayList<Card> defaultHands;
    private Deck deck;
    private PrintWriter writer;

    public IO(Deck deck) {
        this.defaultHands = new ArrayList<Card>();
        this.deck = deck;
        try {
            this.writer = new PrintWriter(new File("results.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Error printing results.csv");
            System.exit(1);
        }
    }

    public void close() {
        writer.close();
    }

    public ArrayList<Card> defaultHands(String line) {
        String[] parts = line.split(" ");
        if(parts.length != 4){
            return null;
        }
        this.deck.buildDeck();
        this.deck.shuffleDeck();
        Card card1 = processCards(parts[0], parts[1]);
        Card card2 = processCards(parts[2], parts[3]);
        if(card1 == null || card2 == null){
            return null;
        }
        defaultHands.add(card1);
        defaultHands.add(card2);
        return this.defaultHands;
    }

    public Card processCards(String rankInput, String suitInput) {
        String newCardRank = "";
        String newCardSuit = "";
        for (String rankPossible : Deck.ranksList) {
            if (rankInput.toLowerCase().trim().equals(rankPossible.toLowerCase().trim())) {
                newCardRank = rankPossible;
                break;
            }
        }

        for (String suitPossible : Deck.suitsList) {
            if (suitPossible.toLowerCase().trim().equals(suitInput.toLowerCase().trim())) {
                newCardSuit = suitPossible;
                break;
            }
            if (suitInput.trim().length() == 1) {
                if (suitInput.toLowerCase().equals(suitPossible.toLowerCase().substring(0, 1))) {
                    newCardSuit = suitPossible;
                    break;
                }
            }
        }

        if (newCardSuit.equals("") || newCardRank.equals("")) {
            return null;
        }

        return this.deck.findAndRemoveCard(newCardRank, newCardSuit);
    }

    public void printHeader() {

        writer.write("Cards Drawn");
        writer.write(',');
        writer.write("Winner");
        writer.write(',');
        for (int i = 0; i < Game.players.size(); i++) {
            writer.write("P" + (i + 1));
            writer.write(',');
            writer.write("Hand of P" + (i + 1));
            writer.write(',');
        }
        writer.write("\n");
    }

    public void printToFile(ArrayList<Card> cardsDrawn, Player winner) {
        for (Card card : cardsDrawn) {
            this.writer.write(card.toString() + " ");
        }
        this.writer.write(',');
        this.writer.write("P" + (Game.players.indexOf(winner) + 1));
        this.writer.write(',');
        for (Player player : Game.players) {
            StringBuilder sb = new StringBuilder();
            for (Card card : player.getHand()) {
                sb.append(card);
                sb.append(" ");
            }
            sb.append(',');
            for (Card card : player.getBestFive()) {
                sb.append(card);
                sb.append(" ");
            }
            sb.append(" - " + player.printType());
            sb.append(',');
            writer.write(sb.toString());
        }
        writer.write("\n");
    }
}
