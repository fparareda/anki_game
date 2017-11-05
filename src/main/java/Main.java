import service.GameService;

public class Main {

    private static GameService game = new GameService();
    private static String fileName = "files/deckOfCards.txt";

    public static void main(String[] args) {
        game.initGame(fileName);
        game.play();
        game.endGame();
    }
}
