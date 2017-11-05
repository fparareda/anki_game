import model.Card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AnkiTest {

    @Test
    public void testToCheckTheCard() {
        String question = "What enzyme breaks down sugars mouth and digestive tract?";
        String answer = "Amylase";
        Card card = new Card(question, answer);

        assertEquals(card.getQuestion(), question);
        assertEquals(card.getAnswer(), answer);
    }
}
