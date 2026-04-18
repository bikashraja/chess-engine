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

    @Test
    void testGeneratePseudoLegalMoves_whitePawn_generatesSingleAndDoubleForwardMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(6, 4), new Piece(PieceType.PAWN, Color.WHITE)); // e2

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(6, 4), new Position(5, 4)))); // e2 -> e3
        assertTrue(moves.contains(Move.normal(new Position(6, 4), new Position(4, 4)))); // e2 -> e4
    }

    @Test
    void testGeneratePseudoLegalMoves_whitePawn_generatesPromotionMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(1, 4), new Piece(PieceType.PAWN, Color.WHITE)); // e7

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.promotion(new Position(1, 4), new Position(0, 4), PieceType.QUEEN)));
        assertTrue(moves.contains(Move.promotion(new Position(1, 4), new Position(0, 4), PieceType.ROOK)));
        assertTrue(moves.contains(Move.promotion(new Position(1, 4), new Position(0, 4), PieceType.BISHOP)));
        assertTrue(moves.contains(Move.promotion(new Position(1, 4), new Position(0, 4), PieceType.KNIGHT)));
    }

    @Test
    void testGeneratePseudoLegalMoves_whitePawn_generatesDiagonalCapture() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.PAWN, Color.WHITE)); // e4
        board.setPiece(new Position(3, 3), new Piece(PieceType.KNIGHT, Color.BLACK)); // d5

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 3)))); // e4 x d5
    }

    @Test
    void testGeneratePseudoLegalMoves_whitePawn_generatesEnPassantMove() {
        Board board = Board.empty();
        board.setPiece(new Position(3, 4), new Piece(PieceType.PAWN, Color.WHITE)); // e5

        GameState state = new GameState(
                Color.WHITE,
                false, false, false, false,
                new Position(2, 3) // d6
        );

        MoveGenerator generator = new MoveGenerator();
        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.enPassant(new Position(3, 4), new Position(2, 3)))); // e5 -> d6 e.p.
    }

    @Test
    void testGeneratePseudoLegalMoves_blackPawn_generatesSingleAndDoubleForwardMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(1, 3), new Piece(PieceType.PAWN, Color.BLACK)); // d7

        GameState state = new GameState(Color.BLACK, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(1, 3), new Position(2, 3)))); // d7 -> d6
        assertTrue(moves.contains(Move.normal(new Position(1, 3), new Position(3, 3)))); // d7 -> d5
    }

    @Test
    void testGeneratePseudoLegalMoves_bishopInCenter_generatesDiagonalMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.BISHOP, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 3))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 2))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(1, 1))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(0, 0))));

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 5))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 6))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(1, 7))));

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(5, 3))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(6, 2))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 1))));

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(5, 5))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(6, 6))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 7))));
    }

    @Test
    void testGeneratePseudoLegalMoves_bishopBlockedByOwnPiece_doesNotMovePastBlock() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.BISHOP, Color.WHITE));
        board.setPiece(new Position(2, 2), new Piece(PieceType.PAWN, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 3))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(2, 2))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(1, 1))));
    }

    @Test
    void testGeneratePseudoLegalMoves_bishopCanCaptureEnemyPiece() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.BISHOP, Color.WHITE));
        board.setPiece(new Position(2, 2), new Piece(PieceType.PAWN, Color.BLACK));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(3, 3))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(2, 2))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(1, 1))));
    }

    @Test
    void testGeneratePseudoLegalMoves_rookInCenter_generatesStraightMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.ROOK, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(0, 4))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 4))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 0))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 7))));
    }

    @Test
    void testGeneratePseudoLegalMoves_rookBlockedByOwnPiece_doesNotMovePastBlock() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.ROOK, Color.WHITE));
        board.setPiece(new Position(4, 6), new Piece(PieceType.PAWN, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 5))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(4, 6))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(4, 7))));
    }

    @Test
    void testGeneratePseudoLegalMoves_rookCanCaptureEnemyPiece() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.ROOK, Color.WHITE));
        board.setPiece(new Position(4, 6), new Piece(PieceType.PAWN, Color.BLACK));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 5))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 6))));
        assertFalse(moves.contains(Move.normal(new Position(4, 4), new Position(4, 7))));
    }

    @Test
    void testGeneratePseudoLegalMoves_queenInCenter_generatesDiagonalAndStraightMoves() {
        Board board = Board.empty();
        board.setPiece(new Position(4, 4), new Piece(PieceType.QUEEN, Color.WHITE));

        GameState state = new GameState(Color.WHITE, false, false, false, false, null);
        MoveGenerator generator = new MoveGenerator();

        List<Move> moves = generator.generatePseudoLegalMoves(board, state);

        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 0))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(4, 7))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(0, 4))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 4))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(1, 1))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(1, 7))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 1))));
        assertTrue(moves.contains(Move.normal(new Position(4, 4), new Position(7, 7))));
    }
}