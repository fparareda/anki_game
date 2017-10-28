package model;

/**
 * Created by ferran on 17/10/17.
 */
public class Card {

    private CardStatus status;
    private String question;
    private String answer;

    public Card(CardStatus status, String question, String answer) {
        this.status = status;
        this.question = question;
        this.answer = answer;
    }


    public void setAnswerStatus(int usersAnswer) {
        switch (usersAnswer){
            case 1:
                moveCardToGreenBox();
                break;
            case 2:
                moveCardToOrangeBox();
                break;
            default:
                moveCardToRedBox();
                break;
        }
    }

    public CardStatus getStatus() {
        return status;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }


    @Override
    public String toString() {
        return this.status.name() + "\n" + this.question + "\n" + this.answer;
    }

    public boolean canBeChecked() {
        return CardStatus.RED_BOX.equals(this.status);
    }

    public boolean isInGreenBox() {
        return CardStatus.GREEN_BOX.name().equals(this.status.name());
    }
    public boolean isInOrangeBox() {
        return CardStatus.ORANGE_BOX.name().equals(this.status.name());
    }
    public boolean isInRedBox() {
        return CardStatus.RED_BOX.name().equals(this.status.name());
    }

    public void moveCardToGreenBox() {
        this.status = CardStatus.GREEN_BOX;
    }
    public void moveCardToOrangeBox() {
        this.status = CardStatus.ORANGE_BOX;
    }
    public void moveCardToRedBox() {
        this.status = CardStatus.RED_BOX;
    }


    public void printStatus(){
        String ANSI_RESET = "\u001B[0m";
        if(CardStatus.GREEN_BOX.name().equals(this.status.name())){
            System.out.println("\u001B[32m" +"Green Box: "+ANSI_RESET);
        } else if(CardStatus.ORANGE_BOX.name().equals(this.status.name())){
            System.out.println("\u001B[36m" +"Orange Box: "+ANSI_RESET);
        } else if(CardStatus.RED_BOX.name().equals(this.status.name())){
            System.out.println("\u001B[31m" +"Red Box: "+ANSI_RESET);
        }
    }
}
