package com.bikashraja.chess.engine;

import com.bikashraja.chess.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void evaluate_returnsZeroForInitialPosition() {
        Evaluator evaluator = new Evaluator();

        assertEquals(0, evaluator.evaluate(Board.initial()));
    }

    @Test
    void evaluate_returnsPositiveScoreWhenWhiteHasMaterialAdvantage() {
        Board board = Board.empty();
        board.setPiece(new Position(7, 4), new Piece(PieceType.KING, Color.WHITE));
        board.setPiece(new Position(0, 4), new Piece(PieceType.KING, Color.BLACK));
        board.setPiece(new Position(3, 3), new Piece(PieceType.QUEEN, Color.WHITE));

        Evaluator evaluator = new Evaluator();

        assertTrue(evaluator.evaluate(board) > 0);
    }
}