package com.bikashraja.chess.model;

import java.util.Objects;

/**
 * Represents the placement of pieces on the chessboard.
 *
 * IMPORTANT:
 * - Board only stores piece positions
 * - Board doesn't handle game rules (turn, legality, etc.)
 * - Board is immutable: makeMove() returns a new Board
 */
public final class Board {

    private final Piece[][] squares; // 8x8 board

    /**
     * Creates a board with given squares.
     * Assumes squares is a properly initialized 8x8 array.
     */
    public Board(Piece[][] squares) {
        this.squares = Objects.requireNonNull(squares, "squares must not be null");
    }

    /**
     * Creates an empty board.
     */
    public static Board empty() {
        return new Board(new Piece[8][8]);
    }

    /**
     * Creates the standard chess starting position.
     */
    public static Board initial() {
        Board board = Board.empty();

        // TODO: place all pieces for both sides

        return board;
    }

    // --- Basic access methods ---

    public Piece getPiece(Position pos) {
        // TODO
        return null;
    }

    public void setPiece(Position pos, Piece piece) {
        // TODO
    }

    public boolean isEmpty(Position pos) {
        // TODO
        return false;
    }

    // --- Copy ---

    /**
     * @return a deep copy of this board.
     * The Piece objects themselves can be reused (immutable).
     */
    public Board copy() {
        // TODO
        return null;
    }

    // --- Move application ---

    /**
     * Applies a move and returns a new Board.
     * Does NOT modify the current board.
     */
    public Board makeMove(Move move, GameState state) {
        Board newBoard = this.copy();

        switch (move.getMoveType()) {
            case NORMAL -> {
                // TODO
            }
            case PROMOTION -> {
                // TODO
            }
            case CASTLING -> {
                // TODO
            }
            case EN_PASSANT -> {
                // TODO
            }
        }

        return newBoard;
    }

    // --- Helper methods for move handling ---

    private void applyNormalMove(Move move) {
        // TODO
    }

    private void applyPromotion(Move move) {
        // TODO
    }

    private void applyCastling(Move move) {
        // TODO
    }

    private void applyEnPassant(Move move) {
        // TODO
    }

    @Override
    public String toString() {
        return super.toString();
    }
}