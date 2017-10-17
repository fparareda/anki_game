import model.Card;

import java.util.List;

/**
 * Created by ferran on 17/10/17.
 */
public class Main {

    private static AnkiLogic ankiLogic = new AnkiLogic();
    private static String fileName = "files/deckOfCards.txt";

    public static void main(String[] args) {

        System.out.println("Welcome to the Anki game!");

        List<Card> listOfCards = ankiLogic.loadCardsStatus(fileName);
        if(listOfCards != null){
            ankiLogic.playAnki(listOfCards);
            ankiLogic.endGame(fileName, listOfCards);
        } else {
            System.out.println("\nThe file is not in the right place! Please, be sure the location of the file.");
        }
    }
}
