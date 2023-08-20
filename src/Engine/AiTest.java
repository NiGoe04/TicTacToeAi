package Engine;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AiTest{

    Game game;
    AI ai;

    @Before 
    public void setUp() {
        this.ai = new AI();
        this.game = new Game();
        Board board = new Board();
        game.setBoard(board);
        board.setMarkAt(0, Symbol.CROSS);
        board.setMarkAt(3, Symbol.CROSS);
        board.setMarkAt(1, Symbol.CIRCLE);
        board.setMarkAt(4, Symbol.CIRCLE);
    }

    @Test
    public void testAiSimple() {
        assertEquals(ai.getAIMove(game, Symbol.CROSS, AI.MAX_DEPTH).index(), 6);
    }







}