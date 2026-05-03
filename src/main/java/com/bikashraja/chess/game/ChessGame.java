package com.bikashraja.chess.game;

import com.bikashraja.chess.engine.MoveGenerator;
import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.Color;
import com.bikashraja.chess.model.GameState;
import com.bikashraja.chess.model.Move;
import com.bikashraja.chess.model.MoveType;
import com.bikashraja.chess.model.Piece;
import com.bikashraja.chess.model.PieceType;
import com.bikashraja.chess.model.Position;

import java.util.List;
import java.util.Objects;

/**
 * Represents an active chess game.
 *
 * <p>{@code ChessGame} coordinates the current board position, rule-related
 * game state, and legal move generation. It acts as the main domain-level
 * entry point for applying moves and querying the current legal moves.</p>
 *
 * <p>This class does not handle user input, board rendering, FEN parsing,
 * or engine search. Those responsibilities belong to the UI, IO, and engine
 * packages.</p>
 */
public class ChessGame {

    private static final Position WHITE_QUEENSIDE_ROOK_START = new Position(7, 0); // a1
    private static final Position WHITE_KINGSIDE_ROOK_START  = new Position(7, 7); // h1
    private static final Position BLACK_QUEENSIDE_ROOK_START = new Position(0, 0); // a8
    private static final Position BLACK_KINGSIDE_ROOK_START  = new Position(0, 7); // h8

    private Board board;
    private GameState gameState;
    private final MoveGenerator moveGenerator;

    /**
     * Creates a chess game with the given board and game state.
     *
     * @param board current board position
     * @param gameState current rule state, including side to move, castling rights,
     *                  and en passant target
     */
    public ChessGame(Board board, GameState gameState) {
        this(board, gameState, new MoveGenerator());
    }

    /**
     * Creates a chess game with an explicitly provided move generator.
     * Useful for testing or replacing move generation logic.
     */
    public ChessGame(Board board, GameState gameState, MoveGenerator moveGenerator) {
        this.board = Objects.requireNonNull(board, "board must not be null");
        this.gameState = Objects.requireNonNull(gameState, "gameState must not be null");
        this.moveGenerator = Objects.requireNonNull(moveGenerator, "moveGenerator must not be null");
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Move> getLegalMoves() {
        return moveGenerator.generateLegalMoves(board, gameState);
    }

    public boolean isLegalMove(Move move) {
        Objects.requireNonNull(move, "move must not be null");

        return getLegalMoves().contains(move);
    }

    public GameStatus getStatus() {
        List<Move> legalMoves = getLegalMoves();

        if (!legalMoves.isEmpty()) {
            return GameStatus.ONGOING;
        }

        Color sideToMove = gameState.getSideToMove();
        boolean inCheck = moveGenerator.isKingInCheck(board, sideToMove);

        return inCheck ? GameStatus.CHECKMATE : GameStatus.STALEMATE;
    }

    /**
     * Applies a legal move to the game.
     *
     * <p>The board is updated using {@link Board#makeMove(Move)}, while rule-related
     * state such as side to move, castling rights, and en passant target is updated
     * separately.</p>
     *
     * @throws IllegalArgumentException if the move is not legal
     */
    public void applyMove(Move move) {
        Objects.requireNonNull(move, "move must not be null");

        if (!isLegalMove(move)) {
            throw new IllegalArgumentException("Illegal move: " + move);
        }

        Board oldBoard = this.board;
        GameState oldGameState = this.gameState;

        Board newBoard = oldBoard.makeMove(move);
        GameState newGameState = updateGameState(oldBoard, oldGameState, move);

        this.board = newBoard;
        this.gameState = newGameState;
    }

    private GameState updateGameState(Board oldBoard, GameState oldState, Move move) {
        Piece movedPiece = oldBoard.getPiece(move.getFrom());
        if (movedPiece == null) {
            throw new IllegalStateException("No piece found on move source square: " + move.getFrom());
        }

        Piece capturedPiece = getCapturedPiece(oldBoard, move, movedPiece);

        boolean whiteKingSide = oldState.canWhiteCastleKingSide();
        boolean whiteQueenSide = oldState.canWhiteCastleQueenSide();
        boolean blackKingSide = oldState.canBlackCastleKingSide();
        boolean blackQueenSide = oldState.canBlackCastleQueenSide();

        Position newEnPassantTarget = null;

        // Update castling rights based on moved piece
        if (movedPiece.getType() == PieceType.KING) {
            if (movedPiece.getColor() == Color.WHITE) {
                whiteKingSide = false;
                whiteQueenSide = false;
            } else {
                blackKingSide = false;
                blackQueenSide = false;
            }
        }

        if (movedPiece.getType() == PieceType.ROOK) {
            Position from = move.getFrom();

            if (from.equals(WHITE_KINGSIDE_ROOK_START)) {
                whiteKingSide = false;
            } else if (from.equals(WHITE_QUEENSIDE_ROOK_START)) {
                whiteQueenSide = false;
            } else if (from.equals(BLACK_KINGSIDE_ROOK_START)) {
                blackKingSide = false;
            } else if (from.equals(BLACK_QUEENSIDE_ROOK_START)) {
                blackQueenSide = false;
            }
        }

        // Update castling rights if rook is captured on its home square
        if (capturedPiece != null && capturedPiece.getType() == PieceType.ROOK) {
            Position captureSquare = getCapturedPiecePosition(move, movedPiece);

            if (captureSquare.equals(WHITE_KINGSIDE_ROOK_START)) {
                whiteKingSide = false;
            } else if (captureSquare.equals(WHITE_QUEENSIDE_ROOK_START)) {
                whiteQueenSide = false;
            } else if (captureSquare.equals(BLACK_KINGSIDE_ROOK_START)) {
                blackKingSide = false;
            } else if (captureSquare.equals(BLACK_QUEENSIDE_ROOK_START)) {
                blackQueenSide = false;
            }
        }

        // Update en passant target
        if (isPawnDoubleStep(movedPiece, move)) {
            int middleRow = (move.getFrom().getRow() + move.getTo().getRow()) / 2;
            int col = move.getFrom().getCol();
            newEnPassantTarget = new Position(middleRow, col);
        }

        // Switch side to move
        Color nextSideToMove = oldState.getSideToMove().opposite();

        return new GameState(
                nextSideToMove,
                whiteKingSide,
                whiteQueenSide,
                blackKingSide,
                blackQueenSide,
                newEnPassantTarget
        );
    }

    private Piece getCapturedPiece(Board oldBoard, Move move, Piece movedPiece) {
        // For en passant, the captured pawn is not on move.getTo()
        if (isEnPassantMove(move, movedPiece)) {
            Position capturedPawnPosition = getEnPassantCapturedPawnPosition(move);
            return oldBoard.getPiece(capturedPawnPosition);
        }

        return oldBoard.getPiece(move.getTo());
    }

    private Position getCapturedPiecePosition(Move move, Piece movedPiece) {
        if (isEnPassantMove(move, movedPiece)) {
            return getEnPassantCapturedPawnPosition(move);
        }
        return move.getTo();
    }

    private boolean isEnPassantMove(Move move, Piece movedPiece) {
        return movedPiece.getType() == PieceType.PAWN
                && move.getMoveType() == MoveType.EN_PASSANT;
    }

    private Position getEnPassantCapturedPawnPosition(Move move) {
        int capturedPawnRow = move.getFrom().getRow();
        int capturedPawnCol = move.getTo().getCol();
        return new Position(capturedPawnRow, capturedPawnCol);
    }

    private boolean isPawnDoubleStep(Piece movedPiece, Move move) {
        return movedPiece.getType() == PieceType.PAWN
                && Math.abs(move.getFrom().getRow() - move.getTo().getRow()) == 2
                && move.getFrom().getCol() == move.getTo().getCol();
    }
}