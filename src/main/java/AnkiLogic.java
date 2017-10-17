import model.Card;
import model.CardStatus;
import utils.AnkiUtils;

import java.util.List;
import java.util.Scanner;

/**
 * Created by ferran on 17/10/17.
 */
public class AnkiLogic {

    public List<Card> loadCardsStatus(String fileName){
        List<Card> listOfCards = AnkiUtils.readCardsFromFile(fileName);

//        System.out.println("Loaded these cards:");
//        for(Card card : listOfCards){
//            System.out.println(card.getStatus() + "\t | " + card.getQuestion() + "\t | " + card.getAnswer());
//        }
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
        AnkiUtils.saveStatusFile(fileName, listOfCards);

        if(AnkiUtils.allTheCardsSettled(listOfCards)){
            System.out.println("Congratulate! The cards are studied properly!");
        } else {
            System.out.println("Good job! Goodbye!");
        }
    }
}
