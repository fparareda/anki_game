package utils;

import model.Card;
import model.CardStatus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ferran on 17/10/17.
 */
public class AnkiUtils {

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_CYAN = "\u001B[36m";

        public static List<Card> readCardsFromFile(String fileName){
                List<Card> listOfCards = null;
                List<String> listOfSentences = readFile(fileName);
                if(listOfSentences != null){
                        listOfCards = convertSentencesToCards(listOfSentences);
                }
                return listOfCards;
        }

        private static List<String> readFile(String fileName){
                List<String> listOfSentences = null;
                File file = new File(fileName);
                if(file.exists()){
                        listOfSentences = new ArrayList<>();
                        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                                listOfSentences = stream.collect(Collectors.toList());
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                return listOfSentences;
        }

        private static List<Card> convertSentencesToCards(List<String> allSentences){
                List<Card> listOfCards = new ArrayList<>();

                for (String sentence : allSentences) {
                        Card card = convertSentenceToCard(sentence);
                        if(card != null){
                                listOfCards.add(card);
                        }
                }

                return listOfCards;
        }

        private static Card convertSentenceToCard(String sentence){
                String[] phrases = sentence.split("\\|");
                if(phrases.length == 2){        // This card does not have status
                        return new Card(CardStatus.RED_BOX, phrases[0], phrases[1]);
                } else if(phrases.length > 2){  // Card with a status
                        return new Card(getCardStatus(phrases[2]), phrases[0], phrases[1]);
                }
                return null;
        }

        private static CardStatus getCardStatus(String statusOfCard) {
                for(CardStatus status : CardStatus.values()){
                        if(status.name().equals(statusOfCard)){
                                return status;
                        }
                }
                return CardStatus.RED_BOX;
        }


        public static boolean allTheCardsSettled(List<Card> listOfCards) {
                for(Card card : listOfCards){
                        if(!CardStatus.GREEN_BOX.name().equals(card.getStatus().name())){
                                return false;
                        }
                }
                return true;
        }

        public static void currentStatusAnki(List<Card> listOfCards) {
                System.out.println(ANSI_BLUE + "----- Current tatus: -----"+ ANSI_RESET);

                System.out.println(ANSI_RED + "Red Box: "+ ANSI_RESET);
                printCardsByStatus(listOfCards, CardStatus.RED_BOX);

                System.out.println(ANSI_CYAN + "Orange Box: "+ ANSI_RESET);
                printCardsByStatus(listOfCards, CardStatus.ORANGE_BOX);

                System.out.println(ANSI_GREEN + "Green Box: "+ ANSI_RESET);
                printCardsByStatus(listOfCards, CardStatus.GREEN_BOX);
                System.out.println(ANSI_BLUE + "--------------------------"+ ANSI_RESET);
        }

        private static void printCardsByStatus(List<Card> listOfCards, CardStatus statusBox) {
                for(Card card : listOfCards){
                        if(statusBox.name().equals(card.getStatus().name())){
                                System.out.println("\tQ: " + card.getQuestion());
                        }
                }
        }

        public static void saveStatusFile(String fileName, List<Card> listOfCards) {
                try {
                        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                        for(Card card : listOfCards){
                                writer.println(card.getQuestion() + "|" + card.getAnswer() + "|" + card.getStatus());
                        }
                        writer.close();
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                }
        }
}
