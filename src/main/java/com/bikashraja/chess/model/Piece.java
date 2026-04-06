package com.bikashraja.chess.model;

public class Piece {

    private final PieceType type;
    private final Color color;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    /**
     * @return material value of this piece
     */
    public int getValue() {
        return type.getValue();
    }

    /**
     * @return a single Unicode character for this piece.
     * Used by BoardRenderer to display the board in the terminal.
     *
     * White pieces: ♙♘♗♖♕♔
     * Black pieces: ♟♞♝♜♛♚
     */
    public String getSymbol() {
        if (color == Color.WHITE) {
            return switch (type) {
                case PAWN   -> "♙";
                case KNIGHT -> "♘";
                case BISHOP -> "♗";
                case ROOK   -> "♖";
                case QUEEN  -> "♕";
                case KING   -> "♔";
            };
        } else {
            return switch (type) {
                case PAWN   -> "♟";
                case KNIGHT -> "♞";
                case BISHOP -> "♝";
                case ROOK   -> "♜";
                case QUEEN  -> "♛";
                case KING   -> "♚";
            };
        }
    }

    /**
     * @return a single ASCII letter for this piece (for simpler terminals).
     * White = uppercase, Black = lowercase.
     * P/N/B/R/Q/K for White, p/n/b/r/q/k for Black.
     */
    public String getAsciiSymbol() {
        String letter = switch (type) {
            case PAWN   -> "P";
            case KNIGHT -> "N";
            case BISHOP -> "B";
            case ROOK   -> "R";
            case QUEEN  -> "Q";
            case KING   -> "K";
        };
        return color == Color.WHITE ? letter : letter.toLowerCase();
    }

    @Override
    public String toString() {
        return color + " " + type;
    }
}
