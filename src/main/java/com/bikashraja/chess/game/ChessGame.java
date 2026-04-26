package com.bikashraja.chess.game;

import com.bikashraja.chess.engine.MoveGenerator;
import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.GameState;
import com.bikashraja.chess.model.Move;

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

    private GameState updateGameState(Board oldBoard, GameState oldGameState, Move move) {
        // TODO
        throw new UnsupportedOperationException("TODO: implement updateGameState");
    }
}