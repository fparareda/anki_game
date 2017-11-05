package model;

import java.util.*;

public class Game {

    private Session session;

    private List<Card> deck;;
    private List<Card> redBox;
    private List<Card> orangeBox;
    private List<Card> greenBox;

    public Game() {
        this.deck = new ArrayList<>();
        this.redBox = new ArrayList<>();
        this.orangeBox = new ArrayList<>();
        this.greenBox = new ArrayList<>();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getRedBox() {
        return redBox;
    }

    public List<Card> getOrangeBox() {
        return orangeBox;
    }

    public List<Card> getGreenBox() {
        return greenBox;
    }

    public void initSession(String fileName){
        session = new Session(fileName);
        distributeCardsByType(session.getFileContent());
        printStatus();
    }

    public void endSession(){
        String ANSI_BOLD = "\u001B[1m", ANSI_RESET = "\u001B[0m";
        if(redBox.size() == 0 && orangeBox.size() == 0){
            System.out.println(ANSI_BOLD + "\nCongratulate! The cards are studied properly!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BOLD + "\nGood job! Goodbye!" + ANSI_RESET);
        }
        session.saveCardsIntoFile(getAllBoxesWithCardsToSave());
    }

    private void distributeCardsByType(List<String> listOfCards) {
        for (String sentence : listOfCards) {
            String[] phrases = sentence.split("\\|");
            Card card = new Card(phrases[0], phrases[1]);
            if (phrases.length == 2) {        // This card does not have status
                deck.add(card);
            } else if (phrases.length > 2) {  // Card with a status
                CardStatus statusOfCard = getCardStatus(phrases[2]);
                separateInSpecificBox(card, statusOfCard);
            }
        }
    }

    private void separateInSpecificBox(Card card, CardStatus statusOfCard) {
        if(statusOfCard.equals(CardStatus.GREEN_BOX)){
            this.greenBox.add(card);
        } else if(statusOfCard.equals(CardStatus.ORANGE_BOX)){
            this.redBox.add(card);
        } else if(statusOfCard.equals(CardStatus.RED_BOX)){
            this.deck.add(card);
        }
    }

    public CardStatus getCardStatus(String statusOfCard) {
        for (CardStatus status : CardStatus.values()) {
            if (status.name().equals(statusOfCard)) {
                return status;
            }
        }
        return CardStatus.RED_BOX;
    }

    private boolean isInBox(List<Card> boxOfCards, Card card) {
        for(Card cardOfBox : boxOfCards){
            if(cardOfBox.equals(card)){
                return true;
            }
        }
        return false;
    }

    private void removeFromAllBoxes(Card card){
        removeFromBox(this.greenBox, card);
        removeFromBox(this.orangeBox, card);
        removeFromBox(this.redBox, card);
    }

    private void removeFromBox(List<Card> box, Card card){
        for (Iterator<Card> iterator = box.listIterator(); iterator.hasNext(); ) {
            Card cardToCompare = iterator.next();
            if (cardToCompare.equals(card)) {
                iterator.remove();
            }
        }
    }

    private void moveCardToGreenBox(Card card) {
        Card cardToMove = new Card(card.getQuestion(), card.getQuestion());
        removeFromAllBoxes(card);
        this.greenBox.add(cardToMove);
    }

    private void moveCardToOrangeBox(Card card) {
        Card cardToMove = new Card(card.getQuestion(), card.getQuestion());
        removeFromAllBoxes(card);
        this.orangeBox.add(cardToMove);
    }

    private void moveCardToRedBox(Card card) {
        Card cardToMove = new Card(card.getQuestion(), card.getQuestion());
        removeFromAllBoxes(card);
        this.redBox.add(cardToMove);
    }


    public void showPossibilities(Card card) {
        System.out.println("\nQuestion: " + card.getQuestion());
        System.out.println("(1) Do you know the answer");
        System.out.println("(2) Do you know a part of the answer");
        System.out.println("(3) You don't know the answer");
    }

    public Integer getFeedBackFromUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nAnswer: ");
        return scan.nextInt();
    }

    public void setCardInTheRightBox(Card card, Integer usersAnswer) {
        switch (usersAnswer){
            case 1:
                moveCardToGreenBox(card);
                break;
            case 2:
                moveCardToOrangeBox(card);
                break;
            default:
                moveCardToRedBox(card);
                break;
        }
    }

    private void printStatus(){
        printCardsByStatus(this.deck, "Deck: ");
        printCardsByStatus(this.greenBox, "Green Box: ");
        printCardsByStatus(this.orangeBox, "Orange Box: ");
        printCardsByStatus(this.redBox, "Red Box: ");
    }

    private void printCardsByStatus(List<Card> cards, String title){
        String ANSI_RESET = "\u001B[0m";
        System.out.println("\u001B[31m" + title + ANSI_RESET);
        for(Card card : cards){
            System.out.println(card.getQuestion());
        }
    }

    public List<String> getAllBoxesWithCardsToSave() {
        List<String> finalDeck = new ArrayList<>();
        finalDeck.addAll(flatterizeCardInTheBox(redBox, CardStatus.RED_BOX));
        finalDeck.addAll(flatterizeCardInTheBox(orangeBox, CardStatus.ORANGE_BOX));
        finalDeck.addAll(flatterizeCardInTheBox(greenBox, CardStatus.GREEN_BOX));
        return finalDeck;
    }

    private List<String> flatterizeCardInTheBox(List<Card> boxWithCards, CardStatus statusBox) {
        List<String> boxFlattered = new ArrayList<>();
        for(Card card : boxWithCards){
            boxFlattered.add(card.getQuestion() + "|" + card.getAnswer() + "|" + statusBox);
        }
        return boxFlattered;
    }
}
