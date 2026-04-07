package com.bikashraja.chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testNormalMove() {
        Move move = Move.normal(new Position(6, 4), new Position(4, 4));

        assertEquals(MoveType.NORMAL, move.getMoveType());
        assertNull(move.getPromotion());
        assertEquals("e2e4", move.toString());
    }

    @Test
    void testPromotionMove() {
        Move move = Move.promotion(new Position(1, 4), new Position(0, 4), PieceType.QUEEN);

        assertTrue(move.isPromotion());
        assertEquals("e7e8=Q", move.toString());
    }

    @Test
    void testCastlingMoves() {
        Move kingSide = Move.castling(new Position(7, 4), new Position(7, 6));
        Move queenSide = Move.castling(new Position(7, 4), new Position(7, 2));

        assertEquals("O-O", kingSide.toString());
        assertEquals("O-O-O", queenSide.toString());
    }

    @Test
    void testEnPassantMove() {
        Move move = Move.enPassant(new Position(3, 4), new Position(2, 3));

        assertTrue(move.isEnPassant());
        assertEquals("e5d6 e.p.", move.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        Move m1 = Move.normal(new Position(6, 4), new Position(4, 4));
        Move m2 = Move.normal(new Position(6, 4), new Position(4, 4));

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testInvalidPromotionCombination() {
        assertThrows(IllegalArgumentException.class, () ->
                new Move(new Position(6, 0), new Position(5, 0), MoveType.NORMAL, PieceType.QUEEN)
        );

        assertThrows(IllegalArgumentException.class, () ->
                new Move(new Position(1, 0), new Position(0, 0), MoveType.PROMOTION, null)
        );
    }
}