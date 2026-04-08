package com.bikashraja.chess.model;

import java.util.Objects;

/**
 * Represents the non-positional state of a chess game.
 *
 * This includes:
 * - which side is to move
 * - castling rights
 * - en passant target square
 *
 * - GameState doesn't know about piece positions (Board handles that)
 * - GameState doesn't apply moves itself
 */
public final class GameState {

    private final Color sideToMove;

    // Castling rights
    private final boolean whiteKingSideCastle;
    private final boolean whiteQueenSideCastle;
    private final boolean blackKingSideCastle;
    private final boolean blackQueenSideCastle;

    // En passant target square (square behind a pawn that moved two squares)
    // null if no en passant is available
    private final Position enPassantTarget;

    public GameState(Color sideToMove,
                     boolean whiteKingSideCastle,
                     boolean whiteQueenSideCastle,
                     boolean blackKingSideCastle,
                     boolean blackQueenSideCastle,
                     Position enPassantTarget) {

        this.sideToMove = Objects.requireNonNull(sideToMove, "sideToMove must not be null");

        this.whiteKingSideCastle = whiteKingSideCastle;
        this.whiteQueenSideCastle = whiteQueenSideCastle;
        this.blackKingSideCastle = blackKingSideCastle;
        this.blackQueenSideCastle = blackQueenSideCastle;

        this.enPassantTarget = enPassantTarget; // can be null
    }

    /**
     * Creates the standard initial game state.
     */
    public static GameState initial() {
        return new GameState(
                Color.WHITE,
                true,  // white can castle king side
                true,  // white can castle queen side
                true,  // black can castle king side
                true,  // black can castle queen side
                null   // no en passant at start
        );
    }

    // --- Getters ---

    public Color getSideToMove() {
        return sideToMove;
    }

    public boolean canWhiteCastleKingSide() {
        return whiteKingSideCastle;
    }

    public boolean canWhiteCastleQueenSide() {
        return whiteQueenSideCastle;
    }

    public boolean canBlackCastleKingSide() {
        return blackKingSideCastle;
    }

    public boolean canBlackCastleQueenSide() {
        return blackQueenSideCastle;
    }

    public Position getEnPassantTarget() {
        return enPassantTarget;
    }

    // --- Convenience methods ---

    public boolean canCastleKingSide(Color color) {
        return color == Color.WHITE ? whiteKingSideCastle : blackKingSideCastle;
    }

    public boolean canCastleQueenSide(Color color) {
        return color == Color.WHITE ? whiteQueenSideCastle : blackQueenSideCastle;
    }

    // --- State transitions ---

    /**
     * @return a new GameState with the side to move switched.
     */
    public GameState switchSide() {
        return new GameState(
                sideToMove.opposite(),
                whiteKingSideCastle,
                whiteQueenSideCastle,
                blackKingSideCastle,
                blackQueenSideCastle,
                enPassantTarget
        );
    }

    /**
     * @return a new GameState with updated en passant target.
     */
    public GameState withEnPassantTarget(Position newTarget) {
        return new GameState(
                sideToMove,
                whiteKingSideCastle,
                whiteQueenSideCastle,
                blackKingSideCastle,
                blackQueenSideCastle,
                newTarget
        );
    }

    /**
     * @return a new GameState with updated castling rights.
     */
    public GameState withCastlingRights(boolean wks, boolean wqs, boolean bks, boolean bqs) {
        return new GameState(
                sideToMove,
                wks,
                wqs,
                bks,
                bqs,
                enPassantTarget
        );
    }

    // --- Object methods ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState that)) return false;

        return whiteKingSideCastle == that.whiteKingSideCastle &&
                whiteQueenSideCastle == that.whiteQueenSideCastle &&
                blackKingSideCastle == that.blackKingSideCastle &&
                blackQueenSideCastle == that.blackQueenSideCastle &&
                sideToMove == that.sideToMove &&
                Objects.equals(enPassantTarget, that.enPassantTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                sideToMove,
                whiteKingSideCastle,
                whiteQueenSideCastle,
                blackKingSideCastle,
                blackQueenSideCastle,
                enPassantTarget
        );
    }

    @Override
    public String toString() {
        return "GameState{" +
                "sideToMove=" + sideToMove +
                ", wK=" + whiteKingSideCastle +
                ", wQ=" + whiteQueenSideCastle +
                ", bK=" + blackKingSideCastle +
                ", bQ=" + blackQueenSideCastle +
                ", enPassant=" + (enPassantTarget != null ? enPassantTarget.toAlgebraic() : "-") +
                '}';
    }
}