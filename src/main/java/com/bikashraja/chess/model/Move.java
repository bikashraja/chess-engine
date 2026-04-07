package com.bikashraja.chess.model;

import java.util.Objects;

/**
 * Represents a single chess move.
 *
 * A Move describes an action:
 * - moving a piece from one square to another
 * - optionally with a special rule (castling, en passant, promotion)
 *
 * It only encodes intent. The Board class is responsible for applying it.
 */
public final class Move {

    private final Position from;
    private final Position to;
    private final MoveType moveType;
    private final PieceType promotion; // only used for PROMOTION, otherwise null

    /**
     * Constructs a Move.
     *
     * @param from       starting position
     * @param to         target position
     * @param moveType   type of move (NORMAL, CASTLING, EN_PASSANT, PROMOTION)
     * @param promotion  piece type for promotion (only for PROMOTION moves, else null)
     *
     * @throws IllegalArgumentException if invalid combination of moveType and promotion
     * @throws NullPointerException if from, to, or moveType is null
     */
    public Move(Position from, Position to, MoveType moveType, PieceType promotion) {
        this.from = Objects.requireNonNull(from, "from must not be null");
        this.to = Objects.requireNonNull(to, "to must not be null");
        this.moveType = Objects.requireNonNull(moveType, "moveType must not be null");
        this.promotion = promotion;

        // Validation: only PROMOTION moves may have a promotion piece
        if (moveType != MoveType.PROMOTION && promotion != null) {
            throw new IllegalArgumentException("Only promotion moves can have a promotion piece");
        }

        // Validation: PROMOTION must specify a piece
        if (moveType == MoveType.PROMOTION && promotion == null) {
            throw new IllegalArgumentException("Promotion move must specify promotion piece");
        }
    }

    // --- Factory methods for each move type ---

    public static Move normal(Position from, Position to) {
        return new Move(from, to, MoveType.NORMAL, null);
    }

    public static Move promotion(Position from, Position to, PieceType promotion) {
        return new Move(from, to, MoveType.PROMOTION, promotion);
    }

    public static Move castling(Position from, Position to) {
        return new Move(from, to, MoveType.CASTLING, null);
    }

    public static Move enPassant(Position from, Position to) {
        return new Move(from, to, MoveType.EN_PASSANT, null);
    }

    // --- Getters ---

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public PieceType getPromotion() {
        return promotion;
    }

    // --- Convenience methods ---

    public boolean isPromotion() {
        return moveType == MoveType.PROMOTION;
    }

    public boolean isCastling() {
        return moveType == MoveType.CASTLING;
    }

    public boolean isEnPassant() {
        return moveType == MoveType.EN_PASSANT;
    }

    // --- Object methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move other)) return false;

        return from.equals(other.from)
                && to.equals(other.to)
                && moveType == other.moveType
                && promotion == other.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, moveType, promotion);
    }

    /**
     * Returns a human-readable representation of the move.
     *
     * Examples:
     * - e2e4
     * - e7e8=Q
     * - O-O
     * - O-O-O
     * - e5d6 e.p.
     */
    @Override
    public String toString() {
        // Castling notation
        if (isCastling()) {
            return (to.getCol() > from.getCol()) ? "O-O" : "O-O-O";
        }

        String move = from.toAlgebraic() + to.toAlgebraic();

        // Promotion notation
        if (isPromotion()) {
            move += "=" + getPromotionChar(promotion);
        }

        // En Passant notation
        if (isEnPassant()) {
            move += " e.p.";
        }

        return move;
    }

    /**
     * Converts a PieceType to its standard algebraic notation character.
     */
    private char getPromotionChar(PieceType type) {
        return switch (type) {
            case QUEEN -> 'Q';
            case ROOK -> 'R';
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            default -> throw new IllegalArgumentException("Invalid promotion type: " + type);
        };
    }
}