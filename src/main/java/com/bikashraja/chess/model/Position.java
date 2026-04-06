package com.bikashraja.chess.model;

/**
 * Represents a single square on the chess board as (row, col).
 *
 * The board is indexed like a 2D array:
 *   row 0 = rank 8
 *   row 7 = rank 1
 *   col 0 = file 'a'
 *   col 7 = file 'h'
 *
 * So a1 = (7, 0), e4 = (4, 4), h8 = (0, 7).
 */
public class Position {

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Checks whether this position is within the 8x8 board bounds.
     *
     * @return true if position is valid, false otherwise
     */
    public boolean isValid() {
        return row >= 0 && row < 8 && col >=0 && col < 8;
    }

    /**
     * Converts this position to standard chess notation (example: "e4", "a1").
     *
     * @return a position in algebraic form
     */
    public String toAlgebraic() {
        char file = (char)('a' + col); // example: col 0 -> file = 'a', col 7 -> file = 'h'
        int rank = 8 - row; // example: row 0 -> rank = 8, row 7 -> rank = 1

        return "" + file + rank;
    }

    /**
     * Parses a position from algebraic notation (example: "e4" -> Position(4, 4)).
     *
     * @param s the algebraic notation string (example: "e2", "g1")
     * @return a Position object representing the square, or null if the input is
     *         invalid
     */
    public static Position fromAlgebraic(String s) {
        if (s == null || s.length() != 2) {
            return null;
        }

        char file = s.charAt(0);
        char rank = s.charAt(1);

        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            return null;
        }

        int col = file - 'a';
        int row = '8' - rank; // example: rank = '3' -> row = '8' - '3' = 56 - 51 = 5

        return new Position(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Two positions are equal if they refer to the same square.
     * Required so that positions can be used as keys in HashMaps or Sets.
     *
     * @param obj   the reference object with which to compare.
     * @return true if two positions are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Position position)) {
            return false;
        }

        return this.row == position.row && this.col == position.col;
    }

    @Override
    public int hashCode() {
        // Simple hash: row * 8 + col gives a unique int for each square (0–63)
        return row * 8 + col;
    }

    @Override
    public String toString() {
        return toAlgebraic();
    }
}
