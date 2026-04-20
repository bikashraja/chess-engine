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

    private static final int[][] KING_OFFSETS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    private static final int[][] BISHOP_DIRECTIONS = {
            {-1, -1}, {-1, 1},
            {1, -1}, {1, 1}
    };

    private static final int[][] ROOK_DIRECTIONS = {
            {-1, 0}, {1, 0},
            {0, -1}, {0, 1}
    };

    private static final int[][] QUEEN_DIRECTIONS = {
            {-1, -1}, {-1, 1},
            {1, -1}, {1, 1},
            {-1, 0}, {1, 0},
            {0, -1}, {0, 1}
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
        for (int[] offset : KING_OFFSETS) {
            int newRow = from.getRow() + offset[0];
            int newCol = from.getCol() + offset[1];

            if (!isInsideBoard(newRow, newCol)) {
                continue;
            }

            Position to = new Position(newRow, newCol);
            Piece targetPiece = board.getPiece(to);

            if (targetPiece == null || targetPiece.getColor() != piece.getColor()) {
                moves.add(Move.normal(from, to));
            }
        }

        addCastlingMoves(board, state, from, piece, moves);
    }

    private void addCastlingMoves(Board board, GameState state, Position from, Piece piece, List<Move> moves) {
        int homeRow = piece.getColor() == Color.WHITE ? 7 : 0;

        if (from.getRow() != homeRow || from.getCol() != 4) {
            return;
        }

        if (state.canCastleKingSide(piece.getColor())
                && canCastleThrough(board, piece, homeRow, 5, 6, 7)) {
            moves.add(Move.castling(from, new Position(homeRow, 6)));
        }

        if (state.canCastleQueenSide(piece.getColor())
                && canCastleThrough(board, piece, homeRow, 1, 3, 0)) {
            moves.add(Move.castling(from, new Position(homeRow, 2)));
        }
    }

    private boolean canCastleThrough(Board board, Piece king, int row, int clearFromCol, int clearToCol, int rookCol) {
        Position rookPosition = new Position(row, rookCol);
        Piece rook = board.getPiece(rookPosition);

        if (rook == null || rook.getType() != PieceType.ROOK || rook.getColor() != king.getColor()) {
            return false;
        }

        for (int col = clearFromCol; col <= clearToCol; col++) {
            if (!board.isEmpty(new Position(row, col))) {
                return false;
            }
        }

        return true;
    }

    private void generateQueenMoves(Board board, Position from, Piece piece, List<Move> moves) {
        generateSlidingMoves(board, from, piece, moves, QUEEN_DIRECTIONS);
    }

    private void generateRookMoves(Board board, Position from, Piece piece, List<Move> moves) {
        generateSlidingMoves(board, from, piece, moves, ROOK_DIRECTIONS);
    }

    private void generateBishopMoves(Board board, Position from, Piece piece, List<Move> moves) {
        generateSlidingMoves(board, from, piece, moves, BISHOP_DIRECTIONS);
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

        // Diagonal captures + en passant
        int[] captureCols = {fromCol - 1, fromCol + 1};

        for (int targetCol : captureCols) {
            int targetRow = fromRow + direction;

            if (!isInsideBoard(targetRow, targetCol)) {
                continue;
            }

            Position target = new Position(targetRow, targetCol);
            Piece targetPiece = board.getPiece(target);

            // Normal diagonal capture
            if (targetPiece != null && targetPiece.getColor() != piece.getColor()) {
                if (targetRow == promotionRow) {
                    addPromotionMoves(from, target, moves);
                } else {
                    moves.add(Move.normal(from, target));
                }
            }

            // En passant
            Position enPassantTarget = state.getEnPassantTarget();
            if (enPassantTarget != null && enPassantTarget.equals(target)) {
                moves.add(Move.enPassant(from, target));
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

    private void generateSlidingMoves(Board board, Position from, Piece piece, List<Move> moves, int[][] directions) {
        for (int[] direction : directions) {
            int row = from.getRow() + direction[0];
            int col = from.getCol() + direction[1];

            while (isInsideBoard(row, col)) {
                Position to = new Position(row, col);
                Piece targetPiece = board.getPiece(to);

                if (targetPiece == null) {
                    moves.add(Move.normal(from, to));
                } else {
                    if (targetPiece.getColor() != piece.getColor()) {
                        moves.add(Move.normal(from, to));
                    }
                    break;
                }

                row += direction[0];
                col += direction[1];
            }
        }
    }
}
