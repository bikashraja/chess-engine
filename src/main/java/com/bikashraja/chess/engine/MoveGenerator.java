package com.bikashraja.chess.engine;

import com.bikashraja.chess.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates chess moves for a given board position.
 *
 * Supports piece-specific move generation and can be extended
 * to provide both pseudo-legal and fully legal moves.
 */
public class MoveGenerator {

    private static final int[][] KNIGHT_OFFSETS = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
    };

    /**
     * Generates pseudo-legal moves for the side to move.
     *
     * Pseudo-legal moves follow movement rules, but may include
     * moves that leave the king in check.
     */
    public List<Move> generatePseudoLegalMoves(Board board, GameState state) {
        List<Move> moves = new ArrayList<>();
        Color sideToMove = state.getSideToMove();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position from = new Position(row, col);
                Piece piece = board.getPiece(from);

                if (piece == null || piece.getColor() != sideToMove) {
                    continue;
                }

                generatePieceMoves(board, state, from, piece, moves);
            }
        }

        return moves;
    }

    private void generatePieceMoves(Board board, GameState state, Position from, Piece piece, List<Move> moves) {
        switch (piece.getType()) {
            case PAWN -> generatePawnMoves(board, state, from, piece, moves);
            case KNIGHT -> generateKnightMoves(board, from, piece, moves);
            case BISHOP -> generateBishopMoves(board, from, piece, moves);
            case ROOK -> generateRookMoves(board, from, piece, moves);
            case QUEEN -> generateQueenMoves(board, from, piece, moves);
            case KING -> generateKingMoves(board, state, from, piece, moves);
        }
    }

    private void generateKingMoves(Board board, GameState state, Position from, Piece piece, List<Move> moves) {
        // TODO
    }

    private void generateQueenMoves(Board board, Position from, Piece piece, List<Move> moves) {
        // TODO
    }

    private void generateRookMoves(Board board, Position from, Piece piece, List<Move> moves) {
        // TODO
    }

    private void generateBishopMoves(Board board, Position from, Piece piece, List<Move> moves) {
        // TODO
    }

    private void generateKnightMoves(Board board, Position from, Piece piece, List<Move> moves) {
        for (int[] offset : KNIGHT_OFFSETS) {
            int newRow = from.getRow() + offset[0];
            int newCol = from.getCol() + offset[1];

            Position to = new Position(newRow, newCol);
            if (!to.isValid()) {
                continue;
            }

            Piece targetPiece = board.getPiece(to);

            if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                moves.add(Move.normal(from, to));
            }
        }
    }

    private void generatePawnMoves(Board board, GameState state, Position from, Piece piece, List<Move> moves) {
        int direction = piece.getColor() == Color.WHITE ? -1 : 1;
        int startRow = piece.getColor() == Color.WHITE ? 6 : 1;
        int promotionRow = piece.getColor() == Color.WHITE ? 0 : 7;

        int fromRow = from.getRow();
        int fromCol = from.getCol();

        int oneStepRow = fromRow + direction;

        // One-square forward move
        if (isInsideBoard(oneStepRow, fromCol)) {
            Position oneStep = new Position(oneStepRow, fromCol);

            if (board.isEmpty(oneStep)) {
                if (oneStepRow == promotionRow) {
                    addPromotionMoves(from, oneStep, moves);
                } else {
                    moves.add(Move.normal(from, oneStep));
                }

                // Two-square forward move
                int twoStepRow = fromRow + 2 * direction;
                if (fromRow == startRow && isInsideBoard(twoStepRow, fromCol)) {
                    Position twoStep = new Position(twoStepRow, fromCol);

                    if (board.isEmpty(twoStep)) {
                        moves.add(Move.normal(from, twoStep));
                    }
                }
            }
        }
    }

    private boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private void addPromotionMoves(Position from, Position to, List<Move> moves) {
        moves.add(Move.promotion(from, to, PieceType.QUEEN));
        moves.add(Move.promotion(from, to, PieceType.ROOK));
        moves.add(Move.promotion(from, to, PieceType.BISHOP));
        moves.add(Move.promotion(from, to, PieceType.KNIGHT));
    }
}
