package service;

import model.Card;
import model.Game;


public class GameService implements IGameService {
    Game game;

    @Override
    public void initGame(String fileName) {
        game = new Game();
        game.initSession(fileName);
    }

    @Override
    public void play() {
        for(Card card : game.getDeck()){
            game.showPossibilities(card);
            Integer usersAnswer = game.getFeedBackFromUser();
            game.setCardInTheRightBox(card, usersAnswer);
        }
    }

    @Override
    public void endGame() {
        game.endSession();
    }
}
