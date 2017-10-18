import model.Card;
import model.CardStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import utils.AnkiUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ferran on 18/10/17.
 */
public class AnkiUtilsTest {

    @Test
    public void testConvertStringToACard() {
        AnkiUtils ankiUtils = new AnkiUtils();
        String question = "What enzyme breaks down sugars mouth and digestive tract?";
        String answer = "Amylase";
        String sentence = question + "|" + answer;
        Card card = new Card(CardStatus.RED_BOX, question, answer);

        Card cardToTest = ankiUtils.convertSentenceToCard(sentence);

        assertThat(card.getQuestion(), cardToTest.getQuestion().equals(card.getQuestion()));
        assertThat(card.getAnswer(), cardToTest.getAnswer().equals(card.getAnswer()));
        assertThat(card.getStatus().name(), cardToTest.getStatus().equals(card.getStatus()));
    }

    @Test
    public void testConvertStringToStatus() {
        AnkiUtils ankiUtils = new AnkiUtils();

        assertEquals(CardStatus.GREEN_BOX.name(), ankiUtils.getCardStatus("GREEN_BOX").name());
        assertEquals(CardStatus.RED_BOX.name(), ankiUtils.getCardStatus("RED_BOX").name());
        assertEquals(CardStatus.ORANGE_BOX.name(), ankiUtils.getCardStatus("ORANGE_BOX").name());
    }

    @Test
    public void testConvertSentencesToCards() {
        AnkiUtils ankiUtils = new AnkiUtils();
        List<String> allSentences = new ArrayList<>();
        allSentences.add("What enzyme breaks down sugars mouth and digestive tract?|Amylase");

        Card card = new Card(CardStatus.RED_BOX, "What enzyme breaks down sugars mouth and digestive tract?", "Amylase");
        List<Card> allCardsOnList = new ArrayList<>();
        allCardsOnList.add(card);


        assertEquals(1, ankiUtils.convertSentencesToCards(allSentences).size());
        assertEquals(ankiUtils.convertSentencesToCards(allSentences).get(0).getStatus(), allCardsOnList.get(0).getStatus());
        assertEquals(ankiUtils.convertSentencesToCards(allSentences).get(0).getQuestion(), allCardsOnList.get(0).getQuestion());
        assertEquals(ankiUtils.convertSentencesToCards(allSentences).get(0).getAnswer(), allCardsOnList.get(0).getAnswer());
    }


    @Test
    public void testAllCardsGreenBox() {
        AnkiUtils ankiUtils = new AnkiUtils();
        Card greenCard = new Card(CardStatus.GREEN_BOX, "What enzyme breaks down sugars mouth and digestive tract?", "Amylase");
        Card orangeCard = new Card(CardStatus.ORANGE_BOX, "How is dietary cholesterol transported to target tissues?","In chylomicrons");
        Card redCard = new Card(CardStatus.RED_BOX, "What is the glucose transporter in the brain and what are its properties?","GLUT-1");
        List<Card> allCardsOnList = new ArrayList<>();
        allCardsOnList.add(greenCard);
        allCardsOnList.add(orangeCard);
        allCardsOnList.add(redCard);
        assertEquals(false, ankiUtils.allTheCardsSettled(allCardsOnList));


        List<Card> allCardsOnList2 = new ArrayList<>();
        allCardsOnList.add(greenCard);
        allCardsOnList.add(greenCard);
        assertEquals(true, ankiUtils.allTheCardsSettled(allCardsOnList2));
    }
}
