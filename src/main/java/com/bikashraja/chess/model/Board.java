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

        for (int col = 0; col < 8; col++) {
            board.setPiece(new Position(6, col), new Piece(PieceType.PAWN, Color.WHITE));
            board.setPiece(new Position(1, col), new Piece(PieceType.PAWN, Color.BLACK));
        }

        board.setPiece(new Position(7, 0), new Piece(PieceType.ROOK, Color.WHITE));
        board.setPiece(new Position(7, 1), new Piece(PieceType.KNIGHT, Color.WHITE));
        board.setPiece(new Position(7, 2), new Piece(PieceType.BISHOP, Color.WHITE));
        board.setPiece(new Position(7, 3), new Piece(PieceType.QUEEN, Color.WHITE));
        board.setPiece(new Position(7, 4), new Piece(PieceType.KING, Color.WHITE));
        board.setPiece(new Position(7, 5), new Piece(PieceType.BISHOP, Color.WHITE));
        board.setPiece(new Position(7, 6), new Piece(PieceType.KNIGHT, Color.WHITE));
        board.setPiece(new Position(7, 7), new Piece(PieceType.ROOK, Color.WHITE));

        board.setPiece(new Position(0, 0), new Piece(PieceType.ROOK, Color.BLACK));
        board.setPiece(new Position(0, 1), new Piece(PieceType.KNIGHT, Color.BLACK));
        board.setPiece(new Position(0, 2), new Piece(PieceType.BISHOP, Color.BLACK));
        board.setPiece(new Position(0, 3), new Piece(PieceType.QUEEN, Color.BLACK));
        board.setPiece(new Position(0, 4), new Piece(PieceType.KING, Color.BLACK));
        board.setPiece(new Position(0, 5), new Piece(PieceType.BISHOP, Color.BLACK));
        board.setPiece(new Position(0, 6), new Piece(PieceType.KNIGHT, Color.BLACK));
        board.setPiece(new Position(0, 7), new Piece(PieceType.ROOK, Color.BLACK));

        return board;
    }

    // --- Basic access methods ---

    public Piece getPiece(Position pos) {
        if (!pos.isValid()) {
            throw new IllegalArgumentException("Invalid position: " + pos);
        }
        return squares[pos.getRow()][pos.getCol()];
    }

    public void setPiece(Position pos, Piece piece) {
        if (!pos.isValid()) {
            throw new IllegalArgumentException("Invalid position: " + pos);
        }
        squares[pos.getRow()][pos.getCol()] = piece;
    }

    public boolean isEmpty(Position pos) {
        if (!pos.isValid()) {
            throw new IllegalArgumentException("Invalid position: " + pos);
        }
        return squares[pos.getRow()][pos.getCol()] == null;
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