package com.bikashraja.chess.model;

/**
 * Represents the two players in chess game.
 *
 * Using enum helps to flip between two possible colors after each move.
 */
public enum Color {
    WHITE,
    BLACK;

    /**
     *
     * @return the opposite color.
     */
    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}
