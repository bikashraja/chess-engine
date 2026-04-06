package com.bikashraja.chess.model;

/**
 * Represents the six types of chess pieces.
 *
 * We use standard centipawn value for each piece used in most of the chess engines.
 *
 * We use 100000 for the King, high enough that losing it always outweighs
 * any other gain.
 */
public enum PieceType {
    PAWN(100),
    KNIGHT(320),
    BISHOP(330),
    ROOK(500),
    QUEEN(900),
    KING(100000);

    // The material value of pieces in centipawns.
    private final int value;

    PieceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
