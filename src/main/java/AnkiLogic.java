import model.Card;
import model.CardStatus;
import utils.AnkiUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Created by ferran on 17/10/17.
 */
public class AnkiLogic {

    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RESET = "\u001B[0m";

    public List<Card> loadCardsStatus(String fileName){
        List<Card> listOfCards = AnkiUtils.readCardsFromFile(fileName);
        return listOfCards;
    }

    public void playAnki(List<Card> listOfCards){
        Scanner scan = new Scanner(System.in);
        int usersAnswer;

        for(Card card : listOfCards){
            if(!CardStatus.GREEN_BOX.equals(card.getStatus())){
                AnkiUtils.currentStatusAnki(listOfCards);

                System.out.println("\nQuestion: " + card.getQuestion());
                System.out.println("(1) Do you know the answer");
                System.out.println("(2) Do you know a part of the answer");
                System.out.println("(3) You don't know the answer");

                System.out.println("\nAnswer: ");
                usersAnswer = scan.nextInt();

                card.setAnswerStatus(usersAnswer);
            }
        }
    }

    public void endGame(String fileName, List<Card> listOfCards){
        if(AnkiUtils.allTheCardsSettled(listOfCards)){
            System.out.println(ANSI_BOLD + "\nCongratulate! The cards are studied properly!" + ANSI_RESET);
        } else {
            updateCardsWithRules(listOfCards);
            System.out.println(ANSI_BOLD + "\nGood job! Goodbye!" + ANSI_RESET);
        }
        AnkiUtils.saveStatusFile(fileName, listOfCards);
    }


    /**
     * Based on the rules, when the session is over :
     *
     * The cards in the red box will be studied again the same day
     * The cards in the orange box will be studied again the next (we can put it into red box)
     * The cards in the green box, will be studied again two days later (we can put it into the orange box)
     */
    public void updateCardsWithRules(List<Card> listOfCards) {
        for(Card card : listOfCards){
            if(CardStatus.GREEN_BOX.name().equals(card.getStatus().name())){
                card.setStatus(CardStatus.ORANGE_BOX);
            } else if(CardStatus.ORANGE_BOX.name().equals(card.getStatus().name())){
                card.setStatus(CardStatus.RED_BOX);
            }
        }
    }
}
