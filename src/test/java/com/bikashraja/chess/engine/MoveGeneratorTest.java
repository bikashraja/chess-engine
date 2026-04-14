package com.bikashraja.chess.engine;

import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.Color;
import com.bikashraja.chess.model.GameState;
import com.bikashraja.chess.model.Move;
import com.bikashraja.chess.model.Piece;
import com.bikashraja.chess.model.PieceType;
import com.bikashraja.chess.model.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveGeneratorTest {

    @Test
    void testGeneratePseudoLegalMoves_knightInCenter_generatesEightMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.KNIGHT, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertEquals(8, moves.size());
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 3))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 5))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 2))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 6))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(5, 2))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(5, 6))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(6, 3))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(6, 5))));
    }

    @Test
    void testGeneratePseudoLegalMoves_knightInCorner_generatesTwoMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(7, 0), new Piece(PieceType.KNIGHT, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertEquals(2, moves.size());
        assertTrue(moves.contains(Move.normal(new Position(7, 0), new Position(5, 1))));
        assertTrue(moves.contains(Move.normal(new Position(7, 0), new Position(6, 2))));
    }

    @Test
    void testGeneratePseudoLegalMoves_knightBlockedByOwnPiece_doesNotGenerateBlockedMove() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.KNIGHT, Color.WHITE));
        board.setPiece(new Position(2, 3), new Piece(PieceType.PAWN, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertEquals(7, moves.size());
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(2, 3))));
    }

    @Test
    void testGeneratePseudoLegalMoves_knightCanCaptureEnemyPiece_generatesCaptureMove() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.KNIGHT, Color.WHITE));
        board.setPiece(new Position(2, 3), new Piece(PieceType.PAWN, Color.BLACK));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertEquals(8, moves.size());
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 3))));
    }
}