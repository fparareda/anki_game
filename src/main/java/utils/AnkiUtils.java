package utils;

import model.Card;
import model.CardStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferran on 17/10/17.
 */
public class AnkiUtils {


        public List<Card> readCardsFromFile(String fileName) {
                List<Card> listOfCards = null;
                List<String> listOfSentences = readFileByFilename(fileName);
                if (listOfSentences != null) {
                        listOfCards = convertSentencesToCards(listOfSentences);
                }
                return listOfCards;
        }

        private List<String> readFileByFilename(String fileName) {
                List<String> listOfSentences = null;
                File file = new File(fileName);
                if (file.exists()) {
                        listOfSentences = readFile(file);
                }
                return listOfSentences;
        }

        private List<String> readFile(File file) {
                List<String> listOfSentences = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                                listOfSentences.add(line);
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return listOfSentences;
        }

        public List<Card> convertSentencesToCards(List<String> allSentences) {
                List<Card> listOfCards = new ArrayList<>();

                for (String sentence : allSentences) {
                        Card card = convertSentenceToCard(sentence);
                        if (card != null) {
                                listOfCards.add(card);
                        }
                }
                return listOfCards;
        }

        public Card convertSentenceToCard(String sentence) {
                String[] phrases = sentence.split("\\|");
                if (phrases.length == 2) {        // This card does not have status
                        return new Card(CardStatus.RED_BOX, phrases[0], phrases[1]);
                } else if (phrases.length > 2) {  // Card with a status
                        return new Card(getCardStatus(phrases[2]), phrases[0], phrases[1]);
                }
                return null;
        }

        public CardStatus getCardStatus(String statusOfCard) {
                for (CardStatus status : CardStatus.values()) {
                        if (status.name().equals(statusOfCard)) {
                                return status;
                        }
                }
                return CardStatus.RED_BOX;
        }


        public boolean allTheCardsSettled(List<Card> listOfCards) {
                for (Card card : listOfCards) {
                        if (!card.isInGreenBox()) {
                                return false;
                        }
                }
                return true;
        }

        public void currentStatusAnki(List<Card> listOfCards) {
                String ANSI_RESET = "\u001B[0m";
                String ANSI_BLUE = "\u001B[34m";
                System.out.println(ANSI_BLUE + "----- Current tatus: -----" + ANSI_RESET);
                for(Card card : listOfCards) {
                        card.printStatus();
                }
                System.out.println(ANSI_BLUE + "--------------------------" + ANSI_RESET);
        }

        public void saveStatusFile(String fileName, List<Card> listOfCards) {
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
