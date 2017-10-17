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
                setStatus(CardStatus.GREEN_BOX);
                break;
            case 2:
                setStatus(CardStatus.ORANGE_BOX);
                break;
            default:
                setStatus(CardStatus.RED_BOX);
                break;
        }
    }

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
