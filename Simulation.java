import java.io.*;
import java.util.*;

public class Simulation {

    public static void main(String[] args){
        Deck deck = new Deck();
        IO ioControl = new IO(deck);

        Game game = new Game(deck, ioControl);

        Scanner sc = new Scanner(System.in);

        ArrayList<Card> defaultHand = new ArrayList<Card>();
        instructions();

        while(true) {
            String inputLine = sc.nextLine();
            defaultHand = ioControl.defaultHands(inputLine);
            if(defaultHand != null){
                break;
            }
            System.out.println("Error with input");
        }

        System.out.print("Number of Trials: ");
        int noOfRuns = sc.nextInt();
        System.out.print("Number of Players: ");
        int noPlayers = sc.nextInt();
        sc.close();

        game.initializePlayers(noPlayers);
        ioControl.printHeader();

        game.setDefaultHand(defaultHand);
        game.simulation(noOfRuns);

        System.out.print("Win rate of ");
        for(Card card: defaultHand){
            System.out.print(card.stringRank() + " " + card.suit().charAt(0) + " ");
        }
        float winRate = (float)game.defaultWinCount()/(float)noOfRuns;
        System.out.print(": " + winRate);
    }

    public static void instructions(){
        System.out.println("Enter Default Hand (2 cards)");
        System.out.println("Rank: 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, Ace");
        System.out.println("Suit: Clubs, Diamonds, Hearts, Spades");
        System.out.println("Format: Rank Suit Rank Suit (separated by one whitespace)");
        System.out.println("Suits can be entered with initials only (C,D,H,S)");
        System.out.println("Ex: Ace Hearts 3 Spades");
        System.out.println("OR  Ace H 3 S");
        System.out.println();
        System.out.print("Input: ");
    }
}
