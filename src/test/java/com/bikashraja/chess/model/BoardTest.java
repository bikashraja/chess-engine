package com.bikashraja.chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testInitial_whiteMajorPiecesPlacedCorrectly() {
        Board board = Board.initial();

        assertEquals(new Piece(PieceType.ROOK, Color.WHITE), board.getPiece(new Position(7, 0)));
        assertEquals(new Piece(PieceType.KNIGHT, Color.WHITE), board.getPiece(new Position(7, 1)));
        assertEquals(new Piece(PieceType.BISHOP, Color.WHITE), board.getPiece(new Position(7, 2)));
        assertEquals(new Piece(PieceType.QUEEN, Color.WHITE), board.getPiece(new Position(7, 3)));
        assertEquals(new Piece(PieceType.KING, Color.WHITE), board.getPiece(new Position(7, 4)));
        assertEquals(new Piece(PieceType.BISHOP, Color.WHITE), board.getPiece(new Position(7, 5)));
        assertEquals(new Piece(PieceType.KNIGHT, Color.WHITE), board.getPiece(new Position(7, 6)));
        assertEquals(new Piece(PieceType.ROOK, Color.WHITE), board.getPiece(new Position(7, 7)));
    }

    @Test
    void testInitial_blackMajorPiecesPlacedCorrectly() {
        Board board = Board.initial();

        assertEquals(new Piece(PieceType.ROOK, Color.BLACK), board.getPiece(new Position(0, 0)));
        assertEquals(new Piece(PieceType.KNIGHT, Color.BLACK), board.getPiece(new Position(0, 1)));
        assertEquals(new Piece(PieceType.BISHOP, Color.BLACK), board.getPiece(new Position(0, 2)));
        assertEquals(new Piece(PieceType.QUEEN, Color.BLACK), board.getPiece(new Position(0, 3)));
        assertEquals(new Piece(PieceType.KING, Color.BLACK), board.getPiece(new Position(0, 4)));
        assertEquals(new Piece(PieceType.BISHOP, Color.BLACK), board.getPiece(new Position(0, 5)));
        assertEquals(new Piece(PieceType.KNIGHT, Color.BLACK), board.getPiece(new Position(0, 6)));
        assertEquals(new Piece(PieceType.ROOK, Color.BLACK), board.getPiece(new Position(0, 7)));
    }

    @Test
    void testInitial_whitePawnsPlacedCorrectly() {
        Board board = Board.initial();

        for (int col = 0; col < 8; col++) {
            assertEquals(new Piece(PieceType.PAWN, Color.WHITE), board.getPiece(new Position(6, col)));
        }
    }

    @Test
    void testInitial_blackPawnsPlacedCorrectly() {
        Board board = Board.initial();

        for (int col = 0; col < 8; col++) {
            assertEquals(new Piece(PieceType.PAWN, Color.BLACK), board.getPiece(new Position(1, col)));
        }
    }

    @Test
    void testInitial_middleSquaresAreEmpty() {
        Board board = Board.initial();

        assertNull(board.getPiece(new Position(2, 3)));
        assertNull(board.getPiece(new Position(3, 4)));
        assertNull(board.getPiece(new Position(4, 4)));
        assertNull(board.getPiece(new Position(5, 3)));
    }

    @Test
    void testIsEmpty_emptySquareReturnsTrue() {
        Board board = Board.initial();

        assertTrue(board.isEmpty(new Position(4, 4)));
    }

    @Test
    void testIsEmpty_occupiedSquareReturnsFalse() {
        Board board = Board.initial();

        assertFalse(board.isEmpty(new Position(7, 4)));
    }
}